package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.Environment;
import cpw.mods.modlauncher.Launcher;
import io.github.toolfactory.jvm.function.catalog.ConsulterSupplyFunction;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.Map.Entry;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Figures out which version to load on and how to load stuff on it
 */
@SuppressWarnings("unused") 
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
        ClassHelper.checkBurningWaveInit();
    }
    
    /**
     * Fix Configuration instance for both the BOOT and SERVICE layers
     */
    private static void addConfigurationModule(Object configuration, String name, Object resolvedModule,
            ClassLoader thisLoader) {
        
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
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(Object module, Object resolvedModule, Object moduleLayer, String name,
            Set<String> packages, Object moduleRef, ClassLoader target) {
        Fields.set(module,"name",name);
        Object configuration = Fields.get(target,"configuration");
        Map<String,Object> resolvedRoots = Fields.get(target,"resolvedRoots");
        Map<String,Object> packageLookup = Fields.get(target,"packageLookup");
        Map<String,ClassLoader> parentLoaders = Fields.get(target,"parentLoaders");
        resolvedRoots.put(name,moduleRef);
        for(String pkg : packages) packageLookup.put(pkg,resolvedModule);
        parentLoaders.entrySet().removeIf(entry -> packages.contains(entry.getKey()));
        Set<Object> configModules = new HashSet<>(Fields.get(configuration,"modules"));
        Map<String,Object> configNameToModule = new HashMap<>(Fields.get(configuration,"nameToModule"));
        configModules.removeIf(rm -> name.equals(resolvedName(rm)));
        configModules.add(resolvedModule);
        configNameToModule.put(name,resolvedModule);
        Fields.set(configuration,"modules",Collections.unmodifiableSet(configModules));
        Fields.set(configuration,"nameToModule",Collections.unmodifiableMap(configNameToModule));
        Set<Object> layerModules = Fields.get(moduleLayer,"modules");
        boolean found = false;
        if(Objects.nonNull(layerModules)) {
            layerModules = new HashSet<>(layerModules);
            for(Object lModule : layerModules) {
                if(moduleName(lModule).equals(moduleName(module))) {
                    found = true;
                }
            }
        }
        if(!found) {
            if(Objects.nonNull(layerModules)) {
                layerModules.add(module);
                Fields.set(moduleLayer,"modules",layerModules);
            }
            Map<String,Object> layerNameToModule = new HashMap<>(Fields.get(moduleLayer,"nameToModule"));
            layerNameToModule.put(name,module);
            Fields.set(moduleLayer,"nameToModule", Collections.unmodifiableMap(layerNameToModule));
        }
        Fields.set(module,"layer",moduleLayer);
        Fields.set(module,"loader",target);
        Fields.set(resolvedModule,"cf",configuration);
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
    
    static Object buildNewModuleDescriptor(String name, Object secureJar, List<String> usesServices) throws Throwable {
        LOGGER.info("Building new module descriptor for {}",name);
        Set<String> packages = new HashSet<>(Methods.invoke(secureJar,"getPackages"));
        Collection<Object> providers = Methods.invoke(secureJar,"getProviders");
        Class<?> cDesc = Class.forName("java.lang.module.ModuleDescriptor");
        Object metadata = Fields.get(secureJar,"metadata");
        String version = Methods.invoke(metadata,"version");
        Object builder = Methods.invokeStatic(cDesc,"newAutomaticModule",name);
        builder = Methods.invoke(builder,"version",version);
        builder = Methods.invoke(builder,"packages",packages);
        for(Object provider : providers) {
            Collection<String> actualProviders = Methods.invoke(provider,"providers");
            if(!actualProviders.isEmpty()) {
                String service = Methods.invoke(provider,"serviceName");
                Methods.invoke(builder,"provides",service,new ArrayList<>(actualProviders));
            }
        }
        for(String service : usesServices) Methods.invoke(builder,"uses",service);
        Object desc = Methods.invoke(builder,"build");
        LOGGER.info("Finished building descriptor {}",desc);
        return desc;
    }
    
    static Map<?,?> burningWaveProperties() {
        Map<Object,Object> properties = new HashMap<>();
        properties.put("banner.hide","true");
        properties.put("managed-logger.repository.enabled","false");
        return properties;
    }
    
    /**
     * After the hacking the module system to get everything to load properly, we need to make sure all the clases
     * are moved to the game layer so the other game modules are accessible.
     * Any classes loaded to the BOOT, SERVICE, or PLUGIN layer that is a part of the given module will have their
     * classLoader field reassigned to the ClassLoader of the GAME layer
     */
    static void finalizeModule(String oldName, String newName, Object module, ClassLoader target,
            ClassLoader ... loaders) {
        String moduleName = moduleName(module);
        Set<Class<?>> allMoved = new HashSet<>();
        Map<ClassLoader,Collection<Class<?>>> removals = new HashMap<>();
        for(ClassLoader loader : loaders) {
            Collection<Class<?>> classes = Fields.get(loader,"classes");
            for(Class<?> c : classes) {
                String name = moduleName(Fields.get(c,"module"));
                if(moduleName.equals(oldName) || moduleName.equals(newName)) {
                    Fields.set(c,"classLoader",target);
                    allMoved.add(c);
                    removals.putIfAbsent(loader,new HashSet<>());
                    removals.get(loader).add(c);
                    Fields.set(c,"module",module);
                }
            }
        }
        //Handle the ClassLoader side & make sure classes on the target loader aren't in a nonexistant module
        Collection<Class<?>> targetClasses = Fields.get(target,"classes");
        for(Class<?> targetClass : targetClasses) {
            String name = moduleName(Fields.get(targetClass,"module"));
            if(moduleName.equals(name)) Fields.set(targetClass,"module",module);
        }
        targetClasses.addAll(allMoved);
        for(Entry<ClassLoader,Collection<Class<?>>> removalEntry : removals.entrySet()) {
            Collection<Class<?>> classes = Fields.get(removalEntry.getKey(),"classes");
            classes.removeAll(removalEntry.getValue());
        }
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
        }
        return foundClass;
    }
    
    /**
     * Returns an array where the elements are the ClassLoader, resolved module, and the name of the layer.
     * Assumes the given loaders array will always be in the order of BOOT, SERVICE, PLUGIN, GAME
     */
    public static Object[] findModuleLoaderForPackage(String pkg, ClassLoader[] loaders) {
        for(int i=0;i<loaders.length;i++) {
            ClassLoader loader = loaders[i];
            String name = i==0 ? "BOOT" : (i==1 ? "SERVICE" : "PLUGIN");
            Map<String,Object> lookup = Fields.get(loader,"packageLookup");
            Object resolvedModule = lookup.get(pkg);
            if(Objects.nonNull(resolvedModule)) return new Object[]{loader,resolvedModule,name};
        }
        return null;
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
    
    @SuppressWarnings({"unchecked","SameParameterValue"})
    static Object getModuleFromLayer(String layerName, String name) {
        return ((Map<String,Object>)Fields.get(getModuleLayer(layerName),"nameToModule")).get(name);
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
        LOGGER.debug("Returning ClassLoader for layer {} as {}",name,loader);
        return loader;
    }
    
    /**
     * Define necessary classes for the versioned CoreAPI instance
     * Returns the instance class
     */
    static Class<?> loadAPI(String version) {
        String className = versionClassName("core.TILCoreForge",version);
        ClassLoader loader = bootLoader();
        if(isJava8()) {
            URL source = ClassHelper.getSourceURL(ForgeCoreLoader.class);
            if(!ClassHelper.loadURL((URLClassLoader)loader,source))
                LOGGER.error("Failed to load source {}",source);
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className,true,loader);
        } catch(Exception ex) {
            LOGGER.error("Failed to load class {} for {}",className,loader,ex);
        }
        if(Objects.isNull(clazz)) throw new RuntimeException("Failed to load CoreAPI instance [Forge-"+version+"]");
        LOGGER.info("Successfully loaded CoreAPI instance {}",clazz);
        return clazz;
    }
    
    @SuppressWarnings("SameParameterValue")
    static void loadNewModuleTo(@Nullable IModInfo mod, String targetLayerName, Set<String> finalizedPkgs) {
        if(Objects.isNull(mod)) {
            LOGGER.error("Cannot load module from nonexistant file!");
            return;
        }
        try {
            Object fileInfo = mod.getOwningFile();
            Object file = Methods.invoke(fileInfo,"getFile");
            Object secureJar = Methods.invoke(file,"getSecureJar");
            ClassLoader targetLoader = layerClassLoader("GAME");
            String existingName = Methods.invoke(secureJar,"name");
            String name = mod.getModId(); //Usually the same as existingName, but there are some edge cases...
            Object layer = getModuleLayer(targetLayerName);
            Map<String,Object> nameToModule = Fields.get(layer,"nameToModule");
            Object module = nameToModule.get(existingName);
            if(Objects.isNull(module)) module = nameToModule.get(name);
            boolean existed = false;
            if(Objects.nonNull(module)) {
                LOGGER.info("Found existing module to set up for {}",name);
                existed = true;
            } else LOGGER.info("Setting up new module with name {}",name);
            Object descriptor;
            if(Objects.nonNull(module)) {
                descriptor = Fields.get(module,"descriptor");
                Fields.set(descriptor,"name",name);
            }
            else {
                List<String> usesServices = Methods.invoke(fileInfo,"usesServices");
                descriptor = buildNewModuleDescriptor(name,secureJar,usesServices);
            }
            Class<?> rClass = Class.forName("cpw.mods.cl.JarModuleFinder$JarModuleReference");
            Object reference = Constructors.newInstanceOf(rClass,secureJar);
            URI uri = Fields.get(reference,"location");
            Object config = Fields.get(targetLoader,"configuration");
            Class<?> refClass = reference.getClass().getSuperclass();
            Class<?> cResolved = Class.forName("java.lang.module.ResolvedModule");
            Fields.set(reference,"descriptor",descriptor);
            Object resolvedModule = Constructors.newInstanceOf(cResolved,config,reference);
            Set<String> packages = new HashSet<>(resolvedPackages(resolvedModule));
            packages.removeAll(finalizedPkgs);
            packages = Collections.unmodifiableSet(packages);
            finalizedPkgs.addAll(packages);
            Class<?> cModule = Class.forName("java.lang.Module");
            if(Objects.isNull(module)) module = Constructors.newInstanceOf(cModule,layer,targetLoader,descriptor,uri);
            addModuleThouroughly(module,resolvedModule,layer,name,packages,reference,targetLoader);
            LOGGER.info("Finished setting up {}",module);
            
            //nuke & finalize
            ClassLoader boot = bootLoader();
            ClassLoader service = layerClassLoader("SERVICE");
            ClassLoader plugin = layerClassLoader("PLUGIN");
            nukeConfig(name,boot,service,plugin);
            nukeLoaderFields(name,boot,service,plugin);
            nukeModuleLayer(name,"BOOT","SERVICE","PLUGIN");
            if(!existingName.equals(name) && existed) {
                nukeConfig(existingName,boot,service,plugin,targetLoader);
                nukeLoaderFields(existingName,boot,service,plugin,targetLoader);
                nukeModuleLayer(existingName,"BOOT","SERVICE","PLUGIN","GAME");
            }
            finalizeModule(existingName,name,module,targetLoader,boot,service,plugin);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
            LOGGER.warn("SUCCESSFULLY LOADED {} TO THE GAME LAYER HAVE A NICE DAY", name);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
        } catch(Throwable t) {
            LOGGER.error("Failed to load new module!",t);
        }
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
     * Add the module for the given package to the GAME layer and nuke all references to it from other layers
     */
    public static void nukeAndFinalize(IModInfo mod, String pkg, Set<String> finalizedPkgs) {
        LOGGER.info("Finalizing package {}",pkg);
        ClassLoader boot = bootLoader();
        ClassLoader service = layerClassLoader("SERVICE");
        ClassLoader plugin = layerClassLoader("PLUGIN");
        Object[] found = findModuleLoaderForPackage(pkg,new ClassLoader[]{boot,service,plugin});
        if(Objects.isNull(found)) {
            loadNewModuleTo(mod,"GAME",finalizedPkgs);
            return;
        }
        ClassLoader foundLoader = (ClassLoader)found[0];
        Object resolvedModule = found[1];
        LOGGER.info("Got resolved module as {}",resolvedModule);
        String name = resolvedName(resolvedModule);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        LOGGER.warn("NUKING ALL REFERENCES OF MODULE {} FROM THE BOOT, SERVICE, & PLUGIN LAYERS",name);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        Map<String,Object> bootRoots = Fields.get(foundLoader,"resolvedRoots");
        Object ref = bootRoots.get(name);
        Object foundLayer = getModuleLayer((String)found[2]);
        Map<String,Object> layerModules = Fields.get(foundLayer,"nameToModule");
        Object module = layerModules.get(name);
        ClassLoader target = layerClassLoader("GAME");
        Object moduleLayer = getModuleLayer("GAME");
        Set<String> packages = new HashSet<>(resolvedPackages(resolvedModule));
        packages.removeAll(finalizedPkgs);
        packages = Collections.unmodifiableSet(packages);
        finalizedPkgs.addAll(packages);
        addModuleThouroughly(module,resolvedModule,moduleLayer,name,packages,ref,target);
        
        //nuke & finalize
        nukeConfig(name,boot,service,plugin);
        nukeLoaderFields(name,boot,service,plugin);
        nukeModuleLayer(name,"BOOT","SERVICE","PLUGIN");
        finalizeModule(name,name,module,target,boot,service,plugin);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        LOGGER.warn("MODULE {} HAS BEEN SUCCESSFULLY MOVED TO THE GAME LAYER HAVE A NICE DAY",name);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
    }
    
    /**
     * Java 8 doesn't have modules, so move all classes loaded from the source of the given package to the target
     * ClassLoader and things should work fine.
     * Requires generated classes to be excluded from source searching.
     */
    public static void nukeAndFinalizeJava8(Set<Class<?>> getSourcesFrom, ClassLoader target, boolean first) {
        if(getSourcesFrom.isEmpty()) {
            LOGGER.error("No classes to get sources from!");
            return;
        }
        Set<String> sources = new HashSet<>();
        for(Class<?> from : getSourcesFrom) ClassHelper.addSource(sources,from);
        CoreAPI core = CoreAPI.getInstance();
        if(first) core.addSources(sources);
        LOGGER.info("Adding {} sources to target loader {}",sources.size(),target);
        sources.forEach(source -> {
            LOGGER.info("Adding source {}",source);
            core.addURLToClassLoader(target,source);
        });
    }
    
    static void nukeConfig(String name, ClassLoader ... loaders) {
        for(ClassLoader loader : loaders) {
            Object configuration = Fields.get(loader,"configuration");
            Map<String,Object> nameToModule = new HashMap<>(Fields.get(configuration,"nameToModule"));
            Object module = nameToModule.get(name);
            if(Objects.nonNull(module)) {
                nameToModule.remove(name);
                Fields.set(configuration,"nameToModule",Collections.unmodifiableMap(nameToModule));
                Set<Object> modules = new HashSet<>(Fields.get(configuration,"modules"));
                modules.remove(module);
                Fields.set(configuration,"modules",modules);
            }
        }
    }
    
    static void nukeLoaderFields(String moduleName, ClassLoader ... loaders) {
        for(ClassLoader loader : loaders) {
            Map<String,Object> resolvedRoots = Fields.get(loader,"resolvedRoots");
            Map<String,Object> packageLookup = Fields.get(loader,"packageLookup");
            Map<String,Object> parentLoaders = Fields.get(loader,"parentLoaders");
            resolvedRoots.remove(moduleName);
            Object module = null;
            for(Entry<String,Object> pkgEntry : packageLookup.entrySet()) {
                Object value = pkgEntry.getValue();
                if(moduleName.equals(resolvedName(value))) {
                    module = value;
                    break;
                }
            }
            if(Objects.isNull(module)) continue;
            Set<String> packages = resolvedPackages(module);
            if(Objects.isNull(packages)) continue;
            for(String pkg : packages) {
                packageLookup.remove(pkg);
                parentLoaders.remove(pkg);
            }
        }
    }
    
    static void nukeModuleLayer(String name, String ... layers) {
        for(String layer : layers) {
            Object moduleLayer = getModuleLayer(layer);
            Map<String,Object> nameToModule = new HashMap<>(Fields.get(moduleLayer,"nameToModule"));
            nameToModule.remove(name);
            Fields.set(moduleLayer,"nameToModule",Collections.unmodifiableMap(nameToModule));
            Set<Object> modules = Fields.get(moduleLayer,"modules");
            if(Objects.nonNull(modules)) {
                modules = new HashSet<>(modules);
                modules.removeIf(m -> name.equals(moduleName(m)));
                Fields.set(moduleLayer,"modules",Collections.unmodifiableSet(modules));
            }
        }
    }
    
    /**
     * Not present in Java 8
     */
    static ClassLoader platformLoader() {
        return Methods.invokeStatic(ClassLoader.class,"getPlatformClassLoader");
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeFromUnmodifiableMapField(Object object, String name, Object toRemove) {
        Map<?,?> map = new HashMap<>(Fields.get(object,name));
        map.remove(toRemove);
        Fields.set(object,name,Collections.unmodifiableMap(map));
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeFromUnmodifiableSetField(Object object, String name, Object toRemove) {
        Set<?> set = new HashSet<>(Fields.get(object,name));
        set.remove(toRemove);
        Fields.set(object,name,Collections.unmodifiableSet(set));
    }
    
    /**
     * Get name of resolved module via reflection since this is a Java 8 context
     */
    static Object resolvedDescriptor(Object resolvedModule) {
        return Methods.invoke(resolvedModule,"descriptor");
    }
    
    /**
     * Get name of resolved module via reflection since this is a Java 8 context
     */
    static String resolvedName(Object resolvedModule) {
        return Methods.invoke(resolvedDescriptor(resolvedModule),"name");
    }
    
    static Set<String> resolvedPackages(Object resolvedModule) {
        return Fields.get(resolvedDescriptor(resolvedModule),"packages");
    }
    
    /**
     * Since this class is intially loaded in the SERVICE layer which has BOOT as a parent separate from PLUGIN,
     * we need a workaround for the PLUGIN layer thinking there are duplicate modules.
     * This is needed since IModLanguageProvider implementations are forced into PLUGIN layer from service loading and
     * can likely only be called via reflection.
     */
    public static void resyncModules(ClassLoader loaderTo, String layerTo, ClassLoader loaderFrom) {
        if(isJava8()) return; //Not needed on Java 8
        LOGGER.info("Resyncing module to {}",layerTo);
        final String pkg = "mods.thecomputerizer.theimpossiblelibrary.forge.core";
        Map<String,Object> fromPkg = Fields.get(loaderFrom,"packageLookup"); //Fix BOOT modules first
        Object fromModule = fromPkg.get(pkg);
        Object fromCfg = Fields.get(loaderFrom,"configuration");
        if(!"PLUGIN".equals(layerTo)) {
            Set<Object> modules = new HashSet<>(Fields.get(fromCfg,"modules"));
            modules.add(fromModule);
            Fields.set(fromCfg,"modules",modules);
        }
        Map<String,Object> pkgs = Fields.get(loaderTo,"packageLookup"); //Remove module from PLUGIN layer
        Object module = pkgs.get(pkg);
        String name = resolvedName(module);
        Map<String,Object> roots = Fields.get(loaderTo,"resolvedRoots");
        roots.remove(name);
        Object config = Fields.get(loaderTo,"configuration");
        removeFromUnmodifiableSetField(config,"modules",module);
        removeFromUnmodifiableMapField(config,"nameToModule",name);
        Object reference = Methods.invoke(module,"reference");
        Object descriptor = Methods.invoke(reference,"descriptor");
        Set<String> packages = Methods.invoke(descriptor,"packages");
        
        //Finalize by dealing with the module layers & fixing parent loaders
        Object layer = getModuleLayer(layerTo);
        Map<String,Object> map = new HashMap<>(Fields.get(layer,"nameToModule"));
        map.remove(name);
        Fields.set(layer,"nameToModule",map);
        Map<String,ClassLoader> parentLoaders = Fields.get(loaderTo,"parentLoaders");
        for(String p : packages) parentLoaders.put(p,loaderFrom);
        
        //Deal with the module graph again ._.
        Map<?,Set<?>> graph = new HashMap<>(Fields.get(config,"graph"));
        graph.remove(module); //Don't cross-check the name since we have 2 different module this time
        graph.forEach((key,values) -> values.remove(module));
        Fields.set(config,"graph",graph);
        
        pkgs.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
    }
    
    public static void sanityCheckModule(Class<?> c, String name) {
        Object module = Methods.invoke(c,"getModule");
        String actualName = moduleName(module);
        if(!name.equals(actualName)) {
            //By this point the class is definitely in the GAME layer regardless of whether the module is correct
            Fields.set(c,"module",getModuleFromLayer("GAME",name));
            LOGGER.info("Moved {} from module {} to module {}",c,actualName,name);
        }
    }
    
    public static void verifyModule(String className, IModInfo info, Object moduleLayer) throws Exception {
        LOGGER.info("Verifying that {} is valid for {} and can be found in {}",className,info,moduleLayer);
        IModFileInfo fileInfo = info.getOwningFile();
        String modid = info.getModId();
        LOGGER.info("Mod id is {} and owning file is {}",modid,fileInfo);
        String moduleName = Methods.invoke(fileInfo,"moduleName");
        IModFile file = Methods.invoke(fileInfo,"getFile");
        LOGGER.info("Module name is {} and file is {}",moduleName,file);
        if(!modid.equals(moduleName)) LOGGER.error("Mod id {} does not equal module name {}!",modid,moduleName);
        Optional<Object> optionalModule = Methods.invoke(moduleLayer,"findModule",moduleName);
        LOGGER.info("Module present? {}",optionalModule.isPresent());
        if(optionalModule.isPresent()) {
            Object module = optionalModule.get();
            LOGGER.info("Got module as {}",module);
            Class<?> c = Class.forName(className);
            LOGGER.info("Got class as {}",c);
            Object cModule = Methods.invoke(c,"getModule");
            LOGGER.info("Got module for class as {}",cModule);
            if(module!=cModule) {
                LOGGER.error("Modules are not equal! Attempting to fix");
                Fields.set(c,"module",module);
            } else LOGGER.info("Modules are equal");
        }
        LOGGER.info("Finished verifying {}",className);
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