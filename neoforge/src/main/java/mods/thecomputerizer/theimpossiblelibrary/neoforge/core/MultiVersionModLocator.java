package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class MultiVersionModLocator implements IModLocator {
    
    static {
        NeoForgeCoreLoader.initCoreAPI(MultiVersionModLocator.class.getClassLoader());
    }
    
    final boolean failed;
    
    public MultiVersionModLocator() {
        Class<?> c = getClass();
        TILDev.logInfo("Core Neoforge Locator plugin loaded on {}", c.getClassLoader());
        this.failed = !NeoForgeModLoading.setLoadingVersion(c);
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
