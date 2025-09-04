package mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.LOADERID;

/**
 * Used by neoforge and forge launch plugins to load the module in the BOOT layer.
 * Validates the correct runtime environment by checking if a specific validator class exists.
 */
public abstract class TILLauncher {
    
    //`-Dtil.dev=true`
    protected static final boolean DEV = Boolean.parseBoolean(System.getProperty("til.dev","false"));
    
    static {
        Hacks.checkBurningWaveInit();
    }
    
    protected static <E extends Enum<E>> EnumSet<E> none(Class<E> enumClass) {
        return EnumSet.noneOf(enumClass);
    }
    
    protected static String fmlLoader(String loaderPkg) {
        return "net."+loaderPkg+".fml.loading.FMLLoader";
    }
    
    private final String name;
    protected final boolean active;
    protected final Logger logger;
    
    protected TILLauncher(final String loader, final String validatorClassName) {
        this.name = LOADERID+"_"+loader;
        this.logger = TILRef.createLogger(this.name);
        this.active = validate(validatorClassName);
        this.logger.info("Created {}active launch plugin",this.active ? "" : "in");
        if(DEV) Hacks.removeEnvironmentProperty("MOD_CLASSES");
    }
    
    public String name() {
        return this.name;
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
            this.logger.debug("Assuming incorrect environment since the validator class was not found {} "+
                              "(ClassLoader={})",validator,thisClassLoader);
        }
        return false;
    }
}
