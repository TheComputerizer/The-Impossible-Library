package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.function.Consumer;

public interface TILForgeLanguageProvider {
    
    Consumer<ModFileScanData> getFileVisitor(IModLanguageProvider provider);
}