package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.MultiVersionLoaderNeoForge;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class MultiVersionLoaderNeoForge1_20 extends MultiVersionLoaderNeoForge {
    
    public MultiVersionLoaderNeoForge1_20(CoreAPI parent) {
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
        return List.copyOf(set);
    }
    
    @Override protected @Nullable Attributes getFileAttributes(File file) {
        if(Objects.isNull(file) || !file.exists()) return null;
        try(JarFile jar = new JarFile(file)) {
            Manifest manifest = jar.getManifest();
            return Objects.nonNull(manifest) ? manifest.getMainAttributes() : null;
        } catch(IOException ex) {
            TILRef.logError("Error getting attributes for jar file {}",file,ex);
        }
        return null;
    }
    
    @IndirectCallers
    public FMLModContainer getModContainer(String modid) {
        ModList list = ModList.get();
        Optional<? extends ModContainer> container = list.getModContainerById(modid);
        return (FMLModContainer)container.orElse(null);
    }
    
    @Override protected MultiVersionModInfo loadModInfo(
            ClassLoader classLoader, MultiVersionModCandidate candidate, MultiVersionModInfo info) {
        return info;
    }
}
