package mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

import static java.lang.System.err;
import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.launcher;

/**
 * Abstraction for Forge/Neoforge SERVICE layer entrypoint launching
 */
public abstract class TILForgeLikeServiceLauncher {
    
    public static final String HACKS = "mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks";
    static TILForgeLikeServiceLauncher instance;
    
    private static Class<?> bootClass(String target, boolean useHacks) throws Throwable {
        return findClass(target,useHacks,bootLoader());
    }
    
    protected static ClassLoader bootLoader() {
        return instance.bootLoader;
    }
    
    protected static @Nullable Object callMethod(@Nullable Method m, @Nullable Object target, Object ... args) {
        if(Objects.isNull(m)) return null;
        try {
            return m.invoke(target,args);
        } catch(Throwable t) {
            instance.logger.fatal("Failed to invoke {} on target {} with args {}!",m,target,args,t);
        }
        return null;
    }
    
    private static void checkHacksInit(boolean direct) {
        checkHacksInit(direct,bootLoader());
    }
    
    public static void checkHacksInit(boolean direct, ClassLoader loader) {
        if(direct) {
            Hacks.checkBurningWaveInit();
            return;
        }
        try {
            callMethod(getMethod(findClass(HACKS,false,loader),"checkBurningWaveInit"),null);
        } catch(Throwable t) {
            instance.logger.fatal("Failed to invoke {}#checkBurningWaveInit in the BOOT layer!",HACKS,t);
        }
    }
    
    private static Class<?> findClass(String target, boolean useHacks, ClassLoader loader) throws Throwable {
        if(useHacks) {
            checkHacksInit(false);
            return Hacks.findClass(target,loader,true);
        }
        return Class.forName(target,true,loader);
    }
    
    @SuppressWarnings("SameParameterValue")
    protected static @Nullable Method getMethod(Class<?> c, String name, Class<?> ... argTypes) {
        try {
            return c.getDeclaredMethod(name,argTypes);
        } catch(Throwable declaredT) {
            instance.logger.debug("Declared method {} not found in {}! Checking for inherited method");
            try {
                return c.getMethod(name,argTypes);
            } catch(Throwable t) {
                instance.logger.error("Method {} not found in {} with args {}",name,c,argTypes,t);
                instance.logger.error("Declared method stacktrace",declaredT);
            }
        }
        return null;
    }
    
    public static void init(Class<?> caller, Class<?> instanceClass, boolean serviceLoaded) {
        printClassInit(instanceClass,serviceLoaded);
        try {
            instanceClass.getConstructor(Class.class).newInstance(caller);
        } catch(Throwable t) {
            err.println("Failed to initialize SERVICE launcher "+instanceClass);
            t.printStackTrace(err);
        }
    }
    
    protected static boolean java8() {
        return System.getProperty("java.version").startsWith("1.");
    }
    
    static Function<TILLauncher,Boolean> launcherCheck(String loader) {
        return "Forge".equals(loader) ? TILLauncher::isActiveForge : TILLauncher::isActiveNeoforge;
    }
    
    static void printClassInit(Class<?> c, boolean serviceLoaded) {
        out.println("["+(serviceLoaded ? "SERVICE" : "BOOT")+"] Class init: "+c.getName());
    }
    
    protected static Class<?> thisClass() {
        return instance.getClass();
    }
    
    protected static ClassLoader thisClassLoader() {
        return thisClass().getClassLoader();
    }
    
    final ClassLoader bootLoader;
    final String loader;
    protected final Logger logger;
    
    protected TILForgeLikeServiceLauncher(ClassLoader bootLoader, String loader) {
        instance = this;
        this.bootLoader = bootLoader;
        this.loader = loader;
        this.logger = initLogger();
    }
    
    boolean bootLoaded() {
        return this.bootLoader==thisClassLoader();
    }
    
    protected abstract String coreLoader();
    
    boolean handleServiceEntryPoint() {
        if(java8()) {
            this.logger.info("I see you are running Java 8. Good choice, but I'll be using burningwave anyways");
            try {
                checkHacksInit(true);
                ClassHelper.loadURL(this.bootLoader,thisClass());
                return true;
            } catch(Throwable t) {
                this.logger.fatal("Failed to handle Java 8 entrypoint??",t);
            }
            return false;
        }
        this.logger.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
        try {
            checkHacksInit(true);
            String moduleName = moduleName(thisClass());
            if(Objects.nonNull(moduleName)) {
                moveModule(moduleName);
                this.logger.info("Moved module {} to the BOOT layer",moduleName);
                return true;
            }
        } catch(Throwable t) {
            //We can't use the Logger here since it tries to load the class associated with each StackTraceElement
            //Since the class that tries to load may have just been moved (or errored while moving), we can't do that
            err.println("Entrypoint handling for the SERVICE layer failed!");
            t.printStackTrace(err);
            return false;
        }
        this.logger.error("Entrypoint handling for the SERVICE layer failed without a stacktrace??");
        return false;
    }
    
    /**
     * If this class is already on the BOOT layer we can initialize it directly
     */
    protected final void initCoreAPI(Class<?> caller) {
        if(Objects.nonNull(initCoreAPI())) {
            checkHacksInit(true);
            setModLoadingVersion(caller);
        } else this.logger.error("Failed to initialized {} CoreAPI instance in the BOOT layer!",loader);
    }
    
    protected abstract Object initCoreAPI();
    
    Logger initLogger() {
        if(Objects.isNull(launcher)) launcherCheck(this.loader).apply(TILLauncher.init(bootLoaded()));
        return launcher.getLogger();
    }
    
    protected final void load(Class<?> caller) {
        if(bootLoaded()) {
            initCoreAPI(caller);
            return;
        }
        this.logger.info("Handling SERVICE layer launcher");
        if(!handleServiceEntryPoint()) return;
        try {
            Hacks.invokeStatic(bootClass(coreLoader(),true),"initCoreAPI");
            this.logger.info("Successfully handled SERVICE layer initialized");
        } catch(Throwable t) {
            this.logger.fatal("Failed to initialize BOOT layer CoreAPI instance",t);
        }
    }
    
    protected abstract String modLoading();
    protected abstract String moduleName(Class<?> c);
    protected abstract void moveModule(String moduleName);
    
    void setModLoadingVersion(Class<?> caller) {
        try {
            Method m = getMethod(bootClass(modLoading(),false),"setLoadingVersion",Class.class);
            if(!Boolean.parseBoolean(String.valueOf(callMethod(m,null,caller))))
                throw new RuntimeException("Failed to set mod loading version for "+caller);
        } catch(Throwable t) {
            this.logger.fatal("Failed to set version for mod loading!",t);
        }
    }
}