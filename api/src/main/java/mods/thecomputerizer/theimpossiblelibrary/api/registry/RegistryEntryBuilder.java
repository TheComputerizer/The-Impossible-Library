package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16_5;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V18_2;

public abstract class RegistryEntryBuilder<API> {
    
    protected static final boolean NAMED_ENV = CoreAPI.isNamedEnv();
    protected static final boolean SRG_ENV = CoreAPI.isSrgEnv();
    protected static final GameVersion VERSION = CoreAPI.gameVersion();
    protected static final boolean DEFAULT_MINOR_VERSION = VERSION==V12_2 || VERSION==V16_5 || VERSION==V18_2;
    
    protected ResourceLocationAPI<?> registryName;
    
    public abstract API build();
    
    /**
     * Assumes the target class is in the same package as the instance of this class.
     * Finds the version qualified target class and initializes it with the input args
     * Minor versions will be qualified for 1.12 (1.12.2), 1.16 (1.16.5), and 1.18 (1.18.2)
     */
    protected <T> T findAndInitializeForVersion(String baseClassName, Object ... args) {
        return findAndInitializeForVersion(baseClassName,DEFAULT_MINOR_VERSION,args);
    }
    
    /**
     * Assumes the target class is in the same package as the instance of this class.
     * Finds the version qualified target class and initializes it with the input args
     * Minor versions will be qualified for 1.12 (1.12.2), 1.16 (1.16.5), and 1.18 (1.18.2)
     */
    @SuppressWarnings("SameParameterValue")
    protected <T> T findAndInitializeForVersion(String baseClassName, boolean minor, Object ... args) {
        return ClassHelper.findAndInitialize(getClassForVersion(baseClassName,minor),args);
    }
    
    /**
     * Assumes the target class is in the same package as the instance of this class.
     * Returns the version qualified name of the target class.
     * Minor versions will be qualified for 1.12 (1.12.2), 1.16 (1.16.5), and 1.18 (1.18.2)
     */
    @IndirectCallers
    protected String getClassForVersion(String className) {
        return getClassForVersion(className,DEFAULT_MINOR_VERSION);
    }
    
    /**
     * Assumes the target class is in the same package as the instance of this class.
     * Returns the version qualified name of the target class
     */
    protected String getClassForVersion(String className, boolean minor) {
        return VERSION.withClassExt(getClass().getPackage().getName()+"."+className,minor);
    }
    
    public RegistryEntryBuilder<API> setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}