package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.core;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

public class TILNeoLanguageProvider implements IModLanguageLoader {
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public String version() {
        return VERSION;
    }
    
    @Override public ModContainer loadMod(IModInfo info, ModFileScanData scan, ModuleLayer layer)
            throws ModLoadingException {
        return null;
    }
}