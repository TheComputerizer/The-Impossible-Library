package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import cpw.mods.jarhandling.JarContents;
import cpw.mods.jarhandling.JarContentsBuilder;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
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
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import net.neoforged.fml.loading.ClasspathLocatorUtils;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.fml.loading.moddiscovery.ModFileParser;
import net.neoforged.neoforgespi.language.IConfigurable;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFile.Type;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.jar.Attributes.Name.IMPLEMENTATION_TITLE;
import static java.util.jar.Attributes.Name.IMPLEMENTATION_VERSION;
import static java.util.jar.JarFile.MANIFEST_NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.ALOAD;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.INVOKESPECIAL;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.RETURN_OBJ;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.LOADER_ID;
import static net.neoforged.neoforgespi.locating.IModFile.Type.LIBRARY;
import static net.neoforged.neoforgespi.locating.IModFile.Type.MOD;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;

/**
 * Helper methods for common functionalities between all 1.20.4+ versions of NeoForge mod loading
 */
public class NeoForgeModLoading {
    
    static final Logger LOGGER = LoggerFactory.getLogger("NeoForge Mod Loading");
    static final BiConsumer<TILBetterModScan,Object> AFTER_WRITING_MODS = (scan,language) -> {
        LOGGER.debug("Injecting scan data into the language loader");
        Consumer<ModFileScanData> visitor = Hacks.invokeDirect(language,"getFileVisitor");
        if(Objects.nonNull(visitor)) visitor.accept(scan);
    };
    
    //maps
    static final Map<MultiVersionModCandidate,IModFile> CANDIDATE_MAP = new HashMap<>();
    static final Map<IModFile,Map<MultiVersionModInfo,MultiVersionModData>> FILE_INFO_MAP = new HashMap<>();
    static final Map<String,Manifest> MANIFEST_MAP = new HashMap<>(); //Module name -> Manifest
    static final Map<String,SecureJar> SECURE_JAR_MAP = new HashMap<>(); //Module name -> SecureJar
    
    //collections
    static final Collection<Object> IDENTIFIED_FILES = new HashSet<>();
    static final Collection<IModFile> LOADER_FILES = new ArrayList<>();
    
    //version-dependent
    static final boolean JAVA_21 = NeoForgeCoreLoader.isJava21();
    static final String MOD_SCAN_PKG = "net.neoforged.fml.loading.mod"+(JAVA_21 ? "scan" : "discovery");
    
    //class names
    static final String COREMOD_ENGINE = "net.neoforged.coremod.CoreMod"+(JAVA_21 ? "Scripting" : "")+"Engine";
    static final String MOD_CLASS_VISITOR = MOD_SCAN_PKG+".ModClassVisitor";
    static final String MOD_FILE_DISCOVERY_ATTRIBUTES = "net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes";
    static final String MOD_FILE_INFO_PARSER = "net.neoforged.neoforgespi.locating."+(JAVA_21 ? "" : "ModFileFactory$")+"ModFileInfoParser";
    static final String MOD_FILE_OR_EXCEPTION = "net.neoforged.neoforgespi.locating.IModLocator$ModFileOrException";
    static final String MOD_PROVIDER = "net.neoforged.neoforgespi.locating.IModProvider";
    static final String NIGHT_CONFIG_WRAPPER = "net.neoforged.fml.loading.moddiscovery.NightConfigWrapper";
    static final String SCANNER = MOD_SCAN_PKG+".Scanner";
    static final String SELF_ENTRYPOINT = "mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint";
    
    //modifiable
    static Name automaticModuleName;
    static Class<?> dynamicModFileClass;
    static Set<String> coreModExtensions;
    static boolean fixedCoreMods;
    static MultiVersionModCandidate loaderCandidate;
    @Getter static String workingVersion;
    
    private static <F> void addScannedMod(IModFile file, List<F> mods, String type) {
        Hacks.setFieldDirect(file,"modFileType",getModFileType(type));
        if(JAVA_21) mods.add(GenericUtils.cast(file));
        else mods.add(Hacks.construct(MOD_FILE_OR_EXCEPTION,file,null));
    }
    
    private static JarContents buildJarContents(Object contentsOrPath, String moduleName) {
        return contentsOrPath instanceof JarContents contents ?
                contents : buildJarContents(moduleName,(Path)contentsOrPath);
    }
    
    public static JarContents buildJarContents(String moduleName, Path ... paths) {
        return new JarContentsBuilder().paths(paths).defaultManifest(getDefaultManifest(moduleName)).build();
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
        checkPath(loader,ClasspathLocatorUtils.findJarPathFor(MANIFEST_NAME,MANIFEST_NAME,url),filter);
    }
    
    public static Object createDiscoveryAttributes(Object readerOrLocator) {
        if(Objects.isNull(readerOrLocator)) {
            LOGGER.warn("Returning DEFAULT {} instance",MOD_FILE_DISCOVERY_ATTRIBUTES);
            return Hacks.getFieldStatic(MOD_FILE_DISCOVERY_ATTRIBUTES,"DEFAULT");
        }
        String simpleClassName = readerOrLocator.getClass().getSimpleName();
        if("ModFileDiscoveryAttributes".equals(simpleClassName)) return readerOrLocator;
        return "MultiVersionModReader".equals(simpleClassName) ?
                Hacks.construct(MOD_FILE_DISCOVERY_ATTRIBUTES,null,readerOrLocator,null,null) :
                Hacks.construct(MOD_FILE_DISCOVERY_ATTRIBUTES,null,null,readerOrLocator,null);
    }
    
    public static Supplier<Manifest> createLoaderManfiest(Path sourcePath) {
        if(!DEV || Objects.isNull(sourcePath) || !TILDev.isLoaderPath(sourcePath)) return null;
        return () -> {
            LOGGER.info("Creating loader manifest for {}",sourcePath);
            return getDefaultManifest(MODID).get();
        };
    }
    
    /**
     * Create separate LIBRARY and MOD type mod files for this library.
     * This should only be called from TILSelfLocator in 1.20.6+
     */
    public static IModFile[] createLoaderFiles(JarContents[] contents, Object locator,
            MultiVersionModCandidate candidate, final Collection<?> infos) {
        loaderCandidate = candidate;
        IModFile modFile = createModFile(contents[0],locator,f -> getFileInfo(f,infos),MOD,MODID);
        FILE_INFO_MAP.put(modFile,initInfoMap(candidate,infos));
        if(Objects.isNull(modFile)) return new IModFile[]{};
        IModFile langFile = createModFile(contents[1],locator,NeoForgeModLoading::langFileInfo,LIBRARY,LOADER_ID);
        IModFile[] files = new IModFile[]{langFile,modFile};
        LOADER_FILES.addAll(List.of(files));
        return files;
    }
    
    @SuppressWarnings("SameParameterValue")
    static IModFile createModFile(IModFile reference, Function<IModFile,IModFileInfo> parser, String type,
            final String moduleName) {
        Object locator = Hacks.invoke(reference,"getProvider");
        return createModFile(reference.getFilePath(),locator,parser,type,moduleName);
    }
    
    public static IModFile createModFile(Object pathOrJarContents, Object locator, MultiVersionModCandidate candidate,
            final Collection<?> infos, Type type) {
        String moduleName = getFirstModId(candidate,infos);
        IModFile file = createModFile(pathOrJarContents,locator,f -> getFileInfo(f,infos),type,moduleName);
        CANDIDATE_MAP.put(candidate,file);
        FILE_INFO_MAP.put(file,initInfoMap(candidate,infos));
        return file;
    }
    
    private static IModFile createModFile(Object pathOrJarContents, Object locator,
            Function<IModFile,IModFileInfo> parserFunc, Object type, final String moduleName) {
        if(Objects.isNull(dynamicModFileClass)) {
            LOGGER.error("Cannot create ModFile with null dynamicModFileClass! Did it fail to initialize?");
            return null;
        }
        LOGGER.debug("Creating mod file of type {} with module name {} at path {}",type,moduleName,pathOrJarContents);
        SecureJar jar = pathOrJarContents instanceof SecureJar ? (SecureJar)pathOrJarContents : null;
        boolean updatePathMap = false;
        if(Objects.nonNull(moduleName)) {
            if(SECURE_JAR_MAP.containsKey(moduleName)) {
                if(Objects.isNull(jar)) {
                    LOGGER.debug("Found existing SecureJar for module {}",moduleName);
                    jar = SECURE_JAR_MAP.get(moduleName);
                }
            } else updatePathMap = true;
        }
        if(Objects.isNull(jar)) jar = getDefaultJar(buildJarContents(pathOrJarContents,moduleName),moduleName);
        //ModFileInfoParser isn't an inner class in 1.20.6+ and we can't just cast Function (I already tried that)
        Object parser = ClassHelper.newGenericProxy(Hacks.findClass(MOD_FILE_INFO_PARSER),"build",
                args -> parserFunc.apply((IModFile)args[0]));
        IModFile file;
        if(JAVA_21) {
            Object discoveryAttributes = createDiscoveryAttributes(locator);
            file = Hacks.constructAndCast(dynamicModFileClass,jar,parser,type,discoveryAttributes);
            updateDiscoveryAttributes(file,"withParent",file);
        } else file = Hacks.constructAndCast(dynamicModFileClass,jar,locator,parser,type);
        //Construct the file first to ensure there aren't any errors before the SECURE_JAR_MAP is updated
        if(updatePathMap) {
            LOGGER.debug("Adding SecureJar instance for module {} to the cache",moduleName);
            SECURE_JAR_MAP.put(moduleName,jar);
        }
        return file;
    }
    
    static @Nullable Class<?> dynamicModFileCreator() {
        String pkgName = NeoForgeCoreLoader.class.getPackage().getName();
        String className = pkgName+".TILNeoForgeModFile";
        byte[] byteCode = generateModFileExtension(className);
        if(Objects.isNull(byteCode)) {
            LOGGER.error("Failed to define bytecode for {}",className);
            return null;
        }
        ASMHelper.writeDebugByteCode(className, byteCode);
        LOGGER.info("Successfully generated bytecode for {}",className);
        try {
            Class<?> defined = ClassHelper.defineAndResolveClass(ModFile.class.getClassLoader(),className,byteCode);
            Hacks.setFieldDirect(defined,"module",Hacks.invoke(NeoForgeModLoading.class,"getModule"));
            LOGGER.info("Successfully generated ModFile extension {}",defined);
            return defined;
        } catch(Throwable t) {
            LOGGER.error("Failed to generate ModFile extension {}",className,t);
        }
        return null;
    }
    
    static void findFiles(MultiVersionLoaderAPI loader, Predicate<Path> filter, File... files) {
        TILRef.logInfo("[{}]: Loading {} mod files", loader.getName(), files.length);
        for(File mod : files) {
            TILRef.logDebug("[{}]: Potentially loading mod file at path {}",loader.getName(),mod.toPath());
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
            } else optionalManifest = Optional.ofNullable(SecureJar.from(path).moduleDataProvider().getManifest());
        } catch(Throwable t) {
            LOGGER.error("Failed to find manifest for {}",path,t);
        }
        return optionalManifest.isPresent() || Objects.isNull(fallback) ?
                optionalManifest : Optional.ofNullable(fallback.get());
    }
    
    public static void findPaths(ClassLoader classLoader, MultiVersionLoaderAPI loader) {
        Predicate<Path> filter = path -> {
            if(Objects.isNull(path)) return false;
            Manifest manifest = findManifest(path).orElse(null);
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
            TILRef.logError("[{}]: Failed to calculate URLs for paths with {} using {}",loader.getName(),
                            MANIFEST_NAME,classLoader,ex);
        }
    }
    
    /**
     * No easy way for generic core mods? Fine, I'll do it myself
     */
    private static void fixCoreModPackages() {
        Function<String,Collection<String>> fieldGetter = f -> Hacks.getFieldStaticDirect(COREMOD_ENGINE,f);
        Hacks.addToCollectionField("ALLOWED_PACKAGES",coreModExtensions,fieldGetter,
                (f,allowed) -> LOGGER.debug("Expanded coremod package whitelist to {}",allowed));
    }
    
    private static byte[] generateModFileExtension(String className) {
        int javaVer = NeoForgeCoreLoader.isJava21() ? JAVA21 : JAVA17;
        ClassWriter writer = ASMHelper.getWriter(javaVer,ASMRef.PUBLIC,TypeHelper.fromBinary(className),
                                                 TypeHelper.get(ModFile.class));
        Class<?>[] constructorArgTypes = new Class<?>[]{SecureJar.class,null,null,String.class};
        if(JAVA_21) {
            constructorArgTypes[1] = Hacks.findClass(MOD_FILE_INFO_PARSER);
            constructorArgTypes[2] = Type.class;
            constructorArgTypes[3] = Hacks.findClass(MOD_FILE_DISCOVERY_ATTRIBUTES);
        } else {
            constructorArgTypes[1] = Hacks.findClass(MOD_PROVIDER);
            constructorArgTypes[2] = Hacks.findClass(MOD_FILE_INFO_PARSER);
        }
        String constructorDesc = TypeHelper.voidMethodDesc(constructorArgTypes);
        String modFile = TypeHelper.get(ModFile.class).getInternalName();
        String modLoading = TypeHelper.get(NeoForgeModLoading.class).getInternalName();
        String writeModsDesc = TypeHelper.methodDesc(ModFileScanData.class,ModFile.class);
        String identifyModsDesc = TypeHelper.methodDesc(BOOLEAN_TYPE,TypeHelper.get(IModFile.class));
        
        MethodVisitor constructor = writer.visitMethod(PUBLIC,"<init>",constructorDesc,null,null);
        constructor.visitCode();
        for(int i=0;i<5;i++) constructor.visitVarInsn(ALOAD,i);
        constructor.visitMethodInsn(INVOKESPECIAL,modFile,"<init>",constructorDesc,false);
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
        
        return writer.toByteArray();
    }
    
    private static SecureJar getDefaultJar(JarContents contents, final String moduleName) {
        return LOADER_ID.equals(moduleName) || MODID.equals(moduleName) ?
                TILLoaderJar.get(contents,moduleName) : SecureJar.from(contents,JarMetadata.from(contents));
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
    
    /**
     * Also initializes the info map
     */
    public static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        if(file instanceof ModFile) {
            IConfigurable configWrapper = initFileConfig(infos);
            Consumer<IModFileInfo> configConsumer = info ->
                    Hacks.invoke(configWrapper,"setFile",info);
            return Hacks.construct(ModFileInfo.class,file,configWrapper,configConsumer);
        }
        LOGGER.error("Cannot get IModFileInfo for IModFile that is not an instance of ModFile! {}",file);
        return null;
    }
    
    private static String getFirstModId(MultiVersionModCandidate candidate,
            Collection<?> infos) {
        for(Object info : infos) {
            String modid = ((MultiVersionModInfo)info).getModID();
            if(Objects.nonNull(modid) && !modid.isEmpty()) return modid;
        }
        if(Objects.nonNull(candidate)) {
            LOGGER.debug("Returning file name for MultiVersionModCandidate as first modid");
            return candidate.getFile().getName();
        }
        LOGGER.debug("First modid not found! Returning null");
        return null;
    }
    
    public static Type getModFileType(String name) {
        if(Objects.isNull(name) || name.isEmpty()) {
            LOGGER.error("Null or empty mod file type! LIBRARY will be assumed");
            return LIBRARY;
        }
        return Type.valueOf(name);
    }
    
    private static boolean hasCoreModPath(String ... paths) {
        for(String path : paths)
            if(path.contains("coremods.json")) return true;
        return false;
    }
    
    /**
     * Adapted from ModFile#identifyMods
     */
    private static List<Path> identifyAccessTransformers(IModFile file) {
        final IModFileInfo fileInfo = file.getModFileInfo();
        Optional<List<String>> potentialPaths = Hacks.invokeStaticDirect(ModFileParser.class,"getAccessTransformers",fileInfo);
        if(Objects.isNull(potentialPaths)) potentialPaths = Optional.empty();
        return potentialPaths.map(list -> list.stream().map(file::findResource).filter(path -> {
                    if(Files.notExists(path)) {
                        LOGGER.error("Access transformer file {} provided by mod {} does not exist!",path,
                                     fileInfo.moduleName());
                        return false;
                    }
                    return true;
                }))
                .orElseGet(() -> Stream.of(file.findResource("META-INF","accesstransformer.cfg"))
                        .filter(Files::exists))
                .toList();
    }
    
    private static List<Object> identifyMixinConfigs(IModFileInfo fileInfo) {
        return Hacks.invokeStaticDirect(ModFileParser.class,"getMixinConfigs",fileInfo);
    }
    
    /**
     * Called via ASM from the generated ModFile extension
     */
    @IndirectCallers
    public static boolean identifyMods(IModFile file) {
        String fileName = file.getFileName();
        Path absolutePath = file.getFilePath().toAbsolutePath();
        LOGGER.debug("Finalizing mod identification for {} (path={})",fileName,absolutePath);
        if(!CANDIDATE_MAP.containsValue(file) && !LOADER_FILES.contains(file)) {
            LOGGER.warn("There are no mods to identify for {}",fileName);
            return false;
        }
        if(IDENTIFIED_FILES.contains(file))
            LOGGER.debug("Skipping file that was already identified {}",fileName);
        else {
            LOGGER.debug("Querying access transformers, coremods, & mixin conifgs for mod file {}",fileName);
            queryCoreMods(file);
            Hacks.setFieldDirect(file,"mixinConfigs",identifyMixinConfigs(file.getModFileInfo()));
            Hacks.setFieldDirect(file,"accessTransformers",identifyAccessTransformers(file));
            IDENTIFIED_FILES.add(file);
        }
        LOGGER.debug("Finalized mod identification for {} (path={})",fileName,absolutePath);
        int candidateCount = (CANDIDATE_MAP.size()+LOADER_FILES.size())-1; //Ignore the language provider file
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
        dependency.set("versionRange","[0.4.6,)");
        return dependency;
    }
    
    private static List<Config> initConfigMods(Config config, Collection<?> infos) {
        List<Config> mods = new ArrayList<>();
        boolean setLicense = false;
        for(Object o : infos) {
            MultiVersionModInfo info = (MultiVersionModInfo)o;
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
        if(!setLicense) config.set("license",DEFAULT_LICENSE);
        return mods;
    }
    
    public static IConfigurable initFileConfig(Collection<?> infos) {
        Config config = Config.inMemory();
        config.set("modLoader","multiversionprovider");
        config.set("loaderVersion","[0.4.6,)");
        List<Config> mods = initConfigMods(config,infos);
        config.add("mods",mods);
        if(!mods.isEmpty() && !MODID.equals(mods.get(0).get("modId")))
            config.add("dependencies",new ArrayList<>(Collections.singletonList(initConfigDependencies())));
        return wrapConfig(config);
    }
    
    /**
     * 1.20.4 version
     */
    private static Map<MultiVersionModInfo,MultiVersionModData> initInfoMap(
            Collection<MultiVersionModInfo> infos) {
        infos = Objects.nonNull(infos) ? infos : Collections.emptyList();
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = new HashMap<>();
        for(MultiVersionModInfo info : infos) infoMap.put(info,null);
        LOGGER.info("Created <info,data> map with {} entries for multiversion mod file ({}) using {}",
                    infos.size(),workingVersion,infos);
        return infoMap;
    }
    
    /**
     * 1.20.6+ version
     */
    private static Map<MultiVersionModInfo,MultiVersionModData> initInfoMap(MultiVersionModCandidate candidate,
            Collection<?> infos) {
        CoreAPI core = CoreAPI.getInstance();
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = new HashMap<>();
        for(Object o : infos) {
            MultiVersionModInfo info = (MultiVersionModInfo)o;
            MultiVersionModData data = core.getModData(new File("."),candidate,info);
            infoMap.put(info,data);
        }
        return infoMap;
    }
    
    public static void initModLoading(ClassLoader loader, Object locator) {
        Object core = CoreAPI.getInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize multiversion mod loader! Cannot find CoreAPI on "+loader);
        findPaths(loader,Hacks.invoke(core,"getLoader"));
        loadMods(loader,locator,core);
    }
    
    private static @Nullable TILBetterModScan initModScanner(ModFile file) {
        LOGGER.info("Starting multiversion mod scan");
        TILBetterModScan scan = new TILBetterModScan();
        scan.addModFileInfo(file.getModFileInfo());
        file.scanFile(p -> scanReflectively(Hacks.construct(SCANNER,file),p,scan));
        LOGGER.debug("Injecting @Mod annotations from multiversion mod info");
        if(Objects.nonNull(scan.getAnnotations())) {
            scan.setCore(CoreAPI.getInstance());
            return scan;
        }
        LOGGER.error("@Mod scan annotation set for multiversion mod is null???");
        return null;
    }
    
    private static IModFileInfo langFileInfo(IModFile file) {
        if(!(file instanceof ModFile)) {
            LOGGER.error("IModFile instance must extend ModFile to be supported for IModFileInfo construction!");
            return null;
        }
        IConfigurable configWrapper = wrapConfig(langProviderConfig());
        Consumer<IModFileInfo> consumer = info ->
                Hacks.invokeDirect(configWrapper,"setFile",info);
        return new ModFileInfo((ModFile)file,configWrapper,consumer,Collections.emptyList());
    }
    
    public static Config langProviderConfig() {
        Config config = Config.inMemory();
        config.set("modLoader","minecraft");
        config.set("loaderVersion","1");
        config.set("license",DEFAULT_LICENSE);
        Config mod = Config.inMemory();
        mod.set("modId",PROVIDERID);
        mod.set("version",VERSION);
        mod.set("displayName","Multiversion Language Provider");
        mod.set("logoFile","logo.png");
        mod.set("authors","The_Computerizer");
        mod.set("description","Multiversion language loader for "+NAME);
        config.set("mods",Collections.singletonList(mod));
        return config;
    }
    
    public static IModFile langProviderModFile(IModFile reference, String moduleName) {
        return createModFile(reference,NeoForgeModLoading::langFileInfo,"LANGPROVIDER",moduleName);
    }
    
    private static void loadCandidateInfos(Object locator, Map<?,Collection<MultiVersionModInfo>> infoMap) {
        if(Objects.isNull(infoMap)) {
            LOGGER.error("Tried to load mod candidate info with null info map! locator = {}",locator);
            return;
        }
        for(Entry<?,Collection<MultiVersionModInfo>> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Collection<MultiVersionModInfo> infos = entry.getValue();
            if(Objects.isNull(infos)) {
                LOGGER.error("Null MultiVersionModInfo collection for candidate {}",candidate);
                continue;
            }
            Function<IModFile,IModFileInfo> parser = file -> getFileInfo(file,infos);
            String firstModId = getFirstModId(candidate,infos);
            IModFile file = createModFile(candidate.getFile().toPath(),locator,parser,"MOD",firstModId);
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
    
    private static TILBetterModScan onFinishedWritingMods(TILBetterModScan scan, IModFile file) {
        if(!JAVA_21) {
            List<?> loaders = Hacks.invoke(file,"getLoaders");
            if(Objects.isNull(loaders) || loaders.isEmpty()) LOGGER.error("Why are there no language loaders??");
            else for(Object loader : loaders) AFTER_WRITING_MODS.accept(scan,loader);
        }
        LOGGER.debug("Finishing multiversion mod scan");
        scan.addFilePath(file.getFilePath());
        return scan;
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
    
    /**
     * Called via ASM from the generated ModFile extension
     */
    @IndirectCallers
    public static void queryCoreMods(String ... resourcePaths) {
        if(!fixedCoreMods && hasCoreModPath(resourcePaths)) {
            fixCoreModPackages();
            fixedCoreMods = true;
        }
    }
    
    public static void queryCoreMods(Object file) {
        List<Object> coremods = Hacks.invokeStaticDirect(ModFileParser.class,"getCoreMods",file);
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
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        LOGGER.debug("Scanning for mods in multiversion jars (context = {})",context);
        List<F> mods = new ArrayList<>();
        LOGGER.debug("Getting CoreAPI instance");
        CoreAPI instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) LOGGER.error("Failed to get CoreAPI instance :(");
        Map<String,MultiVersionModData> data = instance.getModData(new File("."));
        for(Entry<MultiVersionModCandidate,IModFile> candidateEntry : CANDIDATE_MAP.entrySet()) {
            IModFile candidateFile = candidateEntry.getValue();
            Map<MultiVersionModInfo,MultiVersionModData> map = FILE_INFO_MAP.get(candidateFile);
            if(Objects.isNull(map)) {
                LOGGER.error("Cannot populate multiversion data with null info map! Was the getter set up correctly?");
                continue;
            }
            populateMultiversionData(map,data);
            if(candidateEntry.getKey().getModClassNames().contains(SELF_ENTRYPOINT)) {
                LOGGER.info("Adding scanned lang provider mod {}",candidateFile);
                addScannedMod(langProviderModFile(candidateFile, LOADER_ID), mods, "LANGPROVIDER");
            }
            LOGGER.info("Adding scanned mod {}",candidateFile);
            addScannedMod(candidateFile,mods,"MOD");
        }
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
    
    private static void setFileVersion(Class<?> caller, String version, String actualVersion) {
        workingVersion = version;
        Set<String> set = new HashSet<>(Arrays.asList("api","neoforge"));
        for(String coremodVersionExtension : JAVA_21 ? new String[]{"v21","v20.m6","v21.m1"} : new String[]{"v20","v20.m4"})
            set.add("neoforge."+coremodVersionExtension);
        coreModExtensions = set.stream().map(s -> BASE_PACKAGE+"."+s+".core").collect(Collectors.toSet());
        dynamicModFileClass = dynamicModFileCreator();
        LOGGER.info("{} NeoForge Locator plugin loaded on {}",actualVersion,caller.getClassLoader());
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
        LOGGER.info("Successfully set Neoforge mod loading version ({}->{})",checkedVersion,version);
        return true;
    }
    
    public static void updateDiscoveryAttributes(IModFile file, String method, Object updateWith) {
        Object attributes = Hacks.invoke(file,"getDiscoveryAttributes");
        Hacks.invoke(file,"setDiscoveryAttributes",updateDiscoveryAttributes(attributes,method,updateWith));
    }
    
    public static Object updateDiscoveryAttributes(Object attributes, String method, Object updateWith) {
        return Hacks.invoke(attributes,method,updateWith);
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
            return null;
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