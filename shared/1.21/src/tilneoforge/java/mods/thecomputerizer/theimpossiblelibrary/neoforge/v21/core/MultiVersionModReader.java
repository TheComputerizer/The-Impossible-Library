package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.jarhandling.JarContents;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileReader;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class MultiVersionModReader extends TILModFinderNeoForge1_21 implements IModFileReader {
    
    static final Set<String> alreadyHandled = new HashSet<>();
    
    public MultiVersionModReader() {
        super();
    }
    
    /**
     * Try reading multiversion mods first so they can be filtered out from normal mod loading earlier.
     * Don't use HIGHEST_SYSTEM_PRIORITY to avoid potential conflicts with important readers.
     * DEFAULT_PRIORITY = 0
     */
    @Override public int getPriority() {
        return 1;
    }
    
    @Override protected boolean queryFile(String loaderName, File file) {
        String fileName = file.getName();
        if(alreadyHandled.contains(fileName)) {
            this.logger.debug("Skipping file that was already queried {}",fileName);
            return true;
        }
        alreadyHandled.add(fileName);
        return super.queryFile(loaderName,file);
    }
    
    @Override public @Nullable IModFile read(JarContents jar, ModFileDiscoveryAttributes attributes) {
        return findAndLoad(jar,() -> attributes.withReader(this));
    }
}