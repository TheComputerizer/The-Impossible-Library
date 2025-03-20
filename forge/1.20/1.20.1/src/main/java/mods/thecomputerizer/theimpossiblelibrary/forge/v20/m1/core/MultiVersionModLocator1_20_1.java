package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.loader.TILModFileForge1_20;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minecraftforge.forgespi.locating.IModLocator.ModFileOrException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiVersionModLocator1_20_1 implements TILForgeModLocator {
    
    private final Map<MultiVersionModCandidate,TILModFileForge1_20> candidateMap = new HashMap<>();
    
    @IndirectCallers
    public MultiVersionModLocator1_20_1(CoreAPI core) {
        ForgeModLoading.setFileVersion(getClass(),"20","20.1");
    }
    
    @Override public void initFor(ClassLoader loader, IModLocator locator) {
        ForgeModLoading.initModLoading(loader,locator,this.candidateMap);
    }
    
    @SuppressWarnings("unchecked")
    @Override public List<ModFileOrException> scanMods(IModLocator locator) {
        return ForgeModLoading.scanMods(this.candidateMap.values());
    }
}