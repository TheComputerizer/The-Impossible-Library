package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILLanguageLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider.IModLanguageLoader;
import net.minecraftforge.forgespi.language.ModFileScanData;

/**
 * Basically the same as FMLJavaModLanguageProvider$FMLModTarget but since it's private, we can't use it...
 */
public class TILLanguageLoader1_16_5 extends TILLanguageLoader implements IModLanguageLoader {
    
    public TILLanguageLoader1_16_5(CoreAPI core, String modClass, String modid, ModFileScanData scan) {
        super(core,modClass,modid,scan);
    }
    
    @Override public <T> T loadMod(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults) {
        return super.loadModInner(info,classLoader,scanResults);
    }
}