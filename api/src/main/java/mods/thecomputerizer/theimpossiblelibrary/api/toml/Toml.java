package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.toml.TomlReader.TableBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.io.IOUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.IterableHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Matching;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Sorting;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

/**
 Represents a TOML table. The root is considered a table.
 */
@SuppressWarnings({"unused","UnusedReturnValue"}) public class Toml {
    
    public static Toml getEmpty() {
        return new Toml("root");
    }
    
    public static Toml readBuf(ByteBuf buf) {
        return new Toml(buf);
    }
    
    public static Toml readFile(File file) throws TomlParsingException, IOException {
        return readFile(file,new TomlReader());
    }
    
    public static Toml readFile(File file, TomlReader reader) throws IOException, TomlParsingException {
        try(FileInputStream stream = new FileInputStream(file)) { //Close the stream without catching the exception
            return readStream(stream,reader);
        }
    }
    
    public static Toml readFile(String filePath) throws TomlParsingException, IOException {
        return readFile(filePath,new TomlReader());
    }
    
    public static Toml readFile(String filePath, TomlReader reader) throws TomlParsingException, IOException {
        try(FileInputStream stream = new FileInputStream(filePath)) { //Close the stream without catching the exception
            TILDev.logInfo("Reading toml from file path {}",filePath);
            return readStream(stream,reader);
        }
    }
    
    public static Toml readLines(Iterable<String> lines) throws TomlParsingException {
        return readLines(lines,new TomlReader());
    }
    
    public static Toml readLines(Iterable<String> lines, TomlReader reader) throws TomlParsingException {
        return readString(TextHelper.fromIterable(lines),reader);
    }
    
    public static Toml readPath(Path path, OpenOption ... options) throws TomlParsingException, IOException {
        return readPath(path,new TomlReader(),options);
    }
    
    public static Toml readPath(Path path, TomlReader reader, OpenOption ... options)
            throws IOException, TomlParsingException {
        try(InputStream stream = Files.newInputStream(path,options)) { //Close the stream without catching the exception
            return readStream(stream,reader);
        }
    }
    
    public static Toml readStream(InputStream stream) throws IOException, TomlParsingException {
        return readStream(stream,new TomlReader());
    }
    
    public static Toml readStream(InputStream stream, TomlReader reader) throws IOException, TomlParsingException {
        TILDev.logInfo("Reading toml from input stream of type {}",stream.getClass());
        return readString(IOUtils.streamToString(stream),reader);
    }
    
    public static Toml readString(String tomlString) throws TomlParsingException {
        return readString(tomlString,new TomlReader());
    }
    
    public static Toml readString(String tomlString, TomlReader reader) throws TomlParsingException {
        TILDev.logInfo("Reading toml from input string\n{}",tomlString);
        reader.read(tomlString);
        return new Toml(reader.rootBuilder,"root");
    }
    
    public static Toml readURI(URI uri, OpenOption ... options) throws TomlParsingException, IOException {
        return readURI(uri,new TomlReader(),options);
    }
    
    public static Toml readURI(URI uri, TomlReader reader, OpenOption ... options)
            throws TomlParsingException, IOException {
        return readPath(Paths.get(uri),reader,options);
    }
    
    public static Toml readURL(URL url) throws TomlParsingException, IOException {
        return readURL(url,new TomlReader());
    }
    
    public static Toml readURL(URL url, TomlReader reader) throws IOException, TomlParsingException {
        try(InputStream stream = url.openStream()) { //Close the stream without catching the exception
            return readStream(stream,reader);
        }
    }
    
    @Getter private String name;
    private final Map<String,Toml[]> tables;
    private final Map<String,TomlEntry<?>> entries;
    private String[] comments; //Table comments only - entry comments are under the TableEntry class
    @Getter private Toml parent;
    @Setter @Getter private Sorting[] sorters; //TODO Make this functional
    
    Toml(String name) {
        this(null,name);
    }
    
    Toml(@Nullable TableBuilder builder, String name) {
        this.name = name;
        this.entries = new LinkedHashMap<>();
        this.tables = new LinkedHashMap<>();
        this.comments = new String[]{};
        if(Objects.nonNull(builder)) {
            setRootEntries(builder.entries);
            setTables(builder.tables);
            setComments(builder.tableComments,builder.entryComments);
        }
    }
    
    Toml(ByteBuf buf) {
        this.name = NetworkHelper.readString(buf);
        boolean comments = buf.readBoolean();
        this.tables = NetworkHelper.readMapEntries(buf,() -> {
            String name = NetworkHelper.readString(buf);
            return IterableHelper.getMapEntry(name,NetworkHelper.readList(buf,() -> {
                Toml table = new Toml(buf);
                table.parent = this;
                return table;
            }).toArray(new Toml[0]));
        });
        this.entries = NetworkHelper.readMapEntries(buf,() -> {
            String key = NetworkHelper.readString(buf);
            return IterableHelper.getMapEntry(key,new TomlEntry<>(key,buf,comments));
        });
        this.comments = comments ? NetworkHelper.readList(buf,() ->
                NetworkHelper.readString(buf)).toArray(new String[0]) : new String[]{};
    }
    
    public void addComment(String comment) {
        if(StringUtils.isNotEmpty(comment)) this.comments = ArrayHelper.append(this.comments,comment,true);
    }
    
    public void addComments(Iterable<String> comments) {
        for(String comment : comments) addComment(comment);
    }
    
    public void addComments(String ... comments) {
        for(String comment : comments) addComment(comment);
    }
    
    public <V> TomlEntry<V> addEntry(String key, V value) {
        TomlEntry<V> entry = new TomlEntry<>(key,value);
        addEntry(entry);
        return entry;
    }
    
    public <V> void addEntry(@Nullable TomlEntry<V> entry) {
        if(Objects.nonNull(entry)) this.entries.put(entry.getKey(),entry);
    }
    
    public void addEntryComment(String key, String comment) {
        TomlEntry<?> entry = getEntry(key);
        if(Objects.nonNull(entry)) entry.addComment(comment);
        else TILRef.logWarn("Tried to add comment to non existant entry {} (comment -> `{}`)",key,comment);
    }
    
    public void addEntryComments(String key, Iterable<String> comments) {
        TomlEntry<?> entry = getEntry(key);
        if(Objects.nonNull(entry)) {
            for(String comment : comments) entry.addComment(comment);
        } else TILRef.logWarn("Tried to add {} comments to non existant entry {}",IterableHelper.count(comments),key);
    }
    
    public void addEntryComments(String key, String ... comments) {
        TomlEntry<?> entry = getEntry(key);
        if(Objects.nonNull(entry)) {
            for(String comment : comments) entry.addComment(comment);
        } else TILRef.logWarn("Tried to add {} comments to non existant entry {}",comments.length,key);
    }
    
    public Toml addTable(String name, boolean array) throws TomlWritingException {
        Toml toml = new Toml(name);
        Toml[] tomls = this.tables.get(name);
        if(Objects.nonNull(tomls)) {
            if(array) ArrayHelper.append(tomls,toml,true);
            else throw new TomlWritingException("Cannot add table ["+name+"] that already exists");
        } else this.tables.put(name,new Toml[]{toml});
        toml.parent = this;
        return toml;
    }
    
    /**
     Renames the input table and adds it
     */
    public void addTable(String name, Toml table) {
        table.name = name;
        this.tables.put(name,ArrayHelper.append(this.tables.get(name),table,true));
        table.parent = this;
    }
    
    public void clearAllComments() {
        clearComments();
        clearAllEntryComments();
    }
    
    public void clearAllEntryComments() {
        for(TomlEntry<?> entry : this.entries.values()) entry.clearComments();
    }
    
    public void clearAnyCommentsMatching(String toMatch, Matching ... matchers) {
        clearCommentsMatching(toMatch,matchers);
        for(TomlEntry<?> entry : this.entries.values()) entry.clearCommentsMatching(toMatch,matchers);
    }
    
    public void clearComments() {
        this.comments = new String[]{};
    }
    
    public void clearCommentsMatching(String toMatch, Matching ... matchers) {
        this.comments = ArrayHelper.removeMatching(this.comments,toMatch,comment ->
                Matching.matchesAny(comment,toMatch,matchers));
    }
    
    public void clearEntryCommentsMatching(String key, String toMatch, Matching ... matchers) {
        TomlEntry<?> entry = getEntry(key);
        if(Objects.nonNull(entry)) entry.clearCommentsMatching(toMatch,matchers);
    }
    
    public void clearEntryComments(String key) {
        TomlEntry<?> entry = getEntry(key);
        if(Objects.nonNull(entry)) entry.clearComments();
    }
    
    public Collection<TomlEntry<?>> getAllEntries() {
        return this.entries.values();
    }
    
    public List<Toml> getAllTables() {
        List<Toml> tables = new ArrayList<>();
        for(Toml[] tomls : this.tables.values()) tables.addAll(Arrays.asList(tomls));
        return Collections.unmodifiableList(tables);
    }
    
    public TomlEntry<?> getEntry(String name) {
        return this.entries.getOrDefault(name,null);
    }
    
    @SuppressWarnings("unchecked") public TomlEntry<List<?>> getEntryArray(String name) {
        TomlEntry<?> entry = getEntry(name);
        return Objects.nonNull(entry) && entry.value instanceof List<?> ? (TomlEntry<List<?>>)entry : null;
    }
    
    @SuppressWarnings("unchecked") public TomlEntry<Boolean> getEntryBool(String name) {
        TomlEntry<?> entry = getEntry(name);
        return Objects.nonNull(entry) && entry.value instanceof Boolean ? (TomlEntry<Boolean>)entry : null;
    }
    
    @SuppressWarnings("unchecked") public TomlEntry<Float> getEntryFloat(String name) {
        TomlEntry<?> entry = getEntry(name);
        return Objects.nonNull(entry) && entry.value instanceof Float ? (TomlEntry<Float>)entry : null;
    }
    
    @SuppressWarnings("unchecked") public TomlEntry<Integer> getEntryInt(String name) {
        TomlEntry<?> entry = getEntry(name);
        return Objects.nonNull(entry) && entry.value instanceof Integer ? (TomlEntry<Integer>)entry : null;
    }
    
    @SuppressWarnings("unchecked") public TomlEntry<Number> getEntryNumber(String name) {
        TomlEntry<?> entry = getEntry(name);
        return Objects.nonNull(entry) && entry.value instanceof Number ? (TomlEntry<Number>)entry : null;
    }
    
    @SuppressWarnings("unchecked") public TomlEntry<String> getEntryString(String name) {
        TomlEntry<?> entry = getEntry(name);
        return Objects.nonNull(entry) && entry.value instanceof String ? (TomlEntry<String>)entry : null;
    }
    
    public Map<String,Object> getEntryValuesAsMap() {
        Map<String,Object> map =  new LinkedHashMap<>(); //Preserve insertion order
        for(TomlEntry<?> entry : this.entries.values()) map.put(entry.key,entry.value);
        return map;
    }
    
    public <V> V getOrSetValue(String key, V def) {
        return hasEntry(key) ? getValue(key) : addEntry(key,def).value;
    }
    
    /**
     Returns the fully qualified path of this table including all non-root parent tables.
     */
    public String getPath() {
        if("root".equals(this.name)) return "";
        String path = Objects.nonNull(this.parent) ? this.parent.getPath() : "";
        return path.isEmpty() ? this.name : path+"."+this.name;
    }
    
    public Toml getTable(String name) {
        Toml[] tomls = this.tables.get(name);
        return ArrayHelper.isNotEmpty(tomls) ? tomls[0] : null;
    }
    
    public Toml[] getTableArray(String name) {
        return this.tables.get(name);
    }
    
    @SuppressWarnings("unchecked") public <T> T getValue(String name) {
        TomlEntry<T> entry = (TomlEntry<T>)getEntry(name);
        return Objects.nonNull(entry) ? entry.value : null;
    }
    
    public List<?> getValueArray(String name) {
        TomlEntry<List<?>> entry = getEntryArray(name);
        return Objects.nonNull(entry) ? entry.value : null;
    }
    
    public boolean getValueBool(String name, boolean defVal) {
        TomlEntry<Boolean> entry = getEntryBool(name);
        return Objects.nonNull(entry) ? entry.value : defVal;
    }
    
    public byte getValueByte(String name, byte defVal) {
        Number number = getValueNumber(name);
        return Objects.nonNull(number) ? number.byteValue() : defVal;
    }
    
    public double getValueDouble(String name, double defVal) {
        Number number = getValueNumber(name);
        return Objects.nonNull(number) ? number.doubleValue() : defVal;
    }
    
    public float getValueFloat(String name, float defVal) {
        TomlEntry<Float> entry = getEntryFloat(name);
        return Objects.nonNull(entry) ? entry.value : defVal;
    }
    
    public int getValueInt(String name, int defVal) {
        TomlEntry<Integer> entry = getEntryInt(name);
        return Objects.nonNull(entry) ? entry.value : defVal;
    }
    
    public long getValueLong(String name, long defVal) {
        Number number = getValueNumber(name);
        return Objects.nonNull(number) ? number.longValue() : defVal;
    }
    
    public Number getValueNumber(String name) {
        TomlEntry<Number> entry = getEntryNumber(name);
        return Objects.nonNull(entry) ? entry.value: null;
    }
    
    public short getValueShort(String name, short defVal) {
        Number number = getValueNumber(name);
        return Objects.nonNull(number) ? number.shortValue() : defVal;
    }
    
    public String getValueString(String name) {
        TomlEntry<String> entry = getEntryString(name);
        return Objects.nonNull(entry) ? entry.value : null;
    }
    
    public boolean hasEntry(String name) {
        return this.entries.containsKey(name);
    }
    
    public boolean hasTable(String name) {
        return this.tables.containsKey(name);
    }
    
    /**
     Removes all tables with the given name regardless of whether they are singluar or in an array
     */
    public void removeTables(String name) {
        removeTable(name,-1);
    }
    
    /**
     Set index to -1 to remove all elements in a table array with the input name
     */
    public void removeTable(String name, int index) {
        if(index==-1) this.tables.remove(name);
        else {
            Toml[] tomls = ArrayHelper.removeElement(this.tables.get(name),index);
            if(Objects.isNull(tomls)) return;
            if(tomls.length==0) this.tables.remove(name);
            else this.tables.put(name,tomls);
        }
    }
    
    public void removeEntry(String name) {
        this.entries.remove(name);
    }
    
    public void remapTables(String original, String remapped) {
        remapTable(original,remapped,-1);
    }
    
    /**
     Set the index to -1 to remap all tables with the original name
     */
    public void remapTable(String original, String remapped, int index) {
        if(Objects.isNull(remapped)) {
            this.tables.remove(original);
            return;
        }
        Toml[] tables = this.tables.get(original);
        if(Objects.nonNull(tables)) {
            this.tables.remove(original);
            for(Toml table : tables) table.name = remapped;
            this.tables.put(remapped,tables);
        }
    }
    
    void setComments(List<String> comments, Map<String,List<String>> entryComments) {
        this.comments = ArrayHelper.fromIterable(comments,String.class);
        for(Entry<String,List<String>> entryComment : entryComments.entrySet()) {
            TomlEntry<?> entry = this.entries.get(entryComment.getKey());
            if(Objects.nonNull(entry)) entry.setComments(entryComment.getValue());
        }
    }
    
    void setRootEntries(Map<String,Object> entries) {
        for(Entry<String,Object> entry : entries.entrySet()) {
            String key = entry.getKey();
            this.entries.put(key,new TomlEntry<>(key,entry.getValue()));
        }
    }
    
    void setTables(Map<String,List<TableBuilder>> builders) {
        for(Entry<String,List<TableBuilder>> entry : builders.entrySet()) {
            String name = entry.getKey();
            List<TableBuilder> tables = entry.getValue();
            Toml[] tomls = new Toml[tables.size()];
            for(int i=0;i<tomls.length;i++) {
                Toml table = new Toml(tables.get(i),name);
                table.parent = this;
                tomls[i] = table;
            }
            this.tables.put(name,tomls);
        }
    }
    
    /**
     Write this table to a StringBuilder with optional formatting and comments enabled.
     * @param builder A StringBuilder output of the written table
     * @param tabs The number of tabs to use when writing the table. Set -1 to disable formatting entirely
     */
    public void write(StringBuilder builder, int tabs) {
        write(builder,tabs,true);
    }
    
    /**
     Write this table to a StringBuilder with optional formatting.
     * @param builder A StringBuilder output of the written table
     * @param tabs The number of tabs to use when writing the table. Set -1 to disable formatting entirely
     * @param comments Enables the writing of comments
     */
    public void write(StringBuilder builder, int tabs, boolean comments) {
        for(String comment : this.comments)
            builder.append(tabs==-1 ? "#"+comment : TextHelper.withTabs("# "+comment,tabs)).append("\n");
        if(this.comments.length!=0) builder.append("\n");
        List<String> sorted = new ArrayList<>(this.entries.keySet());
        Collections.sort(sorted);
        for(String key : sorted) {
            TomlEntry<?> entry = this.entries.get(key);
            String entryLine = key+" = "+entry.writeValue();
            for(String comment : entry.comments)
                builder.append(tabs==-1 ? "#"+comment : TextHelper.withTabs("# "+comment,tabs)).append("\n");
            builder.append(tabs==-1 ? entryLine : TextHelper.withTabs(entryLine,tabs)).append("\n");
        }
        if("root".equals(this.name) && tabs!=-1 && !sorted.isEmpty()) builder.append("\n");
        sorted = new ArrayList<>(this.tables.keySet());
        Collections.sort(sorted);
        for(String key : sorted) {
            Toml[] tomls = this.tables.get(key);
            if(ArrayHelper.isNotEmpty(tomls)) {
                boolean array = tomls.length>1;
                for(Toml toml : tomls) {
                    String tableName = (array ? "[[" : "[")+toml.getPath()+(array ? "]]" : "]");
                    builder.append(tabs!=-1 ? tableName : TextHelper.withTabs(tableName,tabs)).append("\n");
                    toml.write(builder,tabs==-1 ? -1 : tabs+1,comments);
                    if("root".equals(this.name) && tabs!=-1) builder.append("\n");
                }
            }
        }
    }
    
    /**
     Write this table to a collection of strings with optional formatting and comments enabled.
     * @param lines A collections of strings where each entry is assumed to be a separate line
     * @param tabs The number of tabs to use when writing the table. Set -1 to disable formatting entirely
     */
    public void write(Collection<String> lines, int tabs) {
        write(lines,tabs,true);
    }
    
    /**
     Write this table to a collection of strings with optional formatting.
     * @param lines A collections of strings where each entry is assumed to be a separate line
     * @param tabs The number of tabs to use when writing the table. Set -1 to disable formatting entirely
     * @param comments Enables the writing of comments
     */
    public void write(Collection<String> lines, int tabs, boolean comments) {
        for(String comment : this.comments)
            lines.add(tabs==-1 ? "#"+comment : TextHelper.withTabs("# "+comment,tabs));
        if(this.comments.length!=0 && tabs!=-1) lines.add("");
        List<String> sorted = new ArrayList<>(this.entries.keySet());
        Collections.sort(sorted);
        for(String key : sorted) {
            TomlEntry<?> entry = getEntry(key);
            String entryLine = key+" = "+entry.writeValue();
            for(String comment : entry.comments)
                lines.add(tabs==-1 ? "#"+comment : TextHelper.withTabs("# "+comment,tabs));
            lines.add(tabs==-1 ? entryLine : TextHelper.withTabs(entryLine,tabs));
        }
        if("root".equals(this.name) && tabs!=-1 && !sorted.isEmpty()) lines.add("");
        sorted = new ArrayList<>(this.tables.keySet());
        Collections.sort(sorted);
        for(String key : sorted) {
            Toml[] tomls = this.tables.get(key);
            if(ArrayHelper.isNotEmpty(tomls)) {
                boolean array = tomls.length>1;
                for(Toml toml : tomls) {
                    String tableName = (array ? "[[" : "[")+toml.getPath()+(array ? "]]" : "]");
                    lines.add(tabs==-1 ? tableName : TextHelper.withTabs(tableName,tabs));
                    toml.write(lines,tabs==-1 ? -1 : tabs+1,comments);
                    if("root".equals(this.name) && tabs!=-1) lines.add("");
                }
            }
        }
    }
    
    /**
     Write this table to a ByteBuf with comments disabled.
     * @param buf The buffer to be written
     */
    public void write(ByteBuf buf) {
        write(buf,false);
    }
    
    /**
     Write this table to a ByteBuf with the option of enabling comments to be written.
     * @param buf The buffer to be written
     * @param comments Enables the writing of comments
     */
    public void write(ByteBuf buf, boolean comments) {
        NetworkHelper.writeString(buf,this.name);
        buf.writeBoolean(comments);
        NetworkHelper.writeMap(buf,this.tables,key -> NetworkHelper.writeString(buf,key),tomls ->
                NetworkHelper.writeList(buf,Arrays.asList(tomls),toml -> toml.write(buf)));
        NetworkHelper.writeMap(buf,this.entries,key -> NetworkHelper.writeString(buf,key),
                               entry -> entry.write(buf,comments));
        if(comments) NetworkHelper.writeList(buf,Arrays.asList(this.comments),c -> NetworkHelper.writeString(buf,c));
    }
    
    @Getter
    public static class TomlEntry<V> {
        
        private final String key;
        private final V value;
        private String[] comments;
        
        public TomlEntry(String key, V value) {
            this.key = key;
            this.value = value;
            this.comments = new String[]{};
        }
        
        @SuppressWarnings("unchecked") private TomlEntry(String key, ByteBuf buf, boolean comments) {
            this.key = key;
            String type = NetworkHelper.readString(buf);
            switch(type) {
                case "bool": {
                    this.value = (V)(Boolean)buf.readBoolean();
                    break;
                }
                case "float": {
                    this.value = (V)(Float)buf.readFloat();
                    break;
                }
                case "int": {
                    this.value = (V)(Integer)buf.readInt();
                    break;
                }
                default: {
                    this.value = (V)NetworkHelper.readString(buf);
                    break;
                }
            }
            this.comments = comments ? NetworkHelper.readList(buf,() ->
                    NetworkHelper.readString(buf)).toArray(new String[0]) : new String[]{};
        }
        
        public void addComment(String comment) {
            if(StringUtils.isNotEmpty(comment)) this.comments = ArrayHelper.append(this.comments,comment,true);
        }
        
        public void clearComments() {
            this.comments = new String[]{};
        }
        
        public void clearCommentsMatching(String toMatch, Matching ... matchers) {
            this.comments = ArrayHelper.removeMatching(this.comments,toMatch,comment ->
                    Matching.matchesAny(comment,toMatch,matchers));
        }
        
        void setComments(List<String> comments) {
            this.comments = ArrayHelper.fromIterable(comments,String.class);
        }
        
        @Override
        public String toString() {
            return "<"+this.key+" = "+writeValue()+">";
        }
        
        void write(ByteBuf buf, boolean comments) {
            if(this.value.getClass().isPrimitive()) {
                if(this.value instanceof Boolean) {
                    NetworkHelper.writeString(buf,"bool");
                    buf.writeBoolean((Boolean)this.value);
                } else if(this.value instanceof Float) {
                    NetworkHelper.writeString(buf,"float");
                    buf.writeFloat((Float)this.value);
                } else {
                    NetworkHelper.writeString(buf,"int");
                    buf.writeFloat((Integer)this.value);
                }
            } else {
                NetworkHelper.writeString(buf,"string");
                NetworkHelper.writeString(buf,this.value.toString());
            }
            if(comments)
                NetworkHelper.writeList(buf,Arrays.asList(this.comments),c -> NetworkHelper.writeString(buf,c));
        }
        
        String writeValue() {
            return writeValue(this.value);
        }
        
        String writeValue(Object value) { //TODO Split long arrays to multiple lines?
            if(value instanceof List<?>) {
                StringJoiner joiner = new StringJoiner(",");
                for(Object element : (List<?>)value) joiner.add(" "+writeValue(element));
                return "["+joiner+" ]";
            }
            return value instanceof String ? "\""+value+"\"" : (Objects.nonNull(value) ? value.toString() : "null");
        }
    }
}