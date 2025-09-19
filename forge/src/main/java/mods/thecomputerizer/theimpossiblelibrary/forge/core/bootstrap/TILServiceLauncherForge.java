package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILForgeLikeServiceLauncher;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_ID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.launcher;

/**
 * Use a dummy IModLocator for Forge SERVICE layer initialization.
 * If this is a dev environment, we already loaded into the BOOT layer and this will do nothing.
 * If this is NOT a dev environment, we need to move ourselves into the BOOT layer.
 * Try not to load too many library classes before verifying we are in the BOOT layer
 */
public class TILServiceLauncherForge implements IModLocator {
    
    static final String LANGUAGE_LOADER = "mods.thecomputerizer.theimpossiblelibrary.forge.core.TILLanguageProvider";
    static final String LOCATOR = "mods.thecomputerizer.theimpossiblelibrary.forge.core.MultiVersionModLocator";
    
    static {
        Class<?> c = TILServiceLauncherForge.class;
        boolean serviceLoaded = c.getClassLoader()!=Launcher.class.getClassLoader();
        TILForgeLikeServiceLauncher.init(c,TILLauncherForge.class,serviceLoaded);
        if(serviceLoaded && notJava8()) validateServices(c.getName(),LOCATOR,LANGUAGE_LOADER);
    }
    
    static boolean notJava8() {
        return !System.getProperty("java.version").startsWith("1.");
    }
    
    static void validateServices(String ... classNames) {
        Logger logger = launcher.getLogger();
        for(String className : classNames) TILLauncherForge.validateBootClass(logger,MODID,className);
    }
    
    public Optional<Manifest> findManifest(Path file) {
        return Optional.empty();
    }
    
    public Path findPath(IModFile modFile, String... path) {
        return null;
    }
    
    @Override public void initArguments(Map<String,?> arguments) {}
    
    @Override public boolean isValid(IModFile modFile) {
        return false;
    }
    
    @Override public void scanFile(IModFile modFile, Consumer<Path> pathConsumer) {}
    
    @Override public List<IModFile> scanMods() {
        return Collections.emptyList();
    }
    
    @Override public String name() {
        return LOADER_ID;
    }
}