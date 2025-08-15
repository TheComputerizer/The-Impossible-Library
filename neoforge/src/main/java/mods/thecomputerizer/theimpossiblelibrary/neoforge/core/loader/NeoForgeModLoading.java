package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import com.electronwill.nightconfig.core.Config;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.SecureJar.ModuleDataProvider;
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
import net.neoforged.fml.loading.ClasspathLocatorUtils;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.fml.loading.moddiscovery.NightConfigWrapper;
import net.neoforged.neoforgespi.language.IConfigurable;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator.ModFileOrException;
import net.neoforged.neoforgespi.locating.IModProvider;
import net.neoforged.neoforgespi.locating.ModFileFactory.ModFileInfoParser;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Helper methods for common functionalities between all 1.20.4+ versions of NeoForge mod loading
 */
@SuppressWarnings("LoggingSimilarMessage")
public class NeoForgeModLoading {
    
    static {
        ClassHelper.checkBurningWaveInit();
    }
    
    static final Logger LOGGER = LoggerFactory.getLogger("NeoForge Mod Loading");
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    static final BiConsumer<TILBetterModScan,Object> AFTER_WRITING_MODS = (scan,language) -> {
        LOGGER.debug("Injecting scan data into the language loader");
        Consumer<ModFileScanData> visitor = Methods.invokeDirect(language,"getFileVisitor");
        visitor.accept(scan);
    };
    static final Function<Object,Object> INFO_GETTER = file -> Methods.invokeDirect(file,"getInfos");
    static final Function<Object,String> MODULE_NAME_GETTER = file -> ((IModFile)file).getModFileInfo().moduleName();
    static final BiFunction<URL,String,Path> urlToPath = (url,manifest) ->
            ClasspathLocatorUtils.findJarPathFor(manifest,manifest,url);
    static final Function<Path,Manifest> pathToManifest = path -> {
        ModuleDataProvider provider = SecureJar.from(path).moduleDataProvider();
        return Objects.nonNull(provider) ? provider.getManifest() : null;
    };
    static String coreModEngineClass;
    static String[] coreModExtensions;
    static Class<?> modClassVisitorClass;
    static Class<?> scannerClass;
    static Function<Object[],Object> modFileCreator;
    static boolean fixedCoreMods;
    static boolean isNew;
    
    @SuppressWarnings("unchecked")
    private static <F> void addScannedMod(Object file, List<F> mods) {
        mods.add((F)new ModFileOrException((IModFile)file,null));
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
        LOGGER.debug("[{}]: Checking URL {} for MANIFEST {}",loader.getName(),url,MANIFEST);
        checkPath(loader,urlToPath.apply(url,MANIFEST),filter);
    }
    
    static void findFiles(MultiVersionLoaderAPI loader, Predicate<Path> filter, File... files) {
        TILRef.logInfo("[{}]: Loading {} mod files", loader.getName(), files.length);
        for(File mod : files) {
            TILRef.logDebug("[{}]: Potentially loading mod file at path {}",loader.getName(),mod.toPath());
            checkPath(loader,mod.toPath(),filter);
        }
    }
    
    public static void findPaths(ClassLoader classLoader, MultiVersionLoaderAPI loader) {
        Predicate<Path> filter = path -> {
            if(Objects.isNull(path)) return false;
            Manifest manifest = pathToManifest.apply(path);
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
    private static void fixCoreModPackages() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?> engineClass = ClassHelper.findClass(coreModEngineClass,loader);
        Set<String> allowed = new HashSet<>(Fields.getStatic(engineClass,"ALLOWED_PACKAGES"));
        allowed.add(BASE_PACKAGE+".api.core");
        allowed.add(BASE_PACKAGE+".neoforge.core");
        for(String extension : coreModExtensions) allowed.add(BASE_PACKAGE+".neoforge."+extension+".core");
        LOGGER.debug("Expanded coremod package whitelist to {}", allowed);
        Fields.setStaticDirect(engineClass,"ALLOWED_PACKAGES",allowed);
    }
    
    private static Object getCoreMods(Object file) {
        return Methods.invoke(file,"getCoreMods");
    }
    
    private static boolean hasCoreModPath(String ... paths) {
        for(String path : paths)
            if(path.contains("coremods.json")) return true;
        return false;
    }
    
    public static boolean identifyMods(boolean result, Object file) {
        LOGGER.debug("Identifying mods");
        if(result) queryCoreMods(file);
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
        file.scanFile(p -> scanReflectively(Constructors.newInstanceOf(scannerClass,file),p,scan));
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
        IConfigurable configWrapper = new NightConfigWrapper(langProviderConfig());
        Consumer<IModFileInfo> consumer = info ->
                Methods.invokeDirect(configWrapper,"setFile",info);
        return new ModFileInfo((ModFile)file,configWrapper,consumer,Collections.emptyList());
    }
    
    public static Config langProviderConfig() {
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
        config.set("mods",Collections.singletonList(mod));
        return config;
    }
    
    public static ModFile langProviderModFile(ModFile reference) {
        final SecureJar jar = SecureJar.from(reference.getFilePath());
        final ModFileInfoParser parser = NeoForgeModLoading::langFileInfo;
        final IModProvider provider = reference.getProvider();
        return new ModFile(jar,provider,parser,"LANGPROVIDER");
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
        if(!isNew) {
            List<?> loaders = Methods.invoke(file,"getLoaders");
            if(loaders.isEmpty()) LOGGER.error("Why are there no language loaders??");
            for(Object loader : loaders) AFTER_WRITING_MODS.accept(scan,loader);
        }
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
    
    @SuppressWarnings("unchecked")
    public static void populateMultiversionData(MultiVersionModCandidate candidate, Object modFile) {
        Object infoMapObj = INFO_GETTER.apply(modFile);
        Map<MultiVersionModInfo,MultiVersionModData> infoMap = (Map<MultiVersionModInfo,MultiVersionModData>)infoMapObj;
        CoreAPI core = candidate.getCore();
        File file = candidate.getFile();
        for(MultiVersionModInfo info : infoMap.keySet()) {
            MultiVersionModData data = core.getModData(file,candidate,info);
            if(Objects.nonNull(data)) {
                infoMap.put(info,data);
                LOGGER.debug("Populated data for {}",info);
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
    public static <F> List<F> scanMods(Collection<?> candidates) {
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        LOGGER.debug("Scanning for mods in multiversion jars (context = {})",context);
        List<F> mods = new ArrayList<>();
        LOGGER.debug("Getting CoreAPI instance");
        CoreAPI instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) LOGGER.error("Failed to get CoreAPI instance :(");
        Object data = CoreAPI.invoke(instance,"getModData",new Class<?>[]{File.class},new File("."));
        for(Object candidate : candidates) {
            populateMultiversionData(INFO_GETTER.apply(candidate),data);
            if(MODID.equals(MODULE_NAME_GETTER.apply(candidate)))
                addScannedMod(langProviderModFile((ModFile)candidate),mods);
            addScannedMod(candidate,mods);
        }
        return mods;
    }
    
    private static void scanReflectively(Object scanner, Path path, ModFileScanData scan) {
        LOGGER.trace("Attempting to scan multiversion jar path {}", path);
        try {
            Methods.invokeDirect(scanner,"fileVisitor",path,scan);
        } catch(Throwable ex) {
            LOGGER.error("Failed to scan {}!",path,ex);
        }
    }
    
    public static void setFileVersion(Class<?> caller, String version, String actualVersion) {
        String pkgExt = version.contains("_")  ? version.replace("_",".m") : version;
        String className = BASE_PACKAGE+".neoforge.v"+pkgExt+".core.loader.TILModFileNeoForge1_"+version;
        final Class<?> fileClass = ClassHelper.findClass(className);
        modFileCreator = args -> {
            SecureJar jar = SecureJar.from((Path)args[0]);
            return Constructors.newInstanceOf(fileClass,jar,args[1],args[2]);
        };
        isNew = version.startsWith("21");
        coreModEngineClass = "net.neoforged.coremod.CoreMod"+(isNew ? "Scripting" : "")+"Engine";
        coreModExtensions = isNew ? new String[]{"v21","v20.m6","v21.m1"} : new String[]{"v20","v20.m4"};
        String pkg = "net.neoforged.fml.loading."+(isNew ? "modscan." : "moddiscovery.");
        modClassVisitorClass = ClassHelper.findClass(pkg+"ModClassVisitor");
        scannerClass = ClassHelper.findClass(pkg+"Scanner");
        LOGGER.info("1.{} NeoForge Locator plugin loaded on {}",actualVersion,caller.getClassLoader());
    }
    
    private static void writeClassBytes(IModFile file, TILBetterModScan scan, MultiVersionModData data,
            String className, byte[] bytes) {
        scan.addWrittenClass(className,data.getInfo(),file,bytes);
        ClassVisitor visitor = Constructors.newInstanceOf(modClassVisitorClass);
        ClassReader reader = new ClassReader(bytes);
        reader.accept(visitor,0);
        Methods.invokeDirect(visitor,"buildData",scan.getClasses(),scan.getAnnotations());
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
        scan.setCore(data.getCandidate().getCore());
        for(Entry<String,byte[]> classBytes : data.writeModClass())
            writeClassBytes(file,scan,data,classBytes.getKey(),classBytes.getValue());
        for(IModInfo mod : file.getModInfos()) {
            if(modid.equals(mod.getModId())) {
                scan.setModClass(mod,info.getModClasspath());
                break;
            }
        }
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
        for(Entry<MultiVersionModInfo,MultiVersionModData> entry : infoMap.entrySet()) writeEntry(file,scan,entry);
        return onFinishedWritingMods(scan,file);
    }
}