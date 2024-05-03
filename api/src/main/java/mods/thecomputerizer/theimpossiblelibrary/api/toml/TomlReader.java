package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TomlReader {
    
    final TomlToken tokenizer;
    final TableBuilder rootBuilder;
    
    public TomlReader() {
        this.tokenizer = new TomlToken(this);
        this.rootBuilder = new TableBuilder(false);
    }
    
    public void endArray(int index) throws TomlParsingException {
        this.rootBuilder.pushArrayEnd(index);
    }
    
    public void endBoolean(String unparsed, int index) throws TomlParsingException {
        boolean b = Boolean.parseBoolean(unparsed);
        this.rootBuilder.pushValue(b,index);
    }
    
    public void endComment(String comment) {
        this.rootBuilder.pushComment(comment);
    }
    
    public void endFloat(String unparsed, int index) throws TomlParsingException {
        float f = Float.parseFloat(unparsed);
        this.rootBuilder.pushValue(f,index);
    }
    
    public void endInt(String unparsed, int index) throws TomlParsingException {
        endInt(Integer.parseInt(unparsed),index);
    }
    
    /**
     Special integer types are parsed before being passed in here
     */
    public void endInt(int i, int index) throws TomlParsingException {
        this.rootBuilder.pushValue(i,index);
    }
    
    public void endKey(String key, int index) throws TomlParsingException {
        this.rootBuilder.pushKey(key,index);
    }
    
    public void endString(String value, int index) throws TomlParsingException {
        this.rootBuilder.pushValue(value,index);
    }
    
    public void endTable(String name, int index) throws TomlParsingException {
        this.rootBuilder.pushTable(name,index);
    }
    
    public void endTableArray(String name, int index) throws TomlParsingException {
        this.rootBuilder.pushTableArray(name,index);
    }
    
    public void read(String tomlString) throws TomlParsingException {
        if(Objects.isNull(tomlString)) throw new TomlParsingException("Tried to read null TOML string",-1);
        for(int i=0;i<tomlString.length();i++) this.tokenizer.step(tomlString.charAt(i),i);
    }
    
    public void startArray(int index) throws TomlParsingException {
        this.rootBuilder.pushArrayStart(index);
    }
    
    static final class TableBuilder { //TODO recursive table building
        
        final boolean array;
        final Map<String,List<TableBuilder>> tables;
        final Map<String,Object> entries;
        TableBuilder currentTable;
        List<Object> arrayBuilder;
        String key;
        int keyIndex;
        
        TableBuilder(boolean array) {
            this.array = array;
            this.tables = new LinkedHashMap<>();
            this.entries = new LinkedHashMap<>();
            this.keyIndex = -1;
        }
        
        void pushArrayEnd(int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushArrayEnd(index);
            else {
                if(Objects.isNull(this.arrayBuilder) || Objects.isNull(this.key))
                    throw new TomlParsingException("Undefined array", index);
                this.entries.put(this.key,new ArrayList<>(this.arrayBuilder));
                this.arrayBuilder = null;
                this.key = null;
                this.keyIndex = -1;
            }
        }
        
        void pushArrayStart(int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushArrayStart(index);
            else {
                if(Objects.nonNull(this.arrayBuilder)) throw new TomlParsingException("Redefined array", index);
                if(Objects.isNull(this.key)) throw new TomlParsingException("Undefined array", index);
                this.arrayBuilder = new ArrayList<>();
            }
        }
        
        void pushComment(String comment) { //TODO
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushComment(comment);
            else {
            
            }
        }
        
        void pushKey(String key, int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushKey(key,index);
            else {
                if(Objects.nonNull(this.key)) throw new TomlParsingException("Unassigned key "+this.key, this.keyIndex);
                this.key = key;
                this.keyIndex = index;
            }
        }
        
        void pushTable(String path, int index) throws TomlParsingException {
            int dotIndex = path.indexOf('.');
            if(dotIndex==-1) {
                if(this.tables.containsKey(path))
                    throw new TomlParsingException("Table ["+path+"] is defined multiple times without double brackets"+
                                                   " [["+path+"]]",index);
                TableBuilder table = new TableBuilder(false);
                this.currentTable = table;
                this.tables.put(path,new ArrayList<>());
                this.tables.get(path).add(table);
            } else {
                String parent = path.substring(0,dotIndex);
                path = path.substring(dotIndex+1);
                TableBuilder table;
                if(!this.tables.containsKey(parent)) {
                    table = new TableBuilder(false); //TODO This is not always correct
                    this.tables.put(parent,new ArrayList<>());
                    this.tables.get(parent).add(table);
                } else {
                    List<TableBuilder> list = this.tables.get(parent);
                    table = list.get(list.size()-1);
                }
                this.currentTable = table;
                table.pushTable(path,index);
            }
        }
        
        void pushTableArray(String path, int index) throws TomlParsingException {
            int dotIndex = path.indexOf('.');
            if(dotIndex==-1) {
                if(this.tables.containsKey(path) && !this.tables.get(path).get((this.tables.get(path).size()-1)).array)
                    throw new TomlParsingException("Table [["+path+"]] has already been defined as a single table "+
                                                   "["+path+"]",index);
                TableBuilder table = new TableBuilder(true);
                this.currentTable = table;
                this.tables.put(path,new ArrayList<>());
                this.tables.get(path).add(table);
            } else {
                String parent = path.substring(0,dotIndex);
                path = path.substring(dotIndex+1);
                TableBuilder table;
                if(!this.tables.containsKey(parent)) {
                    table = new TableBuilder(true); //TODO This is not always correct
                    this.tables.put(parent,new ArrayList<>());
                    this.tables.get(parent).add(table);
                } else {
                    List<TableBuilder> list = this.tables.get(parent);
                    table = list.get(list.size()-1);
                }
                this.currentTable = table;
                table.pushTableArray(path,index);
            }
        }
        
        void pushValue(Object value, int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushValue(value,index);
            else {
                if(Objects.isNull(this.key)) throw new TomlParsingException("Undefined value "+value, index);
                if(Objects.nonNull(this.arrayBuilder)) this.arrayBuilder.add(value);
                else {
                    this.entries.put(this.key,value);
                    this.key = null;
                    this.keyIndex = -1;
                }
            }
        }
    }
}