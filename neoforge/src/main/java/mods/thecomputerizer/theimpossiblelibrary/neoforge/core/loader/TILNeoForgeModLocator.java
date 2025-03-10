package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface TILNeoForgeModLocator {
    
    IModFile createModFile(Path path, IModLocator locator, Collection<?> infos);
    void initFor(ClassLoader loader, IModLocator locator);
    <T> List<T> scanMods(IModLocator locator);
}
