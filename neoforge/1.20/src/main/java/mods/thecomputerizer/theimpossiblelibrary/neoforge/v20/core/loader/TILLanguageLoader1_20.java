package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILLanguageLoader;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageProvider.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;

public class TILLanguageLoader1_20 extends TILLanguageLoader implements IModLanguageLoader {
    
    public TILLanguageLoader1_20(CoreAPI core, String modClass, String modid, ModFileScanData scan) {
        super(core,modClass,modid,scan);
    }
    
    /**
     * Load to GAME layer
     */
    @Override public <T> T loadMod(IModInfo info, ModFileScanData scanResults, ModuleLayer layer) {
        String modid = info.getModId();
        try {
            ClassLoader loader = NeoForgeCoreLoader.layerClassLoader("GAME");
            return super.loadModInner(info,loader,scanResults,layer);
        } catch(Throwable t) {
            String msg = "Failed to load mod "+modid;
            TILRef.logError(msg,t);
            throw new RuntimeException(msg,t);
        }
    }
}