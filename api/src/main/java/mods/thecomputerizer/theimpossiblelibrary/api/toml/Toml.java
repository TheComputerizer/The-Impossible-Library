package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.toml.TomlReader.TableBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.io.IOUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.IterableHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 Represents a TOML table. The root is considered a table.
 */
@SuppressWarnings({"unused","UnusedReturnValue"}) public class Toml { //TODO Comments
    
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
        return readString(IOUtils.streamToString(stream),reader);
    }
    
    public static Toml readString(String tomlString) throws TomlParsingException {
        return readString(tomlString,new TomlReader());
    }
    
    public static Toml readString(String tomlString, TomlReader reader) throws TomlParsingException {
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
    
    @Getter private final String name;
    private final Map<String,Toml[]> tables;
    private final Map<String,TomlEntry<?>> entries;
    
    Toml(String name) {
        this(null,name);
    }
    
    Toml(@Nullable TableBuilder builder, String name) {
        this.name = name;
        this.entries = new LinkedHashMap<>();
        this.tables = new LinkedHashMap<>();
        if(Objects.nonNull(builder)) {
            setRootEntries(builder.entries);
            setTables(builder.tables);
        }
    }
    
    Toml(ByteBuf buf) {
        this.name = NetworkHelper.readString(buf);
        this.tables = NetworkHelper.readMapEntries(buf,() -> {
            String name = NetworkHelper.readString(buf);
            return IterableHelper.getMapEntry(name,NetworkHelper.readList(buf,() -> new Toml(buf)).toArray(new Toml[0]));
        });
        this.entries = NetworkHelper.readMapEntries(buf,() -> {
            String key = NetworkHelper.readString(buf);
            return IterableHelper.getMapEntry(key,new TomlEntry<>(key,buf));
        });
    }
    
    public <V> TomlEntry<V> addEntry(String key, V value) {
        TomlEntry<V> entry = new TomlEntry<>(key,value);
        this.entries.put(key,entry);
        return entry;
    }
    
    public Toml addTable(String name, boolean array) throws TomlWritingException {
        Toml toml = new Toml(name);
        Toml[] tomls = this.tables.get(name);
        if(Objects.nonNull(tomls)) {
            if(array) ArrayHelper.append(tomls,toml,true);
            else throw new TomlWritingException("Cannot add table ["+name+"] that already exists");
        } else this.tables.put(name,new Toml[]{toml});
        return toml;
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
    
    public float getValueInt(String name, int defVal) {
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
            for(int i=0;i<tomls.length;i++) tomls[i] = new Toml(tables.get(i),name);
            this.tables.put(name,tomls);
        }
    }
    
    /**
     Set tabs to -1 to disable formatting entirely
     */
    public void write(StringBuilder builder, int tabs) {
        for(TomlEntry<?> entry : this.entries.values()) {
            String entryLine = entry.key+" = "+entry.value.toString();
            builder.append(tabs==-1 ? entryLine : TextHelper.withTabs(entryLine,tabs)).append("\n");
        }
        if(tabs!=-1) builder.append("\n");
        for(Toml[] tomls : this.tables.values()) {
            if(ArrayHelper.isNotEmpty(tomls)) {
                boolean array = tomls.length>1;
                String name = tomls[0].name;
                for(Toml toml : tomls) {
                    String tableName = (array ? "[[" : "[")+toml.name+(array ? "]]" : "]");
                    builder.append(tabs!=-1 ? tableName : TextHelper.withTabs(tableName,tabs)).append("\n");
                    toml.write(builder,tabs==-1 ? -1 : tabs+1);
                    if(tabs!=-1) builder.append("\n");
                }
            }
        }
    }
    
    /**
     Set tabs to -1 to disable formatting entirely
     */
    public void write(Collection<String> lines, int tabs) {
        for(TomlEntry<?> entry : this.entries.values()) {
            String entryLine = entry.key+" = "+entry.value.toString();
            lines.add(tabs==-1 ? entryLine : TextHelper.withTabs(entryLine,tabs));
        }
        if(tabs!=-1) lines.add("");
        for(Toml[] tomls : this.tables.values()) {
            if(ArrayHelper.isNotEmpty(tomls)) {
                boolean array = tomls.length>1;
                String name = tomls[0].name;
                for(Toml toml : tomls) {
                    String tableName = (array ? "[[" : "[")+toml.name+(array ? "]]" : "]");
                    lines.add(tabs!=-1 ? tableName : TextHelper.withTabs(tableName,tabs));
                    toml.write(lines,tabs==-1 ? -1 : tabs+1);
                    if(tabs!=-1) lines.add("");
                }
            }
        }
    }
    
    public void write(ByteBuf buf) {
        NetworkHelper.writeString(buf,this.name);
        NetworkHelper.writeMap(buf,this.tables,key -> NetworkHelper.writeString(buf,key),tomls ->
                NetworkHelper.writeList(buf,Arrays.asList(tomls),toml -> toml.write(buf)));
        NetworkHelper.writeMap(buf,this.entries,key -> NetworkHelper.writeString(buf,key),entry -> entry.write(buf));
    }
    
    @Getter
    public static class TomlEntry<V> {
        
        private final String key;
        private final V value;
        
        private TomlEntry(String key, V value) {
            this.key = key;
            this.value = value;
        }
        
        @SuppressWarnings("unchecked") private TomlEntry(String key, ByteBuf buf) {
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
        }
        
        void write(ByteBuf buf) {
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
        }
    }
}