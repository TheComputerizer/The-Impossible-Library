package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class AssetUtil {

    public static String genericLang(String modid, String category, String thing) {
        return genericLang(modid, category, thing, true);
    }

    public static String genericLang(String modid, String category, String thing, boolean name) {
        if(name) return I18n.format(category+"."+modid+"."+thing+".name");
        return I18n.format(category+"."+modid+"."+thing);
    }

    public static String extraLang(String modid, String category, String thing, String addition) {
        return extraLang(modid, category, thing, addition, true);
    }

    public static String extraLang(String modid, String category, String thing, String addition, boolean name) {
        if(name) return I18n.format(category+"."+modid+"."+thing+"."+addition+".name");
        return I18n.format(category+"."+modid+"."+thing+"."+addition);
    }

    public static String customLang(String key) {
        return customLang(key, true);
    }

    public static String customLang(String key, boolean name) {
        if(name) return I18n.format(key+".name");
        return I18n.format(key);
    }

    /*
        Checks if the lang key exists before returning and uses the set fallback key otherwise. If neither the main
        input key nor the fallback key exist this will return null.
     */
    public static String customLangWithFallBack(String key, String fallbackKey, boolean name, boolean fallbackName) {
        if(name) key+=".name";
        if(I18n.hasKey(key)) return I18n.format(key);
        if(fallbackName) fallbackKey+=".name";
        if(I18n.hasKey(fallbackKey)) return I18n.format(fallbackKey);
        return null;
    }

    public static ResourceLocation getAltResource(String modid, String path1, String path2, boolean selection) {
        if(selection) return new ResourceLocation(modid,path1);
        return new ResourceLocation(modid,path2);
    }
}
