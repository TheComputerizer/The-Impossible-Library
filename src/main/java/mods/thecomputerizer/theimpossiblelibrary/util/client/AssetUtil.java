package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.TranslationTextComponent;

public class AssetUtil {

    public static TranslationTextComponent genericLang(String modid, String category, String thing) {
        return genericLang(modid, category, thing, true);
    }

    public static TranslationTextComponent genericLang(String modid, String category, String thing, boolean name) {
        if(name) return new TranslationTextComponent(category+"."+modid+"."+thing+".name");
        return new TranslationTextComponent(category+"."+modid+"."+thing);
    }

    public static TranslationTextComponent extraLang(String modid, String category, String thing, String addition) {
        return extraLang(modid, category, thing, addition, true);
    }

    public static TranslationTextComponent extraLang(String modid, String category, String thing, String addition, boolean name) {

        if(name) return new TranslationTextComponent(category+"."+modid+"."+thing+"."+addition+".name");
        return new TranslationTextComponent(category+"."+modid+"."+thing+"."+addition);
    }

    public static TranslationTextComponent customLang(String key) {
        return customLang(key, true);
    }

    public static TranslationTextComponent customLang(String key, boolean name) {
        if(name) return new TranslationTextComponent(key+".name");
        return new TranslationTextComponent(key);
    }

    /*
        Checks if the lang key exists before returning and uses the set fallback key otherwise. If neither the main
        input key nor the fallback key exist this will return null.
     */
    public static TranslationTextComponent customLangWithFallBack(String key, String fallbackKey, boolean name, boolean fallbackName) {
        if(name) key+=".name";
        if(LanguageMap.getInstance().has(key)) return new TranslationTextComponent(key);
        if(fallbackName) fallbackKey+=".name";
        if(LanguageMap.getInstance().has(fallbackKey)) return new TranslationTextComponent(fallbackKey);
        return null;
    }

    public static ResourceLocation getAltResource(String modid, String path1, String path2, boolean selection) {
        if(selection) return new ResourceLocation(modid,path1);
        return new ResourceLocation(modid,path2);
    }
}
