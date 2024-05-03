package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import java.util.Objects;

public class TomlToken {
    
    final TomlReader reader;
    final Comment comment;
    final Entry entry;
    final Table table;
    StringBuilder lineBuilder;
    
    public TomlToken(TomlReader reader) {
        this.reader = reader;
        this.comment = new Comment();
        this.entry = new Entry();
        this.table = new Table();
        this.lineBuilder = new StringBuilder();
    }
    
    private void setBuilding(char c, int index, Root root) throws TomlParsingException {
        if(root.isStartingCharacter(c,index)) root.building=true;
        else if(root.isEndingCharacter(c,index)) root.building=false;
    }
    
    public void step(char c, int index) throws TomlParsingException {
        if(c!='\n') this.lineBuilder.append(c);
        setBuilding(c,index,this.comment);
        if(!stepBuilding(c,index,this.comment)) {
            setBuilding(c,index,this.table);
            if(!stepBuilding(c,index,this.table)) {
                setBuilding(c,index,this.entry);
                stepBuilding(c,index,this.entry);
            }
        }
        if(c=='\n') this.lineBuilder = new StringBuilder();
    }
    
    private boolean stepBuilding(char c, int index, Root root) throws TomlParsingException {
        if(root.building) {
            root.step(c,index);
            return true;
        }
        return false;
    }
    
    abstract static class Root {
        
        StringBuilder builder;
        boolean building;
        
        Root() {
            this.builder = new StringBuilder();
        }
        
        abstract boolean isEndingCharacter(char c, int index) throws TomlParsingException;
        abstract boolean isStartingCharacter(char c, int index) throws TomlParsingException;
        abstract void step(char c, int index) throws TomlParsingException;
    }
    
    static class Comment extends Root { //TODO
        
        @Override boolean isEndingCharacter(char c, int index) {
            return c=='\n';
        }
        
        @Override boolean isStartingCharacter(char c, int index) {
            return c=='#';
        }
        
        @Override void step(char c, int index) {
            this.builder.append(c);
        }
    }
    
    class Table extends Root {
        
        boolean array;
        
        @Override boolean isEndingCharacter(char c, int index) throws TomlParsingException {
            if(c==']') {
                if(this.array) {
                    this.array = false;
                    return false;
                }
                String tableStr = this.builder.append(c).toString();
                TomlReader reader = TomlToken.this.reader;
                if(tableStr.endsWith("]]")) reader.endTableArray(tableStr.substring(2,tableStr.length()-2),index);
                else reader.endTable(tableStr.substring(1,tableStr.length()-1),index);
                return true;
            }
            return false;
        }
        
        @Override boolean isStartingCharacter(char c, int index) {
            boolean start = c=='[';
            if(this.building && start) this.array = true;
            return start;
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(c=='[' || c==']' || TomlParser.isCharTable(c)) this.builder.append(c);
            else throw new TomlParsingException("Illegal table character -> "+TomlToken.this.lineBuilder.toString(),index);
        }
    }
    
    class Entry extends Root {
        
        final Key key;
        final Value value;
        
        Entry() {
            this.key = new Key();
            this.value = new Value();
        }
        
        @Override boolean isEndingCharacter(char c, int index) throws TomlParsingException {
            if(this.key.building && this.key.encapculated=='\0' && c=='=') {
                this.key.built = true;
                return true;
            } else if(this.key.built && this.value.isEndingCharacter(c,index)) {
                this.key.built = false;
                return true;
            }
            return false;
        }
        
        @Override boolean isStartingCharacter(char c, int index) {
            return c=='\'' || c=='"' || TomlParser.isCharKey(c);
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(this.key.built) this.value.step(c,index);
            else this.key.step(c,index);
        }
    }
    
    class Key extends Root {
        
        char encapculated = '\0';
        boolean built;
        
        @Override boolean isEndingCharacter(char c, int index) throws TomlParsingException {
            if(this.encapculated=='\0' && c=='=') {
                TomlToken.this.reader.endKey(this.builder.toString(),index);
                this.builder = new StringBuilder();
                return true;
            }
            return false;
        }
        
        @Override boolean isStartingCharacter(char c, int index) {
            if(c=='\'' || c=='"') {
                this.encapculated = c;
                return true;
            }
            return TomlParser.isCharKey(c);
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(Misc.equalsAny(c,' ','\t')) return;
            if(c=='\n') throw new TomlParsingException("Unassigned key -> "+TomlToken.this.lineBuilder.toString(),index);
            if(c==this.encapculated) this.encapculated = '\0';
        }
    }
    
    class Value extends Root {
        
        ValueType type;
        char previous = '\0';
        
        @Override boolean isEndingCharacter(char c, int index) throws TomlParsingException {
            if(Objects.isNull(this.type)) return true;
            boolean end = false;
            switch(this.type) {
                case ARRAY: {
                    end = c==']';
                    break;
                }
                case BOOLEAN: {
                    end = c=='e';
                    break;
                }
                case INLINE_TABLE: {
                    end = c=='}';
                    break;
                }
                case NUMBER: {
                    end = Character.isWhitespace(c);
                    break;
                }
                case STRING: { //TODO Expand this for ' and '''
                    end = c=='"';
                    break;
                }
            }
            if(end) {
                TomlParser.parseValue(TomlToken.this.reader,this.builder.toString(),
                                      TomlToken.this.lineBuilder.toString(),index);
                this.builder = new StringBuilder();
                if(this.type==ValueType.ARRAY) TomlToken.this.reader.endArray(index);
                this.type = null;
            }
            return end;
        }
        
        @Override boolean isStartingCharacter(char c, int index) throws TomlParsingException {
            switch(c) {
                case '[': {
                    this.type = ValueType.ARRAY;
                    TomlToken.this.reader.startArray(index);
                    return true;
                }
                case 'f':
                case 't': {
                    this.type = ValueType.BOOLEAN;
                    return true;
                }
                case '{': {
                    this.type = ValueType.INLINE_TABLE;
                    return true;
                }
                case '"':
                case '\'': {
                    this.type = ValueType.STRING;
                    return true;
                }
                default: {
                    if(c=='-' || c=='+' || Character.isDigit(c)) {
                        this.type = ValueType.NUMBER;
                        return true;
                    }
                    return false;
                }
            }
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(this.type==ValueType.ARRAY && c==',') {
                TomlParser.parseValue(TomlToken.this.reader,this.builder.toString(),
                                      TomlToken.this.lineBuilder.toString(),index);
                this.builder = new StringBuilder();
            } else {
                if(this.type==ValueType.BOOLEAN && !TomlParser.isCharBool(c))
                    throw new TomlParsingException("Illegal boolean character -> "+TomlToken.this.lineBuilder.toString(),index);
                if(this.type!=ValueType.ARRAY && c=='\n')
                    throw new TomlParsingException("Unterminated value -> "+TomlToken.this.lineBuilder.toString(),index);
                if(this.type==ValueType.NUMBER && this.previous==c && c=='_')
                    throw new TomlParsingException("Bad number format -> "+TomlToken.this.lineBuilder.toString(),index);
                if(c!='_' && (c!='+' || this.builder.length()>0)) this.builder.append(c);
            }
            this.previous = c;
        }
    }
    
    enum ValueType { ARRAY, BOOLEAN, INLINE_TABLE, NUMBER, STRING }
}