package mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;
import java.util.Objects;

import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.LOADERID;
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
        this.logger = TILRef.createLogger(LOADERID);
        boolean active = !this.activeLoader.isEmpty();
        this.logger.info("Created {}active {}launch plugin",active ? "" : "in",
                         active ? this.activeLoader+" " : "");
        if(withHacks) {
            Hacks.checkBurningWaveInit();
            if(DEV) Hacks.removeEnvironmentProperty("MOD_CLASSES");
        }
        launcher = this;
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
        return LOADERID;
    }
    
    /**
     * See if we are running in a valid environment for this launch plugin by checking if the validator class exists
     */
    private boolean validate(String validator) {
        ClassLoader thisClassLoader = getClass().getClassLoader();
        try {
            Class.forName(validator,false,thisClassLoader);
            return true;
        } catch(Throwable t) {
            if(Objects.isNull(this.logger)) {
                t.printStackTrace(out);
                return false;
            }
            this.logger.debug("Assuming incorrect environment since the validator class was not found {} "+
                              "(ClassLoader={})",validator,thisClassLoader);
        }
        return false;
    }
}
