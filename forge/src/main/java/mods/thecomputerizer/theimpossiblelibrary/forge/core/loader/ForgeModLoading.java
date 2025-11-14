package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import net.minecraftforge.fml.loading.ModDirTransformerDiscoverer;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.fml.loading.moddiscovery.ModFileParser;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModFile.Type;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minecraftforge.forgespi.locating.ModFileFactory.ModFileInfoParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static java.util.jar.Attributes.Name.IMPLEMENTATION_TITLE;
import static java.util.jar.Attributes.Name.IMPLEMENTATION_VERSION;
import static java.util.jar.JarFile.MANIFEST_NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_ID;
import static net.minecraftforge.forgespi.locating.IModFile.Type.LANGPROVIDER;
import static net.minecraftforge.forgespi.locating.IModFile.Type.LIBRARY;
import static net.minecraftforge.forgespi.locating.IModFile.Type.MOD;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;

/**
 * Helper methods for common functionalities between all 1.16.5+ versions of Forge mod loading
 */
@SuppressWarnings("LoggingSimilarMessage")
public class ForgeModLoading {
    
    static final Logger LOGGER = LogManager.getLogger("Forge Mod Loading");
    
    static final BiConsumer<TILBetterModScan,Object> AFTER_WRITING_MODS = (scan,language) -> {
        LOGGER.debug("Injecting scan data into the language loader");
        Consumer<ModFileScanData> visitor = Hacks.invokeDirect(language,"getFileVisitor");
        if(Objects.nonNull(visitor)) visitor.accept(scan);
    };
    
    //maps
    static final Map<MultiVersionModCandidate,ModFile> CANDIDATE_MAP = new HashMap<>();
    static final Map<Object,Map<MultiVersionModInfo,MultiVersionModData>> FILE_INFO_MAP = new HashMap<>();
    static final Map<String,Manifest> MANIFEST_MAP = new HashMap<>(); //Module name -> Manifest
    static final Map<String,Object> PATH_OR_JAR_MAP = new HashMap<>(); //Module name -> Path (1.16.5) || SecureJar (1.18.2+)
    
    //collections
    static final Collection<Object> IDENTIFIED_FILES = new HashSet<>();
    
    //class names
    static final String COREMOD_ENGINE = "net.minecraftforge.coremod.CoreModEngine";
    static final String JAR_METADATA = "cpw.mods.jarhandling.JarMetadata";
    static final String MOD_CLASS_VISITOR = "net.minecraftforge.fml.loading.moddiscovery.ModClassVisitor";
    static final String MOD_FILE_OR_EXCEPTION = "net.minecraftforge.forgespi.locating.IModLocator$ModFileOrException";
    static final String MOD_FILE_PARSER = "net.minecraftforge.fml.loading.moddiscovery.ModFileParser";
    static final String MOD_PROVIDER = "net.minecraftforge.forgespi.locating.IModProvider";
    static final String NAMED_PATH = "cpw.mods.modlauncher.api.NamedPath";
    static final String NIGHT_CONFIG_WRAPPER = "net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper";
    static final String SCANNER = "net.minecraftforge.fml.loading.moddiscovery.Scanner";
    static final String SECURE_JAR = "cpw.mods.jarhandling.SecureJar";
    static final String SELF_ENTRYPOINT = "mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint";
    static final String SIMPLE_JAR_METADATA = "cpw.mods.jarhandling.impl.SimpleJarMetadata";
    
    //modifiable
    static Name automaticModuleName;
    static Function<ModFile,IModFileInfo> langProviderFileInfo;
    static BiFunction<URL,String,Path> urlToPath;
    static BiFunction<Path,Object,Manifest> pathToManifest;
    static String[] coreModExtensions;
    static BiFunction<ModFile,Collection<?>,ModFileInfo> modFileInfoCreator;
    static Class<?> dynamicModFileClass;
    static boolean fixedCoreMods;
    @Getter static boolean pathBased;
    @Getter static boolean locatorBased;
    @Getter static boolean secureLoadingFormat;
    @Getter static String workingVersion;
    
    //guard against init methods being called multiple times
    static boolean scanned;
    static boolean initalized;
    
    /**
     * Add the paths found by the library to the found list in ModDirTransformerDiscoverer in order to convince Forge
     * that the ModsFolderLocator doesn't need to try loading them.
     * Only applicable for 1.18.2+ & required for 1.20.4+ to load at all
     */
    private static void addFoundModPath(Object file, String type) {
        if(pathBased || !"MOD".equals(type)) return;
        Object foundPath = toNamedPath(Hacks.invoke(file,"getFilePath"));
        if(Objects.isNull(foundPath)) return;
        Hacks.addToCollectionField("found",foundPath,
                f -> Hacks.getFieldList(ModDirTransformerDiscoverer.class,"getFieldStatic",f));
    }
    
    private static <F> void addScannedMod(Object file, List<F> mods, String type) {
        if(locatorBased) {
            Hacks.setFieldDirect(file,"modFileType",getModFileType(type));
            mods.add(GenericUtils.cast(file));
        } else mods.add(Hacks.construct(MOD_FILE_OR_EXCEPTION,file,null));
        addFoundModPath(file,type);
    }
    
    static void checkPath(MultiVersionLoaderAPI loader, Path path, Predicate<Path> filter) {
        String loaderName = loader.getName();
        LOGGER.debug("[{}]: Checking if {} is the loader",loaderName,path);
        if(Objects.isNull(MultiVersionModCandidate.getLoaderFile()) && TILDev.isLoaderPath(path)) {
            LOGGER.debug("[{}]: File is the loader",loaderName);
            MultiVersionModCandidate.setLoaderPath(path);
        }
        if(filter.test(path)) {
            LOGGER.info("[{}]: Found mod candidate at {}",loaderName,path);
            loader.addPotentialModPath(path);
        }
    }
    
    static void checkURL(MultiVersionLoaderAPI loader, URL url, Predicate<Path> filter) {
        LOGGER.debug("[{}]: Checking URL {} for MANIFEST {}",loader.getName(),url,MANIFEST_NAME);
        checkPath(loader,urlToPath.apply(url,MANIFEST_NAME),filter);
    }
    
    public static Supplier<Manifest> createLoaderManfiest(Path sourcePath) {
        if(!DEV || Objects.isNull(sourcePath) || !TILDev.isLoaderPath(sourcePath)) return null;
        return () -> {
            LOGGER.info("Creating loader manifest for {}",sourcePath);
            return getDefaultManifest(MODID).get();
        };
    }
    
    @SuppressWarnings("SameParameterValue")
    static ModFile createModFile(ModFile reference, ModFileInfoParser parser, String type,
            final String moduleName) {
        return createModFile(reference.getFilePath(),modFileLocator(reference),parser,type,moduleName);
    }
    
    static ModFile createModFile(Path path, Object locator, ModFileInfoParser parser, String type,
            final String moduleName) {
        if(Objects.isNull(dynamicModFileClass)) {
            LOGGER.error("Cannot create ModFile with null dynamicModFileClass! Did it fail to initialize?");
            return null;
        }
        LOGGER.debug("Creating mod file of type {} with module name {} at path {}",type,moduleName,path);
        String pathType = pathBased ? "Path" : "SecureJar";
        Object pathOrJar = null;
        boolean updatePathMap = false;
        if(Objects.nonNull(moduleName)) {
            if(PATH_OR_JAR_MAP.containsKey(moduleName)) {
                LOGGER.debug("Found existing {} for module {}",pathType,moduleName);
                pathOrJar = PATH_OR_JAR_MAP.get(moduleName);
                
            } else updatePathMap = true;
        }
        if(Objects.isNull(pathOrJar)) pathOrJar = getDefaultJar(moduleName,path);
        ModFile file = Hacks.constructAndCast(dynamicModFileClass,pathOrJar,locator,parser,type);
        //Construct the file first to ensure there aren't any errors before the PATH_OR_JAR_MAP is updated
        if(updatePathMap) {
            LOGGER.debug("Adding {} instance for module {} to the cache",pathType,moduleName);
            PATH_OR_JAR_MAP.put(moduleName,pathOrJar);
        }
        return file;
    }
    
    static @Nullable Class<?> dynamicModFileCreator() {
        Hacks.checkBurningWaveInit();
        String pkgName = ForgeModLoading.class.getPackage().getName();
        String className = pkgName+".TILForgeModFile";
        byte[] byteCode = generateModFileExtension(className);
        if(Objects.isNull(byteCode)) {
            LOGGER.error("Failed to define bytecode for {}",className);
            return null;
        }
        ASMHelper.writeDebugByteCode(className,byteCode);
        LOGGER.info("Successfully generated bytecode for {}",className);
        try {
            Class<?> defined = ClassHelper.defineAndResolveClass(ModFile.class.getClassLoader(),className,byteCode);
            LOGGER.info("Successfully generated ModFile extension {}",defined);
            if(!ForgeCoreLoader.isJava8())
                Hacks.setFieldDirect(defined,"module",Hacks.invoke(ForgeModLoading.class,"getModule"));
            return defined;
        } catch(Throwable t) {
            LOGGER.error("Failed to generate ModFile extension {}",className,t);
        }
        return null;
    }
    
    static Path findFilePath(Object modFile, String ... paths) {
        Object target = pathBased ? modFileLocator(modFile) : modFile;
        String method = pathBased ? "findPath" : "findResource";
        Object[] args = pathBased ? new Object[]{modFile,paths} : paths;
        return Hacks.invoke(target,method,args);
    }
    
    static void findFiles(MultiVersionLoaderAPI loader, Predicate<Path> filter, File... files) {
        LOGGER.info("[{}]: Checking {} mod files for potential loading",loader.getName(),files.length);
        for(File mod : files) {
            LOGGER.debug("[{}]: Potentially loading mod file at path {}",loader.getName(),mod.toPath());
            checkPath(loader,mod.toPath(),filter);
        }
    }
    
    public static Optional<Manifest> findManifest(Path path) {
        return findManifest(path,createLoaderManfiest(path));
    }
    
    public static Optional<Manifest> findManifest(Path path, @Nullable Supplier<Manifest> fallback) {
        Optional<Manifest> optionalManifest = Optional.empty();
        try {
            File file = path.toFile();
            if(!file.exists()) LOGGER.warn("Tried to find manifest of nonexistant path {}",path);
            else if(file.isDirectory()) {
                File manifest = new File(file,MANIFEST_NAME);
                if(manifest.exists()) optionalManifest = Optional.ofNullable(parseManifest(manifest));
                else LOGGER.warn("Manifest not found at {}",manifest);
            } else {
                //The nested try is just for autoclosing the JarFile
                try(JarFile jar = new JarFile(path.toFile())) {
                    Manifest manifest = jar.getManifest();
                    if(Objects.nonNull(manifest)) optionalManifest = Optional.of(manifest);
                }
            }
        } catch(Throwable t) {
            LOGGER.error("Failed to find manifest for {}",path,t);
        }
        return optionalManifest.isPresent() || Objects.isNull(fallback) ?
                optionalManifest : Optional.ofNullable(fallback.get());
    }
    
    public static void findPaths(ClassLoader classLoader, MultiVersionLoaderAPI loader, Object locator) {
        Predicate<Path> filter = path -> {
            if(Objects.isNull(path)) return false;
            Manifest manifest = pathToManifest.apply(path,locator);
            if(Objects.isNull(manifest)) return false;
            return MultiVersionModFinder.hasMods(manifest.getMainAttributes());
        };
        findURLs(loader,classLoader,filter);
        findFiles(loader,filter,FileHelper.list(loader.findModRoot(),File::isFile));
    }
    
    static void findURLs(MultiVersionLoaderAPI loader, ClassLoader classLoader, Predicate<Path> filter) {
        try {
            final Enumeration<URL> manifests = ClassLoader.getSystemClassLoader().getResources(MANIFEST_NAME);
            while(manifests.hasMoreElements()) checkURL(loader,manifests.nextElement(),filter);
        } catch(IOException ex) {
            LOGGER.error("[{}]: Failed to calculate URLs for paths with {} using {}",loader.getName(),
                         MANIFEST_NAME,classLoader,ex);
        }
    }
    
    /**
     * No easy way for generic core mods? Fine, I'll do it myself
     */
    public static void fixCoreModPackages() {
        Set<String> allowed = Hacks.getFieldStaticDirect(COREMOD_ENGINE,"ALLOWED_PACKAGES");
        if(Objects.isNull(allowed)) {
            LOGGER.error("Failed to get ALLOWED_PACKAGES for {}! Cannot expand package whitelist",COREMOD_ENGINE);
            return;
        }
        allowed = new HashSet<>(allowed);
        allowed.add(BASE_PACKAGE+".api.core");
        allowed.add(BASE_PACKAGE+".forge.core");
        for(String extension : coreModExtensions) allowed.add(BASE_PACKAGE+".forge."+extension+".core");
        LOGGER.debug("Expanded coremod package whitelist to {}", allowed);
        Hacks.setFieldStaticDirect(COREMOD_ENGINE,"ALLOWED_PACKAGES",Collections.unmodifiableSet(allowed));
    }
    
    private static byte[] generateModFileExtension(String className) {
        Class<?> pathOrJarClass = pathBased ? Path.class : Hacks.findClass(SECURE_JAR);
        Class<?> locatorClass = locatorBased ? IModLocator.class : Hacks.findClass(MOD_PROVIDER);
        int javaVer = ForgeCoreLoader.isJava8() ? JAVA8 : (ForgeCoreLoader.isJava21() ? JAVA21 : JAVA17);
        ClassWriter writer = ASMHelper.getWriter(javaVer,ASMRef.PUBLIC,TypeHelper.fromBinary(className),
                                                 TypeHelper.get(ModFile.class));
        if(Objects.isNull(pathOrJarClass) || Objects.isNull(locatorClass)) {
            LOGGER.error("Cannot add dynamic ModFile creator! Found null parameter class {} or {}",
                         pathOrJarClass,locatorClass);
            return null;
        }
        String constructorDesc = TypeHelper.voidMethodDesc(
                pathOrJarClass,locatorClass,ModFileInfoParser.class,String.class);
        String superDesc = pathBased ? TypeHelper.voidMethodDesc(pathOrJarClass,locatorClass,ModFileInfoParser.class) :
                constructorDesc;
        String modFile = TypeHelper.get(ModFile.class).getInternalName();
        String modLoading = TypeHelper.get(ForgeModLoading.class).getInternalName();
        String writeModsDesc = TypeHelper.methodDesc(ModFileScanData.class,ModFile.class);
        String identifyModsDesc = TypeHelper.methodDesc(BOOLEAN_TYPE,OBJECT_TYPE);
        
        MethodVisitor constructor = writer.visitMethod(PUBLIC,"<init>",constructorDesc,null,null);
        constructor.visitCode();
        for(int i=0;i<(pathBased ? 4 : 5);i++) constructor.visitVarInsn(ALOAD,i);
        constructor.visitMethodInsn(INVOKESPECIAL,modFile,"<init>",superDesc,false);
        constructor.visitInsn(RETURN);
        ASMHelper.finishMethod(constructor);
        
        MethodVisitor compileContent = writer.visitMethod(PUBLIC,"compileContent",
                                                          TypeHelper.methodDesc(ModFileScanData.class),null,null);
        compileContent.visitCode();
        compileContent.visitVarInsn(ALOAD,0);
        compileContent.visitMethodInsn(INVOKESTATIC,modLoading,"writeMods",writeModsDesc,false);
        compileContent.visitInsn(RETURN_OBJ);
        ASMHelper.finishMethod(compileContent);
        
        MethodVisitor identifyMods = writer.visitMethod(PUBLIC,"identifyMods","()Z",null,null);
        identifyMods.visitCode();
        identifyMods.visitVarInsn(ALOAD,0);
        identifyMods.visitMethodInsn(INVOKESTATIC,modLoading,"identifyMods",identifyModsDesc,false);
        identifyMods.visitInsn(RETURN_INT_OR_BOOL);
        ASMHelper.finishMethod(identifyMods);
        
        if(!pathBased) { //Workaround for Sinytra Connector directly invoking ModFileParser#getCoreMods
            String findResourceDesc = TypeHelper.methodDesc(Path.class,String[].class);
            String queryCoreModsDesc = TypeHelper.voidMethodDesc(String[].class);
            
            MethodVisitor findResource = writer.visitMethod(PUBLIC,"findResource",findResourceDesc,null,null);
            findResource.visitCode();
            for(int i=0;i<2;i++) findResource.visitVarInsn(ALOAD,i);
            findResource.visitMethodInsn(INVOKESTATIC,modLoading,"queryCoreMods",queryCoreModsDesc,false);
            for(int i=0;i<2;i++) findResource.visitVarInsn(ALOAD,i);
            findResource.visitMethodInsn(INVOKESPECIAL,modFile,"findResource",findResourceDesc,false);
            findResource.visitInsn(RETURN_OBJ);
            ASMHelper.finishMethod(findResource);
        }
        
        return writer.toByteArray();
    }
    
    private static Supplier<Manifest> getDefaultManifest(final String automaticModuleName) {
        return () -> {
            if(Objects.isNull(automaticModuleName)) return new Manifest();
            if(MANIFEST_MAP.containsKey(automaticModuleName)) return MANIFEST_MAP.get(automaticModuleName);
            Manifest manifest = new Manifest();
            Attributes attributes = manifest.getMainAttributes();
            attributes.put(IMPLEMENTATION_TITLE,NAME);
            attributes.put(IMPLEMENTATION_VERSION,VERSION);
            LOGGER.debug("Setting Automatic-Module-Name for Manifest to {}",automaticModuleName);
            setAutomaticModuleName(manifest.getMainAttributes(),automaticModuleName);
            MANIFEST_MAP.put(automaticModuleName,manifest);
            return manifest;
        };
    }
    
    private static Object getDefaultJar(String moduleName, Path path) {
        if(pathBased) return path;
        Supplier<Manifest> defaultManifest = getDefaultManifest(moduleName);
        if(LOADER_ID.equals(moduleName) || MODID.equals(moduleName))
            return TILLoaderJar.get(defaultManifest,moduleName,path,!locatorBased);
        Function<Object,Object> jarMetadataSupplier = getJarMetadataSupplier(path);
        return Hacks.invokeStatic(SECURE_JAR,"from",defaultManifest,jarMetadataSupplier,path);
    }
    
    /**
     * Also initializes the info map
     */
    public static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        if(file instanceof ModFile) return modFileInfoCreator.apply((ModFile)file,infos);
        LOGGER.error("Cannot get IModFileInfo for IModFile that is not an instance of ModFile! {}",file);
        return null;
    }
    
    @IndirectCallers
    private static String getFirstModId(Collection<MultiVersionModInfo> infos) {
        return getFirstModId(null,infos);
    }
    
    private static String getFirstModId(MultiVersionModCandidate candidate,
            Collection<MultiVersionModInfo> infos) {
        for(MultiVersionModInfo info : infos) {
            String modid = info.getModID();
            if(Objects.nonNull(modid) && !modid.isEmpty()) return modid;
        }
        if(Objects.nonNull(candidate)) {
            LOGGER.debug("Returning file name for MultiVersionModCandidate as first modid");
            return candidate.getFile().getName();
        }
        LOGGER.debug("First modid not found! Returning null");
        return null;
    }
    
    private static Function<Object,Object> getJarMetadataSupplier(Path ... paths) {
        return secureJar -> {
            Set<String> packages = new HashSet<>();
            List<?> providers = new ArrayList<>();
            Object manifestHolder = locatorBased ? secureJar : Hacks.invoke(secureJar,"moduleDataProvider");
            Manifest manifest = Hacks.invoke(manifestHolder,"getManifest");
            String name = Objects.nonNull(manifest) ? manifest.getMainAttributes().getValue(automaticModuleName) : null;
            if(Objects.isNull(name)) {
                LOGGER.info("Falling back to default jar metatdata since {} attribute was not found for: {}",
                            automaticModuleName,paths);
                return Hacks.invokeStatic(JAR_METADATA,"from",secureJar,paths);
            }
            Object candidate = Hacks.invokeStatic(JAR_METADATA,"fromFileName",paths[0],packages,providers);
            if(Objects.isNull(candidate)) {
                LOGGER.error("Failed to get jar metadata candidate from paths {}",(Object)paths);
                return Hacks.invokeStatic(JAR_METADATA,"from",secureJar,paths);
            }
            LOGGER.debug("Returning customized JarMetadata for module {}",name);
            return Hacks.construct(SIMPLE_JAR_METADATA,name,Hacks.invoke(candidate,"version"),packages,providers);
        };
    }
    
    public static Type getModFileType(String name) {
        if(Objects.isNull(name) || name.isEmpty()) {
            LOGGER.error("Null or empty mod file type! LIBRARY will be assumed");
            return LIBRARY;
        }
        switch(name) {
            case "GAMELIBRARY": {
                LOGGER.warn("The GAMELIBRARY mod file type is unable to be supported in all Forge versions! "+
                               "Please use LANGPROVIDER, LIBRARY, or MOD");
                LOGGER.warn("MOD will be assumed for now, but things could break soon");
                return MOD;
            }
            case "LANGPROVIDER": return LANGPROVIDER;
            case "LIBRARY": return LIBRARY;
            case "MOD": return MOD;
            default: {
                LOGGER.error("Unknown mod file type {}! LIBRARY will be assumed",name);
                return LIBRARY;
            }
        }
    }
    
    private static boolean hasCoreModPath(String ... paths) {
        for(String path : paths)
            if(path.contains("coremods.json")) return true;
        return false;
    }
    
    /**
     * Called via the dynamically generated ModFile extension class
     */
    @IndirectCallers
    public static boolean identifyMods(Object file) {
        ModFile modFile = (ModFile)file;
        String fileName = modFile.getFileName();
        Path absolutePath = modFile.getFilePath().toAbsolutePath();
        LOGGER.debug("Finalizing mod identification for {} (path={})",fileName,absolutePath);
        if(!CANDIDATE_MAP.containsValue(modFile)) {
            LOGGER.warn("There are no mods to identify for {}",fileName);
            return false;
        }
        if(IDENTIFIED_FILES.contains(file))
            LOGGER.debug("Skipping file that was already identified {}",fileName);
        else {
            if(pathBased) parseAndSetModFileInfo(file);
            LOGGER.debug("Querying coremods for mod file {}",fileName);
            queryCoreMods(file);
            String[] atPaths = new String[]{"META-INF","accesstransformer.cfg"};
            Hacks.setFieldDirect(file,"accessTransformer",findFilePath(file,atPaths));
            IDENTIFIED_FILES.add(file);
        }
        LOGGER.debug("Finalized mod identification for {} (path={})",fileName,absolutePath);
        int candidateCount = CANDIDATE_MAP.size();
        int identifiedCount = IDENTIFIED_FILES.size();
        if(identifiedCount>=candidateCount)
            LOGGER.debug("Successfully identified {}/{} mod files",identifiedCount,candidateCount);
        return true;
    }
    
    private static Config initConfigDependencies() {
        Config dependency = Config.inMemory();
        dependency.set("mandatory",true);
        dependency.set("modId",MODID);
        dependency.set("ordering","AFTER");
        dependency.set("side","BOTH");
        dependency.set("versionRange","[0.4.0,)");
        return dependency;
    }
    
    private static List<Config> initConfigMods(Config config, Collection<MultiVersionModInfo> infos) {
        if(Objects.isNull(infos)) return Collections.emptyList();
        List<Config> mods = new ArrayList<>();
        boolean setLicense = false;
        for(MultiVersionModInfo info : infos) {
            if(!setLicense) {
                config.set("license",info.getLicense());
                setLicense = true;
            }
            Config mod = Config.inMemory();
            mod.set("description",info.getDescription());
            mod.set("displayName",info.getName());
            mod.set("license",info.getLicense());
            mod.set("logoFile","logo.png");
            mod.set("modId",info.getModID());
            mod.set("version",info.getVersion());
            mods.add(mod);
        }
        if(!setLicense) config.set("license","LGPL V3");
        return mods;
    }
    
    public static IConfigurable initFileConfig(Collection<?> infos) {
        Config config = Config.inMemory();
        config.set("modLoader","multiversionprovider");
        config.set("loaderVersion","[0.4.0,)");
        List<Config> mods = initConfigMods(config,GenericUtils.cast(infos));
        config.add("mods",mods);
        if(!mods.isEmpty() && !MODID.equals(mods.get(0).get("modId")))
            config.add("dependencies",new ArrayList<>(Collections.singletonList(initConfigDependencies())));
        return wrapConfig(config);
    }
    
    private static Map<MultiVersionModInfo,MultiVersionModData> initInfoMap(
            Collection<MultiVersionModInfo> infos) {
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = new HashMap<>();
        for(MultiVersionModInfo info : infos) infoMap.put(info,null);
        LOGGER.info("Created <info,data> map with {} entries for multiversion mod file ({}) using {}",
                    infos.size(),workingVersion,infos);
        return infoMap;
    }
    
    public static boolean initModLoading(ClassLoader loader, Object locator, Map<String,?> arguments) {
        if(initalized) {
            LOGGER.debug("Skipping duplicate Forge mod loading initialization call");
            return false;
        }
        LOGGER.info("Initializing Forge mod loading with args {}",arguments);
        Object core = CoreAPI.getInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize Forge mod loading! Cannot find CoreAPI on "+loader);
        Hacks.checkBurningWaveInit();
        findPaths(loader,Hacks.invoke(core,"getLoader"),locator);
        loadMods(loader,locator,core);
        initalized = true;
        return true;
    }
    
    private static @Nullable TILBetterModScan initModScanner(ModFile file) {
        LOGGER.info("Starting multiversion mod scan");
        TILBetterModScan scan = new TILBetterModScan();
        scan.addModFileInfo(file.getModFileInfo());
        file.scanFile(p -> scanReflectively(Hacks.construct(SCANNER,file),p,scan));
        LOGGER.debug("Injecting @Mod annotations from multiversion mod info");
        if(Objects.nonNull(scan.getAnnotations())) return scan;
        LOGGER.error("@Mod scan annotation set for multiversion mod is null???");
        return null;
    }
    
    private static IModFileInfo langFileInfo(IModFile file) {
        if(!(file instanceof ModFile)) {
            LOGGER.error("IModFile instance must extend ModFile to be supported for IModFileInfo construction!");
            return null;
        }
        return langProviderFileInfo.apply((ModFile)file);
    }
    
    private static Config langProviderConfig() {
        Config config = Config.inMemory();
        config.set("modLoader","minecraft");
        config.set("loaderVersion","1");
        Config mod = Config.inMemory();
        mod.set("modId","multiversionprovider");
        mod.set("version",VERSION);
        mod.set("displayName","Multiversion Language Provider");
        mod.set("logoFile","logo.png");
        mod.set("authors","The_Computerizer");
        mod.set("description","Multiversion language loader for "+NAME);
        config.set("mods",new ArrayList<>(Collections.singletonList(mod)));
        return config;
    }
    
    public static ModFile langProviderModFile(ModFile reference, String moduleName) {
        return createModFile(reference,ForgeModLoading::langFileInfo,"LANGPROVIDER",moduleName);
    }
    
    private static void loadCandidateInfos(Object locator, Map<?,?> infoMap) {
        if(Objects.isNull(infoMap)) {
            LOGGER.error("Tried to load mod candidate info with null info map! locator = {}",locator);
            return;
        }
        for(Entry<?,?> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Collection<MultiVersionModInfo> infos = GenericUtils.cast(entry.getValue());
            if(Objects.isNull(infos)) {
                LOGGER.error("Null MultiVersionModInfo collection for candidate {}",candidate);
                continue;
            }
            ModFileInfoParser parser = file -> getFileInfo(file,infos);
            String firstModId = getFirstModId(candidate,infos);
            ModFile file = createModFile(candidate.getFile().toPath(),locator,parser,"MOD",firstModId);
            CANDIDATE_MAP.put(candidate,file);
            FILE_INFO_MAP.put(file,initInfoMap(infos));
        }
    }
    
    private static void loadMods(ClassLoader loader, Object locator, Object core) {
        Hacks.invoke(core,"loadCoreModInfo",loader);
        Hacks.invoke(core,"instantiateCoreMods");
        Hacks.invoke(core,"writeModContainers",loader);
        loadCandidateInfos(locator,Hacks.invoke(core,"getModInfo"));
    }
    
    private static Object modFileLocator(Object modFile) {
        return Hacks.invoke(modFile,locatorBased ? "getLocator" : "getProvider");
    }
    
    private static TILBetterModScan onFinishedWritingMods(TILBetterModScan scan, IModFile file) {
        Object loader = Hacks.invokeDirect(file,pathBased ? "getLoader" : "getLoaders");
        if(Objects.isNull(loader)) LOGGER.error("Why are there no language loaders??");
        else if(loader instanceof Collection<?>) {
            Collection<?> loaders = (Collection<?>)loader;
            if(loaders.isEmpty()) LOGGER.error("Why are there no language loaders??");
            else for(Object l : loaders) AFTER_WRITING_MODS.accept(scan,l);
        } else AFTER_WRITING_MODS.accept(scan,loader);
        LOGGER.debug("Finishing multiversion mod scan");
        scan.addFilePath(file.getFilePath());
        return scan;
    }
    
    static void parseAndSetModFileInfo(Object modFile) {
        Object modFileInfoParser = Hacks.getFieldDirect(modFile,"parser");
        if(Objects.nonNull(modFileInfoParser)) {
            LOGGER.debug("Parsing ModFileInfo for {}",modFile);
            Class<?> c = Hacks.findClass(MOD_FILE_PARSER);
            Object modFileInfo = Hacks.invokeStatic(c,"readModList",modFile,modFileInfoParser);
            Hacks.setFieldDirect(modFile,"modFileInfo",modFileInfo);
        } else LOGGER.error("Failed to get ModFileInfoParser for {}!",modFile);
    }
    
    static @Nullable Manifest parseManifest(File file) {
        Manifest manifest = null;
        try(InputStream stream = Files.newInputStream(file.toPath())) {
            manifest = new Manifest(stream);
        } catch(IOException ex) {
            LOGGER.error("Failed to parse manifest from {}",file,ex);
        }
        return manifest;
    }
    
    public static void populateMultiversionData(Map<MultiVersionModInfo,MultiVersionModData> infoMap,
            Map<String,MultiVersionModData> dataMap) {
        if(Objects.isNull(dataMap)) {
            LOGGER.error("Tried to populate multiversion mod data with null data map! infoMap = {}",infoMap);
            return;
        }
        for(MultiVersionModData data : dataMap.values()) {
            MultiVersionModInfo info = data.getInfo();
            if(infoMap.containsKey(info)) {
                LOGGER.debug("Populated data for {}",info);
                infoMap.put(info,data);
            }
        }
    }
    
    public static void queryCoreMods(String ... resourcePaths) {
        if(!fixedCoreMods && hasCoreModPath(resourcePaths)) {
            fixCoreModPackages();
            fixedCoreMods = true;
        }
    }
    
    public static void queryCoreMods(Object file) {
        List<Object> coremods;
        try {
            coremods = Hacks.invokeStaticDirect(ModFileParser.class,"getCoreMods",file);
        } catch(Throwable ignored) {
            coremods = Collections.emptyList();
        }
        if(Objects.nonNull(coremods)) {
            Hacks.setFieldDirect(file,"coreMods",coremods);
            if(!fixedCoreMods && !coremods.isEmpty()) {
                fixCoreModPackages();
                fixedCoreMods = true;
            }
        }
    }
    
    /**
     * Returns the list of mods
     */
    public static <F> List<F> scanMods() {
        if(scanned) {
            LOGGER.debug("Skipping duplicate mod scan");
            return Collections.emptyList();
        }
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        LOGGER.debug("Scanning for mods in multiversion jars (context = {})",context);
        List<F> mods = new ArrayList<>();
        LOGGER.debug("Getting CoreAPI instance");
        CoreAPI instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) LOGGER.error("Failed to get CoreAPI instance :(");
        Map<String,MultiVersionModData> data = Hacks.invoke(instance,"getModData",new File("."));
        for(Entry<MultiVersionModCandidate,ModFile> candidateEntry : CANDIDATE_MAP.entrySet()) {
            ModFile candidateFile = candidateEntry.getValue();
            Map<MultiVersionModInfo,MultiVersionModData> map = FILE_INFO_MAP.get(candidateFile);
            if(Objects.isNull(map)) {
                LOGGER.error("Cannot populate multiversion data with null info map! Was the getter set up correctly?");
                continue;
            }
            populateMultiversionData(map,data);
            if(candidateEntry.getKey().getModClassNames().contains(SELF_ENTRYPOINT)) {
                LOGGER.info("Adding scanned lang provider mod {}",candidateFile);
                addScannedMod(langProviderModFile(candidateFile,LOADER_ID),mods,"LANGPROVIDER");
            }
            LOGGER.info("Adding scanned mod {}",candidateFile);
            addScannedMod(candidateFile,mods,"MOD");
        }
        scanned = true;
        return Collections.unmodifiableList(mods);
    }
    
    private static void scanReflectively(Object scanner, Path path, ModFileScanData scan) {
        LOGGER.trace("Attempting to scan multiversion jar path {}", path);
        try {
            Hacks.invokeDirect(scanner,"fileVisitor",path,scan);
        } catch(Throwable t) {
            LOGGER.error("Failed to scan {}!",path,t);
        }
    }
    
    private static void setAutomaticModuleName(Attributes attributes, String moduleName) {
        if(Objects.isNull(automaticModuleName)) automaticModuleName = new Name("Automatic-Module-Name");
        attributes.put(automaticModuleName,moduleName);
    }
    
    private static String[] setCoreModExtensions(String version) {
        switch(version) {
            case "16":
            case "16_5": return new String[]{"v16.m5"};
            case "18":
            case "18_2": return new String[]{"v18.m2"};
            case "19":
            case "19_2": return new String[]{"v19","v19.m2"};
            case "19_4": return new String[]{"v19","v19.m4"};
            case "20":
            case "20_1": return new String[]{"v20","v20.m1"};
            case "20_4": return new String[]{"v20","v20.m4"};
            case "20_6": return new String[]{"v20","v20.m6"};
            case "21":
            case "21_1": return new String[]{"v21","v21.m1"};
            default: return new String[]{};
        }
    }
    
    public static void setFileVersion(Class<?> caller, String version, String actualVersion) {
        workingVersion = version;
        pathBased = Misc.equalsAny(version,"16","16_5");
        locatorBased = pathBased || Misc.equalsAny(version,"18","18_2");
        secureLoadingFormat = Misc.equalsAny(version,"20_4","20_6","21","21_1");
        modFileInfoCreator = setModFileInfoCreator(version);
        dynamicModFileClass = dynamicModFileCreator();
        langProviderFileInfo = setLangProviderFileInfo(version);
        coreModExtensions = setCoreModExtensions(version);
        final String lClass = "net.minecraftforge.fml.loading."+(pathBased ? "LibraryFinder" : "ClasspathLocatorUtils");
        final String arg2 = pathBased ? "manifest_jar" : MANIFEST_NAME;
        urlToPath = (url,manifest) -> Hacks.invokeStatic(lClass,"findJarPathFor",manifest,arg2,url);
        pathToManifest = setPathToManifest();
        LOGGER.info("{} Forge Locator plugin loaded on {}",actualVersion,caller.getClassLoader());
    }
    
    private static Function<ModFile,IModFileInfo> setLangProviderFileInfo(String version) {
        switch(version) {
            case "16":
            case "16_5": return file -> Hacks.construct(ModFileInfo.class,file,
                                                        wrapConfig(langProviderConfig()));
            case "18":
            case "18_2":
            case "19":
            case "19_2":
            case "19_4": return file -> Hacks.construct(ModFileInfo.class,file,
                                                        wrapConfig(langProviderConfig()),Collections.emptyList());
            case "20":
            case "20_1":
            case "20_4":
            case "20_6":
            case "21":
            case "21_1": return file -> {
                IConfigurable configWrapper = wrapConfig(langProviderConfig());
                Consumer<IModFileInfo> consumer = info ->
                        Hacks.invokeDirect(configWrapper,"setFile",info);
                List<?> languageSpecs = Collections.emptyList();
                return Hacks.construct(ModFileInfo.class,file,configWrapper,consumer,languageSpecs);
            };
            default: return file -> {
                LOGGER.error("Unknown version for creating a ModFileInfo instance {}",version);
                return null;
            };
        }
    }
    
    /**
     * Returns false if the version was not set correctly
     */
    @IndirectCallers
    public static boolean setLoadingVersion(Class<?> caller) {
        if(Objects.nonNull(workingVersion)) {
            LOGGER.debug("Tried to set loading version from {} after it was already set",caller);
            return false;
        }
        String version = String.valueOf(CoreAPI.gameVersion());
        String checkedVersion = version.substring(2).replace('.','_');
        setFileVersion(caller,checkedVersion,version);
        LOGGER.info("Successfully set Forge mod loading version ({}->{})",checkedVersion,version);
        return true;
    }
    
    static BiFunction<ModFile,Collection<?>,ModFileInfo> setModFileInfoCreator(String version) {
        switch(version) {
            case "16":
            case "16_5":
            case "18":
            case "18_2":
            case "19":
            case "19_2":
            case "19_4": return (file,infos) -> Hacks.construct(ModFileInfo.class,file,
                                                                initFileConfig(infos));
            case "20":
            case "20_1":
            case "20_4":
            case "20_6":
            case "21":
            case "21_1": return (file,infos) -> {
                IConfigurable configWrapper = initFileConfig(infos);
                Consumer<IModFileInfo> configConsumer = info ->
                        Hacks.invoke(configWrapper,"setFile",info);
                return Hacks.construct(ModFileInfo.class,file,configWrapper,configConsumer);
            };
            default: {
                LOGGER.error("Cannot set ModFileInfo creator function for unknown version {}",version);
                return (file,infos) -> null;
            }
        }
    }
    
    private static BiFunction<Path,Object,Manifest> setPathToManifest() {
        if(pathBased) return (path,locator) -> {
            try {
                Optional<Manifest> optional = Hacks.invoke(locator,"findManifest",path);
                return Objects.nonNull(optional) ? optional.orElse(null) : null;
            } catch(Throwable ignoredT) {
                LOGGER.warn("Failed to get manifest from path {}",path);
            }
            return null;
        };
        final Class<?> jarClass = Hacks.findClass("cpw.mods.jarhandling.SecureJar");
        if(locatorBased) return (path,ignored) -> {
            try {
                Object jar = Hacks.invokeStatic(jarClass,"from", path);
                return Hacks.invoke(jar,"getManifest");
            } catch(Throwable ignoredT) {
                LOGGER.warn("Failed to get manifest from path {}",path);
            }
            return null;
        };
        return (path,ignored) -> {
            try {
                Object manifestHolder = Hacks.invokeStatic(jarClass,"from",path);
                if(Objects.nonNull(manifestHolder) && !locatorBased)
                    manifestHolder = Hacks.invoke(manifestHolder,"moduleDataProvider");
                return Objects.nonNull(manifestHolder) ? Hacks.invoke(manifestHolder,"getManifest") : null;
            } catch(Throwable ignoredT) {
                LOGGER.warn("Failed to get manifest from path {}",path);
            }
            return null;
        };
    }
    
    /**
     * Called via BurningWave direct access to get around runtime casting issues
     */
    @IndirectCallers
    public static Object stupidCast(Object o) {
        LOGGER.info("Stupidly casting {}",o);
        return o;
    }
    
    static Object toNamedPath(Path path) {
        if(Objects.isNull(path)) return null;
        String name = path.toFile().getName();
        return toNamedPath(name.contains(".") ? name.substring(0,name.lastIndexOf('.')) : name,new Path[]{path});
    }
    
    static Object toNamedPath(String name, Path[] paths) {
        if(Objects.isNull(name) || Objects.isNull(paths) || paths.length==0) return null;
        return Hacks.construct(NAMED_PATH,name,paths);
    }
    
    private static IConfigurable wrapConfig(UnmodifiableConfig config) {
        return Hacks.construct(NIGHT_CONFIG_WRAPPER,config);
    }
    
    private static void writeClassBytes(IModFile file, TILBetterModScan scan, MultiVersionModData data,
            String className, byte[] bytes) {
        scan.addWrittenClass(className,data.getInfo(),file,bytes);
        ClassVisitor visitor = Hacks.construct(MOD_CLASS_VISITOR);
        ClassReader reader = new ClassReader(bytes);
        reader.accept(visitor,0);
        Hacks.invokeDirect(visitor,"buildData",scan.getClasses(),scan.getAnnotations());
    }
    
    private static void writeEntry(IModFile file, TILBetterModScan scan,
            Entry<MultiVersionModInfo,MultiVersionModData> entry) {
        MultiVersionModInfo info = entry.getKey();
        String modid = info.getModID();
        MultiVersionModData data = entry.getValue();
        if(Objects.isNull(data)) {
            LOGGER.warn("Skipping mod injection for {} since no data exists",modid);
            return;
        }
        for(Entry<String,byte[]> classBytes : data.writeModClass())
            writeClassBytes(file,scan,data,classBytes.getKey(),classBytes.getValue());
    }
    
    /**
     * Called via the dynamically generated ModFile extension class
     */
    @IndirectCallers
    public static ModFileScanData writeMods(ModFile file) {
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = FILE_INFO_MAP.get(file);
        if(Objects.isNull(infoMap) || infoMap.isEmpty()) {
            LOGGER.error("Cannot write multiversion mods for {} with null or empty info map! {}",file,infoMap);
        }
        TILBetterModScan scan = initModScanner(file);
        if(Objects.isNull(scan)) {
            LOGGER.error("Failed to initialize TILBetterModScan!");
            return null;
        }
        for(Entry<MultiVersionModInfo,MultiVersionModData> entry : infoMap.entrySet()) writeEntry(file,scan,entry);
        return onFinishedWritingMods(scan,file);
    }
}