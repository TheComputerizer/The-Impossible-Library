package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import com.electronwill.nightconfig.core.Config;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.ModFileFactory.ModFileInfoParser;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static net.minecraftforge.forgespi.locating.IModFile.Type.LANGPROVIDER;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Helper methods for common functionalities between all 1.16.5+ versions of Forge mod loading
 */
@SuppressWarnings("LoggingSimilarMessage")
public class ForgeModLoading {
    
    static {
        ClassHelper.checkBurningWaveInit();
    }
    
    static final Logger LOGGER = LoggerFactory.getLogger("NeoForge Mod Loading");
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    static final String MOD_CLASS_VISITOR = "net.minecraftforge.fml.loading.moddiscovery.ModClassVisitor";
    static final String NIGHT_CONFIG_WRAPPER = "net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper";
    static final String SCANNER = "net.minecraftforge.fml.loading.moddiscovery.Scanner";
    static final BiConsumer<TILBetterModScan,Object> AFTER_WRITING_MODS = (scan,language) -> {
        LOGGER.debug("Injecting scan data into the language loader");
        Consumer<ModFileScanData> visitor = Methods.invokeDirect(language, "getFileVisitor");
        visitor.accept(scan);
    };
    static final Function<Object,Object> INFO_GETTER = file -> Methods.invokeDirect(file,"getInfos");
    static Function<Object,String> moduleNameGetter = file -> null;
    static Function<IModFile,Object> getLoaders;
    static Function<ModFile,IModFileInfo> langProviderFileInfo;
    static BiFunction<URL,String,Path> urlToPath;
    static BiFunction<Path,Object,Manifest> pathToManifest;
    static String coreModEngineClass;
    static String[] coreModExtensions;
    static Function<Object[],Object> modFileCreator;
    static boolean fixedCoreMods;
    static boolean pathBased;
    static boolean locatorBased;
    
    @SuppressWarnings("unchecked")
    private static <F> void addScannedMod(Object file, List<F> mods) {
        if(locatorBased) mods.add((F)file);
        else {
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
    
    static void findFiles(MultiVersionLoaderAPI loader, Predicate<Path> filter, File... files) {
        TILRef.logInfo("[{}]: Loading {} mod files",loader.getName(),files.length);
        for(File mod : files) {
            TILRef.logInfo("[{}]: Potentially loading mod file at path {}",loader.getName(),mod.toPath());
            checkPath(loader,mod.toPath(),filter);
        }
    }
    
    public static void findPaths(ClassLoader classLoader, MultiVersionLoaderAPI loader) {
        findPaths(classLoader,loader,null);
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
            TILRef.logError("[{}]: Failed to calculate URLs for paths with {} using {}",loader.getName(),MANIFEST,classLoader,ex);
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
    
    private static Object getCoreMods(Object file) {
        return Methods.invoke(file,"getCoreMods");
    }
    
    public static boolean identifyMods(boolean result, Object file) {
        LOGGER.debug("Identifying mods");
        if(result) {
            Object coremods = getCoreMods(file);
            if(!fixedCoreMods && coremods instanceof Collection<?> && !((Collection<?>)coremods).isEmpty()) {
                fixCoreModPackages();
                fixedCoreMods = true;
            }
        }
        LOGGER.debug("Finished identifying mods");
        return result;
    }
    
    public static Map<MultiVersionModInfo,MultiVersionModData> initFileInfo(String version, Collection<?> infos) {
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = new HashMap<>();
        for(Object info : infos) infoMap.put((MultiVersionModInfo)info,null);
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        LOGGER.debug("Created TILModFileNeoForge1_{} with {} in context {}",version,infos,context);
        return infoMap;
    }
    
    public static <F> void initModLoading(ClassLoader loader, Object locator,
            Map<MultiVersionModCandidate,F> candidateMap) {
        Object core = CoreAPI.getInstance(loader);
        if(Objects.isNull(core))
            throw new RuntimeException("Failed to initialize multiversion mod loader! Cannot find CoreAPI on "+loader);
        findPaths(loader,(MultiVersionLoaderAPI)CoreAPI.invoke(core,"getLoader"));
        loadMods(loader,locator,core,candidateMap);
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
        config.set("mods", Collections.singletonList(mod));
        return config;
    }
    
    public static ModFile langProviderModFile(ModFile reference) {
        final Path path = reference.getFilePath();
        final Object locator = Methods.invoke(reference,locatorBased ? "getLocator" : "getProvider");
        final ModFileInfoParser parser = ForgeModLoading::langFileInfo;
        if(pathBased) {
            ModFile file = Constructors.newInstanceOf(ModFile.class,path,locator,parser);
            //ModFile doesn't have a constructor with the type in 1.16.5, so we need to update the field after creation.
            Fields.setDirect(file,"modFileType",LANGPROVIDER);
            return file;
        }
        final Class<?> jarClass = ClassHelper.findClass("cpw.mods.jarhandling.SecureJar");
        final Object jar = Methods.invokeStatic(jarClass,"from",path);
        return Constructors.newInstanceOf(ModFile.class,jar,locator,parser,"LANGPROVIDER");
    }
    
    @SuppressWarnings("unchecked")
    private static <F> void loadCandidateInfos(Object locator, Map<?,?> infoMap,
            Map<MultiVersionModCandidate,F> candidateMap) {
        if(Objects.isNull(modFileCreator)) {
            LOGGER.error("Cannot load mod candidate info with null modFileCreator function! Was setModFileCreator called?");
            return;
        }
        for(Entry<?,?> entry : infoMap.entrySet()) {
            MultiVersionModCandidate candidate = (MultiVersionModCandidate)entry.getKey();
            Object file = modFileCreator.apply(new Object[]{candidate.getFile().toPath(),locator,entry.getValue()});
            candidateMap.put(candidate,(F)file);
        }
    }
    
    private static <F> void loadMods(ClassLoader loader, Object locator, Object core,
            Map<MultiVersionModCandidate,F> candidateMap) {
        Class<?>[] withLoader = new Class<?>[]{ClassLoader.class};
        CoreAPI.invoke(core,"loadCoreModInfo",withLoader,loader);
        CoreAPI.invoke(core,"instantiateCoreMods");
        CoreAPI.invoke(core,"writeModContainers",withLoader,loader);
        Object infoMap = CoreAPI.invoke(core,"getModInfo");
        loadCandidateInfos(locator,(Map<?,?>)infoMap,candidateMap);
    }
    
    private static TILBetterModScan onFinishedWritingMods(TILBetterModScan scan, IModFile file) {
        Object loader = Objects.nonNull(getLoaders) ? getLoaders.apply(file) : file;
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
    public static void populateMultiversionData(Object infoMapObj, Object dataMap) {
        if(Objects.isNull(infoMapObj)) {
            LOGGER.error("Cannot populate multiversion data with null info map! Was the getter set up correctly?");
            return;
        }
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = (Map<MultiVersionModInfo,MultiVersionModData>)infoMapObj;
        for(MultiVersionModData data : ((Map<String,MultiVersionModData>)dataMap).values()) {
            MultiVersionModInfo info = data.getInfo();
            if(infoMap.containsKey(info)) {
                LOGGER.debug("Populated data for {}",info);
                infoMap.put(info,data);
            }
        }
    }
    
    /**
     * Returns the list of mods
     */
    public static <F> List<F> scanMods(Collection<?> candidates) {
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        LOGGER.debug("Scanning for mods in multiversion jars (context = {})",context);
        List<F> mods = new ArrayList<>();
        LOGGER.debug("Getting CoreAPI instance");
        CoreAPI instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) TILRef.logError("Failed to get CoreAPI instance :(");
        Object data = CoreAPI.invoke(instance,"getModData",new Class<?>[]{File.class},new File("."));
        for(Object candidate : candidates) {
            populateMultiversionData(INFO_GETTER.apply(candidate),data);
            String moduleName = Objects.nonNull(moduleNameGetter) ? moduleNameGetter.apply(candidate) : null;
            if(Objects.nonNull(moduleName) && MODID.equals(moduleName)) {
                Object langProviderLoader = langProviderModFile((ModFile)candidate);
                if(Objects.nonNull(langProviderLoader)) addScannedMod(langProviderLoader,mods);
            }
            addScannedMod(candidate,mods);
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
            case "16_5": return new String[]{"v16.m5"};
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
    
    private static Function<ModFile,IModFileInfo> setLangProviderFileInfo(String version) {
        final Class<?> wrapperClass = ClassHelper.findClass(NIGHT_CONFIG_WRAPPER);
        switch(version) {
            case "16_5": return file -> {
                IConfigurable configWrapper = Constructors.newInstanceOf(wrapperClass,langProviderConfig());
                return Constructors.newInstanceOf(ModFileInfo.class,file,configWrapper);
            };
            case "18_2":
            case "19":
            case "19_2":
            case "19_4": return file -> {
                IConfigurable configWrapper = Constructors.newInstanceOf(wrapperClass,langProviderConfig());
                return Constructors.newInstanceOf(ModFileInfo.class,file,configWrapper,Collections.emptyList());
            };
            case "20":
            case "20_1":
            case "20_4":
            case "20_6":
            case "21":
            case "21_1": return file -> {
                IConfigurable configWrapper = Constructors.newInstanceOf(wrapperClass,langProviderConfig());
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
    
    public static void setFileVersion(Class<?> caller, String version, String actualVersion) {
        String fileVersion = version.startsWith("19") ? "19" : version;
        String pkgExt = fileVersion.contains("_")  ? fileVersion.replace("_",".m") : fileVersion;
        String className = BASE_PACKAGE+".forge.v"+pkgExt+".core.loader.TILModFileForge1_"+fileVersion;
        final Class<?> fileClass = ClassHelper.findClass(className);
        final String jarClassName = "cpw.mods.jarhandling.SecureJar";
        pathBased = "16_5".equals(version);
        locatorBased = pathBased || "18_2".equals(version);
        modFileCreator = args -> {
            Object arg0 = args[0];
            if(!pathBased) {
                Class<?> sjClass = ClassHelper.findClass(jarClassName);
                arg0 = Methods.invokeStatic(sjClass,"from",arg0);
            }
            return Constructors.newInstanceOf(fileClass,arg0,args[1],args[2]);
        };
        langProviderFileInfo = setLangProviderFileInfo(version);
        coreModEngineClass = "net.minecraftforge.coremod.CoreModEngine";
        coreModExtensions = setCoreModExtensions(version);
        if(!pathBased) moduleNameGetter = file -> {
            IModFileInfo fileInfo = Methods.invoke(file,"getModFileInfo");
            return Methods.invoke(fileInfo,"moduleName");
        };
        final Class<?> lClass = ClassHelper.findClass("net.minecraftforge.fml.loading."+
                (pathBased ? "LibraryFinder" : "ClasspathLocatorUtils"));
        final String arg2 = pathBased ? "manifest_jar" : MANIFEST;
        urlToPath = (url,manifest) ->
                Methods.invokeStatic(lClass,"findJarPathFor",manifest,arg2,url);
        pathToManifest = setPathToManifest();
        LOGGER.info("1.{} Forge Locator plugin loaded on {}",actualVersion,caller.getClassLoader());
    }
    
    private static BiFunction<Path,Object,Manifest> setPathToManifest() {
        if(pathBased) return (path,locator) -> {
            Optional<Manifest> optional = Methods.invoke(locator,"findManifest",path);
            return optional.orElse(null);
        };
        final Class<?> jarClass = ClassHelper.findClass("cpw.mods.jarhandling.SecureJar");
        if(locatorBased) return (path,ignored) -> {
            Object jar = Methods.invokeStatic(jarClass,"from",path);
            return Methods.invoke(jar,"getManifest");
        };
        return (path,ignored) -> {
            Object jar = Methods.invokeStatic(jarClass,"from",path);
            Object dataProvider = Methods.invoke(jar,"moduleDataProvider");
            return Objects.nonNull(dataProvider) ? Methods.invoke(dataProvider,"getManifest") : null;
        };
    }
    
    private static void writeClassBytes(IModFile file, TILBetterModScan scan, Class<?> visitorClass,
            MultiVersionModData data, String className, byte[] bytes) {
        scan.addWrittenClass(className,data.getInfo(),file,bytes);
        ClassVisitor visitor = Constructors.newInstanceOf(visitorClass);
        ClassReader reader = new ClassReader(bytes);
        reader.accept(visitor,0);
        Methods.invokeDirect(visitor,"buildData",scan.getClasses(),scan.getAnnotations());
        LOGGER.info("Successfully loaded & scanned mod class {}!",className);
    }
    
    private static void writeEntry(IModFile file, TILBetterModScan scan, Class<?> visitorClass,
            Entry<MultiVersionModInfo,MultiVersionModData> entry) {
        MultiVersionModData data = entry.getValue();
        if(Objects.isNull(data)) {
            LOGGER.warn("Skipping mod injection for {} since no data exists",entry.getKey().getModID());
            return;
        }
        for(Pair<String,byte[]> classBytes : data.writeModClass())
            writeClassBytes(file,scan,visitorClass,data,classBytes.getLeft(),classBytes.getRight());
    }
    
    @SuppressWarnings("unchecked")
    public static TILBetterModScan writeMods(ModFile file) {
        Object infoMapObj = INFO_GETTER.apply(file);
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = (Map<MultiVersionModInfo,MultiVersionModData>)infoMapObj;
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