package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.Launcher;
import io.github.toolfactory.jvm.function.catalog.ConsulterSupplyFunction;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.*;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Figures out which version to load on and how to load stuff on it
 *
 */
@SuppressWarnings({"unused","LoggingSimilarMessage"})
public class ForgeCoreLoader { //TODO Refactor common accessors & setters for the module system out
    
    public static final boolean MODULE_LAYERS = Boolean.parseBoolean(System.getProperty("til.debug.forge.modules.layers","true"));
    private static final String API_PKG = "mods.thecomputerizer.theimpossiblelibrary.api";
    private static final String FORGE_PKG = "mods.thecomputerizer.theimpossiblelibrary.forge";
    private static final String APICORE = API_PKG+".core.CoreAPI";
    private static final Logger LOGGER = LogManager.getLogger("TIL ForgeCoreLoader");
    public static final boolean SECURE_CLASSLOADER_FORMAT = newModuleClassLoaderFormat();
    
    private static boolean newModuleClassLoaderFormat() {
        if(isJava21()) return true;
        final String newFormatClassName = "net.minecraftforge.securemodules.SecureModuleClassLoader";
        try {
            Class<?> newFormatClass = Class.forName(newFormatClassName);
            return true;
        } catch(Throwable ignored) {
            LOGGER.debug("Assuming 1.18.2-1.20.1 ModuleClassLoader format (missing {})",newFormatClassName);
        }
        return false;
    }
    
    //The module system forced me to find a very powerful alternative, but at least I don't need to do Unsafe hacking
    static {
        if(ForgeCoreLoader.class.getClassLoader()!=bootLoader()) {
            if(isJava8()) LOGGER.info("I see you are running Java 8. Good choice, but I'll be using burningwave anyways");
            else LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
        }
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(ModuleAccess module, ResolvedModuleAccess resolvedModule,
            ModuleLayerAccess moduleLayer, String name, Set<String> packages,
            ModuleReferenceAccess moduleRef, ModuleClassLoaderAccess target) {
        module.setName(name);
        target.addRoot(name,moduleRef);
        target.addPackages(packages,resolvedModule);
        target.parentLoaders().entrySet().removeIf(entry -> packages.contains(entry.getKey()));
        ConfigurationAccess configuration = target.configuration();
        configuration.removeModule(name);
        configuration.addModuleIfAbsent(name,resolvedModule);
        if(!moduleLayer.hasMatchingModuleInSet(module.getName())) moduleLayer.addModule(module);
        module.setLayer(moduleLayer);
        module.setLoader(target);
    }
    
    /**
     * So basically the only way to guaruntee stuff will work in the BOOT layer is if it can find the right
     * package in the right ResolvedModule. Luckily we already have those in the current (SERVICE) layer, so
     * all we need to do is transfer some stuff over and then handle the duplicates
     */
    private static void addResolvedModule(ResolvedModuleAccess resolvedModule, ModuleClassLoaderAccess thisLoader) {
        ModuleClassLoaderAccess loader = bootLoaderAccess();
        
        loader.addRoot(resolvedModule);
        loader.addPackages(resolvedModule);
        
        Set<String> pkgs = resolvedModule.packages();
        
        //Finalize by moving the original Module from SERVICE to the BOOT layer & fixing parent loaders
        ModuleSystemAccessor.getModuleLayer("SERVICE",LOGGER).moveModule("BOOT",resolvedModule);
        thisLoader.addParentLoaders(pkgs,loader);
        
        //Fix configurations & prevent reading duplicate modules
        loader.configuration().addModuleIfAbsent(resolvedModule);
        thisLoader.configuration().removeModule(resolvedModule);
        thisLoader.removeRoot(resolvedModule.name());
        
        LOGGER.debug("Finished migrating module {} from the SERVICE layer to the BOOT layer",resolvedModule.name());
    }
    
    /**
     * Should be the ClassLoader for the BOOT layer or the system ClassLoader if Java 8
     */
    public static ClassLoader bootLoader() {
        ClassLoader loader = Launcher.class.getClassLoader();
        return Objects.nonNull(loader) ? loader : ClassLoader.getSystemClassLoader();
    }
    
    static ModuleClassLoaderAccess bootLoaderAccess() {
        ModuleClassLoaderAccess bootLoader = ModuleSystemAccessor.getModuleClassLoader(bootLoader(),LOGGER);
        bootLoader.setLayerName("BOOT");
        return bootLoader;
    }
    
    /**
     * Export the given module to all packages loaded to a module in the GAME layer
     */
    public static void exportAllModules() {
        LOGGER.info("Exporting all modules");
        ModuleSystemAccessor.exportAllPackages(LOGGER,"BOOT","SERVICE","PLUGIN","GAME");
    }
    
    /**
     * After the hacking the module system to get everything to load properly, we need to make sure all the clases
     * are moved to the game layer so the other game modules are accessible.
     * Any classes loaded to the BOOT, SERVICE, or PLUGIN layer that is a part of the given module will have their
     * classLoader field reassigned to the ClassLoader of the GAME layer
     */
    static void finalizeModule(String oldName, String newName, ModuleAccess module, ModuleClassLoaderAccess targetLoader,
            ModuleClassLoaderAccess ... loaders) {
        String moduleName = module.getName();
        Set<Class<?>> allMoved = new HashSet<>();
        Map<ModuleClassLoaderAccess,Collection<Class<?>>> removals = new HashMap<>();
        for(ModuleClassLoaderAccess loader : loaders) {
            Collection<Class<?>> classes = loader.classes();
            if(Objects.isNull(classes)) continue;
            classes = new HashSet<>(classes);
            for(Class<?> c : classes) {
                if(loader.accessClass(c).moveIfInModule(loader,module,oldName,newName)) {
                    allMoved.add(c);
                    removals.putIfAbsent(loader,new HashSet<>());
                    removals.get(loader).add(c);
                }
            }
        }
        //Handle the ClassLoader side & make sure classes on the target loader aren't in a nonexistant module
        if(Objects.nonNull(moduleName)) {
            Collection<Class<?>> targetClasses = targetLoader.classes();
            for(Class<?> targetClass : targetClasses)
                targetLoader.accessClass(targetClass).setModuleIfIn(module,moduleName);
            targetClasses.addAll(allMoved);
        }
        for(Entry<ModuleClassLoaderAccess,Collection<Class<?>>> removalEntry : removals.entrySet())
            removalEntry.getKey().removeClasses(removalEntry.getValue());
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
    public static Object[] findModuleLoaderForPackage(String pkg, ModuleClassLoaderAccess ... loaders) {
        for(int i=0;i<loaders.length;i++) {
            ModuleClassLoaderAccess loader = loaders[i];
            String name = i==0 ? "BOOT" : (i==1 ? "SERVICE" : "PLUGIN");
            ResolvedModuleAccess resolvedModule = loader.getResolvedModule(pkg);
            if(Objects.nonNull(resolvedModule)) return new Object[]{loader,resolvedModule,name};
        }
        return null;
    }
    
    /**
     * Sets up some important stuff needed to initialize the loading process.
     */
    public static void fixFirstEntryPoint() {
        if(isJava8()) fixForJava8();
        else fixForModuleSystem();
    }
    
    private static void fixForJava8() {
        URL source = ClassHelper.getSourceURL(ForgeCoreLoader.class);
        if(!ClassHelper.loadURL((URLClassLoader)bootLoader(),source))
            LOGGER.error("Failed to load source {}",source);
    }
    
    private static void fixForModuleSystem() {
        ClassHelper.checkBurningWaveInit();
        String pkg = ConsulterSupplyFunction.class.getPackage().getName();
        ClassLoader thisLoader = ForgeCoreLoader.class.getClassLoader();
        ModuleClassLoaderAccess loaderAccess = ModuleSystemAccessor.getModuleClassLoader(thisLoader,LOGGER);
        ResolvedModuleAccess resolvedModule = loaderAccess.getResolvedModule(pkg);
        if(Objects.nonNull(resolvedModule)) {
            if(MODULE_LAYERS) {
                loaderAccess.removePackagesForModule(resolvedModule);
                addResolvedModule(resolvedModule,loaderAccess);
            } else {
                LOGGER.debug("Skipping module layer movement hacks (-Dtil.debug.forge.modules.layers=false)");
                logModuleNames("BOOT","SERVICE");
            }
        } else LOGGER.fatal("FAILED TO GET RESOLVED MODULE FOR {}",pkg);
    }
    
    static Map<String,Collection<String>> getAllLayerPaths(String ... layerNames) {
        Map<String,Collection<String>> pathMap = new HashMap<>();
        Object layerManager = isJava21() ? null : ModuleSystemAccessor.getModuleLayerHandler(LOGGER);
        for(String layerName : layerNames) {
            List<Object> paths = getLayerPaths(layerName);
            if(paths.isEmpty()) continue;
            if(isJava21()) pathMap.put(layerName,paths.stream()
                    .map(secureJar -> {
                        String name = Methods.invoke(secureJar,"name");
                        URI uri = Methods.invoke(secureJar,"uri");
                        return "name = "+name+" | location = "+uri;
                    }).collect(Collectors.toSet()));
            else {
                Set<String> pathStrs = new HashSet<>();
                for(Object path : paths) {
                    try {
                        Object namedPath = getRecordFieldInstance(layerManager,"path");
                        Object name = getRecordFieldInstance(namedPath,"name");
                        Object pathsObj = getRecordFieldInstance(namedPath,"paths");
                        pathStrs.add("name = "+name+" | paths = "+pathsObj);
                    } catch(Throwable t) {
                        LOGGER.error("Failed to extract path string from {}",path,t);
                    }
                }
                if(!pathStrs.isEmpty()) pathMap.put(layerName,Collections.unmodifiableSet(pathStrs));
            }
        }
        if(pathMap.isEmpty()) LOGGER.warn("Returning empty path map for layers {}",(Object)layerNames);
        return pathMap;
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
    
    public static <E extends Enum<E>> E getEnum(ClassLoader loader, String className, String name) {
        Class<?> foundClass = findClassInHeirarchy(loader,className);
        return Objects.nonNull(foundClass) ? getEnum(foundClass,name) : null;
    }
    
    @SuppressWarnings("unchecked")
    static <E extends Enum<E>> E getEnum(Class<?> enumClass, String name) {
        return Enum.valueOf((Class<E>)enumClass,name);
    }
    
    static List<Object> getLayerPaths(String layerName) {
        ModuleLayerHandlerAccess handler = ModuleSystemAccessor.getModuleLayerHandler(LOGGER);
        Map<Object,List<Object>> map = handler.layerPathMap();
        Enum<?> layerEnum = ModuleSystemAccessor.getLayerEnum(layerName);
        if(Objects.isNull(layerEnum)) {
            LOGGER.error("Failed to get Layer {}! Cannot return paths",layerName);
            return Collections.emptyList();
        }
        if(!map.containsKey(layerEnum)) {
            LOGGER.warn("Layer path map does not contain paths for {}",layerName);
            return Collections.emptyList();
        }
        return map.get(layerEnum);
    }
    
    static Object getRecordFieldInstance(Object target, String name) {
        if(Objects.isNull(target)) {
            LOGGER.error("Cannot get record field {} for null target!",name);
            return null;
        }
        return getRecordFieldInstance(target,target.getClass(),name);
    }
    
    static Object getRecordFieldInstance(Object target, Class<?> targetClass, String name) {
        if(isJava8()) {
            LOGGER.error("Records do not exist in Java 8!");
            return null;
        }
        if(Objects.isNull(targetClass) || Objects.isNull(name)) {
            LOGGER.error("Target class and record name cannot be null! class = {} | name = {}",targetClass,name);
            return null;
        }
        Object[] components = Methods.invoke(targetClass,"getRecordComponents");
        if(Objects.isNull(components)) {
            LOGGER.error("No record components found in class {}! name = {})",targetClass,name);
            return null;
        }
        Object foundComponent = null;
        for(Object component : components) {
            String componentName = Methods.invoke(component,"getName");
            if(Objects.nonNull(componentName) && name.equals(componentName)) {
                foundComponent = component;
                break;
            }
        }
        return getRecordFieldInstance(target,foundComponent);
    }
    
    static Object getRecordFieldInstance(Object target, Object component) {
        if(Objects.isNull(component)) {
            LOGGER.error("Cannot access null record component! target = {}",target);
            return null;
        }
        Method accessor = Methods.invoke(component,"getAccessor");
        if(Objects.isNull(accessor)) {
            LOGGER.error("Accessor for record component is null! target = {} | component = {}",target,component);
            return null;
        }
        return Methods.invoke(target,accessor);
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
        ArgumentHandlerAccess handler = ModuleSystemAccessor.getLauncher(LOGGER).argumentHandler();
        if(Objects.isNull(handler)) return null;
        String[] rawArgs = handler.getArgs();
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find version using handler {}",handler.access());
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
        int javaVersion = 17;
        String mcVersion = "1.20.1";
        if(isJava8()) {
            javaVersion = 8;
            mcVersion = "1.16.5";
        } else if(isJava21()) {
            javaVersion = 21;
            mcVersion = "1.21.1";
        }
        LOGGER.warn("Guessing the current Minecraft version is {} since this is Java {}",mcVersion,javaVersion);
        return mcVersion;
    }
    
    /**
     * Returns a CoreAPI instance on the input ClassLoader. Initializes the source if necessary
     */
    static @Nullable Object initCoreAPI(ClassLoader loader) {
        ClassHelper.checkBurningWaveInit();
        LOGGER.info("Starting CoreAPI init");
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
    
    public static boolean isJava21() {
        return System.getProperty("java.version").startsWith("21");
    }
    
    /**
     * Tries to get the ClassLoader instance associated with the given layer name
     */
    public static ClassLoader layerClassLoader(String layerName) {
        return ModuleSystemAccessor.getModuleClassLoader(layerName,LOGGER).unwrap();
    }
    
    /**
     * Define necessary classes for the versioned CoreAPI instance
     * Returns the instance class
     */
    static Class<?> loadAPI(String version) {
        ClassLoader loader = bootLoader();
        String className = versionClassName("core.TILCoreForge",version);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className,true,loader);
        } catch(Exception ex) {
            LOGGER.error("Failed to load class {} for {}",className,loader,ex);
        }
        if(Objects.isNull(clazz)) throw new RuntimeException("Failed to load CoreAPI instance [Forge-"+version+"]");
        else if(isJava8()) {
            String forgeModLoading = "mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading";
            try {
                Class.forName(forgeModLoading,true,loader);
            } catch(Exception ex) {
                LOGGER.error("Failed to load class {} for {}",forgeModLoading,loader,ex);
            }
        }
        LOGGER.debug("Successfully loaded CoreAPI instance {}",clazz);
        return clazz;
    }
    
    @SuppressWarnings("SameParameterValue")
    static void loadNewModuleTo(@Nullable IModInfo mod, String targetLayerName, Set<String> finalizedPkgs) {
        if(Objects.isNull(mod)) {
            LOGGER.error("Cannot load module from nonexistent file!");
            return;
        }
        try {
            String name = mod.getModId(); //Usually the same as existingName, but there are some edge cases...
            ModFileInfoAccess fileInfo = ModuleSystemAccessor.getModFileInfo(mod.getOwningFile(),LOGGER);
            SecureJarAccess secureJar = fileInfo.secureJar();
            ModuleClassLoaderAccess targetLoader = ModuleSystemAccessor.getModuleClassLoader("GAME",LOGGER);
            String existingName = secureJar.name();
            ModuleLayerAccess layer = ModuleSystemAccessor.getModuleLayer(targetLayerName,LOGGER);
            ModuleAccess module = layer.getAnyModule(existingName,name);
            ModuleDescriptorAccess descriptor;
            boolean existed = false;
            if(Objects.nonNull(module)) {
                LOGGER.info("Found existing module to set up for {}",name);
                descriptor = module.getDescriptor();
                descriptor.setName(name);
                existed = true;
            } else {
                LOGGER.info("Setting up new module with name {}",name);
                descriptor = fileInfo.newModuleDescriptor(name,secureJar);
            }
            ModuleReferenceAccess reference = secureJar.newModuleFinder().getModuleReference(existingName);
            reference.setDescriptor(descriptor);
            URI uri = reference.location();
            ResolvedModuleAccess resolvedModule = targetLoader.configuration().newResolvedModule(reference);
            
            //This looks stupid but packages and finalizedPkgs need to stay unique.
            //Any packages that were already finalized are skipped
            Set<String> packages = resolvedModule.packages(true);
            packages.removeAll(finalizedPkgs);
            finalizedPkgs.addAll(packages);
            Class<?> cModule = Class.forName("java.lang.Module");
            if(Objects.isNull(module)) module = Constructors.newInstanceOf(cModule,layer,targetLoader,descriptor,uri);
            addModuleThouroughly(module,resolvedModule,layer,name,packages,reference,targetLoader);
            LOGGER.info("Finished setting up {}",module);
            
            //nuke & finalize
            ModuleClassLoaderAccess boot = bootLoaderAccess();
            ModuleClassLoaderAccess service = ModuleSystemAccessor.getModuleClassLoader("SERVICE",LOGGER);
            ModuleClassLoaderAccess plugin = ModuleSystemAccessor.getModuleClassLoader("PLUGIN",LOGGER);
            nukeLoaderFields(name,boot,service,plugin);
            if(!existingName.equals(name) && existed) nukeLoaderFields(existingName,boot,service,plugin,targetLoader);
            finalizeModule(existingName,name,module,targetLoader,boot,service,plugin);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
            LOGGER.warn("SUCCESSFULLY LOADED {} TO THE GAME LAYER HAVE A NICE DAY", name);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
        } catch(Throwable t) {
            LOGGER.error("Failed to load new module!",t);
        }
    }
    
    public static void logModuleNames(String ... layerNames) {
        LOGGER.debug("Printing all module names for the following layers: {}",(Object)layerNames);
        for(Entry<String,Collection<String>> entry : ModuleSystemAccessor.getAllModuleNames(LOGGER,layerNames).entrySet()) {
            LOGGER.debug("\t{}",entry.getKey());
            for(String moduleName : entry.getValue()) LOGGER.debug("\t\t{}",moduleName);
        }
        LOGGER.debug("Finished printing all requested module names");
    }
    
    public static void logLayerPaths(String ... layerNames) {
        LOGGER.debug("Printing all paths for the following layers: {}",(Object)layerNames);
        for(Entry<String,Collection<String>> entry : getAllLayerPaths(layerNames).entrySet()) {
            LOGGER.debug("\t{}",entry.getKey());
            for(String path : entry.getValue()) LOGGER.debug("\t\t{}",path);
        }
        LOGGER.debug("Finished printing all requested paths");
    }
    
    /**
     * Add the module for the given package to the GAME layer and nuke all references to it from other layers
     */
    public static void nukeAndFinalize(IModInfo mod, String pkg, Set<String> finalizedPkgs) {
        LOGGER.info("Finalizing package {}",pkg);
        ModuleClassLoaderAccess boot = ModuleSystemAccessor.getModuleClassLoader(bootLoader(),LOGGER);
        ModuleClassLoaderAccess service = ModuleSystemAccessor.getLayerModuleClassLoader("SERVICE",LOGGER);
        ModuleClassLoaderAccess plugin = ModuleSystemAccessor.getLayerModuleClassLoader("PLUGIN",LOGGER);
        Object[] found = findModuleLoaderForPackage(pkg,boot,service,plugin);
        if(Objects.isNull(found)) {
            loadNewModuleTo(mod,"GAME",finalizedPkgs);
            return;
        }
        ModuleClassLoaderAccess foundLoader = (ModuleClassLoaderAccess)found[0];
        ResolvedModuleAccess resolvedModule = (ResolvedModuleAccess)found[1];
        LOGGER.info("Got resolved module as {}",resolvedModule);
        String name = resolvedModule.name();
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        LOGGER.warn("NUKING ALL REFERENCES OF MODULE {} FROM THE BOOT, SERVICE, & PLUGIN LAYERS",name);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        Map<String,Object> bootRoots = foundLoader.resolvedRoots();
        ModuleReferenceAccess ref = foundLoader.getRoot(name);
        ModuleLayerAccess foundLayer = ModuleSystemAccessor.getModuleLayer((String)found[2],LOGGER);
        Map<String,Object> layerModules = foundLayer.nameToModule();
        ModuleAccess module = foundLayer.getModule(name);
        ModuleClassLoaderAccess target = ModuleSystemAccessor.getLayerModuleClassLoader("GAME",LOGGER);
        ModuleLayerAccess gameLayer = ModuleSystemAccessor.getModuleLayer("GAME",LOGGER);
        Set<String> packages = resolvedModule.packages(true);
        packages.removeAll(finalizedPkgs);
        packages = Collections.unmodifiableSet(packages);
        finalizedPkgs.addAll(packages);
        addModuleThouroughly(module,resolvedModule,gameLayer,name,packages,ref,target);
        
        //nuke & finalize
        nukeLoaderFields(name,boot,service,plugin);
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
    
    /**
     * Remove references to the input module from the associated ModuleClassLoader instances.
     * Config
     */
    static void nukeLoaderFields(String moduleName, ModuleClassLoaderAccess ... loaders) {
        for(ModuleClassLoaderAccess loader : loaders) {
            loader.configuration().removeModule(moduleName);
            loader.removeRoot(moduleName);
            loader.removeSecureModule(moduleName);
            ResolvedModuleAccess module = loader.lookupResolvedModule(moduleName);
            if(Objects.nonNull(module)) loader.removePackages(module.packages());
            loader.getModuleLayer().removeModule(moduleName);
        }
    }
    
    /**
     * Not present in Java 8
     */
    static ClassLoader platformLoader() {
        return Methods.invokeStaticDirect(ClassLoader.class,"getPlatformClassLoader");
    }
    
    public static void removeDevModules(String ... layers) {
        if(!MODULE_LAYERS) {
            LOGGER.debug("Removing dev modules");
            removeResolvedModules(Arrays.asList("BOOT","SERVICE","main"));
            removeResolvedModules("SERVICE","forge");
        }
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeResolvedModules(Collection<String> layerNames, String ... moduleNames) {
        for(String layerName : layerNames) removeResolvedModules(layerName,moduleNames);
    }
    
    @SuppressWarnings("SameParameterValue")
    static void removeResolvedModules(String layerName, String ... moduleNames) {
        for(String moduleName : moduleNames) removeResolvedModule(layerName,moduleName);
    }
    
    static void removeResolvedModule(String layerName, String moduleName) {
        ModuleSystemAccessor.getModuleClassLoader(layerName,LOGGER).removeModuleFully(moduleName);
    }
    
    public static void removeServiceFrom(String service, String impl, String layer) {
        ClassHelper.checkBurningWaveInit();
        LOGGER.info("Attempting to fix service {} (implementation of {})",impl,service);
        ModuleSystemAccessor.getModuleLayer(layer,LOGGER).getServicesCatalog().removeImplementations(service,impl);
        LOGGER.info("Sucessfully removed all service providers from {} layer for {}",layer,impl);
    }
    
    /**
     * Get name of resolved module via reflection since this is a Java 8 context
     */
    static Object resolvedDescriptor(Object resolvedModule) {
        if(Objects.nonNull(resolvedModule)) return Methods.invokeDirect(resolvedModule,"descriptor");
        LOGGER.error("Cannot get ModuleDescriptor for null ResolvedModule!");
        return Collections.emptySet();
    }
    
    /**
     * Since this class is intially loaded in the SERVICE layer which has BOOT as a parent separate from PLUGIN,
     * we need a workaround for the PLUGIN layer thinking there are duplicate modules.
     * This is needed since IModLanguageProvider implementations are forced into PLUGIN layer from service loading and
     * can likely only be called via reflection.
     */
    public static void resyncModules(ClassLoader loaderTo, String layerTo, ClassLoader loaderFrom) {
        if(isJava8()) return; //Not needed on Java 8
        if(!MODULE_LAYERS) { //Resolve differently in dev since the modules are initially loaded in the BOOT layer
            LOGGER.debug("Skipping PLUGIN layer module resyncing (-Dtil.debug.forge.modules.layers=false)");
            bootLoaderAccess().moveModulesTo("PLUGIN","forge","main");
            logModuleNames("BOOT","SERVICE","PLUGIN");
            return;
        }
        resyncModules(ModuleSystemAccessor.getModuleClassLoader(loaderTo,LOGGER),layerTo,
                      ModuleSystemAccessor.getModuleClassLoader(loaderFrom,LOGGER));
    }
    
    private static void resyncModules(ModuleClassLoaderAccess loaderTo, String layerTo,
            ModuleClassLoaderAccess loaderFrom) {
        LOGGER.info("Resyncing module to {}",layerTo);
        final String pkg = "mods.thecomputerizer.theimpossiblelibrary.forge.core";
        if(!"PLUGIN".equals(layerTo)) loaderFrom.configuration().addModule(loaderFrom.getResolvedModule(pkg));
        
        //Remove module from PLUGIN layer
        ResolvedModuleAccess resolvedModule = loaderTo.getResolvedModule(pkg);
        String name = resolvedModule.name();
        ConfigurationAccess configuration = loaderTo.configuration();
        configuration.removeModule(resolvedModule);
        Set<String> packages = resolvedModule.packages();
        
        //Finalize by dealing with the module layers & fixing parent loaders
        ModuleSystemAccessor.getModuleLayer(layerTo,LOGGER).removeModule(name);
        loaderTo.addParentLoaders(resolvedModule.packages(),loaderFrom);
        loaderTo.removeRoot(name);
        
        loaderTo.removePackagesForModule(resolvedModule); //Prevent reading duplicate modules
    }
    
    public static void sanityCheckModule(Class<?> c, String name) {
        ClassAccess access = ModuleSystemAccessor.getClassAccess(c,LOGGER);
        if(Objects.isNull(access)) {
            LOGGER.error("Failed to get ClassAccess for {}! Cannot run sanity check",c);
            return;
        }
        String moduleName = access.getModuleName();
        if(!name.equals(moduleName)) {
            //By this point the class is definitely in the GAME layer regardless of whether the module is correct
            access.setModule("GAME",name);
            LOGGER.info("Moved {} from module {} to module {}",c,moduleName,name);
        }
    }
    
    public static void verifyModule(String className, IModInfo info, Object moduleLayer) {
        LOGGER.info("Verifying that {} is valid for {} and can be found in {}",className,info,moduleLayer);
        String modid = info.getModId();
        ModFileInfoAccess fileInfo = ModuleSystemAccessor.getModFileInfo(info.getOwningFile(),LOGGER);
        String moduleName = fileInfo.moduleName();
        if(!modid.equals(moduleName)) LOGGER.error("Mod id {} does not equal module name {}!",modid,moduleName);
        ModuleLayerAccess layerAccess = ModuleSystemAccessor.getModuleLayer(moduleLayer,LOGGER);
        Optional<Object> optionalModule = layerAccess.findModule(moduleName);
        if(!optionalModule.isPresent()) {
            layerAccess.findAndAddModule(moduleLayer,moduleName,modid);
            optionalModule = layerAccess.findModule(moduleName);
        }
        if(optionalModule.isPresent())
            ModuleSystemAccessor.getModule(optionalModule.get(),LOGGER).addClassIfMissing(className,layerAccess);
        else LOGGER.error("Module {} is not present in the target layer!",moduleName);
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