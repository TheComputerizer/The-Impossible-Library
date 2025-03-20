package mods.thecomputerizer.theimpossiblelibrary.forge.v19.core.loader;

import cpw.mods.jarhandling.SecureJar;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILFileConfigForge;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class TILModFileForge1_19 extends ModFile {
    
    static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        IConfigurable config = new TILFileConfigForge(infos);
        return new ModFileInfo((ModFile)file,config,Collections.emptyList());
    }
    
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    
    public TILModFileForge1_19(SecureJar file, IModLocator locator, Collection<?> infos) {
        super(file,locator,mod -> getFileInfo(mod,infos),"MOD");
        this.infos = ForgeModLoading.initFileInfo("19",infos);
    }
    
    @Override public ModFileScanData compileContent() {
        return ForgeModLoading.writeMods(this);
    }
    
    @Override public boolean identifyMods() {
        return ForgeModLoading.identifyMods(super.identifyMods(),this);
    }
}