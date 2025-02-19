package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.Environment;
import cpw.mods.modlauncher.Launcher;
import io.github.toolfactory.jvm.function.catalog.ConsulterSupplyFunction;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.burningwave.core.assembler.StaticComponentContainer.Configuration.Default;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Figures out which version to load on and how to load stuff on it
 */
public class ForgeCoreLoader {
    
    private static final String API_PKG = "mods.thecomputerizer.theimpossiblelibrary.api";
    private static final String FORGE_PKG = "mods.thecomputerizer.theimpossiblelibrary.forge";
    private static final String APICORE = API_PKG+".core.CoreAPI";
    private static final Logger LOGGER = LogManager.getLogger("TIL ForgeCoreLoader");
    
    //The module system forced me to find a very powerful alternative, but at least I don't need to do Unsafe hacking
    static {
        if(ForgeCoreLoader.class.getClassLoader()!=bootLoader()) {
            if(isJava8()) LOGGER.info("I see you are running Java 8. Good choice, but I'll be using burningwave anyways");
            else LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
        }
        try {
            Default.add(burningWaveProperties());
        } catch(Throwable t) {
            LOGGER.error("Failed to set default BuringWave properties??",t);
        }
    }
    
    /**
     * Fix Configuration instance for both the BOOT and SERVICE layers
     */
    private static void addConfigurationModule(Object configuration, String name, Object resolvedModule,
            ClassLoader thisLoader) {
        //Set<Object> modules = new HashSet<>(Fields.get(configuration,"modules"));
        //modules.add(resolvedModule);
        //Fields.set(configuration,"modules",Collections.unmodifiableSet(modules));
        
        Map<String,Object> nameToModule = new HashMap<>(Fields.get(configuration,"nameToModule"));
        nameToModule.putIfAbsent(name,resolvedModule);
        Fields.set(configuration,"nameToModule",Collections.unmodifiableMap(nameToModule));
        
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
        Map<String,Object> roots = Fields.get(loader,"resolvedRoots");
        Map<String,Object> packageLookup = Fields.get(loader,"packageLookup");
        Object reference = Methods.invoke(module,"reference");
        Object descriptor = Methods.invoke(reference,"descriptor");
        String name = Methods.invoke(descriptor,"name");
        roots.put(name,reference);
        Set<String> packages = Methods.invoke(descriptor,"packages");
        for(String pkg : packages) {
            packageLookup.put(pkg,module);
        }
        
        //Finalize by moving the original Module from SERVICE to the BOOT layer & fixing parent loaders
        moveModuleToLayer(loader,"BOOT","SERVICE",name);
        Map<String,ClassLoader> parentLoaders = Fields.get(thisLoader,"parentLoaders");
        for(String pkg : packages) parentLoaders.put(pkg,loader);
        
        //Fix configurations & prevent reading duplicate modules
        addConfigurationModule(Fields.get(loader,"configuration"),name,module,thisLoader);
        Map<String,Object> theseRoots = Fields.get(thisLoader,"resolvedRoots");
        theseRoots.remove(name);
        
        LOGGER.info("Finished migrating module {} from the SERVICE layer to the BOOT layer",name);
    }
    
    /**
     * Should be the ClassLoader for the BOOT layer or the system ClassLoader if Java 8
     */
    public static ClassLoader bootLoader() {
        ClassLoader loader = Launcher.class.getClassLoader();
        return Objects.nonNull(loader) ? loader : ClassLoader.getSystemClassLoader();
    }
    
    static Map<?,?> burningWaveProperties() {
        Map<Object,Object> properties = new HashMap<>();
        properties.put("banner.hide","true");
        properties.put("managed-logger.repository.enabled","false");
        return properties;
    }
    
    static Class<?> findClassInHeirarchy(ClassLoader loader, String className) {
        Class<?> foundClass = null;
        ClassLoader searchIn = loader;
        while(Objects.nonNull(searchIn)) {
            try {
                foundClass = Driver.getClassByName(className,false,loader,Classes.getClass());
            } catch(Throwable t) {
                LOGGER.debug("Class not found in ClassLoader {} (name = {})",searchIn,className);
            }
            if(Objects.nonNull(foundClass)) break;
            searchIn = ClassLoaders.getParent(searchIn);
        }
        if(Objects.isNull(foundClass)) {
            LOGGER.error("Class {} not found in ClassLoader heirarchy for {}",className,loader);
            return null;
        } else LOGGER.info("Found class {} on loader {}",className,searchIn);
        return foundClass;
    }
    
    public static void fixIfNotJava8() {
        if(!isJava8()) {
            String pkg = ConsulterSupplyFunction.class.getPackage().getName();
            ClassLoader thisLoader = ForgeCoreLoader.class.getClassLoader();
            Map<String,Object> packageLookup = Fields.get(thisLoader,"packageLookup");
            Object module = packageLookup.get(pkg);
            if(Objects.nonNull(module)) {
                addResolvedModule(module,thisLoader);
                packageLookup.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
            } else LOGGER.fatal("FAILED TO GET RESOLVED MODULE FOR {}",pkg);
        }
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
    
    static <E extends Enum<E>> E getEnum(ClassLoader loader, String className, String name) {
        Class<?> foundClass = findClassInHeirarchy(loader,className);
        return Objects.nonNull(foundClass) ? getEnum(foundClass,name) : null;
    }
    
    @SuppressWarnings("unchecked")
    static <E extends Enum<E>> E getEnum(Class<?> enumClass, String name) {
        return Enum.valueOf((Class<E>)enumClass,name);
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
    
    /**
     * Get a Layer enum by name
     */
    static Object getLayer(String name) {
        ClassLoader loader = bootLoader();
        String className = "cpw.mods.modlauncher.api.IModuleLayerManager$Layer";
        return getEnum(loader,className,name);
    }
    
    /**
     * Get IModuleLayerManager based on current environment
     */
    static Object getLayerManager() {
        Environment env = INSTANCE.environment();
        return ((Optional<?>)Methods.invoke(env,"findModuleLayerManager")).orElse(null);
    }
    
    public static Object getModuleFromPackage(String pkg, String layerName) {
        Object layer = getModuleLayer(layerName);
        Map<String,Object> packageLookup = Fields.get(layerClassLoader(layerName),"packageLookup");
        Object resolved = packageLookup.get(pkg);
        if(Objects.isNull(resolved)) {
            LOGGER.error("Cannot get module for pacakge {} since it does not exist in input layer {}!",pkg,layerName);
            return null;
        }
        Map<String,Object> nameToModule = Fields.get(layer,"nameToModule");
        return nameToModule.get(resolvedName(resolved));
    }
    
    /**
     * Get a ModuleLayer instance by name (BOOT/SERVICE/PLUGIN/GAME) for module manipulation
     * 1.18.2+ only
     */
    static Object getModuleLayer(String name) {
        Object layerEnum = getLayer(name);
        if(Objects.isNull(layerEnum)) {
            LOGGER.error("Layer not found for name {}!",name);
            return null;
        }
        Object layerManager = getLayerManager();
        if(Objects.isNull(layerManager)) {
            LOGGER.error("IModuleLayerManager instance not found in environment!");
            return null;
        }
        return ((Optional<?>)Methods.invoke(layerManager,"getLayer",layerEnum)).orElse(null);
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
    
    public static boolean isJava8() {
        return System.getProperty("java.version").startsWith("1.");
    }
    
    /**
     * Tries to get the ClassLoader instance associated with the given layer name
     */
    public static ClassLoader layerClassLoader(String name) {
        Object layer = getLayer(name);
        Object layerManager = getLayerManager();
        if(Objects.isNull(layer) || Objects.isNull(layerManager)) {
            LOGGER.error("Layer manager or layer with name {} is null! Boot loader will be returned",name);
            return bootLoader();
        }
        Map<?,?> completedLayers = Fields.get(layerManager,"completedLayers");
        ClassLoader loader = Fields.get(completedLayers.get(layer),"cl");
        TILDev.logInfo("Returning ClassLoader for layer {} as {}",name,loader);
        return loader;
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
    
    public static String moduleName(Object module) {
        return Methods.invoke(module,"getName");
    }
    
    @SuppressWarnings("SameParameterValue")
    public static void moveModuleToLayer(ClassLoader targetLoader, String layerTo, String layerFrom, String moduleName) {
        Object to = getModuleLayer(layerTo);
        if(Objects.isNull(to)) {
            LOGGER.error("Unable to move module {}! Cannot find target layer {}",moduleName,layerTo);
            return;
        }
        Object from = getModuleLayer(layerFrom);
        if(Objects.isNull(from)) {
            LOGGER.error("Unable to move module {}! Cannot find supplier layer {}",moduleName,layerFrom);
            return;
        }
        String fieldName = "nameToModule";
        Map<String,Object> moduleMapFrom = new HashMap<>(Fields.get(from,fieldName));
        Object module = moduleMapFrom.get(moduleName);
        if(Objects.isNull(module)) {
            LOGGER.error("Unable to move module {}! Cannot find module in supplier layer {}",moduleName,layerFrom);
            return;
        }
        Fields.set(module,"loader",targetLoader);
        Fields.set(module,"layer",to);
        Map<String,Object> moduleMapTo = new HashMap<>(Fields.get(to,fieldName));
        moduleMapTo.putIfAbsent(moduleName,module);
        Fields.set(to,fieldName,Collections.unmodifiableMap(moduleMapTo));
        moduleMapFrom.remove(moduleName);
        Fields.set(from,fieldName,Collections.unmodifiableMap(moduleMapFrom));
    }
    
    /**
     * Not present in Java 8
     */
    static ClassLoader platformLoader() {
        return Methods.invokeStatic(ClassLoader.class,"getPlatformClassLoader");
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
     * Since this class is intially loaded in the SERVICE layer which has BOOT as a parent separate from PLUGIN,
     * we need a workaround for the PLUGIN layer thinking there are duplicate modules.
     * This is needed since IModLanguageProvider implementations are forced into PLUGIN layer from service loading and
     * can likely only be called via reflection.
     */
    @IndirectCallers
    public static void resyncModules(ClassLoader loader, String layerName) {
        if(isJava8()) return; //Not needed on Java 8
        ClassLoader bootLoader = bootLoader();
        final String pkg = "mods.thecomputerizer.theimpossiblelibrary.forge.core";
        Map<String,Object> bootPkg = Fields.get(bootLoader,"packageLookup"); //Fix BOOT modules first
        Object bootModule = bootPkg.get(pkg);
        Object bootCfg = Fields.get(bootLoader,"configuration");
        if(!"PLUGIN".equals(layerName)) {
            Set<Object> modules = new HashSet<>(Fields.get(bootCfg,"modules"));
            modules.add(bootModule);
            Fields.set(bootCfg,"modules",modules);
        }
        Map<String,Object> pkgs = Fields.get(loader,"packageLookup"); //Remove module from PLUGIN layer
        Object module = pkgs.get(pkg);
        String name = resolvedName(module);
        Map<String,Object> roots = Fields.get(loader,"resolvedRoots");
        roots.remove(name);
        Object config = Fields.get(loader,"configuration");
        removeFromUnmodifiableSetField(config,"modules",module);
        removeFromUnmodifiableMapField(config,"nameToModule",name);
        Object reference = Methods.invoke(module,"reference");
        Object descriptor = Methods.invoke(reference,"descriptor");
        Set<String> packages = Methods.invoke(descriptor,"packages");
        
        //Finalize by dealing with the module layers & fixing parent loaders
        Object layer = getModuleLayer(layerName);
        Map<String,Object> map = new HashMap<>(Fields.get(layer,"nameToModule"));
        map.remove(name);
        Fields.set(layer,"nameToModule",map);
        Map<String,ClassLoader> parentLoaders = Fields.get(loader,"parentLoaders");
        for(String p : packages) parentLoaders.put(p,bootLoader);
        
        //Deal with the module graph again ._.
        Map<?,Set<?>> graph = new HashMap<>(Fields.get(config,"graph"));
        graph.remove(module); //Don't cross-check the name since we have 2 different module this time
        graph.forEach((key,values) -> values.remove(module));
        Fields.set(config,"graph",graph);
        
        pkgs.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
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
