package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.libraries.Artifact;
import net.minecraftforge.fml.relauncher.libraries.LibraryManager;
import net.minecraftforge.fml.relauncher.libraries.Repository;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA8;
import static net.minecraft.launchwrapper.Launch.classLoader;
import static net.minecraft.launchwrapper.Launch.minecraftHome;

public class TILLoadingPlugin1_12_2 implements IFMLLoadingPlugin {

    static {
        List<Artifact> mavenCandidates = LibraryManager.flattenLists(minecraftHome);
        List<File> fileCandidates = LibraryManager.gatherLegacyCanidates(minecraftHome);
        for(Artifact artifact : mavenCandidates) {
            artifact = Repository.resolveAll(artifact);
            if(Objects.nonNull(artifact)) {
                File target = artifact.getFile();
                if(!fileCandidates.contains(target)) fileCandidates.add(target);
            }
        }
        new TILCore1_12_2(fileCandidates).loadCoreModInfo(classLoader::addURL);
    }

    public TILLoadingPlugin1_12_2() {
        CoreAPI.INSTANCE.instantiateCoreMods();
        CoreAPI.INSTANCE.writeMods(classLoader,JAVA8);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public @Nullable String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String,Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
