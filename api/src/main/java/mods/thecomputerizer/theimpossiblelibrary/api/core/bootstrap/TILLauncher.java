package mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;

import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_ID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.launcher;

/**
 * Used by neoforge and forge launch plugins to load the module in the BOOT layer.
 * Validates the correct runtime environment by checking if a specific validator class exists.
 */
public class TILLauncher {
    
    //`-Dtil.dev=true`
    protected static final boolean DEV = Boolean.parseBoolean(System.getProperty("til.dev","false"));
    
    static {
        out.println("Class init: "+TILLauncher.class.getName());
    }
    
    protected static String fmlLoader(String loaderPkg) {
        return "net."+loaderPkg+".fml.loading.FMLLoader";
    }
    
    public static TILLauncher init(boolean withHacks) {
        return new TILLauncher(withHacks);
    }
    
    protected static <E extends Enum<E>> EnumSet<E> none(Class<E> enumClass) {
        return EnumSet.noneOf(enumClass);
    }
    
    protected final String activeLoader;
    @Getter protected final Logger logger;
    
    protected TILLauncher(boolean withHacks) {
        this.activeLoader = validate(fmlLoader("minecraftforge")) ? "forge" :
                (validate(fmlLoader("neoforged")) ? "neoforge" : "");
        this.logger = TILRef.createLogger(LOADER_NAME+(isActive() ? " ("+activeLoaderExt()+")" : ""));
        this.logger.info("Created {}active {} launch plugin",isActive() ? "" : "in",
                         withHacks ? "BOOT" : "SERVICE");
        if(withHacks) {
            Hacks.checkBurningWaveInit();
            if(DEV) Hacks.removeEnvironmentProperty("MOD_CLASSES");
        }
        launcher = this;
    }
    
    String activeLoaderExt() {
        return this.activeLoader.substring(0,1).toUpperCase()+this.activeLoader.substring(1);
    }
    
    boolean isActive() {
        return !this.activeLoader.isEmpty();
    }
    
    boolean isActive(String loader) {
        return loader.equals(this.activeLoader);
    }
    
    public boolean isActiveForge() {
        return isActive("forge");
    }
    
    public boolean isActiveNeoforge() {
        return isActive("neoforge");
    }
    
    public String name() {
        return LOADER_ID;
    }
    
    /**
     * See if we are running in a valid environment for this launch plugin by checking if the validator class exists
     */
    private boolean validate(String validator) {
        ClassLoader thisClassLoader = getClass().getClassLoader();
        try {
            Class.forName(validator,false,thisClassLoader);
            return true;
        } catch(Throwable ignored) {}
        return false;
    }
}
