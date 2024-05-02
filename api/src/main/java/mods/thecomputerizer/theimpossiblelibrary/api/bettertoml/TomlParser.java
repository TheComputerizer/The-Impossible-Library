package mods.thecomputerizer.theimpossiblelibrary.api.bettertoml;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Patterns;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;

public class TomlParser {
    
    public static boolean isBinary(String binary) {
        return isSpecialInt(binary,'b',TomlParser::isCharBinary);
    }
    
    public static boolean isBool(String bool) {
        return "false".equals(bool) || "true".equals(bool);
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
        return Character.isDigit(c) || Character.isAlphabetic(c) || Misc.equalsAny(c,'_','-');
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
    
    public static boolean isHex(String hex) {
        return isSpecialInt(hex,'x',TomlParser::isCharHex);
    }
    
    public static boolean isInt(String intStr) {
        if(StringUtils.isBlank(intStr)) return false;
        if(isBinary(intStr) || isHex(intStr) || isOctal(intStr)) return true;
        for(int i=0;i<intStr.length();i++)
            if(!isCharInt(intStr.charAt(i))) return false;
        return true;
    }
    
    public static boolean isFloat(String floatStr) {
        if(StringUtils.isBlank(floatStr)) return false;
        String special = Misc.equalsAny(floatStr.charAt(0),'+','-') ? floatStr.substring(1) : floatStr;
        if(Misc.equalsAny(special,"inf","nan")) return true;
        for(int i=0;i<floatStr.length();i++)
            if(!isCharFloat(floatStr.charAt(i))) return false;
        return true;
    }
    
    public static boolean isKey(String key) {
        if(StringUtils.isBlank(key)) return false;
        if(key.length()==1) return isCharKey(key.charAt(0));
        if(Misc.equalsAny(key.charAt(0),'"','\'')) return isString(key);
        for(int i=0;i<key.length();i++)
            if(!isCharKey(key.charAt(i))) return false;
        return true;
    }
    
    public static boolean isMultilineString(String str) {
        return StringUtils.isNotBlank(str) && Patterns.isEncapsulatedBy(str,"'''");
    }
    
    public static boolean isNumber(String numStr) {
        return isFloat(numStr) || isInt(numStr);
    }
    
    public static boolean isOctal(String octal) {
        return isSpecialInt(octal, 'o', TomlParser::isCharOctal);
    }
    
    public static boolean isSpecialInt(String intStr, char c, Function<Character,Boolean> charChecker) {
        if(Objects.isNull(intStr) || intStr.length()<3 || intStr.charAt(0)!='0' || intStr.charAt(1)!=c) return false;
        for(int i=2;i<intStr.length();i++)
            if(!charChecker.apply(intStr.charAt(i))) return false;
        return true;
    }
    
    public static boolean isString(String str) {
        return Patterns.isEncapsulatedBy(str,'"') || Patterns.isEncapsulatedBy(str,'\n');
    }
    
    public static boolean isTable(String tableStr) {
        if(Objects.isNull(tableStr)) return false;
        int length = tableStr.length();
        if(length<3 || tableStr.charAt(0)!='[' || tableStr.charAt(length-1)!=']') return false;
        for(int i=1;i<length-1;i++)
            if(!isCharTable(tableStr.charAt(i))) return false;
        return true;
    }
    
    public static boolean isTableArray(String tableStr) {
        if(Objects.isNull(tableStr) || tableStr.length()<5) return false;
        return isTable(tableStr) && tableStr.charAt(1)=='[' && tableStr.charAt(tableStr.length()-2)==']';
    }
    
    public static int parseBinary(String unparsed, String line, int index) throws TomlParsingException {
        try {
            return Integer.parseInt(unparsed.substring(2), 2);
        } catch(NumberFormatException ex) {
            throw new TomlParsingException("Failed to parse binary value `"+ex.getMessage()+" -> "+line,index);
        }
    }
    
    public static int parseHex(String unparsed, String line, int index) throws TomlParsingException {
        try {
            return (int)Long.parseLong(unparsed.substring(2), 16);
        } catch(NumberFormatException ex) {
            throw new TomlParsingException("Failed to parse hex value `"+ex.getMessage()+" -> "+line,index);
        }
    }
    
    public static int parseOctal(String unparsed, String line, int index) throws TomlParsingException {
        try {
            return Integer.parseInt(unparsed.substring(2), 8);
        } catch(NumberFormatException ex) {
            throw new TomlParsingException("Failed to parse octal value `"+ex.getMessage()+" -> "+line,index);
        }
    }
    
    public static void parseValue(TomlReader reader, String unparsed, String line, int index) throws TomlParsingException {
        if(isBool(unparsed)) reader.endBoolean(unparsed,index);
        else if(isString(unparsed)) reader.endString(unparsed,index);
        else if(isMultilineString(unparsed)) reader.endString(unparsed,index);
        else if(isNumber(unparsed)) {
            if(isInt(unparsed)) {
                if(isBinary(unparsed)) reader.endInt(parseBinary(unparsed,line,index),index);
                else if(isHex(unparsed)) reader.endInt(parseHex(unparsed,line,index),index);
                else if(isOctal(unparsed)) reader.endInt(parseOctal(unparsed,line,index),index);
                else reader.endInt(unparsed,index);
            }
            else if(isFloat(unparsed)) reader.endFloat(unparsed,index);
            else throw new TomlParsingException("Unknown number type -> "+line,index);
        } else throw new TomlParsingException("Unknown value type -> "+line,index);
    }
}