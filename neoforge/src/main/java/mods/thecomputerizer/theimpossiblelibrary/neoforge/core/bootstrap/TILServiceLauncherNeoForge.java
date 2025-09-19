package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILForgeLikeServiceLauncher;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_ID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.launcher;

/**
 * Use a dummy IModLocator for Forge SERVICE layer initialization.
 * If this is a dev environment, we already loaded into the BOOT layer and this will do nothing.
 * If this is NOT a dev environment, we need to move ourselves into the BOOT layer.
 * Try not to load too many library classes before verifying we are in the BOOT layer
 */
@IndirectCallers
public class TILServiceLauncherNeoForge implements IModLocator {
    
    static final String CORE_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge.core";
    static final String LANGUAGE_LOADER = CORE_PKG+".TILLanguageProvider";
    static final String LOCATOR = CORE_PKG+".MultiVersionModLocator";
    
    static {
        Class<?> c = TILServiceLauncherNeoForge.class;
        boolean serviceLoaded = c.getClassLoader()!=Launcher.class.getClassLoader();
        TILForgeLikeServiceLauncher.init(c,TILLauncherNeoForge.class,serviceLoaded);
        if(serviceLoaded) validateServices(c.getName(),LOCATOR,LANGUAGE_LOADER);
    }
    
    static void validateServices(String ... classNames) {
        Logger logger = launcher.getLogger();
        for(String className : classNames) TILLauncherNeoForge.validateBootClass(logger,BOOT_ID,className);
    }
    
    @Override public void initArguments(Map<String,?> arguments) {}
    
    @Override public boolean isValid(IModFile modFile) {
        return false;
    }
    
    @Override public void scanFile(IModFile modFile, Consumer<Path> pathConsumer) {}
    
    @Override public List<ModFileOrException> scanMods() {
        return Collections.emptyList();
    }
    
    @Override public String name() {
        return LOADER_ID;
    }
    
}