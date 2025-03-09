package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.function.Consumer;

public interface TILForgeLanguageProvider {
    
    default void fixMods() {}
    Consumer<ModFileScanData> getFileVisitor(CoreAPI core, IModLanguageProvider provider);
}