package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import net.neoforged.neoforgespi.locating.IModFile;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface TILNeoForgeModLocator<LOCATOR> {
    
    IModFile createModFile(Path path, LOCATOR locator, Collection<?> infos);
    void initFor(ClassLoader loader, LOCATOR locator);
    <T> List<T> scanMods(LOCATOR locator);
}
