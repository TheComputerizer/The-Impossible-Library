package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader.MODULE_LAYERS;

public class MultiVersionModLocator implements IModLocator {
    
    private static final String IMPL_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge.core";
    private static final String NEOFORGE_PKG = "net.neoforged.neoforgespi";
    private static final String MOD_LOCATOR_IMPL = IMPL_PKG+".MultiVersionModLocator";
    private static final String MOD_LOCATOR_SERVICE = NEOFORGE_PKG+".locating.IModLocator";
    
    static {
        ClassLoader loader = MultiVersionModLocator.class.getClassLoader();
        NeoForgeCoreLoader.fixForServiceLayer();
        Object instance = NeoForgeCoreLoader.initCoreAPI(loader);
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiVersionModLocator");
        if(loader!=NeoForgeCoreLoader.bootLoader())
            NeoForgeCoreLoader.fixService(MOD_LOCATOR_SERVICE,MOD_LOCATOR_IMPL,loader,true);
        if(!MODULE_LAYERS) Hacks.removeEnvironmentProperty("MOD_CLASSES");
    }
    
    final boolean failed;
    
    public MultiVersionModLocator() {
        Class<?> c = getClass();
        TILRef.logInfo("Core Neoforge Locator plugin loaded on {}",c.getClassLoader());
        Object coreAPI = NeoForgeCoreLoader.initCoreAPI(Launcher.class.getClassLoader());
        this.failed = !NeoForgeModLoading.setLoadingVersion(c,coreAPI);
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        if(this.failed) {
            TILRef.logWarn("Not initializing mod loading for MultiVersionModLocator that failed to load");
            return;
        }
        TILRef.logInfo("Initializing Forge mod loading with args {}",arguments);
        NeoForgeModLoading.initModLoading(getClass().getClassLoader(),this);
    }
    
    @Override public boolean isValid(IModFile file) {
        return true;
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public void scanFile(IModFile file, Consumer<Path> pathConsumer) {}
    
    @Override public List<ModFileOrException> scanMods() {
        if(this.failed) {
            TILRef.logWarn("Not scanning for mods with MultiVersionModLocator that failed to load");
            return Collections.emptyList();
        }
        TILRef.logInfo("Scanning for mods");
        try {
            List<ModFileOrException> files = NeoForgeModLoading.scanMods();
            TILRef.logInfo("Returing scanned mods {}",files);
            return files;
        } catch(Throwable t) {
            TILRef.logError("Failed to scan mods",t);
            throw t;
        }
    }
}
