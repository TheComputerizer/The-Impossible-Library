package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TomlReader {
    
    final TomlToken tokenizer;
    final TableBuilder rootBuilder;
    
    public TomlReader() {
        this.tokenizer = new TomlToken(this);
        this.rootBuilder = new TableBuilder("root",false);
    }
    
    public void emptyLine() {
        this.rootBuilder.pushEmptyLine();
    }
    
    public void endArray(String line, int lineNumber, int index) throws TomlParsingException {
        this.rootBuilder.pushArrayEnd(line,lineNumber,index);
    }
    
    public void endBoolean(String unparsed, String line, int lineNumber, int index) throws TomlParsingException {
        boolean b = Boolean.parseBoolean(unparsed);
        this.rootBuilder.pushValue(b,line,lineNumber,index);
    }
    
    public void endComment(String comment) {
        this.rootBuilder.pushComment(comment);
    }
    
    public void endFloat(String unparsed, String line, int lineNumber, int index) throws TomlParsingException {
        float f = Float.parseFloat(unparsed);
        this.rootBuilder.pushValue(f,line,lineNumber,index);
    }
    
    public void endInlineTable(String line, int lineNumber, int index) throws TomlParsingException {
        this.rootBuilder.finishInlineTable(line,lineNumber,index);
    }
    
    public void endInt(String unparsed, String line, int lineNumber, int index) throws TomlParsingException {
        endInt(Integer.parseInt(unparsed),line,lineNumber,index);
    }
    
    /**
     Special integer types are parsed before being passed in here
     */
    public void endInt(int i, String line, int lineNumber, int index) throws TomlParsingException {
        this.rootBuilder.pushValue(i,line,lineNumber,index);
    }
    
    public void endKey(List<String> path, String key, String line, int lineNumber, int index) throws TomlParsingException {
        this.rootBuilder.pushKey(path,key,line,lineNumber,index);
    }
    
    public void endString(String value, String line, int lineNumber, int index) throws TomlParsingException {
        this.rootBuilder.pushValue(value,line,lineNumber,index);
    }
    
    public void endTable(List<String> names, String line, int lineNumber, int index) throws TomlParsingException {
        if(Objects.isNull(names) || names.isEmpty())
            TomlParser.doThrow("Tried to push empty table path",line,lineNumber,index);
        this.rootBuilder.pushTable(names,false,line,lineNumber,index);
    }
    
    public void endTableArray(List<String> names, String line, int lineNumber, int index) throws TomlParsingException {
        if(Objects.isNull(names) || names.isEmpty())
            TomlParser.doThrow("Tried to push empty table array path",line,lineNumber,index);
        this.rootBuilder.pushTable(names,true,line,lineNumber,index);
    }
    
    public void read(String tomlString) throws TomlParsingException {
        if(Objects.isNull(tomlString)) throw new TomlParsingException("Tried to read null TOML string",-1);
        TILDev.logDebug("Tokenizing toml string with length of {}",tomlString.length());
        for(int i=0;i<tomlString.length();i++) this.tokenizer.step(tomlString.charAt(i),i);
        this.tokenizer.finish();
    }
    
    public void startArray(String line, int lineNumber, int index) throws TomlParsingException {
        this.rootBuilder.pushArrayStart(line,lineNumber,index);
    }
    
    public void startInlineTable(String line, int lineNumber, int index) throws TomlParsingException {
        this.rootBuilder.pushInlineTable(line,lineNumber,index);
    }
    
    static final class TableBuilder {
        
        final TableBuilder parent;
        final String name;
        final boolean array;
        final Map<String,List<TableBuilder>> tables;
        final Map<String,Object> entries;
        final List<String> tableComments;
        final Map<String,List<String>> entryComments;
        TableBuilder currentTable;
        List<List<Object>> arrayBuilder; //A double list like this can build to any size since this builder follows FIFO
        List<String> currentComments;
        String key;
        int keyIndex;
        
        TableBuilder(String name, boolean array) {
            this(null,name,array);
        }
        
        TableBuilder(@Nullable TableBuilder parent, String name, boolean array) {
            this.parent = parent;
            this.name = name;
            this.array = array;
            this.tables = new LinkedHashMap<>();
            this.entries = new LinkedHashMap<>();
            this.tableComments = new ArrayList<>();
            this.entryComments = new HashMap<>();
            this.keyIndex = -1;
        }
        
        void addComments(@Nullable String key) {
            if(Objects.isNull(this.currentComments)) this.currentComments = new ArrayList<>();
            if(Objects.isNull(key)) this.tableComments.addAll(this.currentComments);
            else {
                if(this.entryComments.containsKey(key)) this.entryComments.get(key).addAll(this.currentComments);
                else this.entryComments.put(key,new ArrayList<>(this.currentComments));
            }
            this.currentComments.clear();
        }
        
        void finishInlineTable(String line, int lineNumber, int index) throws TomlParsingException {
            if(Objects.nonNull(this.arrayBuilder) || Objects.nonNull(this.key))
                TomlParser.doThrow("Unterminated inline table",line,lineNumber,index);
        }
        
        List<String> getPath(@Nullable String name) {
            List<String> names = new ArrayList<>();
            if(Objects.nonNull(name)) names.add(name);
            TableBuilder builder = this;
            while(Objects.nonNull(builder) && !"root".equals(builder.name)) {
                names.add(builder.name);
                builder = builder.parent;
            }
            Collections.reverse(names);
            return names;
        }
        
        void pushArrayEnd(String line, int lineNumber, int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushArrayEnd(line,lineNumber,index);
            else {
                if(Objects.isNull(this.arrayBuilder) || Objects.isNull(this.key))
                    TomlParser.doThrow("Undefined array",line,lineNumber,index);
                if(this.arrayBuilder.size()==1) {
                    this.entries.put(this.key,new ArrayList<>(this.arrayBuilder.get(0)));
                    addComments(this.key);
                    this.arrayBuilder = null;
                    this.key = null;
                    this.keyIndex = -1;
                } else {
                    List<Object> built = this.arrayBuilder.get(this.arrayBuilder.size()-1);
                    this.arrayBuilder.remove(this.arrayBuilder.size()-1);
                    this.arrayBuilder.get(this.arrayBuilder.size()-1).add(built);
                }
            }
        }
        
        void pushArrayStart(String line, int lineNumber, int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushArrayStart(line,lineNumber,index);
            else {
                if(Objects.isNull(this.key)) TomlParser.doThrow("Undefined array",line,lineNumber,index);
                if(Objects.isNull(this.arrayBuilder)) this.arrayBuilder = new ArrayList<>();
                this.arrayBuilder.add(new ArrayList<>());
            }
        }
        
        void pushComment(String comment) {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushComment(comment);
            else {
                if(Objects.isNull(this.currentComments)) this.currentComments = new ArrayList<>();
                this.currentComments.add(comment);
            }
        }
        
        public void pushEmptyLine() {
            if(Objects.nonNull(this.currentComments)) {
                this.tableComments.addAll(this.currentComments);
                this.currentComments.clear();
            }
        }
        
        void pushInlineTable(String line, int lineNumber, int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushInlineTable(line,lineNumber,index);
            else {
                if(Objects.isNull(this.key)) TomlParser.doThrow("Undefined inline table",line,lineNumber,index);
                pushTable(Collections.singletonList(this.key),false,line,lineNumber,index);
            }
        }
        
        void pushKey(List<String> path, String key, String line, int lineNumber, int index) throws TomlParsingException {
            if(path.isEmpty()) {
                if(Objects.nonNull(this.currentTable))
                    this.currentTable.pushKey(Collections.emptyList(),key,line,lineNumber,index);
                else {
                    if(Objects.nonNull(this.key))
                        TomlParser.doThrow("Unassigned key "+this.key,line,lineNumber,this.keyIndex);
                    this.key = key;
                    this.keyIndex = index;
                }
            } else {
                String name = path.get(0);
                if(!this.tables.containsKey(name)) {
                    this.tables.put(name,new ArrayList<>());
                    this.tables.get(name).add(new TableBuilder(this,name,false)); //Only support single table creation from path-based keys
                }
                TableBuilder table = this.tables.get(name).get(this.tables.get(name).size()-1);
                path.remove(name);
                table.pushKey(path,key,line,lineNumber,index);
                setCurrentTable(table);
            }
        }
        
        void pushTable(List<String> names, boolean array, String line, int lineNumber, int index)
                throws TomlParsingException {
            String name = names.get(0);
            TableBuilder table;
            if(names.size()==1) {
                if(!this.tables.containsKey(name)) this.tables.put(name,new ArrayList<>());
                else if(!array || !this.tables.get(name).get(this.tables.get(name).size()-1).array)
                    throwTable(name,array,line,lineNumber,index);
                table = new TableBuilder(this,name,array);
                table.addComments(null);
                this.tables.get(name).add(table);
            }
            else {
                this.tables.putIfAbsent(name,new ArrayList<>());
                List<TableBuilder> builders = this.tables.get(name);
                if(builders.isEmpty()) {
                    table = new TableBuilder(this,name,array);
                    builders.add(table);
                } else table = builders.get(builders.size()-1);
                names.remove(name);
                table.pushTable(names,array,line,lineNumber,index);
            }
            setCurrentTable(table);
        }
        
        void pushValue(Object value, String line, int lineNumber, int index) throws TomlParsingException {
            if(Objects.nonNull(this.currentTable)) this.currentTable.pushValue(value,line,lineNumber,index);
            else {
                if(Objects.isNull(this.key)) TomlParser.doThrow("Undefined value "+value,line,lineNumber,index);
                if(Objects.nonNull(this.arrayBuilder)) this.arrayBuilder.get((this.arrayBuilder.size()-1)).add(value);
                else {
                    this.entries.put(this.key,value);
                    addComments(this.key);
                    this.key = null;
                    this.keyIndex = -1;
                }
            }
        }
        
        void setCurrentTable(TableBuilder table) {
            if(Objects.nonNull(this.currentComments)) {
                if(Objects.isNull(table.currentComments)) table.currentComments = new ArrayList<>();
                table.currentComments.addAll(this.currentComments);
                this.currentComments.clear();
            }
            this.currentTable = table;
        }
        
        void throwTable(String name, boolean array, String line, int lineNumber, int index) throws TomlParsingException {
            List<String> path = getPath(name);
            String exMsg = "Table "+TomlHelper.tableDef(path,array)+" "+(array ?
                    "is defined multiple times without double brackets" :
                    "has already been defined as a single table")+" "+TomlHelper.tableDef(path,!array);
            TomlParser.doThrow(exMsg,line,lineNumber,index);
        }
    }
}