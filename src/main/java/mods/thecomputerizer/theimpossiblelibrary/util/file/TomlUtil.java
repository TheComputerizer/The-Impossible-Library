package mods.thecomputerizer.theimpossiblelibrary.util.file;

import com.moandjiezana.toml.Toml;

import java.util.Collection;
import java.util.List;

//Helper method for toml files because toml is better than json
public class TomlUtil {

    public static String readIfExists(Toml toml, String key, String def) {
        return toml.getString(key, def);
    }

    public static boolean readIfExists(Toml toml, String key, boolean def) {
        return Boolean.parseBoolean(toml.getString(key, def + ""));
    }

    public static int readIfExists(Toml toml, String key, int def) {
        return Integer.parseInt(toml.getString(key, def + ""));
    }

    public static float readIfExists(Toml toml, String key, float def) {
        return Float.parseFloat(toml.getString(key, def + ""));
    }

    public static Collection<String> readIfExists(Toml toml, String key, Collection<String> def) {
        if (toml.contains(key)) toml.getList(key);
        return def;
    }

    public static String[] readIfExists(Toml toml, String key, String[] def) {
        if (toml.contains(key)) {
            List<String> foundList = toml.getList(key);
            return foundList.toArray(new String[0]);
        }
        return def;
    }
}
