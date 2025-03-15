package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import cpw.mods.cl.JarModuleFinder;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.SecureJar.Provider;
import cpw.mods.jarhandling.impl.Jar;
import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import io.github.toolfactory.jvm.function.catalog.ConsulterSupplyFunction;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.locating.IModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.net.URI;
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
public class NeoForgeCoreLoader {
    
    private static final String API_PKG = "mods.thecomputerizer.theimpossiblelibrary.api";
    private static final String NEOFORGE_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge";
    private static final String APICORE = API_PKG+".core.CoreAPI";
    private static final Logger LOGGER = LogManager.getLogger("TIL NeoForgeCoreLoader");
    
    //The module system forced me to find a very powerful alternative, but at least I don't need to do Unsafe hacking
    static {
        if(NeoForgeCoreLoader.class.getClassLoader()!=bootLoader())
            LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
        ClassHelper.checkBurningWaveInit();
    }
    
    /**
     * Fix Configuration instance for both the BOOT and SERVICE layers
     */
    private static void addConfigurationModule(Configuration configuration, String name, ResolvedModule resolvedModule,
            ClassLoader thisLoader) {
        
        Map<String,ResolvedModule> nameToModule = new HashMap<>(Fields.getDirect(configuration,"nameToModule"));
        nameToModule.putIfAbsent(name,resolvedModule);
        Fields.setDirect(configuration,"nameToModule",Collections.unmodifiableMap(nameToModule));
        
        //Prevent reading duplicate modules
        Configuration thisConfig = Fields.getDirect(thisLoader,"configuration");
        removeFromUnmodifiableSetField(thisConfig,"modules",resolvedModule);
        removeFromUnmodifiableMapField(thisConfig,"nameToModule",name);
        
        //Update the configuration field for the module
        Fields.setDirect(resolvedModule,"cf",thisConfig);
        
        //Deal with the module graph ._.
        Map<ResolvedModule,Set<ResolvedModule>> thisGraph = new HashMap<>(Fields.getDirect(thisConfig,"graph"));
        thisGraph.entrySet().removeIf(entry -> entry.getKey().name().equals(name));
        thisGraph.forEach((key,values) -> values.remove(resolvedModule));
        Fields.setDirect(thisConfig,"graph",thisGraph);
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(Module module, ResolvedModule resolvedModule, ModuleLayer moduleLayer, String name,
            Set<String> packages, ModuleReference moduleRef, ClassLoader target) {
        Fields.setDirect(module,"name",name);
        Configuration configuration = Fields.getDirect(target,"configuration");
        Map<String,ModuleReference> resolvedRoots = Fields.getDirect(target,"resolvedRoots");
        Map<String,ResolvedModule> packageLookup = Fields.getDirect(target,"packageLookup");
        Map<String,ClassLoader> parentLoaders = Fields.getDirect(target,"parentLoaders");
        resolvedRoots.put(name,moduleRef);
        for(String pkg : packages) packageLookup.put(pkg,resolvedModule);
        parentLoaders.entrySet().removeIf(entry -> packages.contains(entry.getKey()));
        Set<ResolvedModule> configModules = new HashSet<>(configuration.modules());
        Map<String,ResolvedModule> configNameToModule = new HashMap<>(Fields.getDirect(configuration,"nameToModule"));
        configModules.removeIf(rm -> name.equals(rm.name()));
        configModules.add(resolvedModule);
        configNameToModule.put(name,resolvedModule);
        Fields.setDirect(configuration,"modules",Collections.unmodifiableSet(configModules));
        Fields.setDirect(configuration,"nameToModule",Collections.unmodifiableMap(configNameToModule));
        Set<Module> layerModules = moduleLayer.modules();
        boolean found = false;
        if(Objects.nonNull(layerModules)) {
            layerModules = new HashSet<>(layerModules);
            for(Module lModule : layerModules) {
                String lName = lModule.getName();
                if(Objects.nonNull(lName) && lName.equals(module.getName())) {
                    found = true;
                    break;
                }
            }
        }
        if(!found) {
            if(Objects.nonNull(layerModules)) {
                layerModules.add(module);
                Fields.setDirect(moduleLayer,"modules",layerModules);
            }
            Map<String,Module> layerNameToModule = new HashMap<>(Fields.getDirect(moduleLayer,"nameToModule"));
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
    private static void addResolvedModule(ResolvedModule module, ClassLoader thisLoader) {
        ClassLoader loader = bootLoader();
        Map<String,ModuleReference> roots = Fields.getDirect(loader, "resolvedRoots");
        Map<String,ResolvedModule> packageLookup = Fields.getDirect(loader,"packageLookup");
        ModuleReference reference = Methods.invokeDirect(module,"reference");
        ModuleDescriptor descriptor = reference.descriptor();
        String name = descriptor.name();
        roots.put(name,reference);
        Set<String> packages = descriptor.packages();
        for(String pkg : packages) {
            packageLookup.put(pkg,module);
        }
        
        //Finalize by moving the original Module from SERVICE to the BOOT layer & fixing parent loaders
        moveModuleToLayer(loader,"BOOT","SERVICE",name);
        Map<String,ClassLoader> parentLoaders = Fields.getDirect(thisLoader,"parentLoaders");
        for(String pkg : packages) parentLoaders.put(pkg,loader);
        
        //Fix configurations & prevent reading duplicate modules
        addConfigurationModule(Fields.getDirect(loader,"configuration"),name,module,thisLoader);
        Map<String,ModuleReference> theseRoots = Fields.getDirect(thisLoader,"resolvedRoots");
        theseRoots.remove(name);
        
        LOGGER.info("Finished migrating module {} from the SERVICE layer to the BOOT layer",name);
    }
    
    /**
     * Should be the ClassLoader for the BOOT layer
     */
    public static ClassLoader bootLoader() {
        return Launcher.class.getClassLoader();
    }
    
    static ModuleDescriptor buildNewModuleDescriptor(String name, Jar jar, List<String> usesServices) {
        LOGGER.info("Building new module descriptor for {}",name);
        Set<String> packages = jar.getPackages();
        List<Provider> providers = jar.getProviders();
        JarMetadata metadata = Fields.getDirect(jar, "metadata");
        String version = Methods.invokeDirect(metadata,"version");
        Builder builder = ModuleDescriptor.newAutomaticModule(name).version(version).packages(packages);
        for(Provider provider : providers) {
            List<String> actualProviders = provider.providers();
            if(!actualProviders.isEmpty()) {
                String service = provider.serviceName();
                builder = builder.provides(service,new ArrayList<>(actualProviders));
            }
        }
        for(String service : usesServices) builder.uses(service);
        ModuleDescriptor desc = builder.build();
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
            ModuleLayer layer = getModuleLayer(layerName);
            Map<String,Module> nameToModule = Fields.getDirect(layer,"nameToModule");
            for(Module module : nameToModule.values()) {
                for(String pkg : module.getDescriptor().packages()) {
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
    static void finalizeModule(String oldName, String newName, Module module, ClassLoader target,
            ClassLoader ... loaders) {
        String moduleName = module.getName();
        Set<Class<?>> allMoved = new HashSet<>();
        Map<ClassLoader,Collection<Class<?>>> removals = new HashMap<>();
        for(ClassLoader loader : loaders) {
            Collection<Class<?>> classes = Fields.getDirect(loader,"classes");
            for(Class<?> c : classes) {
                String name = c.getModule().getName();
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
            String name = targetClass.getModule().getName();
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
    public static Object[] findModuleLoaderForPackage(String pkg, ClassLoader[] loaders) {
        for(int i=0;i<loaders.length;i++) {
            ClassLoader loader = loaders[i];
            String name = i==0 ? "BOOT" : (i==1 ? "SERVICE" : "PLUGIN");
            Map<String,Object> lookup = Fields.getDirect(loader,"packageLookup");
            Object resolvedModule = lookup.get(pkg);
            if(Objects.nonNull(resolvedModule)) return new Object[]{loader,resolvedModule,name};
        }
        return null;
    }
    
    public static void fixForServiceLayer() {
        String pkg = ConsulterSupplyFunction.class.getPackage().getName();
        ClassLoader thisLoader = NeoForgeCoreLoader.class.getClassLoader();
        Map<String,ResolvedModule> packageLookup = Fields.getDirect(thisLoader,"packageLookup");
        ResolvedModule module = packageLookup.get(pkg);
        if(Objects.nonNull(module)) {
            addResolvedModule(module,thisLoader);
            packageLookup.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
        } else LOGGER.fatal("FAILED TO GET RESOLVED MODULE FOR {}",pkg);
    }
    
    /**
     * Get the command line argument handler in case we need to check stuff very early in the loading process
     */
    static ArgumentHandler getArgumentHandler() {
        LOGGER.info("Atempting to get ArgumentHandler for loader {}",Thread.currentThread().getContextClassLoader());
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
    
    /**
     * Get IModuleLayerManager based on current environment
     */
    static IModuleLayerManager getLayerManager() {
        return INSTANCE.environment().findModuleLayerManager().orElse(null);
    }
    
    @SuppressWarnings("SameParameterValue")
    static Module getModuleFromLayer(String layerName, String name) {
        ModuleLayer layer = getModuleLayer(layerName);
        Map<String,Module> nameToModule = Fields.getDirect(layer,"nameToModule");
        return nameToModule.get(name);
    }
    
    public static Module getModuleFromPackage(String pkg, String layerName, boolean newFormat) {
        ModuleLayer layer = getModuleLayer(layerName);
        Map<String,ResolvedModule> packageLookup = Fields.get(layerClassLoader(layerName),"packageLookup");
        ResolvedModule resolved = packageLookup.get(pkg);
        if(Objects.isNull(resolved)) {
            LOGGER.error("Cannot get module for pacakge {} since it does not exist in input layer {}!",pkg,layerName);
            return null;
        }
        Map<String,Module> nameToModule = Fields.getDirect(layer,"nameToModule");
        return nameToModule.get(resolved.name());
    }
    
    /**
     * Get a ModuleLayer instance by name (BOOT/SERVICE/PLUGIN/GAME) for module manipulation
     */
    static ModuleLayer getModuleLayer(String name) {
        IModuleLayerManager layerManager = getLayerManager();
        if(Objects.isNull(layerManager)) {
            LOGGER.error("IModuleLayerManager instance not found in environment!");
            return null;
        }
        return layerManager.getLayer(Layer.valueOf(name)).orElse(null);
    }
    
    //TODO Verify this is needed and correct
    static String getVersionFromNeoForgeVersion(String neoforgeVersion) {
        String ignore = "neoforge-";
        String actualVersion = neoforgeVersion.startsWith(ignore) ? neoforgeVersion.substring(ignore.length()) : neoforgeVersion;
        String version = "1.21.1";
        if(actualVersion.startsWith("49.")) version = "1.20.4";
        else if(actualVersion.startsWith("50.")) version = "1.20.6";
        LOGGER.info("Guessed mc version {} from neoforge version {}",version,neoforgeVersion);
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
            LOGGER.info("Found fml.mcVersion arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        LOGGER.info("--fml.mcVersion was not found so the mc version will be guessed from --version instead");
        for(int i=0;i<rawArgs.length;i++) {
            if(rawArgs[i].equals("--version")) {
                versionIndex = i+1;
                found = true;
                break;
            }
        }
        if(found) {
            LOGGER.info("Found neoforge version arg at index {}",versionIndex);
            return getVersionFromNeoForgeVersion(rawArgs[versionIndex]);
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
            //noinspection deprecation
            return coreClass.newInstance();
        } catch(InstantiationException | IllegalAccessException ex) {
            LOGGER.fatal("Caught reflection exception while trying to get CoreAPI instance as {}",coreClass,ex);
        } catch(Exception ex) {
            LOGGER.fatal("Unknown error while trying to get CoreAPI instance as {}",coreClass,ex);
        }
        LOGGER.fatal("Failed to initialize CoreAPI [NeoForge-{}] using {}",version,loader);
        return null;
    }
    
    /**
     * Tries to get the ClassLoader instance associated with the given layer name
     */
    public static ClassLoader layerClassLoader(String name) {
        Layer layer = Layer.valueOf(name);
        IModuleLayerManager layerManager = getLayerManager();
        if(Objects.isNull(layerManager)) {
            LOGGER.error("IModuleLayerManager instance not found in environment!");
            return bootLoader();
        }
        Map<Layer,?> completedLayers = Fields.getDirect(layerManager,"completedLayers");
        ClassLoader loader = Fields.get(completedLayers.get(layer),"cl");
        LOGGER.debug("Returning ClassLoader for layer {} as {}",name,loader);
        return loader;
    }
    
    /**
     * Define necessary classes for the versioned CoreAPI instance
     * Returns the instance class
     */
    static Class<?> loadAPI(String version) {
        String className = versionClassName("core.TILCoreNeoForge",version);
        ClassLoader loader = bootLoader();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className,true,loader);
        } catch(Exception ex) {
            LOGGER.error("Failed to load class {} for {}",className,loader,ex);
        }
        if(Objects.isNull(clazz)) throw new RuntimeException("Failed to load CoreAPI instance [NeoForge-"+version+"]");
        LOGGER.info("Successfully loaded CoreAPI instance {}",clazz);
        return clazz;
    }
    
    @SuppressWarnings("SameParameterValue")
    static void loadNewModuleTo(@Nullable IModInfo mod, String targetLayerName, Set<String> finalizedPkgs) {
        if(Objects.isNull(mod)) {
            LOGGER.error("Cannot load module from nonexistent file!");
            return;
        }
        try {
            IModFileInfo fileInfo = mod.getOwningFile();
            IModFile file = fileInfo.getFile();
            SecureJar jar = file.getSecureJar();
            ClassLoader targetLoader = layerClassLoader("GAME");
            String existingName = Methods.invokeDirect(jar,"name");
            String name = mod.getModId(); //Usually the same as existingName, but there are some edge cases...
            ModuleLayer layer = getModuleLayer(targetLayerName);
            Map<String,Module> nameToModule = Fields.getDirect(layer,"nameToModule");
            Module module = nameToModule.get(existingName);
            if(Objects.isNull(module)) module = nameToModule.get(name);
            boolean existed = false;
            if(Objects.nonNull(module)) {
                LOGGER.info("Found existing module to set up for {}",name);
                existed = true;
            } else LOGGER.info("Setting up new module with name {}",name);
            ModuleDescriptor descriptor;
            if(Objects.nonNull(module)) {
                descriptor = module.getDescriptor();
                Fields.setDirect(descriptor,"name",name);
            }
            else {
                List<String> usesServices = fileInfo.usesServices();
                descriptor = buildNewModuleDescriptor(name,(Jar)jar,usesServices);
            }
            JarModuleFinder finder = JarModuleFinder.of(jar);
            Map<String,ModuleReference> refMap = Fields.getDirect(finder,"moduleReferenceMap");
            ModuleReference reference = refMap.get(existingName);
            URI uri = Fields.getDirect(reference,"location");
            Configuration config = Fields.getDirect(targetLoader,"configuration");
            Fields.setDirect(reference,"descriptor",descriptor);
            ResolvedModule resolvedModule = Constructors.newInstanceOf(ResolvedModule.class,config,reference);
            Set<String> packages = new HashSet<>(resolvedModule.reference().descriptor().packages());
            packages.removeAll(finalizedPkgs);
            packages = Collections.unmodifiableSet(packages);
            finalizedPkgs.addAll(packages);
            if(Objects.isNull(module)) module = Constructors.newInstanceOf(Module.class,layer,targetLoader,descriptor,uri);
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
    
    @SuppressWarnings("SameParameterValue")
    public static void moveModuleToLayer(ClassLoader targetLoader, String layerTo, String layerFrom, String moduleName) {
        ModuleLayer to = getModuleLayer(layerTo);
        if(Objects.isNull(to)) {
            LOGGER.error("Unable to move module {}! Cannot find target layer {}",moduleName,layerTo);
            return;
        }
        ModuleLayer from = getModuleLayer(layerFrom);
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
        ResolvedModule resolvedModule = (ResolvedModule)found[1];
        LOGGER.info("Got resolved module as {}",resolvedModule);
        String name = resolvedModule.name();
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        LOGGER.warn("NUKING ALL REFERENCES OF MODULE {} FROM THE BOOT, SERVICE, & PLUGIN LAYERS",name);
        LOGGER.warn("------------------------------------------------------------------------------------------------");
        Map<String,ModuleReference> bootRoots = Fields.getDirect(foundLoader,"resolvedRoots");
        ModuleReference ref = bootRoots.get(name);
        ModuleLayer foundLayer = getModuleLayer((String)found[2]);
        Map<String,Module> layerModules = Fields.getDirect(foundLayer,"nameToModule");
        Module module = layerModules.get(name);
        ClassLoader target = layerClassLoader("GAME");
        ModuleLayer moduleLayer = getModuleLayer("GAME");
        Set<String> packages = new HashSet<>(resolvedModule.reference().descriptor().packages());
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
    
    static void nukeConfig(String name, ClassLoader ... loaders) {
        for(ClassLoader loader : loaders) {
            Configuration configuration = Fields.getDirect(loader,"configuration");
            Map<String,ResolvedModule> nameToModule = new HashMap<>(Fields.getDirect(configuration,"nameToModule"));
            ResolvedModule module = nameToModule.get(name);
            if(Objects.nonNull(module)) {
                nameToModule.remove(name);
                Fields.setDirect(configuration,"nameToModule",Collections.unmodifiableMap(nameToModule));
                Set<ResolvedModule> modules = new HashSet<>(configuration.modules());
                modules.remove(module);
                Fields.setDirect(configuration,"modules",modules);
            }
        }
    }
    
    static void nukeLoaderFields(String moduleName, ClassLoader ... loaders) {
        for(ClassLoader loader : loaders) {
            Map<String,ModuleReference> resolvedRoots = Fields.getDirect(loader,"resolvedRoots");
            Map<String,ResolvedModule> packageLookup = Fields.getDirect(loader,"packageLookup");
            Map<String,ClassLoader> parentLoaders = Fields.getDirect(loader,"parentLoaders");
            resolvedRoots.remove(moduleName);
            ResolvedModule module = null;
            for(Entry<String,ResolvedModule> pkgEntry : packageLookup.entrySet()) {
                ResolvedModule value = pkgEntry.getValue();
                if(moduleName.equals(value.name())) {
                    module = value;
                    break;
                }
            }
            if(Objects.isNull(module)) continue;
            Set<String> packages = module.reference().descriptor().packages();
            if(Objects.isNull(packages)) continue;
            for(String pkg : packages) {
                packageLookup.remove(pkg);
                parentLoaders.remove(pkg);
            }
        }
    }
    
    static void nukeModuleLayer(String name, String ... layers) {
        for(String layer : layers) {
            ModuleLayer moduleLayer = getModuleLayer(layer);
            if(Objects.isNull(moduleLayer)) {
                LOGGER.warn("Not nuking module layer {} since it was not found",layer);
                continue;
            }
            Map<String,Module> nameToModule = new HashMap<>(Fields.getDirect(moduleLayer,"nameToModule"));
            nameToModule.remove(name);
            Fields.setDirect(moduleLayer,"nameToModule",Collections.unmodifiableMap(nameToModule));
            Set<Module> modules = moduleLayer.modules();
            if(Objects.nonNull(modules)) {
                modules = new HashSet<>(modules);
                modules.removeIf(m -> name.equals(m.getName()));
                Fields.setDirect(moduleLayer,"modules",Collections.unmodifiableSet(modules));
            }
        }
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
     * Since this class is intially loaded in the SERVICE layer which has BOOT as a parent separate from PLUGIN,
     * we need a workaround for the PLUGIN layer thinking there are duplicate modules.
     * This is needed since IModLanguageProvider implementations are forced into PLUGIN layer from service loading and
     * can likely only be called via reflection.
     */
    public static void resyncModules(ClassLoader loaderTo, String layerTo, ClassLoader loaderFrom) {
        LOGGER.info("Resyncing module to {}",layerTo);
        final String pkg = "mods.thecomputerizer.theimpossiblelibrary.neoforge.core";
        //Fix BOOT modules first
        Map<String,ResolvedModule> fromPkg = Fields.getDirect(loaderFrom,"packageLookup");
        ResolvedModule fromModule = fromPkg.get(pkg);
        Configuration fromCfg = Fields.getDirect(loaderFrom,"configuration");
        if(!"PLUGIN".equals(layerTo)) {
            Set<ResolvedModule> modules = new HashSet<>(fromCfg.modules());
            modules.add(fromModule);
            Fields.setDirect(fromCfg,"modules",modules);
        }
        //Remove module from PLUGIN layer
        Map<String,ResolvedModule> pkgs = Fields.getDirect(loaderTo,"packageLookup");
        ResolvedModule module = pkgs.get(pkg);
        String name = module.name();
        Map<String,ModuleReference> roots = Fields.getDirect(loaderTo,"resolvedRoots");
        roots.remove(name);
        Configuration config = Fields.getDirect(loaderTo,"configuration");
        removeFromUnmodifiableSetField(config,"modules",module);
        removeFromUnmodifiableMapField(config,"nameToModule",name);
        Set<String> packages = module.reference().descriptor().packages();
        
        //Finalize by dealing with the module layers & fixing parent loaders
        ModuleLayer layer = getModuleLayer(layerTo);
        Map<String,Module> map = new HashMap<>(Fields.getDirect(layer,"nameToModule"));
        map.remove(name);
        Fields.setDirect(layer,"nameToModule",map);
        Map<String,ClassLoader> parentLoaders = Fields.getDirect(loaderTo,"parentLoaders");
        for(String p : packages) parentLoaders.put(p,loaderFrom);
        
        //Deal with the module graph again ._.
        Map<ResolvedModule,Set<ResolvedModule>> graph = new HashMap<>(Fields.getDirect(config,"graph"));
        graph.remove(module); //Don't cross-check the name since we have 2 different module this time
        graph.forEach((key,values) -> values.remove(module));
        Fields.setDirect(config,"graph",graph);
        
        pkgs.entrySet().removeIf(entry -> module.equals(entry.getValue())); //Prevent reading duplicate modules
    }
    
    public static void sanityCheckModule(Class<?> c, String name) {
        String actualName = c.getModule().getName();
        if(!name.equals(actualName)) {
            //By this point the class is definitely in the GAME layer regardless of whether the module is correct
            Fields.setDirect(c,"module",getModuleFromLayer("GAME",name));
            LOGGER.info("Moved {} from module {} to module {}",c,actualName,name);
        }
    }
    
    public static void verifyModule(String className, IModInfo info, ModuleLayer layer) throws Exception {
        LOGGER.info("Verifying that {} is valid for {} and can be found in {}",className,info,layer);
        IModFileInfo fileInfo = info.getOwningFile();
        String modid = info.getModId();
        String moduleName = fileInfo.moduleName();
        IModFile file = fileInfo.getFile();
        if(!modid.equals(moduleName)) LOGGER.error("Mod id {} does not equal module name {}!",modid,moduleName);
        Optional<Module> optionalModule = layer.findModule(moduleName);
        if(optionalModule.isPresent()) {
            Module module = optionalModule.get();
            Class<?> c = Class.forName(className,false,layer.findLoader(module.getName()));
            Module cModule = c.getModule();
            if(module!=cModule) {
                LOGGER.debug("Attempting to fix modules that are not equal");
                Fields.setDirect(c,"module",module);
            } else LOGGER.debug("Modules are equal");
        }
        LOGGER.info("Finished verifying {}",className);
    }
    
    /**
     * Include any packages after the base. Should include NeoForge in name if necessary
     */
    @SuppressWarnings("SameParameterValue")
    static String versionClassName(String name, String version) {
        return versionPackage(version)+"."+versionQuantify(name,version);
    }
    
    /**
     * ModLoader will always be NeoForge so we can cheat this a bit more than the CoreAPI implementation
     */
    static String versionPackage(String version) {
        String[] split = version.split("\\.");
        if(split.length<3) throw new RuntimeException("Can't parse package for unknown version "+version);
        return NEOFORGE_PKG+".v"+split[1]+".m"+split[2];
    }
    
    static String versionQuantify(String name, String version) {
        return name+version.replace('.','_');
    }
}