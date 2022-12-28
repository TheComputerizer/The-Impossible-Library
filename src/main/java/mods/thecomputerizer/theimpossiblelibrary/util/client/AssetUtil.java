package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.locale.Language;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class AssetUtil {

    public static TranslatableComponent genericLang(String modid, String category, String thing) {
        return genericLang(modid, category, thing, true);
    }

    public static TranslatableComponent genericLang(String modid, String category, String thing, boolean name) {
        if(name) return new TranslatableComponent(category+"."+modid+"."+thing+".name");
        return new TranslatableComponent(category+"."+modid+"."+thing);
    }

    public static TranslatableComponent extraLang(String modid, String category, String thing, String addition) {
        return extraLang(modid, category, thing, addition, true);
    }

    public static TranslatableComponent extraLang(String modid, String category, String thing, String addition, boolean name) {

        if(name) return new TranslatableComponent(category+"."+modid+"."+thing+"."+addition+".name");
        return new TranslatableComponent(category+"."+modid+"."+thing+"."+addition);
    }

    public static TranslatableComponent customLang(String key) {
        return customLang(key, true);
    }

    public static TranslatableComponent customLang(String key, boolean name) {
        if(name) return new TranslatableComponent(key+".name");
        return new TranslatableComponent(key);
    }

    /*
        Checks if the lang key exists before returning and uses the set fallback key otherwise. If neither the main
        input key nor the fallback key exist this will return null.
     */
    public static TranslatableComponent customLangWithFallBack(String key, String fallbackKey, boolean name, boolean fallbackName) {
        if(name) key+=".name";
        if(Language.getInstance().has(key)) return new TranslatableComponent(key);
        if(fallbackName) fallbackKey+=".name";
        if(Language.getInstance().has(fallbackKey)) return new TranslatableComponent(fallbackKey);
        return null;
    }

    public static ResourceLocation getAltResource(String modid, String path1, String path2, boolean selection) {
        if(selection) return new ResourceLocation(modid,path1);
        return new ResourceLocation(modid,path2);
    }
}
