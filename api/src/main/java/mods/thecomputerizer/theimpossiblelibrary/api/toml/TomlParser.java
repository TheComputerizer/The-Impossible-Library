package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.toml.TomlToken.NumberType;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TomlParser {
    
    static void doThrow(String msg, String line, int lineNumber, int index) throws TomlParsingException {
        throw new TomlParsingException(msg+" -> ["+lineNumber+"] "+line,index);
    }
    
    public static boolean isCharBinary(char c) {
        return Misc.equalsAny(c,'_','0','1');
    }
    
    public static boolean isCharBool(char c) {
        return Misc.equalsAny(c,'a','e','f','l','r','s','t','u');
    }
    
    public static boolean isCharFloat(char c) {
        return isCharInt(c) || Misc.equalsAny(c,'.','a','e','f','i','n');
    }
    
    public static boolean isCharHex(char c) {
        return Character.isDigit(c) || Misc.equalsAny(c,'_','a','b','c','d','e','f','E');
    }
    
    public static boolean isCharInt(char c) {
        return Character.isDigit(c) || Misc.equalsAny(c,'+','_','-');
    }
    
    public static boolean isCharKey(char c) {
        return !Character.isWhitespace(c) && (Character.isDigit(c) || Character.isAlphabetic(c) || Misc.equalsAny(c,'_','-'));
    }
    
    public static boolean isCharNewLine(char c) {
        return !String.valueOf(c).matches("."); //Windows moment
    }
    
    public static boolean isCharNumber(char c) {
        return isCharFloat(c) || isCharInt(c) || isCharSpecialInt(c);
    }
    
    public static boolean isCharOctal(char c) {
        return Misc.equalsAny(c,'_','0','1','2','3','4','5','6','7');
    }
    
    public static boolean isCharSpecialInt(char c) {
        return isCharBinary(c) || isCharHex(c) || isCharOctal(c);
    }
    
    public static boolean isCharTable(char c) {
        return isCharKey(c) || Misc.equalsAny(c,'.');
    }
    
    public static int parseBinary(String unparsed, String line, int lineNumber, int index) throws TomlParsingException {
        try {
            return Integer.parseInt(unparsed.substring(2), 2);
        } catch(NumberFormatException ex) {
            doThrow("Failed to parse binary value `"+ex.getMessage()+"`",line,lineNumber,index);
            return 0; //unreachable
        }
    }
    
    public static int parseHex(String unparsed, String line, int lineNumber, int index) throws TomlParsingException {
        try {
            return (int)Long.parseLong(unparsed.substring(2), 16);
        } catch(NumberFormatException ex) {
            doThrow("Failed to parse hex value `"+ex.getMessage()+"`",line,lineNumber,index);
            return 0; //unreachable
        }
    }
    
    public static void parseKey(TomlReader reader, Collection<StringBuilder> builders, String line, int lineNumber,
            int index) throws TomlParsingException {
        String name = null;
        List<String> path = new ArrayList<>();
        int i = 0;
        for(StringBuilder builder : builders) {
            String built = builder.toString();
            if(Patterns.isEncapsulatedBy(built,'\'') || Patterns.isEncapsulatedBy(built,'"'))
                built = built.substring(1,built.length()-1);
            if(i<builders.size()-1) path.add(built);
            else name = built;
        }
        if(Objects.isNull(name)) doThrow("Failed to parse key",line,lineNumber,index);
        Collections.reverse(path);
        reader.endKey(path,name,line,lineNumber,index);
    }
    
    public static void parseNumber(TomlReader reader, String unparsed, NumberType type, String line, int lineNumber,
            int index) throws TomlParsingException {
        if(Objects.isNull(type)) {
            reader.endInt(unparsed,line,lineNumber,index); //Null type is assumed to be an int
            return;
        }
        switch(type) {
            case BINARY: {
                reader.endInt(parseBinary(unparsed,line,lineNumber,index),line,lineNumber,index);
                break;
            }
            case FLOAT: {
                reader.endFloat(unparsed,line,lineNumber,index);
                break;
            }
            case HEXADECIMAL: {
                reader.endInt(parseHex(unparsed,line,lineNumber,index),line,lineNumber,index);
                break;
            }
            case OCTAL: {
                reader.endInt(parseOctal(unparsed,line,lineNumber,index),line,lineNumber,index);
                break;
            }
            default: doThrow("Unknown number type "+type,line,lineNumber,index); //Unreachable?
        }
    }
    
    public static int parseOctal(String unparsed, String line, int lineNumber, int index) throws TomlParsingException {
        try {
            return Integer.parseInt(unparsed.substring(2), 8);
        } catch(NumberFormatException ex) {
            doThrow("Failed to parse octal value `"+ex.getMessage()+"`",line,lineNumber,index);
            return 0; //unreachable
        }
    }
    
    public static void parseTable(TomlReader reader, boolean array,
            Collection<StringBuilder> builders, String line, int lineNumber, int index) throws TomlParsingException {
        List<String> names = new ArrayList<>();
        for(StringBuilder builder : builders) {
            String built = builder.toString();
            if(Patterns.isEncapsulatedBy(built,'\'') || Patterns.isEncapsulatedBy(built,'"'))
                built = built.substring(1,built.length()-1);
            names.add(built);
        }
        if(array) reader.endTableArray(names,line,lineNumber,index);
        else reader.endTable(names,line,lineNumber,index);
    }
}