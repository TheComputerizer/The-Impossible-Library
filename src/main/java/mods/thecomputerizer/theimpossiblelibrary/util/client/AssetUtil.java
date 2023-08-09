package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.locale.Language;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;

public class AssetUtil {

    public static TranslatableContents genericLang(String modid, String category, String thing) {
        return genericLang(modid, category, thing, true);
    }

    public static TranslatableContents genericLang(String modid, String category, String thing, boolean name) {
        if(name) return new TranslatableContents(category+"."+modid+"."+thing+".name","unknown_key",new Object[]{});
        return new TranslatableContents(category+"."+modid+"."+thing,"unknown_key",new Object[]{});
    }

    public static TranslatableContents extraLang(String modid, String category, String thing, String addition) {
        return extraLang(modid, category, thing, addition, true);
    }

    public static TranslatableContents extraLang(String modid, String category, String thing, String addition, boolean name) {

        if(name) return new TranslatableContents(category+"."+modid+"."+thing+"."+addition+".name","unknown_key",new Object[]{});
        return new TranslatableContents(category+"."+modid+"."+thing+"."+addition,"unknown_key",new Object[]{});
    }

    public static TranslatableContents customLang(String key) {
        return customLang(key, true);
    }

    public static TranslatableContents customLang(String key, boolean name) {
        if(name) return new TranslatableContents(key+".name","unknown_key",new Object[]{});
        return new TranslatableContents(key,"unknown_key",new Object[]{});
    }

    /**
        Checks if the lang key exists before returning and uses the set fallback key otherwise. If neither the main
        input key nor the fallback key exist this will return null.
     */
    public static TranslatableContents customLangWithFallBack(String key, String fallbackKey, boolean name, boolean fallbackName) {
        if(name) key+=".name";
        if(Language.getInstance().has(key)) return new TranslatableContents(key,"unknown_key",new Object[]{});
        if(fallbackName) fallbackKey+=".name";
        if(Language.getInstance().has(fallbackKey)) return new TranslatableContents(fallbackKey,"unknown_key",new Object[]{});
        return null;
    }

    public static ResourceLocation getAltResource(String modid, String path1, String path2, boolean selection) {
        if(selection) return new ResourceLocation(modid,path1);
        return new ResourceLocation(modid,path2);
    }
}
