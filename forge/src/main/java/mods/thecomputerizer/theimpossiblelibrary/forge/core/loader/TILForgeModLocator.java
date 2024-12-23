package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface TILForgeModLocator {
    
    IModFile createModFile(Path path, IModLocator locator, Collection<?> infos);
    void initFor(ClassLoader loader, IModLocator locator);
    List<IModFile> scanMods(IModLocator locator);
}
