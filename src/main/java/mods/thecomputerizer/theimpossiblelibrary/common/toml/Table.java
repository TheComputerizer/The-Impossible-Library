package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import com.moandjiezana.toml.Toml;
import mods.thecomputerizer.theimpossiblelibrary.util.TextUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.file.TomlUtil;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Table extends AbstractType {

    /**
     * The level this table is on. Used for spacing and to handle nested elements.
     */
    private final int level;

    /**
     * The name of this table. When writing, the names of the parent table will be inherited for proper TOML formatting.
     */
    private final String tableName;

    /**
     * The TOML object backing the table makes things easier to parse correctly. This will be null if it is a new table
     * added separately after parsing.
     */
    private final Toml backing;

    /**
     * The TOML holder contents contained within the table
     */
    private final List<AbstractType> contents;

    /**
     * Determines the index of the table array this table is in or -1 if it is not in one.
     */
    private int arrIndex;

    public Table(int absoluteIndex, @Nullable Table parentTable, int level, String tableName) {
        this(absoluteIndex,parentTable,level,tableName,null);
    }

    public Table(int absoluteIndex, @Nullable Table parentTable, int level, String tableName, @Nullable Toml backing) {
        super(absoluteIndex, parentTable);
        this.contents = new ArrayList<>();
        this.level = level;
        this.tableName = tableName;
        this.backing = backing;
        this.arrIndex = -1;
    }

    /**
     * Called when the holder finishes indexing if necessary.
     */
    public void setArrayIndex(int index) {
        this.arrIndex = index;
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

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.tableName;
    }

    public boolean isInArray() {
        return this.arrIndex>=0;
    }

    public void addItem(AbstractType type) {
        this.contents.add(type);
    }

    public void removeItem(AbstractType type) {
        this.contents.remove(type);
    }

    public boolean isTopLevel() {
        return Objects.isNull(this.parentTable);
    }

    /**
     * Gets the contents of this table.
     */
    public List<AbstractType> getContents() {
        return this.contents;
    }

    /**
     * Gets the contents of this table and all child tables under it. Used for removing entire tables.
     */
    public List<AbstractType> getNestedContents() {
        return Stream.of(this.contents, getChildren().values().stream().map(Table::getNestedContents)
                .flatMap(Collection::stream).collect(Collectors.toList())).flatMap(Collection::stream)
                .distinct().collect(Collectors.toList());
    }

    /**
     * If the queried index is inside a child table, this will return the next index either above or below the child
     * depending on the input. In the case that the query is outside the bounds of the table, the above input will
     * determine whether the top or bottom of the table is chosen.
     */
    public int adjustForChildren(int query, boolean above) {
        if(query<=getAbsoluteIndex()) return getAbsoluteIndex()+1;
        int max = getMaxIndex(true,false);
        if(query>max) return max;
        return has(query) ? query : above ? getNextHighest(query,max) : getNextLowest(query);
    }

    /**
     * Internal method used for obtaining the next highest index above a failed query
     */
    private int getNextHighest(int query, int max) {
        List<AbstractType> filteredContents = getContents().stream().filter(type -> type.getAbsoluteIndex()>=query)
                .collect(Collectors.toList());
        return filteredContents.isEmpty() ? max : getMinIndexInternal(filteredContents);
    }

    /**
     * Internal method used for obtaining the next highest index above a failed query
     */
    private int getNextLowest(int query) {
        List<AbstractType> filteredContents = getContents().stream().filter(type -> type.getAbsoluteIndex()<=query)
                .collect(Collectors.toList());
        return filteredContents.isEmpty() ? getAbsoluteIndex()+1 : getMaxIndexInternal(filteredContents);
    }

    /**
     * Returns the element with the greatest absolute index the table contains. Use nested to control whether child
     * tables will be taken into account and aboveChildren to only look for indices above the first child table.
     */
    public int getMaxIndex(boolean nested, boolean aboveChildren) {
        return !aboveChildren ? nested ? getMaxIndexInternal(getNestedContents())+1 : getMaxIndexInternal(getContents()) :
                hasChildren() ? getMinIndexInternal(getContents()) : getMaxIndexInternal(getContents())+1;
    }

    /**
     * Internal call so the big one-liners aren't as hard to read
     */
    private int getMaxIndexInternal(List<AbstractType> contents) {
        if(contents.isEmpty()) return getAbsoluteIndex()+1;
        return Collections.max(contents,Comparator.comparing(AbstractType::getAbsoluteIndex)).getAbsoluteIndex();
    }

    /**
     * Internal call so the big one-liners aren't as hard to read
     */
    private int getMinIndexInternal(List<AbstractType> contents) {
        if(contents.isEmpty()) return getAbsoluteIndex()+1;
        return Collections.min(contents,Comparator.comparing(AbstractType::getAbsoluteIndex)).getAbsoluteIndex();
    }

    public boolean has(AbstractType type) {
        return this.contents.contains(type);
    }

    public boolean has(int absoluteIndex) {
        return this.contents.stream().map(AbstractType::getAbsoluteIndex).collect(Collectors.toList()).contains(absoluteIndex);
    }

    /**
     * Gets all next-level child tables and returns them in the form of an indexed map.
     */
    public HashMap<Integer, Table> getChildren() {
        HashMap<Integer, Table> ret = new HashMap<>();
        int i = 0;
        for(AbstractType type : this.contents) {
            if(type instanceof Table) {
                ret.put(i,(Table)type);
                i++;
            }
        }
        return ret;
    }

    public boolean hasChildren() {
        return !getChildren().isEmpty();
    }

    /**
     * Returns a list of all next-level table names.
     */
    public List<String> getTableNames() {
        return this.contents.stream().filter(type -> type instanceof Table)
                .map(type -> (Table)type).filter(table -> table.parentTable==this).map(Table::getName)
                .distinct().collect(Collectors.toList());
    }

    /**
     * Returns true if the input string is present as a child table.
     */
    public boolean hasTable(String name) {
        return getTableNames().contains(name);
    }

    /**
     * Returns a list of next-level child tables whose name matches the input.
     */
    public List<Table> getTablesByName(String name) {
        return this.contents.stream().filter(type -> type instanceof Table)
                .map(type -> (Table)type).filter(table -> table.parentTable==this)
                .filter(table -> table.getName().matches(name)).collect(Collectors.toList());
    }

    /**
     * Returns a table with the matching name. In the case of table arrays, tableArrayIndex is used to control which
     * one is selected. If no match is found or an out-of-bounds table index is input it will return null.
     */
    public Table getTableByName(String name, int tableArrayIndex) {
        List<Table> foundTables = getTablesByName(name);
        if(foundTables.isEmpty()) {
            LogUtil.logInternal(Level.ERROR, "Child table under {} with name {} did not exist",this.tableName,name);
            return null;
        }
        if(foundTables.size()==1) return foundTables.get(0);
        try {
            if(tableArrayIndex<0) tableArrayIndex = 0;
            return foundTables.get(tableArrayIndex);
        } catch (IndexOutOfBoundsException ex) {
            LogUtil.logInternal(Level.ERROR, "Index {} under table {} for child table array {} was invalid with " +
                            "error {}", tableArrayIndex,this.tableName,name,ex);
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
        LogUtil.logInternal(Level.ERROR, "Child table under {} from path {} did not exist", this.tableName,
                TomlUtil.condensePath(path));
        return null;
    }

    public Toml getBacking() {
        return this.backing;
    }

    @Override
    public String getSpacing() {
        return TextUtil.withTabs("",this.level-1);
    }

    @Override
    public List<String> toLines() {
        return Collections.singletonList(getSpacing()+(this.arrIndex>=0 ? "[[" : "[")
                +inheritTableName(this,this.tableName)+(this.arrIndex>=0 ? "]]" : "]"));
    }

    /**
     * Recursive function to build a TOML formatted table name from parent tables
     */
    private String inheritTableName(Table table, String concat) {
        if(Objects.nonNull(table.parentTable))
            return inheritTableName(table.parentTable,table.parentTable.getName()+"."+concat);
        return concat;
    }
}
