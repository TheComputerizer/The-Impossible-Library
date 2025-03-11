package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import net.neoforged.neoforgespi.language.ModFileScanData;

import java.util.function.Consumer;

public interface TILNeoForgeLanguageProvider<LOADER> {
    
    default void fixMods() {}
    Consumer<ModFileScanData> getFileVisitor(CoreAPI core, LOADER loader);
}