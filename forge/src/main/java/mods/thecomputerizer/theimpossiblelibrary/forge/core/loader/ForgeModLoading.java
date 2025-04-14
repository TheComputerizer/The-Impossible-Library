package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
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

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
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
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static net.minecraftforge.forgespi.locating.IModFile.Type.LANGPROVIDER;
import static net.minecraftforge.forgespi.locating.IModFile.Type.LIBRARY;
import static net.minecraftforge.forgespi.locating.IModFile.Type.MOD;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;

/**
 * Helper methods for common functionalities between all 1.16.5+ versions of Forge mod loading
 */
@SuppressWarnings("LoggingSimilarMessage")
public class ForgeModLoading {
    
    static final Logger LOGGER = LogManager.getLogger("Forge Mod Loading");
    
    static final BiConsumer<TILBetterModScan,Object> AFTER_WRITING_MODS = (scan,language) -> {
        LOGGER.debug("Injecting scan data into the language loader");
        Consumer<ModFileScanData> visitor = Methods.invokeDirect(language,"getFileVisitor");
        visitor.accept(scan);
    };
    static final Map<MultiVersionModCandidate,ModFile> CANDIDATE_MAP = new HashMap<>();
    static final Map<Object,Map<MultiVersionModInfo,MultiVersionModData>> FILE_INFO_MAP = new HashMap<>();
    static final String MANIFEST = "META-INF/MANIFEST.MF";
    static final String MOD_CLASS_VISITOR = "net.minecraftforge.fml.loading.moddiscovery.ModClassVisitor";
    static final String NIGHT_CONFIG_WRAPPER = "net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper";
    static final String SCANNER = "net.minecraftforge.fml.loading.moddiscovery.Scanner";
    static final String SELF_ENTRYPOINT = "mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint";
    
    static Function<ModFile,IModFileInfo> langProviderFileInfo;
    static BiFunction<URL,String,Path> urlToPath;
    static BiFunction<Path,Object,Manifest> pathToManifest;
    static String coreModEngineClass;
    static String[] coreModExtensions;
    static BiFunction<ModFile,Collection<?>,ModFileInfo> modFileInfoCreator;
    static Class<?> dynamicModFileClass;
    static boolean fixedCoreMods;
    @Getter static boolean pathBased;
    @Getter static boolean locatorBased;
    @Getter static String workingVersion;
    
    @SuppressWarnings("unchecked")
    private static <F> void addScannedMod(Object file, List<F> mods, String type) {
        if(locatorBased) {
            Fields.setDirect(file,"modFileType",getModFileType(type));
            mods.add((F)file);
        } else {
            final String fileClassName = "net.minecraftforge.forgespi.locating.IModLocator$ModFileOrException";
            final Class<?> fileClass = ClassHelper.findClass(fileClassName);
            mods.add(Constructors.newInstanceOf(fileClass,file,null));
        }
    }
    
    static void checkPath(MultiVersionLoaderAPI loader, Path path, Predicate<Path> filter) {
        String loaderName = loader.getName();
        if(Files.isDirectory(path)) return;
        String fileName = path.getFileName().toString();
        LOGGER.debug("[{}]: Checking if file {} is the loader", loaderName, fileName);
        if(Objects.isNull(MultiVersionModCandidate.loaderFile) && TILDev.isLoader(fileName)) {
            LOGGER.debug("[{}]: File is the loader",loaderName);
            MultiVersionModCandidate.loaderFile = path.toFile();
        }
        if(filter.test(path)) {
            LOGGER.info("[{}]: Found mod candidate at {}",loaderName,path);
            loader.addPotentialModPath(path);
        }
    }
    
    static void checkURL(MultiVersionLoaderAPI loader, URL url, Predicate<Path> filter) {
        LOGGER.debug("[{}]: Checking URL {} for MANIFEST {}",loader.getName(),url,MANIFEST);
        checkPath(loader,urlToPath.apply(url,MANIFEST),filter);
    }
    
    static ModFile createModFile(Path path, Object locator, ModFileInfoParser parser, String type) {
        if(Objects.isNull(dynamicModFileClass)) {
            LOGGER.error("Cannot create ModFile with null dynamicModFileClass! Did it fail to initialize?");
            return null;
        }
        Object pathOrJar = path;
        if(!pathBased) {
            final Class<?> jarClass = ClassHelper.findClass("cpw.mods.jarhandling.SecureJar");
            pathOrJar = Methods.invokeStatic(jarClass,"from",path);
        }
        return Constructors.newInstanceOf(dynamicModFileClass,pathOrJar,locator,parser,type);
    }
    
    static @Nullable Class<?> dynamicModFileCreator() {
        ClassHelper.checkBurningWaveInit();
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
            if(!ForgeCoreLoader.isJava8()) {
                Object module = Methods.invoke(ForgeModLoading.class,"getModule");
                Fields.setDirect(defined,"module",module);
            }
            return defined;
        } catch(Throwable t) {
            LOGGER.error("Failed to generate ModFile extension {}",className,t);
        }
        return null;
    }
    
    static void findFiles(MultiVersionLoaderAPI loader, Predicate<Path> filter, File... files) {
        LOGGER.info("[{}]: Loading {} mod files",loader.getName(),files.length);
        for(File mod : files) {
            LOGGER.debug("[{}]: Potentially loading mod file at path {}",loader.getName(),mod.toPath());
            checkPath(loader,mod.toPath(),filter);
        }
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
            final Enumeration<URL> manifests = ClassLoader.getSystemClassLoader().getResources(MANIFEST);
            while(manifests.hasMoreElements()) checkURL(loader,manifests.nextElement(),filter);
        } catch(IOException ex) {
            LOGGER.error("[{}]: Failed to calculate URLs for paths with {} using {}",loader.getName(),MANIFEST,classLoader,ex);
        }
    }
    
    /**
     * No easy way for generic core mods? Fine, I'll do it myself
     */
    public static void fixCoreModPackages() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?> engineClass = ClassHelper.findClass(coreModEngineClass,loader);
        Set<String> allowed = new HashSet<>(Fields.getStatic(engineClass,"ALLOWED_PACKAGES"));
        allowed.add(BASE_PACKAGE+".api.core");
        allowed.add(BASE_PACKAGE+".forge.core");
        for(String extension : coreModExtensions) allowed.add(BASE_PACKAGE+".forge."+extension+".core");
        LOGGER.debug("Expanded coremod package whitelist to {}", allowed);
        Fields.setStaticDirect(engineClass,"ALLOWED_PACKAGES",allowed);
    }
    
    private static byte[] generateModFileExtension(String className) {
        Class<?> pathOrJarClass = pathBased ? Path.class :
                ClassHelper.findClass("cpw.mods.jarhandling.SecureJar");
        Class<?> locatorClass = locatorBased ? IModLocator.class :
                ClassHelper.findClass("net.minecraftforge.forgespi.locating.IModProvider");
        ClassWriter writer = ASMHelper.getWriter(JAVA8,ASMRef.PUBLIC,TypeHelper.fromBinary(className),
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
        String identifyModsDesc = TypeHelper.methodDesc(BOOLEAN_TYPE,BOOLEAN_TYPE,OBJECT_TYPE);
        
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
        identifyMods.visitMethodInsn(INVOKESPECIAL,modFile,"identifyMods","()Z",false);
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
    
    private static Object getCoreMods(Object file) {
        return Methods.invoke(file,"getCoreMods");
    }
    
    /**
     * Also initializes the info map
     */
    public static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        if(file instanceof ModFile) return modFileInfoCreator.apply((ModFile)file,infos);
        LOGGER.error("Cannot get IModFileInfo for IModFile that is not an instance of ModFile! {}",file);
        return null;
    }
    
    public static Type getModFileType(String name) {
        if(Objects.isNull(name) || name.isEmpty()) {
            LOGGER.error("Null or empty mod file type! LIBRARY will be assumed");
            return LIBRARY;
        }
        switch(name) {
            case "GAMELIBRARY": {
                LOGGER.warn("The GAMELIBRARY mod file type is unable to be supported in all Forge versions! " +
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
    public static boolean identifyMods(boolean result, Object file) {
        LOGGER.debug("Identifying mods");
        if(result) queryCoreMods(file);
        LOGGER.debug("Finished identifying mods");
        return result;
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
    
    private static List<Config> initConfigMods(Config config, Collection<?> infos) {
        List<Config> mods = new ArrayList<>();
        boolean setLicense = false;
        for(Object info : infos) {
            if(!setLicense) {
                config.set("license",Methods.invoke(info,"getLicense"));
                setLicense = true;
            }
            Config mod = Config.inMemory();
            mod.set("description",Methods.invoke(info,"getDescription"));
            mod.set("displayName",Methods.invoke(info,"getName"));
            mod.set("license",Methods.invoke(info,"getLicense"));
            mod.set("logoFile","logo.png");
            mod.set("modId",Methods.invoke(info,"getModID"));
            mod.set("version",Methods.invoke(info,"getVersion"));
            mods.add(mod);
        }
        if(!setLicense) config.set("license","LGPL V3");
        return mods;
    }
    
    public static IConfigurable initFileConfig(Collection<?> infos) {
        Config config = Config.inMemory();
        config.set("modLoader","multiversionprovider");
        config.set("loaderVersion","[0.4.0,)");
        List<Config> mods = initConfigMods(config,infos);
        config.add("mods",mods);
        if(!mods.isEmpty() && !MODID.equals(mods.get(0).get("modId")))
            config.add("dependencies",new ArrayList<>(Collections.singletonList(initConfigDependencies())));
        return wrapConfig(config);
    }
    
    private static Map<MultiVersionModInfo,MultiVersionModData> initInfoMap(Collection<?> infos) {
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = new HashMap<>();
        for(Object info : infos) infoMap.put((MultiVersionModInfo)info,null);
        LOGGER.info("Created <info,data> map with {} entries for multiversion mod file ({}) using {}",
                    infos.size(),workingVersion,infos);
        return infoMap;
    }
    
    public static void initModLoading(ClassLoader loader, Object locator) {
        Object core = CoreAPI.getInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize Forge mod loading! Cannot find CoreAPI on "+loader);
        ClassHelper.checkBurningWaveInit();
        findPaths(loader,(MultiVersionLoaderAPI)CoreAPI.invoke(core,"getLoader"),locator);
        loadMods(loader,locator,core);
    }
    
    private static @Nullable TILBetterModScan initModScanner(ModFile file) {
        LOGGER.info("Starting multiversion mod scan");
        TILBetterModScan scan = new TILBetterModScan();
        scan.addModFileInfo(file.getModFileInfo());
        Class<?> cScanner = ClassHelper.findClass(SCANNER);
        file.scanFile(p -> scanReflectively(Constructors.newInstanceOf(cScanner,file),p,scan));
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
    
    public static ModFile langProviderModFile(ModFile reference) {
        final Object locator = Methods.invoke(reference,locatorBased ? "getLocator" : "getProvider");
        final ModFileInfoParser parser = ForgeModLoading::langFileInfo;
        return createModFile(reference.getFilePath(),locator,parser,"LANGPROVIDER");
    }
    
    private static void loadCandidateInfos(Object locator, Map<?,?> infoMap) {
        for(Entry<?,?> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Collection<?> infos = (Collection<?>)entry.getValue();
            ModFileInfoParser parser = file -> getFileInfo(file,infos);
            ModFile file = createModFile(candidate.getFile().toPath(),locator,parser,"MOD");
            CANDIDATE_MAP.put(candidate,file);
            FILE_INFO_MAP.put(file,initInfoMap(infos));
        }
    }
    
    private static void loadMods(ClassLoader loader, Object locator, Object core) {
        Class<?>[] withLoader = new Class<?>[]{ClassLoader.class};
        CoreAPI.invoke(core,"loadCoreModInfo",withLoader,loader);
        CoreAPI.invoke(core,"instantiateCoreMods");
        CoreAPI.invoke(core,"writeModContainers",withLoader,loader);
        Object infoMap = CoreAPI.invoke(core,"getModInfo");
        loadCandidateInfos(locator,(Map<?,?>)infoMap);
    }
    
    private static TILBetterModScan onFinishedWritingMods(TILBetterModScan scan, IModFile file) {
        Object loader = Methods.invokeDirect(file,pathBased ? "getLoader" : "getLoaders");
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
    
    @SuppressWarnings("unchecked")
    public static void populateMultiversionData(Map<MultiVersionModInfo,MultiVersionModData> infoMap, Object dataMap) {
        for(MultiVersionModData data : ((Map<String,MultiVersionModData>)dataMap).values()) {
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
        if(fixedCoreMods) return;
        Object coremods = getCoreMods(file);
        if(coremods instanceof Collection<?> && !((Collection<?>)coremods).isEmpty()) {
            fixCoreModPackages();
            fixedCoreMods = true;
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
        Object data = CoreAPI.invoke(instance,"getModData",new Class<?>[]{File.class},new File("."));
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
                addScannedMod(langProviderModFile(candidateFile),mods,"LANGPROVIDER");
            }
            LOGGER.info("Adding scanned mod {}",candidateFile);
            addScannedMod(candidateFile,mods,"MOD");
        }
        return Collections.unmodifiableList(mods);
    }
    
    private static void scanReflectively(Object scanner, Path path, ModFileScanData scan) {
        LOGGER.trace("Attempting to scan multiversion jar path {}", path);
        try {
            Methods.invokeDirect(scanner,"fileVisitor",path,scan);
        } catch(Throwable ex) {
            LOGGER.error("Failed to scan {}!",path,ex);
        }
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
        modFileInfoCreator = setModFileInfoCreator(version);
        dynamicModFileClass = dynamicModFileCreator();
        langProviderFileInfo = setLangProviderFileInfo(version);
        coreModEngineClass = "net.minecraftforge.coremod.CoreModEngine";
        coreModExtensions = setCoreModExtensions(version);
        final Class<?> lClass = ClassHelper.findClass("net.minecraftforge.fml.loading."+
                (pathBased ? "LibraryFinder" : "ClasspathLocatorUtils"));
        final String arg2 = pathBased ? "manifest_jar" : MANIFEST;
        urlToPath = (url,manifest) ->
                Methods.invokeStatic(lClass,"findJarPathFor",manifest,arg2,url);
        pathToManifest = setPathToManifest();
        LOGGER.info("{} Forge Locator plugin loaded on {}",actualVersion,caller.getClassLoader());
    }
    
    private static Function<ModFile,IModFileInfo> setLangProviderFileInfo(String version) {
        switch(version) {
            case "16":
            case "16_5": return file -> Constructors.newInstanceOf(ModFileInfo.class,file,
                            wrapConfig(langProviderConfig()));
            case "18":
            case "18_2":
            case "19":
            case "19_2":
            case "19_4": return file -> Constructors.newInstanceOf(ModFileInfo.class,file,
                    wrapConfig(langProviderConfig()),Collections.emptyList());
            case "20":
            case "20_1":
            case "20_4":
            case "20_6":
            case "21":
            case "21_1": return file -> {
                IConfigurable configWrapper = wrapConfig(langProviderConfig());
                Consumer<IModFileInfo> consumer = info ->
                        Methods.invokeDirect(configWrapper,"setFile",info);
                List<?> languageSpecs = Collections.emptyList();
                return Constructors.newInstanceOf(ModFileInfo.class,file,configWrapper,consumer,languageSpecs);
            };
            default: return file -> {
                LOGGER.error("Unknown version for creating a ModFileInfo instance {}",version);
                return null;
            };
        }
    }
    
    static BiFunction<ModFile,Collection<?>,ModFileInfo> setModFileInfoCreator(String version) {
        switch(version) {
            case "16":
            case "16_5":
            case "18":
            case "18_2":
            case "19":
            case "19_2":
            case "19_4": return (file,infos) ->
                    Constructors.newInstanceOf(ModFileInfo.class,file,initFileConfig(infos));
            case "20":
            case "20_1":
            case "20_4":
            case "20_6":
            case "21":
            case "21_1": return (file,infos) -> {
                IConfigurable configWrapper = initFileConfig(infos);
                Consumer<IModFileInfo> configConsumer = info ->
                        Methods.invoke(configWrapper,"setFile",info);
                return Constructors.newInstanceOf(ModFileInfo.class,file,configWrapper,configConsumer);
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
                Optional<Manifest> optional = Methods.invoke(locator, "findManifest", path);
                return optional.orElse(null);
            } catch(Throwable ignoredT) {
                LOGGER.warn("Failed to get manifest from path {}",path);
            }
            return null;
        };
        final Class<?> jarClass = ClassHelper.findClass("cpw.mods.jarhandling.SecureJar");
        if(locatorBased) return (path,ignored) -> {
            try {
                Object jar = Methods.invokeStatic(jarClass,"from", path);
                return Methods.invoke(jar,"getManifest");
            } catch(Throwable ignoredT) {
                LOGGER.warn("Failed to get manifest from path {}",path);
            }
            return null;
        };
        return (path,ignored) -> {
            try {
                Object jar = Methods.invokeStatic(jarClass, "from", path);
                Object dataProvider = Methods.invoke(jar, "moduleDataProvider");
                return Objects.nonNull(dataProvider) ? Methods.invoke(dataProvider, "getManifest") : null;
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
    
    private static IConfigurable wrapConfig(UnmodifiableConfig config) {
        Class<?> wrapperClass = ClassHelper.findClass(NIGHT_CONFIG_WRAPPER);
        return Constructors.newInstanceOf(wrapperClass,config);
    }
    
    private static void writeClassBytes(IModFile file, TILBetterModScan scan, Class<?> visitorClass,
            MultiVersionModData data, String className, byte[] bytes) {
        scan.addWrittenClass(className,data.getInfo(),file,bytes);
        ClassVisitor visitor = Constructors.newInstanceOf(visitorClass);
        ClassReader reader = new ClassReader(bytes);
        reader.accept(visitor,0);
        Methods.invokeDirect(visitor,"buildData",scan.getClasses(),scan.getAnnotations());
    }
    
    private static void writeEntry(IModFile file, TILBetterModScan scan, Class<?> visitorClass,
            Entry<MultiVersionModInfo,MultiVersionModData> entry) {
        MultiVersionModInfo info = entry.getKey();
        String modid = info.getModID();
        MultiVersionModData data = entry.getValue();
        if(Objects.isNull(data)) {
            LOGGER.warn("Skipping mod injection for {} since no data exists",modid);
            return;
        }
        for(Entry<String,byte[]> classBytes : data.writeModClass())
            writeClassBytes(file,scan,visitorClass,data,classBytes.getKey(),classBytes.getValue());
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
        Class<?> mvClass = ClassHelper.findClass(MOD_CLASS_VISITOR);
        for(Entry<MultiVersionModInfo,MultiVersionModData> entry : infoMap.entrySet())
            writeEntry(file,scan,mvClass,entry);
        return onFinishedWritingMods(scan,file);
    }
}