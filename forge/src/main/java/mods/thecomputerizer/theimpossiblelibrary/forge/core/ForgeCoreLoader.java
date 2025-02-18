package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.Launcher;
import io.github.toolfactory.jvm.function.catalog.ConsulterSupplyFunction;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.burningwave.core.assembler.StaticComponentContainer.Configuration.Default;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class ForgeCoreLoader {
    
    private static final String API_PKG = "mods.thecomputerizer.theimpossiblelibrary.api";
    private static final String FORGE_PKG = "mods.thecomputerizer.theimpossiblelibrary.forge";
    private static final String APICORE = API_PKG+".core.CoreAPI";
    private static final Logger LOGGER = LogManager.getLogger("TIL ForgeCoreLoader");
    
    //The module system forced me to find a very powerful alternative, but at least I don't need to do Unsafe hacking
    static {
        Default.add(burningWaveProperties());
        if(isJava8()) LOGGER.info("I see you are running Java 8. Good choice, but I'll be using burningwave anyways");
        else {
            LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
            String pkg = ConsulterSupplyFunction.class.getPackage().getName();
            ClassLoader thisLoader = ForgeCoreLoader.class.getClassLoader();
            Map<String,Object> packageLookup = Fields.get(thisLoader,"packageLookup");
            Object module = packageLookup.get(pkg);
            if(Objects.nonNull(module)) {
                LOGGER.info("GOT RESOLVED MODULE FOR {} AS {}",pkg,module);
                addResolvedModule(module,thisLoader);
                packageLookup.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
            } else LOGGER.fatal("FAILED TO GET RESOLVED MODULE FOR {}",pkg);
        }
    }
    
    /**
     * Man I hope I don't need to deal with the module graph
     */
    @SuppressWarnings("unchecked")
    private static void addConfigurationModule(Object configuration, String name, Object resolvedModule,
            ClassLoader thisLoader) {
        //Set<?> modules = Fields.get(configuration,"modules");
        //Set<?> newModules = new HashSet<>(modules);
        //((Set<Object>)newModules).add(resolvedModule);
        //Fields.set(configuration,"modules",Collections.unmodifiableSet(newModules));
        Map<String,?> nameToModule = Fields.get(configuration,"nameToModule");
        Map<String,?> newMap = new HashMap<>(nameToModule);
        ((Map<String,Object>)newMap).putIfAbsent(name,resolvedModule);
        Fields.set(configuration,"nameToModule",Collections.unmodifiableMap(newMap));
        
        //Prevent reading duplicate modules
        Object thisConfig = Fields.get(thisLoader,"configuration");
        removeFromUnmodifiableSetField(thisConfig,"modules",resolvedModule);
        removeFromUnmodifiableMapField(thisConfig,"nameToModule",name);
        
        //Update the configuration field for the module
        Fields.set(resolvedModule,"cf",thisConfig);
        
        //Deal with the module graph ._.
        Map<?,Set<?>> thisGraph = new HashMap<>(Fields.get(thisConfig,"graph"));
        thisGraph.entrySet().removeIf(entry -> resolvedName(entry.getKey()).equals(name));
        thisGraph.forEach((key,values) -> values.remove(resolvedModule));
        Fields.set(thisConfig,"graph",thisGraph);
    }
    
    /**
     * So basically the only way to guaruntee stuff will work in the BOOT layer is if it can find the right
     * package in the right ResolvedModule. Luckily we already have those in the current (SERVICE) layer, so
     * all we need to do is transfer some stuff over and then handle the duplicates
     */
    private static void addResolvedModule(Object module, ClassLoader thisLoader) {
        ClassLoader loader = bootLoader();
        //String ref = "cpw.mods.cl.JarModuleFinder$JarModuleReference";
        Map<String,Object> roots = Fields.get(loader,"resolvedRoots");
        Map<String,Object> packageLookup = Fields.get(loader,"packageLookup");
        Object reference = Methods.invoke(module,"reference");
        Object descriptor = Methods.invoke(reference,"descriptor");
        String name = Methods.invoke(descriptor,"name");
        roots.put(name,reference);
        Set<String> packages = Methods.invoke(descriptor,"packages");
        for(String pkg : packages) {
            LOGGER.info("ADDING RESOLVED PACKAGE TO BOOT {}",pkg);
            packageLookup.put(pkg,module);
        }
        //Fix configurations & prevent reading duplicate modules
        addConfigurationModule(Fields.get(loader,"configuration"),name,module,thisLoader);
        Map<String,Object> theseRoots = Fields.get(thisLoader,"resolvedRoots");
        theseRoots.remove(name);
    }
    
    /**
     * Should be the ClassLoader for the BOOT layer or the system ClassLoader if Java 8
     */
    static ClassLoader bootLoader() {
        ClassLoader loader = Launcher.class.getClassLoader();
        return Objects.nonNull(loader) ? loader : ClassLoader.getSystemClassLoader();
    }
    
    static Map<?,?> burningWaveProperties() {
        Map<Object,Object> properties = new HashMap<>();
        properties.put("banner.hide","true");
        properties.put("managed-logger.repository.enabled","false");
        return properties;
    }
    
    /**
     * Get the command line argument handler in case we need to check stuff very early in the loading process
     */
    static ArgumentHandler getArgumentHandler() {
        LOGGER.info("Atempting to get ArgumentHandler for loader {}",Thread.currentThread().getContextClassLoader());
        Object args = getField(INSTANCE.getClass(),"argumentHandler",INSTANCE);
        if(!(args instanceof ArgumentHandler)) {
            LOGGER.error("Failed to find argument handler!");
            return null;
        }
        return (ArgumentHandler)args;
    }
    
    public static @Nullable Object getBootLoadedCoreAPI() {
        return getCoreAPIReflectively(bootLoader());
    }
    
    static Object getCoreAPIReflectively(ClassLoader loader) {
        try {
            return getField(Class.forName(APICORE,false,loader),"INSTANCE",null);
        } catch(ClassNotFoundException ex) {
            LOGGER.debug("CoreAPI not found on {}",loader);
        }
        return null;
    }
    
    static @Nullable Object getField(Class<?> cls, String name, @Nullable Object instance) {
        try {
            Field field = cls.getDeclaredField(name);
            if(!field.isAccessible()) field.setAccessible(true);
            return field.get(instance);
        } catch(Exception ex) {
            LOGGER.error("Failed to get field {} from {} on instance {}",name,cls,instance,ex);
        }
        return null;
    }
    
    static String getVersionStr() {
        ArgumentHandler handler = getArgumentHandler();
        if(Objects.isNull(handler)) return null;
        String[] rawArgs = (String[])getField(handler.getClass(),"args",handler);
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find version using handler {}",handler);
            return null;
        }
        int versionIndex = -1;
        for(int i=0;i<rawArgs.length;i++) {
            if(rawArgs[i].equals("--fml.mcVersion")) {
                versionIndex = i+1;
                break;
            }
        }
        if(versionIndex>=0) {
            LOGGER.info("Found fml.mcVersion arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        LOGGER.error("Failed to find version from {}",Arrays.toString(rawArgs));
        return null;
    }
    
    /**
     * Returns a CoreAPI instance on the input ClassLoader. Initializes the source if necessary
     */
    static @Nullable Object initCoreAPI(ClassLoader loader) {
        LOGGER.debug("Starting CoreAPI init");
        Object bootInstance = getBootLoadedCoreAPI();
        if(Objects.nonNull(bootInstance)) {
            LOGGER.info("Returning existing CoreAPI instance found in the BOOT layer");
            return bootInstance;
        }
        String version = getVersionStr();
        Class<?> coreClass = loadAPI(version);
        try {
            return coreClass.newInstance();
        } catch(InstantiationException | IllegalAccessException ex) {
            LOGGER.fatal("Caught reflection exception while trying to get CoreAPI instance as {}",coreClass,ex);
        } catch(Exception ex) {
            LOGGER.fatal("Unknown error while trying to get CoreAPI instance as {}",coreClass,ex);
        }
        LOGGER.fatal("Failed to initialize CoreAPI [Forge-{}] using {}",version,loader);
        return null;
    }
    
    static boolean isJava8() {
        return System.getProperty("java.version").startsWith("1.");
    }
    
    /**
     * Define necessary classes for the versioned CoreAPI instance
     * Returns the instance class
     */
    static Class<?> loadAPI(String version) {
        Class<?> clazz = ClassHelper.findClass(versionClassName("core.TILCoreForge",version),bootLoader());
        if(Objects.isNull(clazz)) throw new RuntimeException("Failed to load CoreAPI instance [Forge-"+version+"]");
        LOGGER.info("Successfully loaded CoreAPI instance {}",clazz);
        return clazz;
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeFromUnmodifiableMapField(Object object, String name, Object toRemove) {
        Map<?,?> map = Fields.get(object,name);
        Map<?,?> copy = new HashMap<>(map);
        copy.remove(toRemove);
        Fields.set(object,name,Collections.unmodifiableMap(copy));
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeFromUnmodifiableSetField(Object object, String name, Object toRemove) {
        Set<?> set = Fields.get(object,name);
        Set<?> copy = new HashSet<>(set);
        copy.remove(toRemove);
        Fields.set(object,name,Collections.unmodifiableSet(copy));
    }
    
    /**
     * Get name of resolved module via reflection since this is a Java 8 context
     */
    static String resolvedName(Object resolvedModule) {
        Object descriptor = Methods.invoke(resolvedModule,"descriptor");
        return Methods.invoke(descriptor,"name");
    }
    
    /**
     * Include any packages after the base. Should include Forge in name if necessary
     */
    @SuppressWarnings("SameParameterValue")
    static String versionClassName(String name, String version) {
        return versionPackage(version)+"."+versionQuantify(name,version);
    }
    
    /**
     * ModLoader will always be Forge so we can cheat this a bit more than the CoreAPI implementation
     */
    static String versionPackage(String version) {
        String[] split = version.split("\\.");
        if(split.length<3) throw new RuntimeException("Can't parse package for unknown version "+version);
        return FORGE_PKG+".v"+split[1]+".m"+split[2];
    }
    
    static String versionQuantify(String name, String version) {
        return name+version.replace('.','_');
    }
}
