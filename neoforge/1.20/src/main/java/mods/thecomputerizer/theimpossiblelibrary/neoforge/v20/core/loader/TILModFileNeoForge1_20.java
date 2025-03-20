package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader;

import cpw.mods.jarhandling.SecureJar;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILFileConfigNeoForge;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforgespi.language.IConfigurable;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class TILModFileNeoForge1_20 extends ModFile {
    
    static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        IConfigurable config = new TILFileConfigNeoForge(infos,"multiversionprovider");
        return new ModFileInfo((ModFile)file,config,info -> {},Collections.emptyList());
    }
    
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    
    public TILModFileNeoForge1_20(SecureJar file, IModLocator locator, Collection<?> infos) {
        super(file,locator,mod -> getFileInfo(mod,infos),"MOD");
        this.infos = NeoForgeModLoading.initFileInfo("20",infos);
    }
    
    @Override public ModFileScanData compileContent() {
        return NeoForgeModLoading.writeMods(this);
    }
    
    @Override public boolean identifyMods() {
        return NeoForgeModLoading.identifyMods(super.identifyMods(),this);
    }
}