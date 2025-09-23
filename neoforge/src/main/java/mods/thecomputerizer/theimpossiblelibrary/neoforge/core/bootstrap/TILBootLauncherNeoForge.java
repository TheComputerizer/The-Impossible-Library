package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.jarhandling.impl.JarContentsImpl;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import cpw.mods.niofs.union.UnionFileSystem;
import cpw.mods.niofs.union.UnionFileSystemProvider;
import cpw.mods.niofs.union.UnionPath;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncher;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.NeoforgeModuleAccess;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;

import java.lang.module.Configuration;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static java.io.File.separator;
import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_NAME;

/**
 * In a dev environment, we can add ourselves to the legacy classpath for BOOT layer service discovery
 * We can make use of this by implementing ILaunchPluginService
 */
public class TILBootLauncherNeoForge extends TILLauncher implements ILaunchPluginService {
    
    static final boolean JAVA21 = System.getProperty("java.version").startsWith("21");
    static final String BASE_PKG = "mods.thecomputerizer.theimpossiblelibrary";
    static final String CORE_PKG = BASE_PKG+".neoforge."+(JAVA21?  "v21." : "")+"core";
    static final String HACKS = BASE_PKG+".api.core.Hacks";
    static final String LANGUAGE_LOADER = CORE_PKG+"."+(JAVA21 ? "MultiVersionLanguageLoader" : "TILLanguageProvider");
    static final String LOCATOR = CORE_PKG+"."+(JAVA21 ? "TILSelfLocator" : "MultiVersionModLocator");
    static final String PACKAGE_VERSION_INFO = Package.class.getName()+"$VersionInfo";
    static final String READER = JAVA21 ? CORE_PKG+".MultiVersionModReader" : null;
    static final String SERVICE_LAUNCHER = CORE_PKG+".bootstrap.TILServiceLauncherNeoForge"+(JAVA21 ? "1_21" : "");
    /**
     * We can't use List#of here since it assumes the input elements are nonnull
     */
    static final Collection<String> SERVICES = Arrays.asList(SERVICE_LAUNCHER,LOCATOR,READER,LANGUAGE_LOADER);
    static final String[] DEV_MODULES = new String[]{"main","tilneoforge"};
    static final Logger LOGGER;
    
    static {
        out.println("[BOOT] Class init: "+TILBootLauncherNeoForge.class.getName());
        LOGGER = TILRef.createLogger(LOADER_NAME+" (Boot)");
    }
    
    static ClassLoader bootLoader() {
        return Launcher.class.getClassLoader();
    }
    
    /**
     * Attempts to find the greatest common parent path for all input paths
     */
    static String commonParent(String unionSeparator, Path ... paths) {
        assert Objects.nonNull(paths) && paths.length>0;
        Path parent = paths[0];
        if(paths.length>1) {
            while(Objects.nonNull(parent)) {
                int found = 0;
                for(int i=1;i<paths.length;i++) {
                    Path path = paths[i];
                    while(Objects.nonNull(path)) {
                        if(parent.equals(path)) {
                            found++;
                            break;
                        }
                        path = path.getParent();
                    }
                }
                if(found==paths.length-1) break;
                parent = parent.getParent();
            }
        }
        if(Objects.isNull(parent)) return null;
        String pathStr = parent.toString().replace(separator,unionSeparator);
        return pathStr.startsWith(unionSeparator) ? pathStr : unionSeparator+pathStr;
    }
    
    static void consolidateDevModules(String ... names) {
        LOGGER.info("Consolidating dev modules {} into {}",names,BOOT_ID);
        if(names.length<1) {
            LOGGER.error("Tried to consolidate empty dev module array");
            return;
        }
        URI unified = unify(names);
        if(Objects.nonNull(unified)) consolidateDevModules(unified,names);
        else LOGGER.error("Failed to unify dev module locations!");
        LOGGER.info("Finished consolidating dev modules");
    }
    
    static void consolidateDevModules(URI unified, String ... names) {
        String combinedName = BOOT_ID;
        String[] others = names.length>1 ? Arrays.copyOfRange(names,1,names.length) : new String[]{};
        LOGGER.info("Combining dev modules: layer = {} | combined name = {} | base = {} | others = {} | " +
                    "location = {}",BOOT,combinedName,names[0],others,unified);
        Class<?> bootJarClass = TILBootJar.class;
        NeoforgeModuleAccess.combineModules(BOOT,unified,combinedName,names[0],others);
        LOGGER.info("Combination successful! Validating service classes");
        Collection<String> validated = validateServices(bootJarClass,combinedName,unified);
        LOGGER.info("Successfully validated {} service classes: {}",validated.size(),validated);
    }
    
    static Method findSetFieldDirect(Class<?> hacksClass, Class<?> ... args) {
        final String methodName = "setFieldDirect";
        try {
            return hacksClass.getDeclaredMethod(methodName,args);
        } catch(Throwable t) {
            LOGGER.error("Failed to find method {}#{} with args {}",hacksClass.getName(),methodName,args,t);
        }
        return null;
    }
    
    static void fixReferenceJar(Class<?> bootJarClass, Module module, URI unified) {
        LOGGER.info("Fixing jar stuff");
        try {
            Configuration configuration = Hacks.getFieldDirect(module.getClassLoader(),"configuration");
            if(Objects.isNull(configuration)) {
                LOGGER.error("Failed to find configuration for module ClassLoader?");
                return;
            }
            ResolvedModule resolved = configuration.findModule(module.getName()).orElse(null);
            if(Objects.isNull(resolved)) {
                LOGGER.error("Failed to find ResolvedModule for Module?");
                return;
            }
            Hacks.invokeStatic(bootJarClass,"updateReference",LOGGER,resolved.reference(),unified);
        } catch(Throwable t) {
            LOGGER.error("Failed to update ModuleReference?",t);
        }
    }
    
    static void invokeMethod(Method method, Object ... args) {
        if(Objects.isNull(method)) {
            LOGGER.error("Cannot invoke null method with args {}",args);
            return;
        }
        try {
            method.invoke(null,args);
        } catch(Throwable t) {
            LOGGER.error("Failed to invoke method with args {}",args,t);
        }
    }
    
    static boolean isDevModule(Module module) {
        if(Objects.isNull(module)) return false;
        String moduleName = module.getName();
        for(String devModuleName : DEV_MODULES)
            if(Objects.nonNull(moduleName) && moduleName.equals(devModuleName)) return true;
        return false;
    }
    
    static <T> Set<Entry<String,T>> mapEntries(Object target, String mapFieldName) {
        try {
            Map<String,T> map = Hacks.getFieldDirect(target,mapFieldName);
            return Objects.nonNull(map) ? map.entrySet() : Collections.emptySet();
        } catch(Throwable t) {
            LOGGER.error("Failed to get map entries for field {} on target {}",mapFieldName,target,t);
        }
        return Collections.emptySet();
    }
    
    static Path modulePath(URI location) {
        if(Objects.isNull(location)) return null;
        Path path = Paths.get(location);
        return (path instanceof UnionPath union ? union.getFileSystem().getPrimaryPath() : path).toAbsolutePath();
    }
    
    static void printDebugStuff(ModuleLayer layer, Module module) {
        String moduleName = module.getName();
        try {
            LOGGER.info("");
            ModuleClassLoader loader = (ModuleClassLoader)module.getClassLoader();
            Configuration configuration = Hacks.getFieldDirect(loader,"configuration");
            if(Objects.nonNull(configuration)) {
                if(configuration==layer.configuration())
                    LOGGER.info("Verified that the layer Configuration is the same as the ModuleClassLoader configuration");
                else LOGGER.warn("Mismatched ModuleLayer/ModuleClassLoader Configuration instance??");
                ResolvedModule configurationModule = configuration.findModule(moduleName).orElse(null);
                if(Objects.nonNull(configurationModule)) {
                    LOGGER.info("Verified that module {} is present in the configuration for ModuleClassLoader {}",moduleName,loader);
                    LOGGER.info("Configuration module location = {}",configurationModule.reference().location().orElse(null));
                } else LOGGER.warn("Failed to find module {} in the configuration for ModuleClassLoader {}",moduleName,loader);
            } else LOGGER.error("Failed to get configuration for ModuleClassLoader {}",loader);
            LOGGER.info("");
            LOGGER.info("Printing packages for module {} found in layer {}",moduleName,layer);
            for(String pkg : module.getPackages()) LOGGER.info("\t- {}",pkg);
            LOGGER.info("");
            Set<Entry<String,ResolvedModule>> packageLookup = mapEntries(loader,"packageLookup");
            if(packageLookup.isEmpty()) LOGGER.warn("No entries found in packageLookup map?");
            else LOGGER.info("Printing package lookup for ModuleClassLoader (filtered for {})",moduleName);
            Map<String,ResolvedModule> uniqueModules = new HashMap<>();
            for(Entry<String,ResolvedModule> packageEntry : packageLookup) {
                String pkg = packageEntry.getKey();
                if(pkg.contains(moduleName)) {
                    ResolvedModule packageModule = packageEntry.getValue();
                    String packageModuleName = packageModule.name();
                    LOGGER.info("\t- [packageLookup] Package = {}",pkg);
                    LOGGER.info("\t- [packageLookup] Module = {}",packageModuleName);
                    uniqueModules.put(packageModuleName,packageModule);
                }
            }
            LOGGER.info("");
            Set<Entry<String,ClassLoader>> parentLoaders = mapEntries(loader,"parentLoaders");
            if(parentLoaders.isEmpty()) LOGGER.warn("No entries found in parentLoaders map?");
            else LOGGER.info("Printing parent loaders for ModuleClassLoader {}",loader);
            for(Entry<String,ClassLoader> parentEntry : parentLoaders) {
                LOGGER.info("\t- [parentLoaders] Package = {}",parentEntry.getKey());
                LOGGER.info("\t- [parentLoaders] ClassLoader = {}",parentEntry.getValue());
            }
            LOGGER.info("");
            Set<Entry<String,ModuleReference>> resolvedRoots = mapEntries(loader,"resolvedRoots");
            if(resolvedRoots.isEmpty()) LOGGER.warn("No entries found in resolvedRoots map?");
            else LOGGER.info("Printing roots for ModuleClassLoader {}",loader);
            for(Entry<String,ModuleReference> rootEntry : resolvedRoots) {
                String rootName = rootEntry.getKey();
                ModuleReference reference = rootEntry.getValue();
                LOGGER.info("\t- [resolvedRoots] Module = {}",rootName);
                LOGGER.info("\t- [resolvedRoots] Location = {}",reference.location().orElse(null));
                if(moduleName.equals(rootName)) {
                    LOGGER.info("\t- [resolvedRoots] Running additional checks for {}",moduleName);
                    LOGGER.info("\t\t- [resolvedRoots] Equal descriptors = {}",reference.descriptor()==module.getDescriptor());
                    if(uniqueModules.containsKey(rootName)) {
                        LOGGER.info("\t\t- [resolvedRoots] Equal references = {}",reference==uniqueModules.get(rootName).reference());
                    } else LOGGER.warn("\t\t- [resolvedRoots] Module was not found in packageLookup");
                }
            }
            LOGGER.info("");
        } catch(Throwable t) {
            LOGGER.error("Failed to print debug stuff for module {}",moduleName,t);
        }
    }
    
    static Class<?> setClassModule(Class<?> hacksClass, Class<?> c, Module m) {
        if(Objects.isNull(hacksClass)) {
            LOGGER.error("Cannot set module for {} to {} with null hacks class!",c,m.getName());
            return c;
        }
        if(Objects.isNull(c)) {
            LOGGER.error("Cannot set module of null class to {}!",m.getName());
            return null;
        }
        if(c.getModule()!=m) {
            Method setField = findSetFieldDirect(hacksClass,Object.class,String.class,Object.class);
            invokeMethod(setField,c,"module",m);
        } else LOGGER.info("Class {} already present in module {}",c.getName(),m.getName());
        setPackageModule(c.getPackage(),m);
        return c;
    }
    
    static void setPackageModule(Package p, Module m) {
        if(Hacks.invokeDirect(p,"module")!=m) {
            Hacks.setFieldDirect(p,"module",m);
            //Account for forge using Package#getImplementationVersion to find service class versions
            Object versionInfo = Hacks.construct(PACKAGE_VERSION_INFO,null,null,null,null,VERSION,null,null);
            Hacks.setFieldDirect(p,"versionInfo",versionInfo);
        }
        else LOGGER.info("Package {} already present in module {}",p.getName(),m.getName());
    }
    
    static String ufsSeparator() {
        return Hacks.getFieldStaticDirect(UnionFileSystem.class,"SEP_STRING");
    }
    
    static UnionFileSystemProvider ufsp() {
        return Hacks.getFieldStaticDirect(JarContentsImpl.class,"UFSP");
    }
    
    static URI unify(String ... moduleNames) {
        return unify(Stream.of(moduleNames)
                      .map(moduleName -> NeoforgeModuleAccess.findResolvedModuleIn(moduleName,BOOT))
                      .filter(Objects::nonNull)
                      .map(module -> module.reference().location())
                      .map(TILBootLauncherNeoForge::modulePath)
                      .filter(Objects::nonNull)
                      .toArray(Path[]::new));
    }
    
    /**
     * Unify the input paths with UnionPath syntax
     */
    static URI unify(Path ... paths) {
        if(Objects.isNull(paths) || paths.length==0) return null;
        String unionSeparator = ufsSeparator();
        if(Objects.isNull(unionSeparator)) unionSeparator = "/";
        String root = commonParent(unionSeparator,paths);
        if(Objects.isNull(root)) {
            LOGGER.error("Failed to find root path for {}",(Object)paths);
            return null;
        }
        LOGGER.info("Unifying paths {} under key path {}",paths,root);
        UnionFileSystemProvider provider = ufsp();
        if(Objects.isNull(provider)) {
            LOGGER.error("Found null UnionFileSystemProvider?? Cannot unify paths {}",(Object)paths);
            return null;
        }
        try {
            Hacks.invokeDirect(provider,"newFileSystemInternal",root,null,paths);
            StringJoiner joiner = new StringJoiner("!");
            joiner.add(root);
            for(Path path : paths) {
                String pathStr = path.toAbsolutePath().toString().replace(separator,unionSeparator);
                if(!pathStr.startsWith(unionSeparator)) pathStr = unionSeparator+pathStr;
                if(pathStr.contains(root)) pathStr = pathStr.substring(root.length());
                LOGGER.info("Joining path {}",pathStr);
                joiner.add(pathStr);
            }
            String joinedPaths = joiner.toString();
            LOGGER.info("Joined paths are {}",joinedPaths);
            LOGGER.info("Full union URI should be {}:{}",provider.getScheme(),joinedPaths);
            return new URI(provider.getScheme(),null,joinedPaths,null);
        } catch(Throwable t) {
            LOGGER.error("Failed to construct URI for unified paths {}",paths,t);
        }
        return null;
    }
    
    static @Nullable Class<?> validateHacks(Module module, final String moduleName) {
        if(Objects.isNull(module)) {
            LOGGER.info("Module {} not found in BOOT layer",moduleName);
            return null;
        }
        try {
            Class<?> c = Class.forName(HACKS,true,bootLoader());
            return setClassModule(c,c,module);
        } catch(Throwable t) {
            LOGGER.error("Failed to find class {}",HACKS,t);
        }
        return null;
    }
    
    static Collection<String> validateServiceProviders(ModuleLayer layer, Module module,
            Collection<String> validated) {
        Object catalog = Hacks.getFieldDirect(layer,"servicesCatalog");
        if(Objects.isNull(catalog)) {
            LOGGER.warn("Unable to retrieve ServicesCatalog for layer of module {}",module);
            return Collections.emptySet();
        }
        Map<String,List<Object>> providerMap = Hacks.getFieldDirect(catalog,"map");
        if(Objects.isNull(providerMap) || providerMap.isEmpty()) {
            LOGGER.warn("No service providers found in layer of module {}",module);
            return Collections.emptySet();
        }
        return providerMap.values().stream().flatMap(Collection::stream)
                .map(provider -> validateServiceProvider(module,validated,provider))
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }
    
    static @Nullable String validateServiceProvider(Module module, Collection<String> validated, Object provider) {
        if(Objects.isNull(module) || Objects.isNull(validated) || Objects.isNull(provider) || validated.isEmpty())
            return null;
        Module providerModule = Hacks.getFieldDirect(provider,"module");
        if(Objects.isNull(providerModule) || module==providerModule || !isDevModule(providerModule)) return null;
        Hacks.setFieldDirect(provider,"module",module);
        return Hacks.getFieldDirect(provider,"providerName");
    }
    
    static Collection<String> validateServices(final Class<?> bootJarClass, final String moduleName, final URI uri) {
        IModuleLayerManager manager = INSTANCE.findLayerManager().orElse(null);
        if(Objects.isNull(manager)) {
            LOGGER.fatal("Failed to find IModuleLayerManager instance??");
            return Collections.emptySet();
        }
        ModuleLayer layer = manager.getLayer(BOOT).orElse(null);
        if(Objects.nonNull(layer)) return validateServices(bootJarClass,layer,moduleName,uri);
        else LOGGER.fatal("Failed to find BOOT layer?? (manager={})",manager);
        return Collections.emptySet();
    }
    
    static Collection<String> validateServices(final Class<?> bootJarClass, ModuleLayer layer,
            final String moduleName, final URI uri) {
        LOGGER.info("Validating service classes for module {}",moduleName);
        Module module = layer.findModule(moduleName).orElse(null);
        Class<?> hacksClass = validateHacks(module,moduleName);
        if(Objects.isNull(hacksClass) || Objects.isNull(module)) {
            LOGGER.error("Cannot validate services with null hacks class!");
            return Collections.emptySet();
        }
        LOGGER.info("Successfully validated hacks class");
        fixReferenceJar(bootJarClass,module,uri);
        printDebugStuff(layer,module);
        Set<String> validated = new HashSet<>();
        ClassLoader loader = module.getClassLoader();
        LOGGER.info("Validating service classes for ClassLoader {}",loader);
        Class<?> thisClass = setClassModule(hacksClass,TILBootLauncherNeoForge.class,module);
        if(Objects.nonNull(thisClass)) validated.add(thisClass.getName());
        for(String service : SERVICES) {
            if(Objects.nonNull(service)) {
                LOGGER.info("Attempting to validate service class {}",service);
                Class<?> serviceClass = Hacks.invoke(loader,"findClass",service);
                serviceClass = setClassModule(hacksClass,serviceClass,module);
                if(Objects.nonNull(serviceClass)) validated.add(service);
                else LOGGER.error("Failed to find service class {}",service);
            } else LOGGER.info("Skipping validation for null service class");
        }
        Collection<String> providers = validateServiceProviders(layer,module,validated);
        if(providers.isEmpty()) LOGGER.info("All service providers are valid");
        else LOGGER.info("Updated module for {} service providers {}",providers.size(),providers);
        return validated;
    }
    
    @IndirectCallers
    public TILBootLauncherNeoForge() {
        super(true);
        if(DEV) consolidateDevModules(DEV_MODULES);
    }
    
    @Override public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return none(Phase.class);
    }
}