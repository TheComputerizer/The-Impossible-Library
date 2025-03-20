package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.core.loader.TILModFileForge1_19;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minecraftforge.forgespi.locating.IModLocator.ModFileOrException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiVersionModLocator1_19_4 implements TILForgeModLocator {
    
    private final Map<MultiVersionModCandidate,TILModFileForge1_19> candidateMap = new HashMap<>();
    
    @IndirectCallers
    public MultiVersionModLocator1_19_4(CoreAPI core) {
        ForgeModLoading.setFileVersion(getClass(),"19_4","19.4");
    }
    
    @Override public void initFor(ClassLoader loader, IModLocator locator) {
        ForgeModLoading.initModLoading(loader,locator,this.candidateMap);
    }
    
    @SuppressWarnings("unchecked")
    @Override public List<ModFileOrException> scanMods(IModLocator locator) {
        return ForgeModLoading.scanMods(this.candidateMap.values());
    }
}
