package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class MultiVersionLoaderForge1_16_5 extends MultiVersionLoaderAPI {
    
    protected MultiVersionLoaderForge1_16_5(CoreAPI parent) {
        super(parent);
    }
    
    @Override protected File findCoreModRoot() {
        return new File("mods");
    }
    
    @Override public File findModRoot() {
        return new File("mods");
    }
    
    @Override protected List<File> gatherCandidateModFiles(File root) {
        Set<File> set = new HashSet<>();
        for(Path path : this.potentialModPaths) {
            File file = path.toFile();
            if(file.isFile() && file.getName().endsWith(".jar")) set.add(file);
        }
        return Collections.unmodifiableList(new ArrayList<>(set));
    }
    
    @Override protected @Nullable Attributes getFileAttributes(File file) {
        try(JarFile jar = new JarFile(file)) {
            return jar.getManifest().getMainAttributes();
        } catch(IOException ex) {
            TILRef.logError("Error getting attributes for jar file {}",file,ex);
        }
        return null;
    }
    
    @IndirectCallers
    public FMLModContainer getModContainer(String modid) {
        ModList list = ModList.get();
        TILRef.logInfo("MOD LIST IS {}",list);
        Optional<? extends ModContainer> container = list.getModContainerById(modid);
        return (FMLModContainer)container.orElse(null);
    }
    
    @Override protected MultiVersionModInfo loadModInfo(
            ClassLoader classLoader, MultiVersionModCandidate candidate, MultiVersionModInfo info) {
        return info;
    }
}
