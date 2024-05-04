package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TomlToken {
    
    final TomlReader reader;
    final Comment comment;
    final Entry entry;
    final Table table;
    StringBuilder lineBuilder;
    int lineNumber = 1;
    
    public TomlToken(TomlReader reader) {
        this.reader = reader;
        this.comment = new Comment();
        this.entry = new Entry();
        this.table = new Table();
        this.lineBuilder = new StringBuilder();
    }
    
    public void finish() throws TomlParsingException { //Build any trailing value and throw if necessary
        if(this.comment.building) this.comment.end(this.lineBuilder.toString(),this.lineNumber,-1);
        if(this.table.building) this.table.end(this.lineBuilder.toString(),this.lineNumber,-1);
        if(this.entry.building) this.entry.end(this.lineBuilder.toString(),this.lineNumber,-1);
        this.lineBuilder = new StringBuilder();
        this.lineNumber = 1;
    }
    
    private void setBuilding(char c, int index, Root root) throws TomlParsingException {
        if(root.checkStart(c,index)) root.building=true;
        else if(root.building) root.checkEnd(c,index);
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
        if(c=='\n') {
            this.lineBuilder = new StringBuilder();
            this.lineNumber++;
        }
    }
    
    private boolean stepBuilding(char c, int index, Root root) throws TomlParsingException {
        if(root.building) {
            root.step(c,index);
            return true;
        }
        return false;
    }
    
    abstract class Root {
        
        final List<StringBuilder> builders;
        char last;
        char beforeLast;
        boolean building;
        char quoting = '\0';
        
        Root() {
            this.builders = new ArrayList<>();
            this.last = '\0';
            this.beforeLast = '\0';
        }
        
        void append(char c) {
            getLastBuilder().append(c);
            pushLast(c);
        }
        
        abstract void checkEnd(char c, int index) throws TomlParsingException;
        abstract boolean checkStart(char c, int index) throws TomlParsingException;
        
        abstract void end(String line, int lineNumber, int index) throws TomlParsingException;
        
        StringBuilder getLastBuilder() {
            return this.builders.get(this.builders.size()-1);
        }
        
        String getLine() {
            return TomlToken.this.lineBuilder.toString();
        }
        
        int getLineNumber() {
            return TomlToken.this.lineNumber;
        }
        
        TomlReader getReader() {
            return TomlToken.this.reader;
        }
        
        boolean isQuoting() {
            return this.quoting=='\'' || this.quoting=='"';
        }
        
        void pushBuild() {
            this.builders.add(new StringBuilder());
        }
        
        void pushLast(char c) {
            this.beforeLast = this.last;
            this.last = c;
        }
        
        void reset() {
            this.builders.clear();
            this.last = '\0';
            this.beforeLast = '\0';
            this.quoting = '\0';
            this.building = false;
            pushBuild();
        }
        
        abstract void step(char c, int index) throws TomlParsingException;
    }
    
    class Comment extends Root {
        
        Comment() {
            pushBuild();
        }
        
        @Override void checkEnd(char c, int index) {
            if(c=='\n') end(getLine(),getLineNumber(),index);
        }
        
        @Override boolean checkStart(char c, int index) {
            return c=='#';
        }
        
        @Override void end(String line, int lineNumber, int index) {
            getReader().endComment(getLastBuilder().toString());
            reset();
        }
        
        @Override void step(char c, int index) {
            if(c!='#' || this.last!='\0') append(c);
            else pushLast(c);
        }
    }
    
    class Table extends Root {
        
        boolean array;
        
        @Override void checkEnd(char c, int index) throws TomlParsingException {
            if(c==']' && (!this.array || this.last==']')) end(getLine(),getLineNumber(),index);
        }
        
        @Override boolean checkStart(char c, int index) {
            return c=='[';
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            TomlParser.parseTable(getReader(),this.array,this.builders,line,lineNumber,index);
            reset();
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            switch(c) {
                case '[': {
                    if(this.last=='\0') return;
                    if(this.beforeLast=='[') TomlParser.doThrow("Illegal table character",getLine(),getLineNumber(),index);
                    if(this.last=='[') this.array = true;
                    break;
                }
                case ']': break;
                case '"':
                case '\'': {
                    append(c);
                    if(this.quoting==c) this.quoting = '\0';
                    else this.quoting=c;
                    return;
                }
                case '.': {
                    pushBuild();
                    break;
                }
                case '\n': TomlParser.doThrow("Unterminated table",getLine(),getLineNumber(),index);
                default: {
                    if(isQuoting() || TomlParser.isCharTable(c)) append(c);
                    else if(Character.isWhitespace(c)) return;
                    else TomlParser.doThrow("Illegal table character",getLine(),getLineNumber(),index);
                    return;
                }
            }
            pushLast(c);
        }
    }
    
    class Entry extends Root {
        
        final Key key;
        final Value value;
        
        Entry() {
            this.key = new Key();
            this.value = new Value(this);
        }
        
        @Override void checkEnd(char c, int index) throws TomlParsingException {
            if(this.key.built) this.value.checkEnd(c,index);
            else this.key.checkEnd(c,index);
        }
        
        @Override boolean checkStart(char c, int index) {
            return this.key.checkStart(c, index);
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            if(!this.key.built) TomlParser.doThrow("Unterminated key",line,lineNumber,index);
            this.value.end(line,lineNumber,index);
            this.key.built = false;
            reset();
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(this.key.built) this.value.step(c,index);
            else this.key.step(c,index);
        }
    }
    
    class Key extends Root {
        
        boolean built;
        
        @Override void checkEnd(char c, int index) throws TomlParsingException {
            if(!isQuoting() && c=='=') end(getLine(),getLineNumber(),index);
        }
        
        @Override boolean checkStart(char c, int index) {
            return c=='\'' || c=='"' || TomlParser.isCharKey(c);
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            TomlParser.parseKey(getReader(),this.builders,line,lineNumber,index);
            this.built = true;
            reset();
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            switch(c) {
                case '"':
                case '\'': {
                    append(c);
                    if(this.quoting==c) this.quoting = '\0';
                    else this.quoting=c;
                    return;
                }
                case '.': {
                    pushBuild();
                    break;
                }
                case '\n': TomlParser.doThrow("Unassigned key",getLine(),getLineNumber(),index);
                default: {
                    if(isQuoting() || TomlParser.isCharKey(c)) append(c);
                    else if(Character.isWhitespace(c)) return;
                    else TomlParser.doThrow("Illegal key character",getLine(),getLineNumber(),index);
                    return;
                }
            }
            pushLast(c);
        }
    }
    
    class Value extends Root {
        
        final Entry parent;
        final List<TomlValue> TYPES;
        TomlValue type;
        
        Value(Entry parent) {
            this.parent = parent;
            this.TYPES = Arrays.asList(
                    new TomlArray(this),new TomlBoolean(this),new TomlInlineTable(this),
                    new TomlNumber(this),new TomlString(this));
        }
        
        @Override void checkEnd(char c, int index) throws TomlParsingException {
            if(this.type.checkValueEnd(this,c,index)) this.parent.end(getLine(),getLineNumber(),index);
        }
        
        @Override boolean checkStart(char c, int index) throws TomlParsingException {
            for(TomlValue value : this.TYPES) {
                if(value.checkStart(c,index)) {
                    this.type = value;
                    return true;
                }
            }
            return false;
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            if(Objects.nonNull(this.type)) this.type.end(line,lineNumber,index);
            this.type = null;
            reset();
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            this.type.step(c,index);
        }
    }
    
    abstract class TomlValue extends Root {
        
        Value parent;
        
        TomlValue(Value parent) {
            this.parent = parent;
        }
        
        @Override void checkEnd(char c, int index) {}
        
        abstract boolean checkValueEnd(Value value, char c, int index) throws TomlParsingException;
    }
    
    class TomlArray extends TomlValue {
        
        TomlValue metaValue;
        
        TomlArray(Value parent) {
            super(parent);
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            if(Objects.nonNull(this.metaValue)) this.metaValue.end(line,lineNumber,index);
            this.metaValue = null;
            getReader().endArray(line,getLineNumber(),index);
            reset();
        }
        
        @Override boolean checkValueEnd(Value value, char c, int index) throws TomlParsingException {
            if(this.metaValue.checkValueEnd(value,c,index)) {
                this.metaValue.end(getLine(),getLineNumber(),index);
                this.metaValue = null;
            }
            return Objects.nonNull(this.metaValue) && c=='\n';
        }
        
        @Override boolean checkStart(char c, int index) throws TomlParsingException {
            if(c=='[') {
                if(Objects.isNull(this.metaValue)) getReader().startArray(getLine(),getLineNumber(),index);
                return true;
            }
            return false;
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(this.last=='\0') pushLast(c);
            else if(Objects.nonNull(this.metaValue)) this.metaValue.step(c,index);
            else {
                for(TomlValue value : this.parent.TYPES) {
                    if(value.checkStart(c,index)) {
                        if(value instanceof TomlArray) this.metaValue = new TomlArray(this.parent);
                        else if(value instanceof TomlBoolean) this.metaValue = new TomlBoolean(this.parent);
                        else if(value instanceof TomlInlineTable) this.metaValue = new TomlInlineTable(this.parent);
                        else if(value instanceof TomlNumber) this.metaValue = new TomlNumber(this.parent);
                        else this.metaValue = new TomlString(this.parent);
                        break;
                    }
                }
                if(Objects.nonNull(this.metaValue)) this.metaValue.step(c,index);
            }
        }
    }
    
    class TomlBoolean extends TomlValue {
        
        TomlBoolean(Value parent) {
            super(parent);
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            getReader().endBoolean(getLastBuilder().toString(),line,lineNumber,index);
            reset();
        }
        
        @Override boolean checkValueEnd(Value value, char c, int index) {
            return c=='\n';
        }
        
        @Override boolean checkStart(char c, int index) {
            return c=='f' || c=='t';
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(TomlParser.isCharBool(c)) append(c);
            else if(!Character.isWhitespace(c)) TomlParser.doThrow("Illegal boolean character",getLine(),getLineNumber(),index);
        }
    }
    
    class TomlInlineTable extends TomlValue {
        
        final Key metaKey;
        final Value metaValue;
        int bracketLevel;
        
        TomlInlineTable(Value parent) {
            super(parent);
            this.metaKey = new Key();
            this.metaValue = new Value(null); //The parent is only used in checkEnd which is skipped in this case
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            if(this.metaKey.building) this.metaKey.end(line,lineNumber,index);
            if(this.metaValue.building) this.metaValue.end(line,lineNumber,index);
            getReader().endInlineTable(getLine(),getLineNumber(),index);
            reset();
        }
        
        @Override boolean checkValueEnd(Value value, char c, int index) throws TomlParsingException {
            if(c=='\n' || (!isQuoting()) && c=='}') {
                end(getLine(),getLineNumber(),index);
                return true;
            }
            if(this.metaKey.building) this.metaKey.checkEnd(c,index);
            return false;
        }
        
        @Override boolean checkStart(char c, int index) throws TomlParsingException {
            if(c=='{') {
                if(this.last=='\0') getReader().startInlineTable(getLine(),getLineNumber(),index);
                return true;
            }
            return false;
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            if(c=='\n') TomlParser.doThrow("Inline tables do not support line breaks",getLine(),getLineNumber(),index);
            if(!this.metaKey.built) {
                if(!this.metaKey.building) this.metaKey.checkStart(c,index);
                if(this.metaKey.building) this.metaKey.step(c,index);
            } else {
                if(!this.metaValue.building) this.metaValue.checkStart(c,index);
                if(this.metaValue.building) this.metaValue.step(c,index);
            }
            switch(c) {
                case '"':
                case '\'': {
                    this.quoting = this.quoting==c ? '\0' : c;
                    break;
                }
                case '[': {
                    if(!isQuoting()) this.bracketLevel++;
                    break;
                }
                case ']': {
                    if(!isQuoting()) {
                        if(this.bracketLevel==0)
                            TomlParser.doThrow("Unexpected closing bracket",getLine(),getLineNumber(),index);
                        else this.bracketLevel--;
                    }
                    break;
                }
                case ',': {
                    if(!isQuoting() && this.bracketLevel==0) {
                        if(this.metaValue.building) {
                            this.metaValue.end(getLine(),getLineNumber(),index);
                            this.metaKey.built = false;
                        } else TomlParser.doThrow("Unassigned key",getLine(),getLineNumber(),index);
                    }
                    break;
                }
            }
        }
    }
    
    class TomlNumber extends TomlValue {
        
        private NumberType type;
        boolean decimal = false;
        boolean e = false;
        
        TomlNumber(Value parent) {
            super(parent);
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            TomlParser.parseNumber(getReader(),getLastBuilder().toString(),this.type,line,lineNumber,index);
            this.type = null;
            reset();
        }
        
        @Override boolean checkValueEnd(Value value, char c, int index) {
            return c=='\n';
        }
        
        @Override boolean checkStart(char c, int index) {
            return TomlParser.isCharNumber(c);
        }
        
        @Override void step(char c, int index) throws TomlParsingException {
            switch(c) {
                case '.': {
                    if(this.last=='\0')
                        TomlParser.doThrow("Illegal first character",getLine(),getLineNumber(),index);
                    if(Objects.nonNull(this.type) && this.type!=NumberType.FLOAT)
                        TomlParser.doThrow("Illegal `.` in non float number",getLine(),getLineNumber(),index);
                    else if(this.decimal)
                        TomlParser.doThrow("Illegal second `.` in number",getLine(),getLineNumber(),index);
                    else {
                        this.type = NumberType.FLOAT;
                        this.decimal = true;
                    }
                    break;
                }
                case '_': {
                    if(this.last=='_')
                        TomlParser.doThrow("Illegal number character (`_` may not be used consecutively)",
                                           getLine(),getLineNumber(),index);
                    pushLast(c);
                    return;
                }
                case '-':
                case '+': {
                    if(this.last=='\0') return; //Ignore first + since positive is implied
                    else if(this.last!='E' && this.last!='e')
                        TomlParser.doThrow("The `"+c+"` character is only allowed to be first, after `e`, or "+
                                           "after `E`",getLine(),getLineNumber(),index);
                    break;
                }
                case 'a': {
                    if(this.last!='n')
                        TomlParser.doThrow("The character `a` may only be used as a number for `nan` values",
                                           getLine(),getLineNumber(),index);
                    break;
                }
                case 'b': {
                    if(this.last!='0')
                        TomlParser.doThrow("The character `b` may only be used as a number for binary values "+
                                           "(0b1111)",getLine(),getLineNumber(),index);
                    this.type = NumberType.BINARY;
                    break;
                }
                case 'e':
                case 'E': {
                    if(this.last=='\0')
                        TomlParser.doThrow("Illegal first character",getLine(),getLineNumber(),index);
                    if(Objects.nonNull(this.type) && this.type!=NumberType.FLOAT)
                        TomlParser.doThrow("Illegal "+c+" in non float number",getLine(),getLineNumber(),index);
                    else if(this.e)
                        TomlParser.doThrow("Illegal second `e` or 'E' in number",getLine(),getLineNumber(),index);
                    else {
                        this.type = NumberType.FLOAT;
                        this.e = true;
                    }
                    break;
                }
                case 'f': {
                    if(this.last!='n')
                        TomlParser.doThrow("The character `f` may only be used as a number for `inf` values",
                                           getLine(),getLineNumber(),index);
                    break;
                }
                case 'i': {
                    if(Misc.equalsAny(c,'\0','-','+')) break;
                    TomlParser.doThrow("The character `i` may only be used as a number for `inf` values",
                                       getLine(),getLineNumber(),index);
                }
                case 'n': {
                    if(Misc.equalsAny(c,'\0','-','+','a','i')) break;
                    TomlParser.doThrow("The character `i` may only be used as a number for `inf` values",
                                       getLine(),getLineNumber(),index);
                }
                case 'o': {
                    if(this.last!='0')
                        TomlParser.doThrow("The character `o` may only be used as a number for octal values "+
                                           "(0o7777)",getLine(),getLineNumber(),index);
                    this.type = NumberType.OCTAL;
                    break;
                }
                case 'x': {
                    if(this.last!='0')
                        TomlParser.doThrow("The character `x` may only be used as a number for hexadecimal values"+
                                           " (0xffff)",getLine(),getLineNumber(),index);
                    this.type = NumberType.HEXADECIMAL;
                    break;
                }
                default: {
                    if(Character.isWhitespace(c)) return;
                    if(!Character.isDigit(c)) TomlParser.doThrow("Illegal character `"+c+"` in number",getLine(),
                                                                 getLineNumber(),index);
                    break;
                }
            }
            append(c);
        }
    }
    
    public enum NumberType{ BINARY, FLOAT, HEXADECIMAL, OCTAL } //Integer is assumed if null
    
    class TomlString extends TomlValue {
        
        boolean multiline;
        
        TomlString(Value parent) {
            super(parent);
        }
        
        @Override void end(String line, int lineNumber, int index) throws TomlParsingException {
            getReader().endString(getLastBuilder().toString(),line,lineNumber,index);
            reset();
        }
        
        @Override boolean checkValueEnd(Value value, char c, int index) {
            return !this.multiline && c=='\n';
        }
        
        @Override boolean checkStart(char c, int index) {
            if(c=='\'' || c=='"') {
                this.quoting = c;
                return true;
            }
            return false;
        }
        
        @Override void step(char c, int index) {
            if(c=='\'') this.multiline = this.last=='\'' && this.beforeLast=='\'' && !this.multiline;
            append(c);
        }
    }
}