package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import java.util.List;
import java.util.StringJoiner;

public class TomlHelper {
    
    /**
     Returns the input table name encapsulated in brackets.
     The array input determines whether single or double brakcets are used.
     */
    public static String tableDef(String name, boolean array) {
        return (array ? "[[" : "[")+name+(array ? "]]" : "]");
    }
    
    /**
     Builds a table path from an ordered list of names.
     Names with illegal characters will automatically be encapsulated in quotes.
     Returns the fully qualified table path encapsulated in brackets.
     The array input determines whether single or double brakcets are used.
     */
    public static String tableDef(List<String> names, boolean array) {
        StringJoiner joiner = new StringJoiner(".");
        for(String name : names) {
            boolean illegalChar = false;
            for(int i=0;i<name.length();i++) {
                if(!TomlParser.isCharTable(name.charAt(i))) {
                    illegalChar = true;
                    break;
                }
            }
            joiner.add(illegalChar ? "\""+name+"\"" : name);
        }
        return tableDef(joiner.toString(),array);
    }
}