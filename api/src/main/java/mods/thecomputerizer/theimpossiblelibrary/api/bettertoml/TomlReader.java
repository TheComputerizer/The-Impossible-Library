package mods.thecomputerizer.theimpossiblelibrary.api.bettertoml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TomlReader {
    
    private final TomlToken tokenizer;
    private final TableBuilder rootBuilder;
    private final Map<String,TableBuilder> tableBuilders;
    private String currentBuilder;
    
    public TomlReader() {
        this.tokenizer = new TomlToken(this);
        this.rootBuilder = new TableBuilder(false);
        this.tableBuilders = new HashMap<>();
        this.currentBuilder = null;
    }
    
    public void read(String tomlString) throws TomlParsingException {
        this.rootBuilder.pushIndex();
        for(int i=0;i<tomlString.length();i++) this.tokenizer.step(tomlString.charAt(i),i);
    }
    
    public void endArray(int index) throws TomlParsingException {
        if(Objects.isNull(this.currentBuilder)) this.rootBuilder.pushArrayEnd(index);
        else this.tableBuilders.get(this.currentBuilder).pushArrayEnd(index);
    }
    
    public void endBoolean(String unparsed, int index) throws TomlParsingException {
        boolean b = Boolean.parseBoolean(unparsed);
        if(Objects.isNull(this.currentBuilder)) this.rootBuilder.pushValue(b,index);
        else this.tableBuilders.get(this.currentBuilder).pushValue(b,index);
    }
    
    public void endFloat(String unparsed, int index) throws TomlParsingException {
        float f = Float.parseFloat(unparsed);
        if(Objects.isNull(this.currentBuilder)) this.rootBuilder.pushValue(f,index);
        else this.tableBuilders.get(this.currentBuilder).pushValue(f,index);
    }
    
    public void endInt(String unparsed, int index) throws TomlParsingException {
        endInt(Integer.parseInt(unparsed),index);
    }
    
    /**
     Special integer types are parsed before being passed in here
     */
    public void endInt(int i, int index) throws TomlParsingException {
        if(Objects.isNull(this.currentBuilder)) this.rootBuilder.pushValue(i,index);
        else this.tableBuilders.get(this.currentBuilder).pushValue(i,index);
    }
    
    public void endKey(String key, int index) throws TomlParsingException {
        if(Objects.isNull(this.currentBuilder)) this.rootBuilder.pushKey(key,index);
        else this.tableBuilders.get(this.currentBuilder).pushKey(key,index);
    }
    
    public void endString(String value, int index) throws TomlParsingException {
        if(Objects.isNull(this.currentBuilder)) this.rootBuilder.pushValue(value,index);
        else this.tableBuilders.get(this.currentBuilder).pushValue(value,index);
    }
    
    public void endTable(String name, int index) throws TomlParsingException {
        if(this.tableBuilders.containsKey(name))
            throw new TomlParsingException("Table ["+name+"] is defined multiple times without double brackets "+
                                           "[["+name+"]]",index);
        TableBuilder builder = new TableBuilder(false);
        builder.pushIndex();
        this.tableBuilders.put(name,builder);
        this.currentBuilder = name;
    }
    
    public void endTableArray(String name, int index) throws TomlParsingException {
        TableBuilder builder = this.tableBuilders.get(name);
        if(Objects.nonNull(builder)) {
            if(!builder.array)
                throw new TomlParsingException("Table array [["+name+"]] is has already been defined as a single table"+
                                               " ["+name+"]",index);
            builder.pushIndex();
        } else {
            builder = new TableBuilder(true);
            builder.pushIndex();
            this.tableBuilders.put(name,builder);
            this.currentBuilder = name;
        }
    }
    
    public void startArray(int index) throws TomlParsingException {
        if(Objects.isNull(this.currentBuilder)) this.rootBuilder.pushArrayStart(index);
        else this.tableBuilders.get(this.currentBuilder).pushArrayStart(index);
    }
    
    static final class TableBuilder { //TODO recursive table building
        
        private final boolean array;
        private final List<Map<String,Object>> values;
        private List<Object> arrayBuilder;
        private String key;
        private int keyIndex;
        
        TableBuilder(boolean array) {
            this.array = array;
            this.values = new ArrayList<>();
            this.keyIndex = -1;
        }
        
        void pushArrayEnd(int index) throws TomlParsingException {
            if(Objects.isNull(this.arrayBuilder) || Objects.isNull(this.key))
                throw new TomlParsingException("Undefined array",index);
            this.values.get(this.values.size()-1).put(this.key,new ArrayList<>(this.arrayBuilder));
            this.arrayBuilder = null;
            this.key = null;
            this.keyIndex = -1;
        }
        
        void pushArrayStart(int index) throws TomlParsingException {
            if(Objects.nonNull(this.arrayBuilder)) throw new TomlParsingException("Redefined array",index);
            if(Objects.isNull(this.key)) throw new TomlParsingException("Undefined array",index);
            this.arrayBuilder = new ArrayList<>();
        }
        
        void pushIndex() throws TomlParsingException {
            if(Objects.nonNull(this.key)) throw new TomlParsingException("Unassigned key "+this.key,this.keyIndex);
            this.values.add(new HashMap<>());
        }
        
        void pushKey(String key, int index) throws TomlParsingException {
            if(this.values.isEmpty()) throw new TomlParsingException("Uninitialized table ",index);
            if(Objects.nonNull(this.key)) throw new TomlParsingException("Unassigned key "+this.key,this.keyIndex);
            this.key = key;
            this.keyIndex = index;
        }
        
        void pushValue(Object value, int index) throws TomlParsingException {
            if(Objects.isNull(this.key)) throw new TomlParsingException("Undefined value "+value,index);
            if(Objects.nonNull(this.arrayBuilder)) this.arrayBuilder.add(value);
            else {
                this.values.get(this.values.size()-1).put(this.key, value);
                this.key = null;
                this.keyIndex = -1;
            }
        }
    }
}