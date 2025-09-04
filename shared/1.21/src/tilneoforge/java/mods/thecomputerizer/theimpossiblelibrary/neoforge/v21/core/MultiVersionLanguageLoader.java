package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILBetterModScan;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;

import java.util.Collections;
import java.util.Objects;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.GAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.PROVIDERID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

public class MultiVersionLanguageLoader implements IModLanguageLoader {
    
    static {
        Package pkg = MultiVersionLanguageLoader.class.getPackage();
        Object versionInfo = Hacks.getFieldDirect(pkg,"versionInfo");
        if(Objects.nonNull(versionInfo)) {
            Hacks.setFieldDirect(versionInfo,"implVersion",VERSION);
            TILDev.logDebug("Set implementation version of {} to {}",pkg,VERSION);
        }
    }
    
    static boolean loadedNewCore;
    
    @Override public ModContainer loadMod(IModInfo info, ModFileScanData scan, ModuleLayer layer) throws ModLoadingException {
        if(scan instanceof TILBetterModScan betterScan) {
            String modid = info.getModId();
            String modClass = betterScan.getModClass(modid);
            String coreName = betterScan.getCore().getClass().getName();
            try {
                ClassLoader loader = NeoForgeCoreLoader.layerClassLoader(GAME);
                betterScan.defineClasses(loader);
                if(!loadedNewCore) setCoreAPI(Class.forName(coreName,true,loader));
                NeoForgeCoreLoader.verifyModule(modClass,info,layer);
                TILRef.logInfo("Attempting to initialize container in module {} for {}",
                               info.getOwningFile().moduleName(),modClass);
                ModContainer container = new FMLModContainer(info,Collections.singletonList(modClass),scan,layer);
                TILRef.logInfo("Successfully initialized mod container for {}",modClass);
                return container;
            } catch(Throwable t) {
                String msg = "Failed to load mod "+modid+" with "+modClass;
                TILRef.logError(msg, t);
                throw new RuntimeException(msg,t);
            }
        } else return null;
    }
    
    @Override public String name() {
        return PROVIDERID;
    }
    
    protected void setCoreAPI(Class<?> implClass) {
        try {
            Hacks.checkBurningWaveInit();
            implClass.getConstructor().newInstance();
            loadedNewCore = true;
        } catch(Throwable t) {
            TILRef.logError("Failed to set CoreAPI instance {}",implClass,t);
        }
    }
    
    @Override public String version() {
        return VERSION;
    }
}