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
import org.burningwave.core.classes.Fields.NoSuchFieldException;

import javax.annotation.Nullable;
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
@SuppressWarnings({"unused","LoggingSimilarMessage"})
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
        
        Map<String,Object> nameToModule = new HashMap<>(Fields.getDirect(configuration,"nameToModule"));
        nameToModule.putIfAbsent(name,resolvedModule);
        Fields.setDirect(configuration,"nameToModule",Collections.unmodifiableMap(nameToModule));
        
        //Prevent reading duplicate modules
        Object thisConfig = Fields.getDirect(thisLoader,"configuration");
        removeFromUnmodifiableSetField(thisConfig,"modules",resolvedModule);
        removeFromUnmodifiableMapField(thisConfig,"nameToModule",name);
        
        //Update the configuration field for the module
        Fields.setDirect(resolvedModule,"cf",thisConfig);
        
        //Deal with the module graph ._.
        Map<?,Set<?>> thisGraph = new HashMap<>(Fields.getDirect(thisConfig,"graph"));
        thisGraph.entrySet().removeIf(entry -> resolvedName(entry.getKey()).equals(name));
        thisGraph.forEach((key,values) -> values.remove(resolvedModule));
        Fields.setDirect(thisConfig,"graph",thisGraph);
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(Object module, Object resolvedModule, Object moduleLayer, String name,
            Set<String> packages, Object moduleRef, ClassLoader target, boolean newFormat) {
        Fields.setDirect(module,"name",name);
        Object configuration = Fields.getDirect(target,"configuration");
        Map<String,Object> resolvedRoots = Fields.getDirect(target,newFormat ? "ourModules" : "resolvedRoots");
        Map<String,Object> packageLookup = Fields.getDirect(target,newFormat ? "packageToOurModules" : "packageLookup");
        Map<String,ClassLoader> parentLoaders = Fields.getDirect(target,newFormat ? "packageToParentLoader" : "parentLoaders");
        resolvedRoots.put(name,moduleRef);
        for(String pkg : packages) packageLookup.put(pkg,resolvedModule);
        parentLoaders.entrySet().removeIf(entry -> packages.contains(entry.getKey()));
        Set<Object> configModules = new HashSet<>(Fields.getDirect(configuration,"modules"));
        Map<String,Object> configNameToModule = new HashMap<>(Fields.getDirect(configuration,"nameToModule"));
        configModules.removeIf(rm -> name.equals(resolvedName(rm)));
        configModules.add(resolvedModule);
        configNameToModule.put(name,resolvedModule);
        Fields.setDirect(configuration,"modules",Collections.unmodifiableSet(configModules));
        Fields.setDirect(configuration,"nameToModule",Collections.unmodifiableMap(configNameToModule));
        Set<Object> layerModules = Fields.getDirect(moduleLayer,"modules");
        boolean found = false;
        if(Objects.nonNull(layerModules)) {
            layerModules = new HashSet<>(layerModules);
            for(Object lModule : layerModules) {
                String lName = moduleName(lModule);
                if(Objects.nonNull(lName) && lName.equals(moduleName(module))) {
                    found = true;
                }
            }
        }
        if(!found) {
            if(Objects.nonNull(layerModules)) {
                layerModules.add(module);
                Fields.setDirect(moduleLayer,"modules",layerModules);
            }
            Map<String,Object> layerNameToModule = new HashMap<>(Fields.getDirect(moduleLayer,"nameToModule"));
            layerNameToModule.put(name,module);
            Fields.setDirect(moduleLayer,"nameToModule", Collections.unmodifiableMap(layerNameToModule));
        }
        Fields.setDirect(module,"layer",moduleLayer);
        Fields.setDirect(module,"loader",target);
        Fields.setDirect(resolvedModule,"cf",configuration);
    }
    
    /**
     * So basically the only way to guaruntee stuff will work in the BOOT layer is if it can find the right
     * package in the right ResolvedModule. Luckily we already have those in the current (SERVICE) layer, so
     * all we need to do is transfer some stuff over and then handle the duplicates
     */
    private static void addResolvedModule(Object module, ClassLoader thisLoader, boolean newFormat) {
        ClassLoader loader = bootLoader();
        Map<String,Object> roots = Fields.getDirect(loader,newFormat ? "ourModules" : "resolvedRoots");
        Map<String,Object> packageLookup = Fields.getDirect(loader,newFormat ? "packageToOurModules" : "packageLookup");
        Object reference = Methods.invokeDirect(module,"reference");
        Object descriptor = Methods.invokeDirect(reference,"descriptor");
        String name = Methods.invokeDirect(descriptor,"name");
        roots.put(name,reference);
        Set<String> packages = Methods.invokeDirect(descriptor,"packages");
        for(String pkg : packages) {
            packageLookup.put(pkg,module);
        }
        
        //Finalize by moving the original Module from SERVICE to the BOOT layer & fixing parent loaders
        moveModuleToLayer(loader,"BOOT","SERVICE",name);
        Map<String,ClassLoader> parentLoaders = Fields.getDirect(thisLoader,newFormat ? "packageToParentLoader" : "parentLoaders");
        for(String pkg : packages) parentLoaders.put(pkg,loader);
        
        //Fix configurations & prevent reading duplicate modules
        addConfigurationModule(Fields.getDirect(loader,"configuration"),name,module,thisLoader);
        Map<String,Object> theseRoots = Fields.getDirect(thisLoader,newFormat ? "ourModules" : "resolvedRoots");
        theseRoots.remove(name);
        
        LOGGER.debug("Finished migrating module {} from the SERVICE layer to the BOOT layer",name);
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
        Set<String> packages = new HashSet<>(Methods.invokeDirect(secureJar,"getPackages"));
        Collection<Object> providers = Methods.invokeDirect(secureJar,"getProviders");
        Class<?> cDesc = Class.forName("java.lang.module.ModuleDescriptor");
        Object metadata = Fields.getDirect(secureJar,"metadata");
        String version = Methods.invokeDirect(metadata,"version");
        Object builder = Methods.invokeStaticDirect(cDesc,"newAutomaticModule",name);
        builder = Methods.invokeDirect(builder,"version",version);
        builder = Methods.invokeDirect(builder,"packages",packages);
        for(Object provider : providers) {
            Collection<String> actualProviders = Methods.invokeDirect(provider,"providers");
            if(!actualProviders.isEmpty()) {
                String service = Methods.invokeDirect(provider,"serviceName");
                Methods.invokeDirect(builder,"provides",service,new ArrayList<>(actualProviders));
            }
        }
        for(String service : usesServices) Methods.invokeDirect(builder,"uses",service);
        Object desc = Methods.invokeDirect(builder,"build");
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
     * Export the given module to all packages loaded to a module in the GAME layer
     */
    public static void exportAllModules() throws Throwable {
        LOGGER.info("Exporting all modules");
        Class<?> mClass = Class.forName("java.lang.Module");
        for(String layerName : new String[]{"BOOT","SERVICE","PLUGIN","GAME"}) {
            Object layer = getModuleLayer(layerName);
            Map<String,Object> nameToModule = Fields.getDirect(layer,"nameToModule");
            for(Object module : nameToModule.values()) {
                Object descriptor = Fields.getDirect(module,"descriptor");
                Set<String> pkgs = Fields.getDirect(descriptor,"packages");
                for(String pkg : pkgs) {
                    Methods.invokeStaticDirect(mClass,"addExportsToAll0",module,pkg);
                    Methods.invokeStaticDirect(mClass,"addExportsToAllUnnamed0",module,pkg);
                }
            }
        }
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
            Collection<Class<?>> classes = Fields.getDirect(loader,"classes");
            for(Class<?> c : classes) {
                String name = moduleName(Fields.getDirect(c,"module"));
                if(Objects.isNull(name)) continue;
                if(name.equals(oldName) || name.equals(newName)) {
                    Fields.setDirect(c,"classLoader",target);
                    allMoved.add(c);
                    removals.putIfAbsent(loader,new HashSet<>());
                    removals.get(loader).add(c);
                    Fields.setDirect(c,"module",module);
                }
            }
        }
        //Handle the ClassLoader side & make sure classes on the target loader aren't in a nonexistant module
        Collection<Class<?>> targetClasses = Fields.getDirect(target,"classes");
        for(Class<?> targetClass : targetClasses) {
            String name = moduleName(Fields.getDirect(targetClass,"module"));
            if(Objects.nonNull(moduleName) && moduleName.equals(name))
                Fields.setDirect(targetClass,"module",module);
        }
        targetClasses.addAll(allMoved);
        for(Entry<ClassLoader,Collection<Class<?>>> removalEntry : removals.entrySet()) {
            Collection<Class<?>> classes = Fields.getDirect(removalEntry.getKey(),"classes");
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
    public static Object[] findModuleLoaderForPackage(String pkg, ClassLoader[] loaders, boolean newFormat) {
        for(int i=0;i<loaders.length;i++) {
            ClassLoader loader = loaders[i];
            String name = i==0 ? "BOOT" : (i==1 ? "SERVICE" : "PLUGIN");
            Map<String,Object> lookup = Fields.getDirect(loader,newFormat ? "packageToOurModules" : "packageLookup");
            Object resolvedModule = lookup.get(pkg);
            if(Objects.nonNull(resolvedModule)) return new Object[]{loader,resolvedModule,name};
        }
        return null;
    }
    
    public static void fixIfNotJava8() {
        if(!isJava8()) {
            String pkg = ConsulterSupplyFunction.class.getPackage().getName();
            ClassLoader thisLoader = ForgeCoreLoader.class.getClassLoader();
            boolean newFormat = false;
            Map<String,Object> packageLookup;
            try {
                packageLookup = Fields.getDirect(thisLoader,"packageLookup");
            } catch(NoSuchFieldException ex) {
                packageLookup = Fields.getDirect(thisLoader,"packageToOurModules");
                newFormat = true;
            }
            Object module = packageLookup.get(pkg);
            if(Objects.nonNull(module)) {
                addResolvedModule(module,thisLoader,newFormat);
                packageLookup.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
            } else LOGGER.fatal("FAILED TO GET RESOLVED MODULE FOR {}",pkg);
        }
    }
    
    /**
     * Get the command line argument handler in case we need to check stuff very early in the loading process
     */
    static ArgumentHandler getArgumentHandler() {
        return Fields.getDirect(INSTANCE,"argumentHandler");
    }
    
    public static @Nullable Object getBootLoadedCoreAPI() {
        return getCoreAPIReflectively(bootLoader());
    }
    
    static Object getCoreAPIReflectively(ClassLoader loader) {
        try {
            return Fields.getStaticDirect(Class.forName(APICORE,false,loader),"INSTANCE");
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
        return ((Optional<?>)Methods.invokeDirect(env,"findModuleLayerManager")).orElse(null);
    }
    
    @SuppressWarnings({"unchecked","SameParameterValue"})
    static Object getModuleFromLayer(String layerName, String name) {
        return ((Map<String,Object>)Fields.getDirect(getModuleLayer(layerName),"nameToModule")).get(name);
    }
    
    public static Object getModuleFromPackage(String pkg, String layerName, boolean newFormat) {
        Object layer = getModuleLayer(layerName);
        Map<String,Object> packageLookup = Fields.get(layerClassLoader(layerName),newFormat ? "packageToOurModules" : "packageLookup");
        Object resolved = packageLookup.get(pkg);
        if(Objects.isNull(resolved)) {
            LOGGER.error("Cannot get module for pacakge {} since it does not exist in input layer {}!",pkg,layerName);
            return null;
        }
        Map<String,Object> nameToModule = Fields.getDirect(layer,"nameToModule");
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
        return ((Optional<?>)Methods.invokeDirect(layerManager,"getLayer",layerEnum)).orElse(null);
    }
    
    static String getVersionFromForgeVersion(String forgeVersion) {
        String ignore = "forge-";
        String actualVersion = forgeVersion.startsWith(ignore) ? forgeVersion.substring(ignore.length()) : forgeVersion;
        String version = "1.21.1";
        if(actualVersion.startsWith("49.")) version = "1.20.4";
        else if(actualVersion.startsWith("50.")) version = "1.20.6";
        LOGGER.info("Guessed mc version {} from forge version {}",version,forgeVersion);
        return version;
    }
    
    static String getVersionStr() {
        ArgumentHandler handler = getArgumentHandler();
        if(Objects.isNull(handler)) return null;
        String[] rawArgs = Fields.getDirect(handler,"args");
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find version using handler {}",handler);
            return null;
        }
        int versionIndex = -1;
        boolean found = false;
        for(int i=0;i<rawArgs.length;i++) {
            if(rawArgs[i].equals("--fml.mcVersion")) {
                versionIndex = i+1;
                found = true;
                break;
            }
        }
        if(found) {
            LOGGER.debug("Found fml.mcVersion arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        LOGGER.debug("--fml.mcVersion was not found so the mc version will be guessed from --version instead");
        for(int i=0;i<rawArgs.length;i++) {
            if(rawArgs[i].equals("--version")) {
                versionIndex = i+1;
                found = true;
                break;
            }
        }
        if(found) {
            LOGGER.debug("Found forge version arg at index {}",versionIndex);
            return getVersionFromForgeVersion(rawArgs[versionIndex]);
        }
        LOGGER.error("Failed to find fml.mcVersion or version flags from args {}",Arrays.toString(rawArgs));
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
        Map<?,?> completedLayers = Fields.getDirect(layerManager,"completedLayers");
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
        LOGGER.debug("Successfully loaded CoreAPI instance {}",clazz);
        return clazz;
    }
    
    @SuppressWarnings("SameParameterValue")
    static void loadNewModuleTo(@Nullable IModInfo mod, String targetLayerName, Set<String> finalizedPkgs,
            boolean newFormat) {
        if(Objects.isNull(mod)) {
            LOGGER.error("Cannot load module from nonexistent file!");
            return;
        }
        try {
            Object fileInfo = mod.getOwningFile();
            Object file = Methods.invokeDirect(fileInfo,"getFile");
            Object secureJar = Methods.invokeDirect(file,"getSecureJar");
            ClassLoader targetLoader = layerClassLoader("GAME");
            String existingName = Methods.invokeDirect(secureJar,"name");
            String name = mod.getModId(); //Usually the same as existingName, but there are some edge cases...
            Object layer = getModuleLayer(targetLayerName);
            Map<String,Object> nameToModule = Fields.getDirect(layer,"nameToModule");
            Object module = nameToModule.get(existingName);
            if(Objects.isNull(module)) module = nameToModule.get(name);
            boolean existed = false;
            if(Objects.nonNull(module)) {
                LOGGER.info("Found existing module to set up for {}",name);
                existed = true;
            } else LOGGER.info("Setting up new module with name {}",name);
            Object descriptor;
            if(Objects.nonNull(module)) {
                descriptor = Fields.getDirect(module,"descriptor");
                Fields.setDirect(descriptor,"name",name);
            }
            else {
                List<String> usesServices = Methods.invokeDirect(fileInfo,"usesServices");
                descriptor = buildNewModuleDescriptor(name,secureJar,usesServices);
            }
            String finderName = newFormat ? "net.minecraftforge.securemodules.SecureModuleFinder" :
                    "cpw.mods.cl.JarModuleFinder";
            Class<?> fClass = Class.forName(finderName);
            Object finder = Constructors.newInstanceOf(fClass,newFormat ? Collections.singletonList(secureJar) : secureJar);
            Map<String,Object> refMap = Fields.getDirect(finder,newFormat ? "references" : "moduleReferenceMap");
            Object reference = refMap.get(existingName);
            URI uri = Fields.getDirect(reference,"location");
            Object config = Fields.getDirect(targetLoader,"configuration");
            Class<?> refClass = reference.getClass().getSuperclass();
            Class<?> cResolved = Class.forName("java.lang.module.ResolvedModule");
            Fields.setDirect(reference,"descriptor",descriptor);
            Object resolvedModule = Constructors.newInstanceOf(cResolved,config,reference);
            Set<String> packages = new HashSet<>(resolvedPackages(resolvedModule));
            packages.removeAll(finalizedPkgs);
            packages = Collections.unmodifiableSet(packages);
            finalizedPkgs.addAll(packages);
            Class<?> cModule = Class.forName("java.lang.Module");
            if(Objects.isNull(module)) module = Constructors.newInstanceOf(cModule,layer,targetLoader,descriptor,uri);
            addModuleThouroughly(module,resolvedModule,layer,name,packages,reference,targetLoader,newFormat);
            LOGGER.info("Finished setting up {}",module);
            
            //nuke & finalize
            ClassLoader boot = bootLoader();
            ClassLoader service = layerClassLoader("SERVICE");
            ClassLoader plugin = layerClassLoader("PLUGIN");
            nukeConfig(name,boot,service,plugin);
            nukeLoaderFields(name,newFormat,boot,service,plugin);
            nukeModuleLayer(name,"BOOT","SERVICE","PLUGIN");
            if(!existingName.equals(name) && existed) {
                nukeConfig(existingName,boot,service,plugin,targetLoader);
                nukeLoaderFields(existingName,newFormat,boot,service,plugin,targetLoader);
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
        return Objects.nonNull(module) ? Methods.invokeDirect(module,"getName") : null;
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
        Map<String,Object> moduleMapFrom = new HashMap<>(Fields.getDirect(from,fieldName));
        Object module = moduleMapFrom.get(moduleName);
        if(Objects.isNull(module)) {
            LOGGER.error("Unable to move module {}! Cannot find module in supplier layer {}",moduleName,layerFrom);
            return;
        }
        Fields.setDirect(module,"loader",targetLoader);
        Fields.setDirect(module,"layer",to);
        Map<String,Object> moduleMapTo = new HashMap<>(Fields.getDirect(to,fieldName));
        moduleMapTo.putIfAbsent(moduleName,module);
        Fields.setDirect(to,fieldName,Collections.unmodifiableMap(moduleMapTo));
        moduleMapFrom.remove(moduleName);
        Fields.setDirect(from,fieldName,Collections.unmodifiableMap(moduleMapFrom));
    }
    
    /**
     * Add the module for the given package to the GAME layer and nuke all references to it from other layers
     */
    public static void nukeAndFinalize(IModInfo mod, String pkg, Set<String> finalizedPkgs, boolean newFormat) {
        LOGGER.info("Finalizing package {}",pkg);
        ClassLoader boot = bootLoader();
        ClassLoader service = layerClassLoader("SERVICE");
        ClassLoader plugin = layerClassLoader("PLUGIN");
        Object[] found = findModuleLoaderForPackage(pkg,new ClassLoader[]{boot,service,plugin},newFormat);
        if(Objects.isNull(found)) {
            loadNewModuleTo(mod,"GAME",finalizedPkgs,newFormat);
            return;
        }
        ClassLoader foundLoader = (ClassLoader)found[0];
        Object resolvedModule = found[1];
        LOGGER.info("Got resolved module as {}",resolvedModule);
        String name = resolvedName(resolvedModule);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        LOGGER.warn("NUKING ALL REFERENCES OF MODULE {} FROM THE BOOT, SERVICE, & PLUGIN LAYERS",name);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        Map<String,Object> bootRoots = Fields.getDirect(foundLoader,newFormat ? "ourModules" : "resolvedRoots");
        Object ref = bootRoots.get(name);
        Object foundLayer = getModuleLayer((String)found[2]);
        Map<String,Object> layerModules = Fields.getDirect(foundLayer,"nameToModule");
        Object module = layerModules.get(name);
        ClassLoader target = layerClassLoader("GAME");
        Object moduleLayer = getModuleLayer("GAME");
        Set<String> packages = new HashSet<>(resolvedPackages(resolvedModule));
        packages.removeAll(finalizedPkgs);
        packages = Collections.unmodifiableSet(packages);
        finalizedPkgs.addAll(packages);
        addModuleThouroughly(module,resolvedModule,moduleLayer,name,packages,ref,target,newFormat);
        
        //nuke & finalize
        nukeConfig(name,boot,service,plugin);
        nukeLoaderFields(name,newFormat,boot,service,plugin);
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
            Object configuration = Fields.getDirect(loader,"configuration");
            Map<String,Object> nameToModule = new HashMap<>(Fields.getDirect(configuration,"nameToModule"));
            Object module = nameToModule.get(name);
            if(Objects.nonNull(module)) {
                nameToModule.remove(name);
                Fields.setDirect(configuration,"nameToModule",Collections.unmodifiableMap(nameToModule));
                Set<Object> modules = new HashSet<>(Fields.getDirect(configuration,"modules"));
                modules.remove(module);
                Fields.setDirect(configuration,"modules",modules);
            }
        }
    }
    
    static void nukeLoaderFields(String moduleName, boolean newFormat, ClassLoader ... loaders) {
        for(ClassLoader loader : loaders) {
            Map<String,Object> resolvedRoots = Fields.getDirect(loader,newFormat ? "ourModules" : "resolvedRoots");
            Map<String,Object> packageLookup = Fields.getDirect(loader,newFormat ? "packageToOurModules" : "packageLookup");
            Map<String,Object> parentLoaders = Fields.getDirect(loader,newFormat ? "packageToParentLoader" : "parentLoaders");
            resolvedRoots.remove(moduleName);
            if(newFormat) {
                Map<String,Object> ourModulesSecure = Fields.getDirect(loader,"ourModulesSecure");
                ourModulesSecure.remove(moduleName);
            }
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
            Map<String,Object> packageToCodeSource = newFormat ?
                    Fields.getDirect(loader,"packageToCodeSource") : null;
            for(String pkg : packages) {
                packageLookup.remove(pkg);
                parentLoaders.remove(pkg);
                if(newFormat) packageToCodeSource.remove(pkg);
            }
        }
    }
    
    static void nukeModuleLayer(String name, String ... layers) {
        for(String layer : layers) {
            Object moduleLayer = getModuleLayer(layer);
            Map<String,Object> nameToModule = new HashMap<>(Fields.getDirect(moduleLayer,"nameToModule"));
            nameToModule.remove(name);
            Fields.setDirect(moduleLayer,"nameToModule",Collections.unmodifiableMap(nameToModule));
            Set<Object> modules = Fields.getDirect(moduleLayer,"modules");
            if(Objects.nonNull(modules)) {
                modules = new HashSet<>(modules);
                modules.removeIf(m -> name.equals(moduleName(m)));
                Fields.setDirect(moduleLayer,"modules",Collections.unmodifiableSet(modules));
            }
        }
    }
    
    /**
     * Not present in Java 8
     */
    static ClassLoader platformLoader() {
        return Methods.invokeStaticDirect(ClassLoader.class,"getPlatformClassLoader");
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeFromUnmodifiableMapField(Object object, String name, Object toRemove) {
        Map<?,?> map = new HashMap<>(Fields.getDirect(object,name));
        map.remove(toRemove);
        Fields.setDirect(object,name,Collections.unmodifiableMap(map));
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeFromUnmodifiableSetField(Object object, String name, Object toRemove) {
        Set<?> set = new HashSet<>(Fields.getDirect(object,name));
        set.remove(toRemove);
        Fields.setDirect(object,name,Collections.unmodifiableSet(set));
    }
    
    /**
     * Get name of resolved module via reflection since this is a Java 8 context
     */
    static Object resolvedDescriptor(Object resolvedModule) {
        return Methods.invokeDirect(resolvedModule,"descriptor");
    }
    
    /**
     * Get name of resolved module via reflection since this is a Java 8 context
     */
    static String resolvedName(Object resolvedModule) {
        return Methods.invokeDirect(resolvedDescriptor(resolvedModule),"name");
    }
    
    static Set<String> resolvedPackages(Object resolvedModule) {
        return Fields.getDirect(resolvedDescriptor(resolvedModule),"packages");
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
        boolean newFormat = false;
        Map<String,Object> fromPkg; //Fix BOOT modules first
        try {
            fromPkg = Fields.getDirect(loaderFrom,"packageLookup");
        } catch(NoSuchFieldException ex) {
            fromPkg = Fields.getDirect(loaderFrom,"packageToOurModules");
            newFormat = true;
        }
        Object fromModule = fromPkg.get(pkg);
        Object fromCfg = Fields.getDirect(loaderFrom,"configuration");
        if(!"PLUGIN".equals(layerTo)) {
            Set<Object> modules = new HashSet<>(Fields.getDirect(fromCfg,"modules"));
            modules.add(fromModule);
            Fields.setDirect(fromCfg,"modules",modules);
        }
        //Remove module from PLUGIN layer
        Map<String,Object> pkgs = Fields.getDirect(loaderTo,newFormat ? "packageToOurModules" : "packageLookup");
        Object module = pkgs.get(pkg);
        String name = resolvedName(module);
        Map<String,Object> roots = Fields.getDirect(loaderTo,newFormat ? "ourModules" : "resolvedRoots");
        roots.remove(name);
        Object config = Fields.getDirect(loaderTo,"configuration");
        removeFromUnmodifiableSetField(config,"modules",module);
        removeFromUnmodifiableMapField(config,"nameToModule",name);
        Object reference = Methods.invokeDirect(module,"reference");
        Object descriptor = Methods.invokeDirect(reference,"descriptor");
        Set<String> packages = Methods.invokeDirect(descriptor,"packages");
        
        //Finalize by dealing with the module layers & fixing parent loaders
        Object layer = getModuleLayer(layerTo);
        Map<String,Object> map = new HashMap<>(Fields.getDirect(layer,"nameToModule"));
        map.remove(name);
        Fields.setDirect(layer,"nameToModule",map);
        Map<String,ClassLoader> parentLoaders = Fields.getDirect(loaderTo,newFormat ? "packageToParentLoader" : "parentLoaders");
        for(String p : packages) parentLoaders.put(p,loaderFrom);
        
        //Deal with the module graph again ._.
        Map<?,Set<?>> graph = new HashMap<>(Fields.getDirect(config,"graph"));
        graph.remove(module); //Don't cross-check the name since we have 2 different module this time
        graph.forEach((key,values) -> values.remove(module));
        Fields.setDirect(config,"graph",graph);
        
        pkgs.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
    }
    
    public static void sanityCheckModule(Class<?> c, String name) {
        Object module = Methods.invokeDirect(c,"getModule");
        String actualName = moduleName(module);
        if(!name.equals(actualName)) {
            //By this point the class is definitely in the GAME layer regardless of whether the module is correct
            Fields.setDirect(c,"module",getModuleFromLayer("GAME",name));
            LOGGER.info("Moved {} from module {} to module {}",c,actualName,name);
        }
    }
    
    public static void verifyModule(String className, IModInfo info, Object moduleLayer) throws Exception {
        LOGGER.info("Verifying that {} is valid for {} and can be found in {}",className,info,moduleLayer);
        IModFileInfo fileInfo = info.getOwningFile();
        String modid = info.getModId();
        String moduleName = Methods.invokeDirect(fileInfo,"moduleName");
        IModFile file = Methods.invokeDirect(fileInfo,"getFile");
        if(!modid.equals(moduleName)) LOGGER.error("Mod id {} does not equal module name {}!",modid,moduleName);
        Optional<Object> optionalModule = Methods.invokeDirect(moduleLayer,"findModule",moduleName);
        if(!optionalModule.isPresent()) {
            Set<Object> modules = Methods.invokeDirect(moduleLayer,"modules");
            for(Object module : modules) {
                String name = moduleName(module);
                if(Objects.isNull(name)) continue;
                if(name.equals(moduleName) || name.equals(modid)) {
                    Object layer = Methods.invokeDirect(module,"getLayer");
                    boolean sameLayer = layer==moduleLayer;
                    LOGGER.info("Found module {} in {} layer that wasn't present in the nameToModule map",
                                moduleName,sameLayer ? "the same" : "a different");
                    Map<String,Object> nameToModule = new HashMap<>(Fields.getDirect(layer,"nameToModule"));
                    nameToModule.put(moduleName,module);
                    Fields.setDirect(layer,"nameToModule",Collections.unmodifiableMap(nameToModule));
                    break;
                }
            }
        }
        if(optionalModule.isPresent()) {
            Object module = optionalModule.get();
            String name = moduleName(module);
            ClassLoader loader = Methods.invokeDirect(moduleLayer,"findLoader",name);
            Class<?> c = Class.forName(className,false,loader);
            Object cModule = Methods.invokeDirect(c,"getModule");
            if(module!=cModule) {
                LOGGER.debug("Attempting to fix modules that are not equal");
                Fields.setDirect(c,"module",module);
            } else LOGGER.debug("Modules are equal");
        } else LOGGER.error("Module {} is not present in the target layer!",moduleName);
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