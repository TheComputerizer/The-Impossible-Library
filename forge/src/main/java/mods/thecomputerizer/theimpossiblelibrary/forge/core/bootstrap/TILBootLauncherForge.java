package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncher;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ForgeModuleAccess;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static java.io.File.separator;
import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_NAME;

/**
 * In a dev environment, we can add ourselves to the legacy classpath for BOOT layer service discovery
 * We can make use of this by implementing ILaunchPluginService
 */
public class TILBootLauncherForge extends TILLauncher implements ILaunchPluginService {
    
    static final String BASE_PKG = "mods.thecomputerizer.theimpossiblelibrary";
    static final String CORE_PKG = BASE_PKG+".forge.core";
    static final String HACKS = BASE_PKG+".api.core.Hacks";
    static final String JAR = "cpw.mods.jarhandling.impl.Jar";
    static final String LANGUAGE_LOADER = CORE_PKG+".TILLanguageProvider";
    static final String LAYER = "cpw.mods.modlauncher.api.IModuleLayerManager$Layer";
    static final String LOCATOR = CORE_PKG+".MultiVersionModLocator";
    static final String PACKAGE_VERSION_INFO = Package.class.getName()+"$VersionInfo";
    static final String SERVICE_LAUNCHER = CORE_PKG+".bootstrap.TILServiceLauncherForge";
    static final String UFS = "cpw.mods.niofs.union.UnionFileSystem";
    static final String UNION_PATH = "cpw.mods.niofs.union.UnionPath";
    /**
     * We can't use List#of here since it assumes the input elements are nonnull
     */
    static final Collection<String> SERVICES = Arrays.asList(SERVICE_LAUNCHER,LOCATOR,LANGUAGE_LOADER);
    static final String[] DEV_MODULES = new String[]{"main","tilforge"};
    static final Logger LOGGER;
    
    static {
        out.println("[BOOT] Class init: "+TILBootLauncherForge.class.getName());
        LOGGER = TILRef.createLogger(LOADER_NAME+" (Boot)");
    }
    
    static ClassLoader bootLoader() {
        return Launcher.class.getClassLoader();
    }
    
    /**
     * Attempts to find the greatest common parent path for all input paths
     */
    static String commonParent(String unionSeparator, Path... paths) {
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
        LOGGER.info("Combining dev modules: layer = BOOT | combined name = {} | base = {} | others = {} | " +
                    "location = {}",combinedName,names[0],others,unified);
        Class<?> bootJarClass = TILBootJar.class;
        ForgeModuleAccess.combineModules("BOOT",unified,combinedName,names[0],others);
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
    
    static void fixReferenceJar(Class<?> bootJarClass, Object module, URI unified) {
        LOGGER.info("Fixing jar stuff");
        try {
            ClassLoader loader = Hacks.invoke(module,"getClassLoader");
            Object configuration = Hacks.getFieldDirect(loader,"configuration");
            if(Objects.isNull(configuration)) {
                LOGGER.error("Failed to find configuration for module ClassLoader?");
                return;
            }
            String moduleName = Hacks.invoke(module,"getName");
            Optional<?> resolved = Hacks.invoke(configuration,"findModule",moduleName);
            if(Objects.isNull(resolved) || !resolved.isPresent()) {
                LOGGER.error("Failed to find ResolvedModule for Module?");
                return;
            }
            Object reference = Hacks.invoke(resolved.get(),"reference");
            Hacks.invokeStatic(bootJarClass,"updateReference",LOGGER,reference,unified);
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
    
    static boolean isDevModule(Object module) {
        if(Objects.isNull(module)) return false;
        String moduleName = Hacks.invoke(module,"getName");
        for(String devModuleName : DEV_MODULES)
            if(Objects.nonNull(moduleName) && moduleName.equals(devModuleName)) return true;
        return false;
    }
    
    static Path modulePath(URI location) {
        if(Objects.isNull(location)) return null;
        Path path = Paths.get(location);
        Class<?> unionPathClass = Hacks.findClass(UNION_PATH);
        if(Objects.nonNull(unionPathClass) && unionPathClass.isInstance(path)) {
            Path primary = Hacks.invoke(Hacks.invoke(path,"getFileSystem"),"getPrimaryPath");
            if(Objects.nonNull(primary)) path = primary;
        }
        return path.toAbsolutePath();
    }
    
    static Class<?> setClassModule(Class<?> hacksClass, Class<?> c, Object m) {
        String moduleName = Hacks.invoke(m,"getName");
        if(Objects.isNull(hacksClass)) {
            LOGGER.error("Cannot set module for {} to {} with null hacks class!",c,moduleName);
            return c;
        }
        if(Objects.isNull(c)) {
            LOGGER.error("Cannot set module of null class to {}!",moduleName);
            return null;
        }
        if(Hacks.invoke(c,"getModule")!=m) {
            Method setField = findSetFieldDirect(hacksClass,Object.class,String.class,Object.class);
            invokeMethod(setField,c,"module",m);
        } else LOGGER.info("Class {} already present in module {}",c.getName(),moduleName);
        setPackageModule(c.getPackage(),m,moduleName);
        return c;
    }
    
    static void setPackageModule(Package p, Object m, String moduleName) {
        if(Hacks.invokeDirect(p,"module")!=m) {
            Hacks.setFieldDirect(p,"module",m);
            //Account for forge using Package#getImplementationVersion to find service class versions
            Object versionInfo = Hacks.construct(PACKAGE_VERSION_INFO,null,null,null,null,VERSION,null,null);
            Hacks.setFieldDirect(p,"versionInfo",versionInfo);
        }
        else LOGGER.info("Package {} already present in module {}",p.getName(),moduleName);
    }
    
    static String ufsSeparator() {
        return Hacks.getFieldStaticDirect(UFS,"SEP_STRING");
    }
    
    static Object ufsp() {
        return Hacks.getFieldStaticDirect(JAR, "UFSP");
    }
    
    static URI unify(String ... moduleNames) {
        return unify(Stream.of(moduleNames)
                             .map(moduleName -> ForgeModuleAccess.findResolvedModuleIn(moduleName,"BOOT"))
                             .filter(Objects::nonNull)
                             .map(module -> module.reference().location())
                             .map(TILBootLauncherForge::modulePath)
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
        Object provider = ufsp();
        if(Objects.isNull(provider)) {
            LOGGER.error("Found null UnionFileSystemProvider?? Cannot unify paths {}",(Object)paths);
            return null;
        }
        try {
            String providerScheme = Hacks.invoke(provider,"getScheme");
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
            LOGGER.info("Full union URI should be {}:{}",providerScheme,joinedPaths);
            return new URI(providerScheme,null,joinedPaths,null);
        } catch(Throwable t) {
            LOGGER.error("Failed to construct URI for unified paths {}",paths,t);
        }
        return null;
    }
    
    static @Nullable Class<?> validateHacks(Object module, final String moduleName) {
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
    
    static Collection<String> validateServiceProviders(Object layer, Object module,
            Collection<String> validated) {
        Object catalog = Hacks.getFieldDirect(layer,"servicesCatalog");
        if(Objects.isNull(catalog)) {
            LOGGER.warn("Unable to retrieve ServicesCatalog for layer of module {}",module);
            return Collections.emptySet();
        }
        Map<String,List<Object>> providerMap = Hacks.getFieldDirect(catalog, "map");
        if(Objects.isNull(providerMap) || providerMap.isEmpty()) {
            LOGGER.warn("No service providers found in layer of module {}",module);
            return Collections.emptySet();
        }
        return providerMap.values().stream().flatMap(Collection::stream)
                .map(provider -> validateServiceProvider(module,validated,provider))
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }
    
    static @Nullable String validateServiceProvider(Object module, Collection<String> validated, Object provider) {
        if(Objects.isNull(module) || Objects.isNull(validated) || Objects.isNull(provider) || validated.isEmpty())
            return null;
        Object providerModule = Hacks.getFieldDirect(provider,"module");
        if(Objects.isNull(providerModule) || module==providerModule || !isDevModule(providerModule)) return null;
        Hacks.setFieldDirect(provider,"module",module);
        return Hacks.getFieldDirect(provider,"providerName");
    }
    
    static Collection<String> validateServices(final Class<?> bootJarClass, final String moduleName, final URI uri) {
        Optional<?> manager = Hacks.invoke(INSTANCE,"findLayerManager");
        if(Objects.isNull(manager) || !manager.isPresent()) {
            LOGGER.fatal("Failed to find IModuleLayerManager instance??");
            return Collections.emptySet();
        }
        Object[] getLayerArgs = new Object[]{Hacks.getFieldStatic(LAYER,"BOOT")};
        Optional<?> layer = Hacks.invoke(manager.get(),"getLayer",getLayerArgs);
        if(Objects.nonNull(layer) && layer.isPresent())
            return validateServices(bootJarClass,layer.get(),moduleName,uri);
        else LOGGER.fatal("Failed to find BOOT layer?? (manager={})",manager);
        return Collections.emptySet();
    }
    
    static Collection<String> validateServices(final Class<?> bootJarClass, Object layer,
            final String moduleName, final URI uri) {
        LOGGER.info("Validating service classes for module {}",moduleName);
        Optional<?> optionalModule = Hacks.invoke(layer,"findModule",moduleName);
        Object module = Objects.nonNull(optionalModule) ? optionalModule.orElse(null) : null;
        Class<?> hacksClass = validateHacks(module,moduleName);
        if(Objects.isNull(hacksClass) || Objects.isNull(module)) {
            LOGGER.error("Cannot validate services with null hacks class!");
            return Collections.emptySet();
        }
        LOGGER.info("Successfully validated hacks class");
        fixReferenceJar(bootJarClass,module,uri);
        Set<String> validated = new HashSet<>();
        ClassLoader loader = Hacks.invoke(module,"getClassLoader");
        LOGGER.info("Validating service classes for ClassLoader {}",loader);
        Class<?> thisClass = setClassModule(hacksClass, TILBootLauncherForge.class, module);
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
    public TILBootLauncherForge() {
        super(true);
        if(DEV && MODULES) consolidateDevModules(DEV_MODULES);
    }
    
    @Override public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return none(Phase.class);
    }
}