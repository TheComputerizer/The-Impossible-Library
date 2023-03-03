package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import com.moandjiezana.toml.Toml;
import mods.thecomputerizer.theimpossiblelibrary.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.util.NetworkUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.file.TomlUtil;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Type storage for toml files separated into table, comments, and blank lines
 */
public class Holder {

    public static final Class<?>[] TOML_VALUES_STRICT = new Class[]{Boolean.class,Long.class,Double.class,List.class,
            String.class, Date.class};

    public static final Class<?>[] TOML_VALUES_CASTABLE = new Class[]{Boolean.class,Long.class,Double.class,List.class,
            String.class,Date.class,Integer.class,Float.class,Character.class};

    private final Toml backing;
    private final List<AbstractType> indexedTypes;
    private final List<VarMatcher> matchers;

    /**
     * Allows the holder to the built created and then built programmatically
     */
    public static Holder makeEmpty() {
        return new Holder();
    }

    public static Holder decoded(PacketBuffer buf) {
        return new Holder(buf);
    }

    public Holder(File tomlFile) throws IOException {
        this(Files.newInputStream(tomlFile.toPath()));
    }

    /**
     * Needs 2 copies of the same input stream for reading in the TOML backing and the custom stuff
     */
    public Holder(InputStream stream) throws IOException {
        ByteArrayOutputStream byteStream = getByteStream(stream);
        InputStream is1 = new ByteArrayInputStream(byteStream.toByteArray());
        InputStream is2 = new ByteArrayInputStream(byteStream.toByteArray());
        this.backing = TomlUtil.get(is1);
        List<String> fileLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is2))) {
            String line = br.readLine();
            while (Objects.nonNull(line)) {
                fileLines.add(line);
                line = br.readLine();
            }
        } catch (Exception ex) {
            LogUtil.logInternal(Level.ERROR, "Unable to index toml file with error {}", ex);
            fileLines = new ArrayList<>();
        } finally {
            stream.close();
            byteStream.close();
            is1.close();
            is2.close();
        }
        this.indexedTypes = new ArrayList<>();
        buildIndex(fileLines);
        this.matchers = new ArrayList<>();
    }

    private Holder() {
        this.backing = null;
        this.indexedTypes = new ArrayList<>();
        this.matchers = new ArrayList<>();
    }

    private Holder(PacketBuffer buf) {
        this.backing = null;
        this.indexedTypes = NetworkUtil.readGenericList(buf,buf1 ->
                TomlPart.getByID(NetworkUtil.readString(buf1)).decode(buf1,null)).stream()
                .sorted(Comparator.comparingInt(AbstractType::getAbsoluteIndex)).collect(Collectors.toList());
        this.matchers = new ArrayList<>();
    }

    private ByteArrayOutputStream getByteStream(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = stream.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        return baos;
    }

    /**
     * Builds the internal indexed map of blank lines, comments, tables, and variables.
     */
    private void buildIndex(List<String> fileLines) {
        int absoluteIndex = 0;
        Table parentTable = null;
        String tabChar = "\t";
        for(int i=0;i<fileLines.size();i++) {
            String line = fileLines.get(i);
            if(line.trim().isEmpty()) {
                int num = 1;
                int i2 = i+1;
                while(i2<fileLines.size()) {
                    String nextLine = fileLines.get(i2);
                    if(nextLine.trim().isEmpty()) num++;
                    else {
                        i = i2-1;
                        break;
                    }
                    i2++;
                }
                BlankLine blank = new BlankLine(absoluteIndex,num);
                this.indexedTypes.add(blank);
                if(Objects.nonNull(parentTable)) parentTable.addItem(blank);
                absoluteIndex++;
            } else {
                if(line.trim().startsWith("#")) {
                    List<String> comments = new ArrayList<>();
                    comments.add(line.substring(line.indexOf('#')+1));
                    int i2 = i+1;
                    while(i2<fileLines.size()) {
                        String nextLine = fileLines.get(i2);
                        if(nextLine.trim().startsWith("#")) comments.add(nextLine.substring(nextLine.indexOf('#')+1));
                        else {
                            i = i2-1;
                            break;
                        }
                        i2++;
                    }
                    Comment comment = new Comment(absoluteIndex,parentTable,comments);
                    this.indexedTypes.add(comment);
                    if(Objects.nonNull(parentTable)) parentTable.addItem(comment);
                    absoluteIndex++;
                } else if(line.contains("=")) {
                    String varName = line.substring(0,line.indexOf('=')).trim();
                    if(varName.isEmpty() || line.substring(line.indexOf('=')+1).isEmpty())
                        LogUtil.logInternal(Level.WARN, "Variable line '{}' was unable to be parsed correctly",line);
                    else {
                        Object value = Objects.isNull(parentTable) ? TomlUtil.genericObject(this.backing,varName) :
                                TomlUtil.genericObject(parentTable.getBacking(),varName);
                        if(Objects.nonNull(value)) {
                            int ind = line.indexOf(varName.charAt(0));
                            String spacing = line.substring(0, Math.max(ind, 0));
                            int level = 1;
                            if(!spacing.isEmpty()) {
                                int tabs = spacing.length() - spacing.replaceAll(tabChar, "").length();
                                String withoutTabs = spacing.replaceAll(tabChar, "");
                                int spaces = (withoutTabs.length() - withoutTabs.replaceAll(" ", "").length() / 4);
                                level = tabs + spaces;
                            }
                            if(Objects.nonNull(parentTable)) {
                                while (level<parentTable.getLevel()) {
                                    parentTable = parentTable.getParent();
                                    if(Objects.isNull(parentTable)) break;
                                }
                            }
                            Variable var = new Variable(absoluteIndex, parentTable, varName, value);
                            this.indexedTypes.add(var);
                            if(Objects.nonNull(parentTable)) parentTable.addItem(var);
                            absoluteIndex++;
                        }
                    }
                } else if(line.contains("[") && line.contains("]")) {
                    boolean multi = line.contains("[[") && line.contains("]]");
                    String[] path = line.replaceAll("\\[", "").replaceAll("]","")
                            .trim().split("\\.");
                    String name = path[path.length-1];
                    boolean badTableName = false;
                    if(path.length==1) parentTable = null;
                    else {
                        parentTable = findClosestParentTable(null, Arrays.copyOfRange(path,0,path.length-1));
                        if(Objects.isNull(parentTable)) badTableName = true;
                    }
                    if(badTableName)
                        LogUtil.logInternal(Level.ERROR, "Could not find parent table(s) for nested table of " +
                                "name {}",TomlUtil.condensePath(path));
                    else {
                        if ((Objects.isNull(parentTable) && (this.backing.containsTable(name) ||
                                        this.backing.containsTableArray(name))) ||
                                Objects.nonNull(parentTable) && (parentTable.getBacking().containsTable(name) ||
                                                parentTable.getBacking().containsTableArray(name))) {
                            int bracketIndex = line.indexOf('[');
                            String spacing = bracketIndex==0 ? "" : line.substring(0, line.indexOf('[') - 1);
                            int level = Objects.isNull(parentTable) ? 1 : parentTable.getLevel()+1;
                            Toml tableBacking;
                            if(multi) {
                                List<Toml> tables = Objects.nonNull(parentTable) ?
                                        parentTable.getBacking().getTables(name) : this.backing.getTables(name);
                                int index = Objects.nonNull(parentTable) ?
                                        parentTable.getTablesByName(name).size() : this.getTablesByName(name).size();
                                tableBacking = tables.get(index);
                            } else tableBacking =  Objects.nonNull(parentTable) ?
                                    parentTable.getBacking().getTable(name) : this.backing.getTable(name);
                            Table table = new Table(absoluteIndex, parentTable, level, name, tableBacking);
                            this.indexedTypes.add(table);
                            if (Objects.nonNull(parentTable)) parentTable.addItem(table);
                            absoluteIndex++;
                            parentTable = table;
                        } else
                            LogUtil.logInternal(Level.ERROR, "Table of name {} was unable to be parsed correctly", name);
                    }
                }
            }
        }
        indexTableArrays();
        sortIndex();
    }

    /**
     * Returns null if nothing is found.
     */
    private Table findClosestParentTable(@Nullable Table parentTable, String ... path) {
        List<Table> foundTables = Objects.isNull(parentTable) ? getTablesByName(path[0]) :
                parentTable.getTablesByName(path[0]);
        if(foundTables.isEmpty()) return null;
        Table closest = foundTables.stream().max(Comparator.comparing(Table::getAbsoluteIndex)).get();
        if(path.length==1) return closest;
        return findClosestParentTable(closest,Arrays.copyOfRange(path,1,path.length));
    }

    private void indexTableArrays() {
        for(String name : getTableNames()) {
            List<Table> tables = getTablesByName(name);
            if(tables.size()>1) {
                int arrIndex = 0;
                for(Table table : tables) {
                    table.setArrayIndex(arrIndex);
                    arrIndex++;
                }
            } else tables.get(0).setArrayIndex(-1);
        }
    }

    public void addMatcher(VarMatcher matcher) {
        this.matchers.add(matcher);
    }

    public boolean matches(Variable var) {
        for(VarMatcher matcher : this.matchers)
            if(!matcher.matches(var.getName(),var.get()))
                return false;
        return true;
    }

    /**
     * Gets all top-level tables and returns them in the form of an indexed map.
     */
    public HashMap<Integer, Table> getTables() {
        HashMap<Integer, Table> ret = new HashMap<>();
        int i = 0;
        for(AbstractType type : this.indexedTypes) {
            if(type instanceof Table) {
                Table table = (Table)type;
                if(table.isTopLevel()) {
                    ret.put(i, (Table)type);
                    i++;
                }
            }
        }
        return ret;
    }

    /**
     * Returns a list of top-level elements that do not have parent tables. Set includeBlanks to false if you do not
     * care about blank lines and includeComments if you do not care about comments. Ignored types are still a part of
     * the index and will be treated as such.
     */
    public List<AbstractType> getTopLevelTypes(boolean includeBlanks, boolean includeComments) {
        return this.indexedTypes.stream().filter(type -> Objects.isNull(type.parentTable) &&
                (includeBlanks || !(type instanceof BlankLine)) &&
                (includeComments || !(type instanceof Comment))).collect(Collectors.toList());
    }

    /**
     * Gets a generic type from its absolute index.
     */
    public AbstractType getByIndex(int index) {
        return this.indexedTypes.get(index);
    }

    /**
     * Removes a generic type via its absolute index and decrements the indices of the rest of the types as needed
     */
    public void removeIndex(int index) {
        remove(getByIndex(index));
    }

    /**
     * Internal method used when adding types to the index for finding, allocating, and returning the next absolute
     * index value for the parent table.
     */
    private int nextPotentialIndex(IndexFinder finder) {
        int found = finder.findPotentialIndex(getTopLevelTypes(true,true),this.indexedTypes.size());
        for(int i=found;i<this.indexedTypes.size();i++) this.indexedTypes.get(i).incrementAbsoluteIndex();
        return found;
    }

    /**
     * Removes a generic type from the indexed map and decrements the indices of the rest of the types as needed
     */
    public void remove(AbstractType type) {
        if(Objects.nonNull(type)) {
            this.indexedTypes.remove(type);
            for(int i=0;i<this.indexedTypes.size();i++) {
                AbstractType update = indexedTypes.get(i);
                update.setAbsoluteIndex(i);

            }
            sortIndex();
        }
    }

    private void sortIndex() {
        this.indexedTypes.sort(Comparator.comparing(AbstractType::getAbsoluteIndex));
    }

    /**
     * Removes a table by name given a parent table. Top-level tables will be removed in the case of a null parent table.
     * In the case of table arrays, tableArrayIndex can be used to select which one to remove.
     * tableArrayIndex begins indexing from 0 and can be set to -1 to remove all instances.
     */
    public void removeTable(@Nullable Table parentTable, String name, int tableArrayIndex) {
        if(tableArrayIndex<0) {
            List<Table> foundTables = Objects.isNull(parentTable) ? getTablesByName(name) :
                    parentTable.getTablesByName(name);
            if(!foundTables.isEmpty())
                for (Table table : foundTables) removeTable(table);
            else logBadTableRemove(parentTable,tableArrayIndex,name);
        } else {
            Table toRemove = Objects.isNull(parentTable) ? getTableByName(name, tableArrayIndex) :
                    parentTable.getTableByName(name, tableArrayIndex);
            if (Objects.nonNull(toRemove)) removeTable(toRemove);
            else logBadTableRemove(parentTable,tableArrayIndex,name);
        }
    }

    /**
     * Removes a table by name given a parent table. Top-level tables will be removed in the case of a null parent table.
     * In the case of table arrays, all will be removed
     */
    public void removeTable(@Nullable Table parentTable, String name) {
        removeTable(parentTable, name,-1);
    }

    private void logBadTableRemove(Table parentTable, int arrIndex, String name) {
        boolean top = Objects.nonNull(parentTable);
        LogUtil.logInternal(Level.DEBUG, "Tried to remove nonexistent {}table {}{}{}", top ? "top-level " : "",
                top ? " from parent "+parentTable.getName() : "",arrIndex<0 ? "" : " with array index "+arrIndex,name);
    }

    /**
     * Remove a table if you have the table object
     */
    public void removeTable(@Nonnull Table toRemove) {
        List<AbstractType> tableContents = toRemove.getNestedContents();
        if(!toRemove.isTopLevel()) toRemove.getParent().removeItem(toRemove);
        for (AbstractType type : tableContents) {
            if (Objects.nonNull(type.getParent())) type.getParent().removeItem(type);
            remove(type);
        }
        remove(toRemove);
    }

    /**
     * Adds a table to the end of the index. If the parent table is null, the resulting table will be top-level.
     * If the name already exists at the specified level, the resulting table and all tables with the name that are
     * present will be converted into table arrays if they are not already part of one. Use an IndexFinder if you want
     * to control where in the index the table should be added.
     */
    public Table addTable(@Nullable Table parentTable, String name, IndexFinder finder) {
        boolean hasParent = Objects.nonNull(parentTable);
        List<Table> alreadyPresent = hasParent ? parentTable.getTablesByName(name) : getTablesByName(name);
        if(alreadyPresent.size()==1) alreadyPresent.get(0).setArrayIndex(0);
        Table table = new Table(nextPotentialIndex(finder),parentTable,hasParent ? parentTable.getLevel()+1 : 1,name);
        table.setArrayIndex(alreadyPresent.isEmpty() ? -1 : alreadyPresent.size());
        this.indexedTypes.add(table);
        sortIndex();
        finder.addToPotentialParent(table);
        return table;
    }

    public Table addTable(@Nullable Table parentTable, String name) {
        return addTable(parentTable,name,new IndexFinder(parentTable));
    }

    /**
     * Adds a variable to a table or the top level of the index. The value must be an accepted type, or it will not
     * be accepted. Unaccepted values will return null. Use an IndexFinder if you want to control where in the index the
     * variable should be added.
     */
    public Variable addVariable(@Nullable Table parentTable, String name, Object value, IndexFinder finder) {
        if(GenericUtils.isAnyType(value,TOML_VALUES_CASTABLE)) {
            Variable var = new Variable(nextPotentialIndex(finder), parentTable, name, value);
            this.indexedTypes.add(var);
            sortIndex();
            finder.addToPotentialParent(var);
            return var;
        } else LogUtil.logInternal(Level.WARN,"Unable to add variable of name {} to the toml index with unknown" +
                "or null value type {}",name,Objects.isNull(value) ? "null" : value.getClass().getName());
        return null;
    }

    public Variable addVariable(@Nullable Table parentTable, String name, Object value) {
        return addVariable(parentTable,name,value,new IndexFinder(parentTable));
    }

    /**
     * Utilizes a map to make adding multiple variable elements at once easier. Returns all nonnull variable elements
     * created as a list.
     */
    public List<Variable> addVariableMap(@Nullable Table parentTable, Map<String, ?> varMap, IndexFinder finder) {
        return varMap.entrySet().stream().map(entry -> addVariable(parentTable,entry.getKey(),entry.getValue(),finder))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Variable> addVariableMap(@Nullable Table parentTable, Map<String, ?> varMap) {
        return addVariableMap(parentTable,varMap,new IndexFinder(parentTable));
    }

    /**
     * Add some comment lines. Use an IndexFinder if you want to control where in the index the
     * variable should be added.
     */
    public Comment addComment(@Nullable Table parentTable, List<String> commentLines, IndexFinder finder) {
        Comment comment = new Comment(nextPotentialIndex(finder), parentTable, commentLines);
        this.indexedTypes.add(comment);
        sortIndex();
        finder.addToPotentialParent(comment);
        return comment;
    }

    public Comment addComment(@Nullable Table parentTable, List<String> commentLines) {
        return addComment(parentTable, commentLines, new IndexFinder(parentTable));
    }

    /**
     * Add some blank lines. Use an IndexFinder if you want to control where in the index the
     * variable should be added. Blank lines cannot be under a table.
     */
    public BlankLine andBlank(int lines, IndexFinder finder) {
        BlankLine blank = new BlankLine(nextPotentialIndex(finder), lines);
        this.indexedTypes.add(blank);
        sortIndex();
        return blank;
    }

    public BlankLine andBlank(int lines) {
        return andBlank(lines, new IndexFinder());
    }

    public void moveTable(Table table, IndexFinder finder) {
        moveType(table,finder);
        List<AbstractType> nestedContents = table.getNestedContents();
        nestedContents.sort(Comparator.comparing(AbstractType::getAbsoluteIndex));
        for(AbstractType type : nestedContents) moveType(type,finder);
    }

    public void moveType(AbstractType type, IndexFinder finder) {
        if(Objects.nonNull(type.parentTable)) type.parentTable.removeItem(type);
        remove(type);
        int index = nextPotentialIndex(finder);
        while(type.getAbsoluteIndex()<index) type.incrementAbsoluteIndex();
        while(type.getAbsoluteIndex()>index) type.setAbsoluteIndex(type.getAbsoluteIndex()-1);
        this.indexedTypes.add(type);
        sortIndex();
        finder.addToPotentialParent(type);
    }

    /**
     * Returns all variable objects present under this table as a map of their names and values
     */
    public Map<String, Object> getVarMap() {
        return getVars().stream().collect(Collectors.toMap(Variable::getName,Variable::get));
    }

    /**
     * Checks whether a variable object with the given name exists under this table.
     */
    public boolean hasVar(String name) {
        for(Variable var : getVars())
            if(var.is(name)) return true;
        return false;
    }


    /**
     * Gets a variable object by name or creates a new one with the default value if one does not exist. If the default
     * value is null, no new variable will be created and null will be returned
     */
    public Variable getOrCreateVar(@Nullable Table parent, String name, @Nullable Object defVal) {
        if(Objects.isNull(parent)) {
            Optional<Variable> optional = getVar(name);
            return optional.orElse(addVariable(null, name, defVal));
        }
        Variable var = parent.getOrCreateVar(name,null);
        if(Objects.isNull(var))
            var = addVariable(parent, name, defVal);
        return var;
    }

    /**
     * Returns a list of all top-level table names.
     */
    public List<String> getTableNames() {
        return this.indexedTypes.stream().filter(type -> type instanceof Table)
                .map(type -> (Table)type).filter(Table::isTopLevel).map(Table::getName)
                .distinct().collect(Collectors.toList());
    }

    /**
     * Returns a list of all top-level table with unique names.
     */
    public List<Table> getUniqueTables() {
        return getTableNames().stream().map(this::getTableByName).collect(Collectors.toList());
    }

    /**
     * Returns a list of top-level tables whose name matches the input.
     */
    public List<Table> getTablesByName(String name) {
        return this.indexedTypes.stream().filter(type -> type instanceof Table)
                .map(type -> (Table)type).filter(Table::isTopLevel).filter(table -> table.getName().matches(name))
                .collect(Collectors.toList());
    }

    /**
     * Returns a table with the matching name. In the case of table arrays, tableArrayIndex is used to control which
     * one is selected. If no match is found or an out-of-bounds table index is input it will return null.
     */
    public Table getTableByName(String name, int tableArrayIndex) {
        List<Table> foundTables = getTablesByName(name);
        if(foundTables.isEmpty()) {
            LogUtil.logInternal(Level.ERROR, "Top-level table with name {} did not exist",name);
            return null;
        }
        if(foundTables.size()==1) return foundTables.get(0);
        try {
            if(tableArrayIndex<0) tableArrayIndex = 0;
            return foundTables.get(tableArrayIndex);
        } catch (IndexOutOfBoundsException ex) {
            LogUtil.logInternal(Level.ERROR, "Index {} for table array {} was invalid with error {}",
                    tableArrayIndex,name,ex);
            return null;
        }
    }

    /**
     * Returns a table with the matching name. In the case of table arrays, the first one found will be returned.
     * Returns null if no table is found
     */
    public Table getTableByName(String name) {
        return getTableByName(name,0);
    }

    /**
     * Returns true if the input string is present as a top-level table.
     */
    public boolean hasTable(String name) {
        return getTableNames().contains(name);
    }

    /**
     * This can handle layered tables by using the . separator as TOML formatted tables do. The input path String array
     * should be ordered 0 -> length via parent -> child. In the case of parent tables being a part of table arrays,
     * tableArrayIndices is provided  to control which one is selected for each table in the path that is not the final
     * child table. The ordering of tableArrayIndices should be identical to that of the path. Negative indices will be
     * rounded to 0. Returns an empty list if the table does not exist or if the specified tableArrayIndex is out of bounds
     */
    public List<Table> getTablesByPath(String[] path, int[] tableArrayIndices) {
        if(path.length==1) return getTablesByName(path[0]);
        Table parentTable = getTableByName(path[0], tableArrayIndices[0]);
        if(Objects.nonNull(parentTable)) {
            String[] childPath = Arrays.copyOfRange(path,1,path.length);
            int[] childArrayIndices = Arrays.copyOfRange(tableArrayIndices,1,tableArrayIndices.length);
        }
        LogUtil.logInternal(Level.ERROR, "Top-level table from path {} did not exist",TomlUtil.condensePath(path));
        return null;
    }

    /**
     * This can handle layered tables by using the . separator as TOML formatted tables do. The input path String array
     * should be ordered 0 -> length via parent -> child. In the case of table arrays, tableArrayIndices is used to
     * control which one is selected for each table in the path. The ordering of tableArrayIndices should be identical
     * to that of the path. Negative indices will be rounded to 0.
     * Returns null if the table does not exist or if the specified tableArrayIndex is out of bounds
     */
    public Table getTableByPath(String[] path, int[] tableArrayIndices) {
        List<Table> foundTables = getTablesByPath(path, tableArrayIndices);
        if(foundTables.isEmpty()) return null;
        if(foundTables.size()==1) return foundTables.get(0);
        int tableArrayIndex = tableArrayIndices[tableArrayIndices.length-1];
        try {
            if(tableArrayIndex<0) tableArrayIndex = 0;
            return foundTables.get(tableArrayIndex);
        } catch (IndexOutOfBoundsException ex) {
            LogUtil.logInternal(Level.ERROR, "Index {} for table array at path {} was invalid with error {}",
                    tableArrayIndex,TomlUtil.condensePath(path),ex);
            return null;
        }
    }

    /**
     * Returns all top-level variable objects
     */
    public List<Variable> getVars() {
        return this.indexedTypes.stream().filter(type -> type instanceof Variable && type.isTopLevel())
                .map(type -> (Variable)type).collect(Collectors.toList());
    }

    /**
     * Gets a top-level variable or an empty optional if it does not exist.
     * Does not check the value type, as there are methods to do that within the variable class.
     */
    public Optional<Variable> getVar(String name) {
        for(Variable var : getVars())
            if(var.is(name)) return Optional.of(var);
        return Optional.empty();
    }

    /**
     * Generic method for getting any value type or the default value. The type of the default value is used when getting
     * the value of the variable object and as such it must be an accepted primitive type. If the type is something that
     * can be parsed from a string, you can choose whether to allow that via allowParsing. The strictNumbers input will
     * control whether integer and long values can be read in from floats and doubles and vice versa If the type is a
     * list, you can use listTypes to whitelist classTypes within the list. If the whitelist is empty any type is acceptable.
     * Note that empty lists will automatically fail the type check.
     */
    @SuppressWarnings("unchecked")
    public <T> T getValOrDefault(String name, T defVal, boolean allowParsing, boolean allowToString,
                                 boolean strictNumbers, Class<?> ... listTypes) {
        Optional<Variable> foundVar = getVar(name);
        if(!foundVar.isPresent()) return defVal;
        Variable var = foundVar.get();
        if(defVal instanceof Boolean) return (T) var.getAsBool(allowParsing).orElse((Boolean)defVal);
        if(defVal instanceof String) return (T) var.getAsString(allowToString).orElse((String)defVal);
        if(defVal instanceof Long)
            return strictNumbers ? (T) var.getAsLongStrict().orElse((Long)defVal) :
                    (T) var.getAsLong(allowParsing).orElse((Long)defVal);
        if(defVal instanceof Integer)
            return strictNumbers ? (T) var.getAsIntStrict().orElse((Integer)defVal) :
                    (T) var.getAsInt(allowParsing).orElse((Integer)defVal);
        if(defVal instanceof Double)
            return strictNumbers ? (T) var.getAsDoubleStrict().orElse((Double)defVal) :
                    (T) var.getAsDouble(allowParsing).orElse((Double)defVal);
        if(defVal instanceof Float)
            return strictNumbers ? (T) var.getAsFloatStrict().orElse((Float)defVal) :
                    (T) var.getAsFloat(allowParsing).orElse((Float)defVal);
        if(defVal instanceof Date) return (T) var.getAsDate(allowParsing).orElse((Date)defVal);
        if(defVal instanceof List<?>) return (T)var.getAsList(listTypes).orElse((List<?>)defVal);
        return defVal;
    }

    /**
     * Uses the above method, but allowParsing is true, strictNumbers is false, and there is no list type whitelist
     */
    public <T> T getValOrDefault(String name, T defVal) {
        return getValOrDefault(name, defVal, true, true,false);
    }

    /**
     * Converts the holder object into a list of strings for printing, writing, or sending over a packet.
     * Ensures the index order by using a basic for loop instead of an enhanced one or a stream.
     * toIgnores controls which types will be ignored when building the list
     */
    public List<String> toLines(TomlPart ... toIgnore) {
        List<Class<? extends AbstractType>> ignoredClasses = Arrays.stream(toIgnore).map(TomlPart::getClassType)
                .collect(Collectors.toList());
        return this.indexedTypes.stream().filter(type -> !ignoredClasses.contains(type.getClass()))
                .filter(type -> {
                    if (type instanceof Variable)
                        return runMatcher((Variable) type);
                    return true;
                })
                .map(AbstractType::toLines).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private boolean runMatcher(Variable var) {
        return Objects.nonNull(var.getParent()) ? var.getParent().matches(var) : matches(var);
    }

    public void encode(PacketBuffer buf, boolean withBlanks, boolean withComments) {
        List<AbstractType> topLevels = getTopLevelTypes(withBlanks,withComments).stream().filter(type -> {
            if (type instanceof Variable)
                return runMatcher((Variable) type);
            return true;
        }).collect(Collectors.toList());
        NetworkUtil.writeGenericList(buf,getTopLevelTypes(withBlanks,withComments),(buf1, type) -> type.write(buf1));
    }
}
