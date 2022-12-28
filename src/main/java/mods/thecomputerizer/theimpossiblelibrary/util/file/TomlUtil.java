package mods.thecomputerizer.theimpossiblelibrary.util.file;

import com.moandjiezana.toml.Toml;
import mods.thecomputerizer.theimpossiblelibrary.util.TextUtil;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

//Helper methods for reading toml files because toml is better than json
public class TomlUtil {

    /*
     * Basic helper methods for getting toml instances
     */
    public static Toml get(InputStream stream) {
        return new Toml().read(stream);
    }
    public static Toml get(URI uri) {
        return new Toml().read(new File(uri));
    }
    public static Toml get(Path path) {
        return new Toml().read(new File(path.toUri()));
    }
    public static Toml get(String filePath) {
        return new Toml().read(new File(filePath));
    }
    public static Toml get(File file) {
        return new Toml().read(file);
    }

    /*
     * Makes a toml instance with default values from another instance.
     */
    public static Toml of(Toml toml) {
        return new Toml().read(toml);
    }

    /*
     * Reads in a toml file that was converted to a string, most likely for packet reasons.
     */
    public static Toml parse(String tomlAsString) {
        return new Toml().read(tomlAsString);
    }

    /*
     * Converts a toml instance to a map, for anyone that finds those easier to deal with.
     */
    public static Map<String, Object> toMap(Toml toml) {
        return toml.toMap();
    }

    /*
     * Converts a toml instance to a generic object for the heretics that like json.
     */
    public static <T> T  toType(Toml toml, Class<T> clazz) {
        return toml.to(clazz);
    }

    /*
     * Most basic method to read in a string element (in quotes) from a toml instance.
     */
    public static String readIfExists(Toml toml, String key, String def) {
        return toml.getString(key, def);
    }

    /*
     * To account for users that may be unfamiliar with TOML formatting, any values read in with the following methods
     * can be in quotes like string values, but are not required to be
     */
    public static boolean readIfExists(Toml toml, String key, boolean def) {
        try {
            if(toml.contains(key)) return toml.getBoolean(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"boolean");
        }
        return Boolean.parseBoolean(toml.getString(key, def + ""));
    }

    public static int readIfExists(Toml toml, String key, int def) {
        try {
            if(toml.contains(key)) return (int) (long) toml.getLong(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"long");
        }
        return Integer.parseInt(toml.getString(key, def + ""));
    }

    public static long readIfExists(Toml toml, String key, long def) {
        try {
            if(toml.contains(key)) return toml.getLong(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"long");
        }
        return Long.parseLong(toml.getString(key, def + ""));
    }

    public static float readIfExists(Toml toml, String key, float def) {
        try {
            if(toml.contains(key)) return (float) (double) toml.getDouble(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"float");
        }
        return Float.parseFloat(toml.getString(key, def + ""));
    }

    public static double readIfExists(Toml toml, String key, double def) {
        try {
            if(toml.contains(key)) return toml.getDouble(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"double");
        }
        return Double.parseDouble(toml.getString(key, def + ""));
    }

    /*
     * These are the same as the above methods but are returned as strings to be parsed later. This can make it a bit
     * more streamlined to read larger blocks of data at once by parsing it on demand rather than when it is read in.
     * All of these return the def value if the key is not found.
     */
    public static String sneakyInt(Toml toml, String key, int def) {
        try {
            if(toml.contains(key)) return ""+toml.getLong(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"int");
        }
        return toml.getString(key, def + "");
    }

    public static String sneakyLong(Toml toml, String key, long def) {
        try {
            if(toml.contains(key)) return ""+toml.getLong(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"long");
        }
        return toml.getString(key, def + "");
    }

    public static String sneakyFloat(Toml toml, String key, float def) {
        try {
            if(toml.contains(key)) return ""+toml.getDouble(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"float");
        }
        return toml.getString(key, def + "");
    }

    public static String sneakyDouble(Toml toml, String key, double def) {
        try {
            if(toml.contains(key)) return ""+toml.getDouble(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"double");
        }
        return toml.getString(key, def + "");
    }

    public static String sneakyBool(Toml toml, String key, boolean def) {
        try {
            if(toml.contains(key)) return ""+toml.getBoolean(key);
        } catch (ClassCastException ignored) {
            LogUtil.logInternal(Level.DEBUG,"Key {} was not of type {} and will be read in as a string or " +
                    "default",key,"boolean");
        }
        return toml.getString(key, def + "");
    }

    /*
     * Generic catch all method to read in any accepted type except Date and return it as a string. Only use this if you
     * want to ignore the object type entirely when reading in data. Returns the default value if they key is not found.
     */
    public static String sneakyGeneric(Toml toml, String key, Object def) {
        if(toml.contains(key)) {
            try {
                if (toml.contains(key)) return "" + toml.getLong(key);
            } catch (ClassCastException ignored) {}
            try {
                if (toml.contains(key)) return "" + toml.getDouble(key);
            } catch (ClassCastException ignored) {}
            try {
                if (toml.contains(key)) return "" + toml.getBoolean(key);
            } catch (ClassCastException ignored) {}
        }
        return toml.getString(key,Objects.nonNull(def) ? def.toString() : null);
    }

    /*
     * I am not sure how to handle the quotes here, so this one does not have a fallback for checking whether it has them.
     */
    public static Date readIfExists(Toml toml, String key, Date def) {
        return toml.getDate(key,def);
    }

    /*
     * This method is the easiest way of reading in data stored as a list, but it does not have a quote fallback.
     * Returns the def value if no list is present.
     */
    public static Collection<?> readGenericList(Toml toml, String key, Collection<?> def) {
        if (toml.contains(key)) return toml.getList(key);
        return def;
    }

    /*
     * Reads in a generic list and converts it to a string. This can be used as a quote fallback, but it will need to be
     * parsed afterwards if string is not the desired return type. Returns the string output of the def value if no list
     * is present.
     */
    public static Collection<String> readListAsString(Toml toml, String key, Collection<?> def) {
        if (toml.contains(key)) return toml.getList(key).stream().map(Object::toString).collect(Collectors.toList());
        return Objects.nonNull(def) ? def.stream().map(Objects::toString).collect(Collectors.toList()) : null;
    }

    /*
     * Does the same thing as the above method but with arrays instead of collections
     */
    public static String[] readGenericArray(Toml toml, String key, Object[] def) {
        Collection<String> read = readListAsString(toml,key, Arrays.asList(def));
        return Objects.nonNull(read) ? read.toArray(new String[0]) : null;
    }

    /*
     * All the following methods return arrays of specific types read in from a toml list. The toml lists can either
     * have all elements in quotes or no elements in quotes, as the parser can not determine the type of a mixed list.
     * Returns the def value if no list is present.
     */


    public static int[] readIfExists(Toml toml, String key, int[] def) {
        if (toml.contains(key)) {
            List<?> foundList = toml.getList(key);
            try {
                return foundList.stream().mapToInt(object -> Integer.parseInt(object.toString())).toArray();
            } catch (NumberFormatException ex) {
                LogUtil.logInternal(Level.ERROR,"Error parsing integer for toml list with key {}! An element " +
                        "was not a valid number. Error: {}",key,ex);
            }
        }
        return def;
    }

    public static long[] readIfExists(Toml toml, String key, long[] def) {
        if (toml.contains(key)) {
            List<?> foundList = toml.getList(key);
            try {
                return foundList.stream().mapToLong(object -> Long.parseLong(object.toString())).toArray();
            } catch (NumberFormatException ex) {
                LogUtil.logInternal(Level.ERROR,"Error parsing long for toml list with key {}! An element " +
                        "was not a valid number. Error: {}",key,ex);
            }
        }
        return def;
    }

    public static double[] readIfExists(Toml toml, String key, double[] def) {
        if (toml.contains(key)) {
            List<?> foundList = toml.getList(key);
            try {
                return foundList.stream().mapToDouble(object -> Double.parseDouble(object.toString())).toArray();
            } catch (NumberFormatException ex) {
                LogUtil.logInternal(Level.ERROR,"Error parsing long for toml list with key {}! An element " +
                        "was not a valid number. Error: {}",key,ex);
            }
        }
        return def;
    }

    /*
     * I could not figure out how to make these return their primitive types efficiently, but these are functionally identical
     */
    public static Float[] readIfExists(Toml toml, String key, Float[] def) {
        if (toml.contains(key)) {
            List<?> foundList = toml.getList(key);
            try {
                return foundList.stream().map(object -> Float.parseFloat(object.toString())).toArray(Float[]::new);
            } catch (NumberFormatException ex) {
                LogUtil.logInternal(Level.ERROR,"Error parsing long for toml list with key {}! An element " +
                        "was not a valid number. Error: {}",key,ex);
            }
        }
        return def;
    }
    public static Boolean[] readIfExists(Toml toml, String key, Boolean[] def) {
        if (toml.contains(key)) {
            List<?> foundList = toml.getList(key);
            try {
                return foundList.stream().map(object -> Boolean.parseBoolean(object.toString())).toArray(Boolean[]::new);
            } catch (NumberFormatException ex) {
                LogUtil.logInternal(Level.ERROR,"Error parsing long for toml list with key {}! An element " +
                        "was not a valid number. Error: {}",key,ex);
            }
        }
        return def;
    }

    /*
     * I am not sure how to handle the quotes here, so this one does not have a fallback for checking whether it has them.
     */
    public static Date[] readIfExists(Toml toml, String key, Date[] def) {
        if (toml.contains(key)) {
            List<Date> foundList = toml.getList(key);
            return foundList.toArray(new Date[0]);
        }
        return def;
    }

    /*
     * Converts a toml instance to a list of lines for packets or file writing. The topLevelLines input is how many
     * blank lines to put in between top level tables. The innerLines input is how many spaces to put between inner level
     * tables. The listSpaces input is many spaces to put after the [ and before the ] for toml lists.
     * The toml instance input here should be top level.
     */
    @SuppressWarnings("unchecked")
    public static List<String> toLines(Toml toml, int topLevelLines, int innerLines, int listSpaces, int depth) {
        List<String> lines = new ArrayList<>();
        boolean first = true;
        Map<String, MutableInt> doubleBracketIndices = new HashMap<>();
        for(Map.Entry<String, Object> entry : toml.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            boolean isTable = toml.containsTable(key) || Objects.nonNull(toml.getTables(key));
            if(!first && isTable)
                for(int i=0;i<topLevelLines;i++)
                    lines.add("");
            if(toml.containsPrimitive(key))
                lines.add(writePrimitive(key,value,depth));
            else if(Objects.nonNull(toml.getList(key)))
                lines.add(writeList(key,(List<Object>) value, listSpaces, depth));
            else if(isTable) {
                if(toml.containsTable(key)) {
                    lines.add(writeTableName(key,false,depth));
                    lines.addAll(toLines(toml.getTable(key), innerLines, innerLines, listSpaces,depth+1));
                } else {
                    doubleBracketIndices.putIfAbsent(key,new MutableInt(0));
                    lines.add(writeTableName(key,true,depth));
                    lines.addAll(toLines(toml.getTables(key).get(doubleBracketIndices.get(key).getValue()),
                            innerLines, innerLines, listSpaces,depth+1));
                    doubleBracketIndices.get(key).increment();
                }
            }
            first = false;
        }
        return lines;
    }

    /*
     * Does the same thing as the above method except the result is stored in a single string with newline characters
     */
    public static String toString(Toml toml, int topLevelLines, int innerLines, int listSpaces, int depth) {
        return TextUtil.listToString(toLines(toml, topLevelLines, innerLines, listSpaces, depth),0);
    }

    private static String writePrimitive(String key, Object value, int depth) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(Math.max(0, depth)));
        builder.append(key).append(" = ");
        if(value instanceof String) return builder.append("\"").append(value).append("\"").toString();
        return builder.append(value).toString();
    }

    private static String writeList(String key, List<Object> values, int spacing, int depth) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(Math.max(0, depth)));
        builder.append(key).append(" = [");
        builder.append(" ".repeat(Math.max(0, spacing)));
        int index = 0;
        for(Object value : values) {
            if (value instanceof String)
                builder.append("\"").append(value).append("\"");
            else builder.append(value);
            if(index<values.size()) {
                builder.append(" ");
                index++;
            }
        }
        builder.append(" ".repeat(Math.max(0, spacing)));
        return builder.append("]").toString();
    }

    private static String writeTableName(String key, boolean isDouble, int depth) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(Math.max(0, depth)));
        if(isDouble) return builder.append("[[").append(key).append("]]").toString();
        return builder.append("[").append(key).append("]").toString();
    }
}