package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.loader.TILModFileForge1_18_2;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.util.*;

public class MultiVersionModLocator1_18_2 implements TILForgeModLocator {
    
    private final Map<MultiVersionModCandidate,TILModFileForge1_18_2> candidateMap = new HashMap<>();
    
    @IndirectCallers
    public MultiVersionModLocator1_18_2(CoreAPI core) {
        ForgeModLoading.setFileVersion(getClass(),"18_2","18.2");
    }
    
    @Override public void initFor(ClassLoader loader, IModLocator locator) {
        ForgeModLoading.initModLoading(loader,locator,this.candidateMap);
    }
    
    @SuppressWarnings("unchecked")
    @Override public List<IModFile> scanMods(IModLocator locator) {
        return ForgeModLoading.scanMods(this.candidateMap.values());
    }
}
