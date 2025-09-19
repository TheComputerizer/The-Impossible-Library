package mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap;

import static java.lang.System.out;

/**
 * Forge/Neoforge dev runs load into the BOOT layer whereas they otherwise load into the SERVICE layer
 * This acts as a simple marker for the SERVICE layer init to know whether things have been initialized
 */
public class TILLauncherRef {
    
    public static final String BOOT_ID = "theimpossiblebootstrap";
    public static final String LOADER_ID = "theimpossibleloader";
    public static final String LOADER_NAME = "The Impossible Loader";
    public static TILLauncher launcher;
    
    static {
        out.println("Class init: "+TILLauncherRef.class.getName());
    }
}