package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class TomlHelper {
    
    /**
     * Builds a table path from an ordered list of names.
     * Names with illegal characters will automatically be encapsulated in quotes.
     * Returns the fully qualified table path encapsulated in brackets.
     * The array input boolean determines whether single or double brackets are used.
     */
    @IndirectCallers
    public static String tableDef(boolean array, String ... names) {
        return tableDef(Arrays.asList(names),array);
    }
    
    /**
     * Builds a table path from an ordered list of names.
     * Names with illegal characters will automatically be encapsulated in quotes.
     * Returns the fully qualified table path encapsulated in brackets.
     * The array input boolean determines whether single or double brackets are used.
     */
    public static String tableDef(List<String> names, boolean array) {
        StringJoiner joiner = new StringJoiner(".");
        for(String name : names) joiner.add(encapsulateTableName(name));
        return encapsulateTablePath(joiner.toString(),array);
    }
    
    
    /**
     * Returns the name of a table that is potentially encapsulated in quotes if an illegal character is found.
     */
    public static String encapsulateTableName(String name) {
        boolean illegalChar = false;
        for(int i=0;i<name.length();i++) {
            if(!TomlParser.isCharTable(name.charAt(i))) {
                illegalChar = true;
                break;
            }
        }
        return illegalChar ? "\""+name+"\"" : name;
    }
    
    /**
     * Encapsulates a fully qualified table path in single or double brackets depending on the input array boolean
     */
    public static String encapsulateTablePath(String path, boolean array) {
        return (array ? "[[" : "[")+path+(array ? "]]" : "]");
    }
}