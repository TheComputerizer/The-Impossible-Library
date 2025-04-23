package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.loader.MultiVersionLoaderFabric;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class MultiVersionLoaderFabric1_21 extends MultiVersionLoaderFabric {
    
    public MultiVersionLoaderFabric1_21(CoreAPI parent) {
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
        File[] mods = root.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        if(Objects.nonNull(mods)) {
            TILRef.logInfo("Adding {} potential mod files",mods.length);
            set.addAll(Arrays.asList(mods));
        } else TILRef.logInfo("Adding 0 potential mod files");
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
    
    @Override protected MultiVersionModInfo loadModInfo(
            ClassLoader classLoader, MultiVersionModCandidate candidate, MultiVersionModInfo info) {
        return info;
    }
}
