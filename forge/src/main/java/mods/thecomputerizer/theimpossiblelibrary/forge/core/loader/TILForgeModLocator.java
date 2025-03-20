package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.util.List;

public interface TILForgeModLocator {
    
    @IndirectCallers void initFor(ClassLoader loader, IModLocator locator);
    <T> List<T> scanMods(IModLocator locator);
}
