package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.jar.Manifest;

import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.LOADERID;

/**
 * Use a dummy IModLocator for Forge SERVICE layer initialization.
 * If this is a dev environment, we already loaded into the BOOT layer and this will do nothing.
 * If this is NOT a dev environment, we need to move ourselves into the BOOT layer.
 * Try not to load too many library classes before verifying we are in the BOOT layer
 */
public class TILServiceLauncherForge implements IModLocator {
    
    static {
        out.println("Class init: "+TILServiceLauncherForge.class.getName());
        TILLauncherForge.checkInit(TILServiceLauncherForge.class);
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
        return LOADERID;
    }
}