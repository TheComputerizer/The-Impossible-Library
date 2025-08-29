package mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.bootstrap;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static java.io.File.pathSeparator;
import static java.io.File.separatorChar;
import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Passthrough logic for implementing BootstrapClasspathModifier in 1.20.4 and (probably) 1.20.6 (I haven't gotten there yet)
 */
public abstract class FixTILDevRunsForge1_20 {
    
    static final String CLASSPATH_FILE = System.getProperty("til.classpath.file","");
    static final String SOURCE_NAME = "tilforge";
    static final String[] RELATIVE_SOURCE_PATHS = new String[]{
            "classes"+separatorChar+"java"+separatorChar+"main",
            "classes"+separatorChar+"java"+separatorChar+SOURCE_NAME,
            "resources"+separatorChar+SOURCE_NAME,
    };
    
    /**
     * The length of foundLoaderPaths is guarunteed to not be 0 at this point
     */
    static void expandLoaderPaths(List<Path[]> classpath, Path[] foundLoaderPaths) {
        if(Objects.isNull(foundLoaderPaths)) {
            out.println("Adding loader paths that were not present in the classpath");
            expandLoaderPaths(classpath,extractBasePath());
            return;
        }
        if(foundLoaderPaths.length==3) return;
        classpath.remove(foundLoaderPaths);
        expandLoaderPaths(classpath,extractBasePath(foundLoaderPaths[0].toAbsolutePath().toString()));
    }
    
    /**
     * Uses the base path to add all the relative source paths to the classpath
     */
    static void expandLoaderPaths(List<Path[]> classpath, String basePath) {
        Path[] newPaths = new Path[3];
        for(int i=0;i<RELATIVE_SOURCE_PATHS.length;i++)
            newPaths[i] = Path.of(basePath+RELATIVE_SOURCE_PATHS[i]);
        classpath.add(newPaths);
        out.println("Expanded TIL loader paths to "+Arrays.toString(newPaths));
    }
    
    /**
     * Assumes the til.classpath.file property is set correctly
     */
    static String extractBasePath() {
        return extractBasePath(CLASSPATH_FILE);
    }
    
    /**
     * Returns the path with the file separator at the end
     */
    static String extractBasePath(String pathStr) {
        for(String relativePath : RELATIVE_SOURCE_PATHS)
            if(pathStr.endsWith(relativePath))
                return pathStr.substring(0,pathStr.length()-relativePath.length());
        err.println("Failed to extract base path from "+pathStr);
        return pathStr;
    }
    
    /**
     * TILDev is loaded from the main source set so we can't reference it yet
     */
    static Collection<String> getLoaderPaths() {
        Set<String> pathSet = new HashSet<>();
        for(String path : System.getProperty("til.classpath.file","").split(";")) {
            pathSet.add(path);
            if(path.endsWith("main")) {
                pathSet.add(path.substring(0,path.length()-4)+SOURCE_NAME);
            } else if(path.endsWith(SOURCE_NAME))
                pathSet.add(path.substring(0,path.length()-SOURCE_NAME.length())+"main");
        }
        return pathSet;
    }
    
    public final String name() {
        return "The Impossible Classpath Modifier";
    }
    
    /**
     * Add the classpath to the legacy classpath with the correct file separator.
     * Also add the dev sources so the mod loading services can be found.
     */
    public boolean process(List<Path[]> classpath) {
        final Set<String> pathSet = new HashSet<>(getLoaderPaths());
        Path[] loaderPaths = null;
        for(Path[] pathArr : classpath) {
            boolean hasLoaderPath = false;
            for(Path path : pathArr) {
                String pathStr = (path.isAbsolute() ? path : path.toAbsolutePath()).toString();
                pathSet.add(pathStr);
                if(pathStr.endsWith(SOURCE_NAME)) {
                    out.println("Found path that ends with '"+SOURCE_NAME+"': "+pathStr);
                    hasLoaderPath = true;
                }
            }
            if(hasLoaderPath) loaderPaths = pathArr;
        }
        expandLoaderPaths(classpath,loaderPaths);
        final StringJoiner joiner = new StringJoiner(pathSeparator);
        for(String path : pathSet) {
            out.println("Adding path to legacyClassPath: "+path);
            joiner.add(path);
        }
        System.setProperty("legacyClassPath",joiner.toString());
        return false;
    }
}