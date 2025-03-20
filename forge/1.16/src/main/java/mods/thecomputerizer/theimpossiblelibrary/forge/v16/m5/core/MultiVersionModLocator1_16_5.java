package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader.TILModFileForge1_16_5;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.util.*;

public class MultiVersionModLocator1_16_5 implements TILForgeModLocator {
    
    private final Map<MultiVersionModCandidate,TILModFileForge1_16_5> candidateMap = new HashMap<>();
    
    @IndirectCallers
    public MultiVersionModLocator1_16_5(CoreAPI core) {
        ForgeModLoading.setFileVersion(getClass(),"16_5","16.5");
    }
    
    public void initFor(ClassLoader loader, IModLocator locator) {
        ForgeModLoading.initModLoading(loader,locator,this.candidateMap);
    }
    
    @SuppressWarnings("unchecked")
    @Override public List<IModFile> scanMods(IModLocator locator) {
        return ForgeModLoading.scanMods(this.candidateMap.values());
    }
}
