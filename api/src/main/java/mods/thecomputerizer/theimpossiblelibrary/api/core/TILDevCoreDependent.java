package mods.thecomputerizer.theimpossiblelibrary.api.core;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.JAR_NAME;

/**
 * Separated from TILDev to store constants that should not be loaded until after a CoreAPI instance has been created
 */
public class TILDevCoreDependent {
    
    private static final Set<String> LOADER_PATHS = parseLoaderPaths(); //`-Dtil.classpath.file=...`
    
    static boolean isLoaderPath(Path path) {
        return LOADER_PATHS.contains(path.toAbsolutePath().toString());
    }
    
    static Set<String> parseLoaderPaths() {
        String[] paths = TILDev.getProperty("classpath.file",JAR_NAME).split(";");
        if(paths.length==0) return Collections.singleton(JAR_NAME);
        Set<String> pathSet = new HashSet<>();
        String loaderSource = TILDev.sourceName();
        boolean isMain = "main".equals(loaderSource);
        for(String path : paths) {
            pathSet.add(path);
            if(path.endsWith("main")) {
                if(!isMain) pathSet.add(path.substring(0,path.length()-4)+loaderSource);
            } else if(path.endsWith(loaderSource))
                pathSet.add(path.substring(0,path.length()-loaderSource.length())+"main");
        }
        return Collections.unmodifiableSet(pathSet);
    }
}