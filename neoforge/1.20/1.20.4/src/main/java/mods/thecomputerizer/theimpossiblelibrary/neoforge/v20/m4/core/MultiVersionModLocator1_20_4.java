package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.core;

import cpw.mods.jarhandling.SecureJar;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILNeoForgeModLocator;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader.TILModFileNeoForge1_20;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;
import net.neoforged.neoforgespi.locating.IModLocator.ModFileOrException;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiVersionModLocator1_20_4 implements TILNeoForgeModLocator<IModLocator> {
    
    private final Map<MultiVersionModCandidate,TILModFileNeoForge1_20> candidateMap = new HashMap<>();
    
    @IndirectCallers
    public MultiVersionModLocator1_20_4(CoreAPI core) {
        NeoForgeModLoading.setFileVersion(getClass(),"20","20.4");
    }
    
    @Override public IModFile createModFile(Path path, IModLocator locator, Collection<?> infos) {
        return new TILModFileNeoForge1_20(SecureJar.from(path),locator,infos);
    }
    
    @Override public void initFor(ClassLoader loader, IModLocator locator) {
        NeoForgeModLoading.initModLoading(loader,locator,this.candidateMap);
    }
    
    @SuppressWarnings("unchecked")
    @Override public List<ModFileOrException> scanMods(IModLocator locator) {
        return NeoForgeModLoading.scanMods(this.candidateMap.values());
    }
}