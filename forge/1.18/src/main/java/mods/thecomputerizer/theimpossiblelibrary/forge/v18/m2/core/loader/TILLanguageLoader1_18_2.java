package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILLanguageLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider.IModLanguageLoader;
import net.minecraftforge.forgespi.language.ModFileScanData;

/**
 * Basically the same as FMLJavaModLanguageProvider$FMLModTarget but since it's private, we can't use it...
 */
public class TILLanguageLoader1_18_2 extends TILLanguageLoader implements IModLanguageLoader {
    
    public TILLanguageLoader1_18_2(String modClass, String modid, ModFileScanData scan) {
        super(modClass,modid,scan);
    }
    
    /**
     * Make all the local variables final like how FML does it.
     */
    @Override public <T> T loadMod(IModInfo info, ModFileScanData scanResults, ModuleLayer layer) {
        ClassLoader loader = layer.findModule(info.getOwningFile().moduleName()).orElseThrow().getClassLoader();
        return super.loadModInner(info,loader,scanResults);
    }
}