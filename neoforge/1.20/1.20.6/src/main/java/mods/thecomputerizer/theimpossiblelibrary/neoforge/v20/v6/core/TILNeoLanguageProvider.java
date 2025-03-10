package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.core;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;

public class TILNeoLanguageProvider implements IModLanguageLoader {
    
    @Override public String name() {
        return "";
    }
    
    @Override public String version() {
        return "";
    }
    
    @Override public ModContainer loadMod(IModInfo info, ModFileScanData scan, ModuleLayer layer)
            throws ModLoadingException {
        return null;
    }
}