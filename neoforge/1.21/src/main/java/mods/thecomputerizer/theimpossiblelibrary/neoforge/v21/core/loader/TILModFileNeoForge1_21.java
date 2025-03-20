package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.loader;

import cpw.mods.jarhandling.SecureJar;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
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
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;
import net.neoforged.neoforgespi.locating.IModFileReader;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static net.neoforged.neoforgespi.locating.IModFile.Type.MOD;
import static net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes.DEFAULT;

@SuppressWarnings("UnstableApiUsage")
public class TILModFileNeoForge1_21 extends ModFile {
    
    static {
        NeoForgeModLoading.setFileVersion(TILModFileNeoForge1_21.class,"21","21.1");
    }
    
    static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        IConfigurable config = new TILFileConfigNeoForge(infos,"multiversionloader");
        return new ModFileInfo((ModFile)file,config,info -> {},Collections.emptyList());
    }
    
    static SecureJar jarFor(MultiVersionModCandidate candidate) {
        return SecureJar.from(candidate.getFile().toPath());
    }
    
    private final MultiVersionModCandidate candidate;
    @Getter private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    
    public TILModFileNeoForge1_21(IModFileCandidateLocator locator, MultiVersionModCandidate candidate,
            Collection<?> infos) {
        this(DEFAULT.withLocator(locator),candidate,infos);
    }
    
    public TILModFileNeoForge1_21(IModFileReader reader, MultiVersionModCandidate candidate, Collection<?> infos) {
        this(DEFAULT.withReader(reader),candidate,infos);
    }
    
    public TILModFileNeoForge1_21(ModFileDiscoveryAttributes attributes, MultiVersionModCandidate candidate,
            Collection<?> infos) {
        super(jarFor(candidate),mod -> getFileInfo(mod,infos),MOD,attributes);
        this.candidate = candidate;
        this.infos = NeoForgeModLoading.initFileInfo("21",infos);
    }
    
    @Override public ModFileScanData compileContent() {
        NeoForgeModLoading.populateMultiversionData(this.candidate,this);
        return NeoForgeModLoading.writeMods(this);
    }
    
    @Override public boolean identifyMods() {
        return NeoForgeModLoading.identifyMods(super.identifyMods(),this);
    }
}