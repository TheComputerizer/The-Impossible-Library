package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.*;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_1;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V21_1;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.NEOFORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

@Getter
public abstract class CoreAPI {
    
    /**
     * Bypasses the auto version detection used to instantiate the CoreAPI
     */
    static final String INSTANCE_CLASS = System.getProperty("til.core.instance");
    static final String JAVA_VERSION = System.getProperty("java.version");
    public static Object INSTANCE;
    static String BINARY = "mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI";
    static int javaVersionCache;
    
    public static @Nullable Object findInstance(ClassLoader loader) {
        return Hacks.invokeStatic(Hacks.findClass(BINARY,loader),"getInstance");
    }
    
    @IndirectCallers
    public static String findLoadingClass() {
        return String.valueOf(findInstance(CoreAPI.class.getClassLoader())).split(" ")[0];
    }
    
    public static String findLoadingClass(ModLoader loader, String versionStr) {
        TILRef.logDebug("Parsing version from {}",versionStr);
        GameVersion version = parseVersion(versionStr);
        TILRef.logInfo("Parsed version is {}",version);
        if(Objects.isNull(version))
            throw new RuntimeException("Failed to parse "+loader+" game version from "+versionStr);
        String versionName = version.getName().replace('.','_');
        return version.getPackageName(loader,BASE_PACKAGE)+".core.TILCore"+loader+versionName;
    }
    
    @IndirectCallers
    public static GameVersion gameVersion() {
        return getInstance().getVersion();
    }
    
    public static CoreAPI getInstance() {
        return getInstance(CoreAPI.class.getClassLoader());
    }
    
    public static CoreAPI getInstance(ClassLoader loader) {
        if(Objects.nonNull(INSTANCE)) return (CoreAPI)INSTANCE;
        if(Objects.nonNull(instantiateFromProperty(loader))) return (CoreAPI)INSTANCE;
        TILRef.logDebug("Attempting to get CoreAPI instance that does not exist yet on loader {}",loader);
        if(Objects.nonNull(loader)) {
            Hacks.checkBurningWaveInit();
            syncInstanceClassLoader(loader);
        } else TILRef.logError("Tried to get CoreAPI instance on null ClassLoader??");
        return (CoreAPI)INSTANCE;
    }
    
    public static @Nullable CoreAPI getInstanceDirect() {
        try {
            return (CoreAPI)INSTANCE;
        } catch(ClassCastException ex) {
            TILRef.logError("Tried to return CoreAPI instance from the wrong ClassLoader?",ex);
        }
        return null;
    }
    
    public static ModLoader getInstanceModLoader() {
        CoreAPI instance = getInstance();
        return Objects.nonNull(instance) ? instance.getModLoader() : null;
    }
    
    /**
     * For this to work properly, the extension class must have a public static getInstance method
     */
    public static <T> T getModLoaderExtension(String post, boolean minor) {
        Class<?> extensionClass = getModLoaderClass(post,minor);
        if(Objects.isNull(extensionClass)) {
            TILRef.logError("Cannot get mod loader extension from null class!");
            return null;
        }
        return Hacks.invokeStatic(getModLoaderClass(post,minor),"getInstance");
    }
    
    public static Class<?> getModLoaderClass(String post, boolean minor) {
        String name = injectModLoaderName(BASE_PACKAGE,post);
        if(Objects.isNull(name)) {
            TILRef.logError("Cannot get mod loader class from null class name!");
            return null;
        }
        ModLoader loader = getInstanceModLoader();
        return Objects.nonNull(loader) ?
                Hacks.findClass(gameVersion().withClassExt(name+loader.name,minor)) : null;
    }
    
    public static String getModLoaderName() {
        CoreAPI instance = getInstance();
        if(Objects.isNull(instance)) {
            TILRef.logError("Cannot get mod loader name from null CoreAPI instance!");
            return null;
        }
        return instance.getModLoader().pkg;
    }
    
    public static String injectModLoaderName(String pre, String post) {
        String loaderName = getModLoaderName();
        if(Objects.isNull(loaderName)) {
            TILRef.logError("Cannot inject null mod loader name!");
            return null;
        }
        return pre+"."+loaderName+"."+post;
    }
    
    public static Object instantiateFromProperty(ClassLoader loader) {
        if(Objects.nonNull(INSTANCE)) return INSTANCE;
        TILRef.logDebug("Searching for CoreAPI instance property '-dtil.core.instance'");
        if(Objects.nonNull(INSTANCE_CLASS)) {
            try {
                INSTANCE = Hacks.construct(Hacks.findClass(INSTANCE_CLASS,loader,true));
            } catch(Throwable t) {
                TILRef.logError("Failed to instantiate CoreAPI from property '-dtil.core.instance={}'",INSTANCE,t);
            }
        } else TILRef.logDebug("CoreAPI instance property not found");
        return INSTANCE;
    }
    
    public static boolean isClient() {
        return getInstance().getSide().isClient();
    }
    
    public static boolean isFabric() {
        return getInstance().getModLoader()==FABRIC;
    }
    
    public static boolean isForge() {
        return getInstance().getModLoader()==FORGE;
    }
    
    public static boolean isJava8() {
        return javaVersion()==8;
    }
    
    @IndirectCallers
    public static boolean isJava17() {
        return javaVersion()==17;
    }
    
    @IndirectCallers
    public static boolean isJava17OrLater() {
        return javaVersion()>=17;
    }
    
    @IndirectCallers
    public static boolean isJava21() {
        return javaVersion()==21;
    }
    
    @IndirectCallers
    public static boolean isJava21OrLater() {
        return javaVersion()>=21;
    }
    
    public static boolean isLegacy() {
        return getInstance().getModLoader()==LEGACY;
    }
    
    public static boolean isNamedEnv() {
        if(DEV) return true;
        GameVersion version = CoreAPI.getInstance().getVersion();
        return (isNeoforge() && version!=V20_1) ||
               (isForge() && (version==V20_4 || version==V20_6 || version==V21_1));
    }
    
    public static boolean isNeoforge() {
        return getInstance().getModLoader()==NEOFORGE;
    }
    
    public static boolean isServer() {
        return getInstance().getSide().isServer();
    }
    
    public static boolean isSrgEnv() {
        CoreAPI core = CoreAPI.getInstance();
        switch(core.getModLoader()) {
            case FORGE:
            case LEGACY: return true;
            case NEOFORGE: return core.getVersion()==V20_1;
            default: return false;
        }
    }
    
    public static boolean isV12() {
        return getInstance().getVersion().isV12();
    }
    
    public static boolean isV16() {
        return getInstance().getVersion().isV16();
    }
    
    public static boolean isV18() {
        return getInstance().getVersion().isV18();
    }
    
    public static boolean isV19() {
        return getInstance().getVersion().isV19();
    }
    
    @IndirectCallers
    public static boolean isV19_2() {
        return getInstance().getVersion().isV19_2();
    }
    
    public static boolean isV19_4() {
        return getInstance().getVersion().isV19_4();
    }
    
    public static boolean isV20() {
        return getInstance().getVersion().isV20();
    }
    
    @IndirectCallers
    public static boolean isV20_1() {
        return getInstance().getVersion().isV20_1();
    }
    
    @IndirectCallers
    public static boolean isV20_4() {
        return getInstance().getVersion().isV20_4();
    }
    
    @IndirectCallers
    public static boolean isV20_6() {
        return getInstance().getVersion().isV20_6();
    }
    
    public static boolean isV21() {
        return getInstance().getVersion().isV21();
    }
    
    @IndirectCallers
    public static boolean isV21_1() {
        return getInstance().getVersion().isV21_1();
    }
    
    @IndirectCallers
    public static boolean isVersionAtLeast(@Nullable String versionStr) {
        return getInstance().getVersion().isAtLeast(versionStr);
    }
    
    @IndirectCallers
    public static boolean isVersionAtLeast(GameVersion version) {
        return getInstance().getVersion().isAtLeast(version);
    }
    
    @IndirectCallers
    public static boolean isVersionAtMost(@Nullable String versionStr) {
        return getInstance().getVersion().isAtMost(versionStr);
    }
    
    @IndirectCallers
    public static boolean isVersionAtMost(GameVersion version) {
        return getInstance().getVersion().isAtMost(version);
    }
    
    @IndirectCallers
    public static boolean isVersionGreaterThan(@Nullable String versionStr) {
        return getInstance().getVersion().isGreaterThan(versionStr);
    }
    
    @IndirectCallers
    public static boolean isVersionGreaterThan(GameVersion version) {
        return getInstance().getVersion().isGreaterThan(version);
    }
    
    @IndirectCallers
    public static boolean isVersionLessThan(@Nullable String versionStr) {
        return getInstance().getVersion().isLessThan(versionStr);
    }
    
    @IndirectCallers
    public static boolean isVersionLessThan(GameVersion version) {
        return getInstance().getVersion().isLessThan(version);
    }
    
    /**
     * Should return 8, 17, 21, etc.
     * Java versions before 9 use 1.x numbering, but since nobody is going to use Java 7 or below it is assumed to be 8.
     * If the Java version fails to parse for whatever reason, 17 will be returned.
     */
    public static int javaVersion() {
        if(javaVersionCache>0) return javaVersionCache;
        javaVersionCache = 17;
        TILRef.logInfo("Parsing Java version from {}",JAVA_VERSION);
        if(JAVA_VERSION.startsWith("1.")) javaVersionCache = 8;
        else {
            String majorVersion = JAVA_VERSION.split("\\.")[0].split("_")[0];
            try {
                javaVersionCache = Integer.parseInt(majorVersion);
            } catch(Exception ex) {
                TILRef.logError("Failed to parse Java version from {} (split={})",majorVersion,JAVA_VERSION,ex);
            }
        }
        if(javaVersionCache>24) {
            throw new RuntimeException(NAME+" is not yet compatible with Java "+javaVersionCache+"! Please ensure "+
                                       "that you are using Java 24 or earlier.");
        }
        return javaVersionCache;
    }
    
    /**
     * A bit of a confusing name since this isn't directly tied to the ModLoader$LEGACY enum.
     * Refers to the packet systems used by forge prior to swiching to the (mod-facing) vanilla payload system.
     */
    public static boolean legacyPacketEnv() {
        return CoreAPI.isLegacy() || (CoreAPI.isForge() && isVersionAtMost(V20_1));
    }
    
    @IndirectCallers
    public static Object parseFrom(Object unparsed, ClassLoader loader, boolean java8) {
        try {
            String className = String.valueOf(unparsed).split(" ")[0];
            Class<?> coreClass;
            if(java8) {
                coreClass = Hacks.findClass(className,loader);
                if(Objects.nonNull(coreClass)) return coreClass.newInstance();
                TILRef.logError("Failed to parse CoreAPI class from {} on loader {}",className,loader);
            } else {
                coreClass = ClassHelper.syncDirect(loader,unparsed.getClass());
                if(Objects.nonNull(coreClass)) return coreClass.newInstance();
                TILRef.logError("Failed to parse synced CoreAPI class from {} on loader {}",className,loader);
            }
            return null;
        } catch(NullPointerException | IllegalAccessException | InstantiationException ex) {
            TILRef.logError("Unable to parse CoreAPI instance from {}",unparsed,ex);
        }
        return null;
    }
    
    public static GameVersion parseVersion(String versionStr) {
        return GameVersion.parse(versionStr);
    }
    
    public static void setInstance(Object instance) {
        if(Objects.isNull(INSTANCE)) INSTANCE = instance;
    }
    
    public static void setInstance(Class<?> clazz) {
        if(Objects.nonNull(INSTANCE)) return;
        try {
            INSTANCE = clazz.newInstance();
        } catch(IllegalAccessException | InstantiationException ex) {
            TILRef.logError("Failed to set CoreAPI instance using {}",clazz,ex);
        }
    }
    
    public static void syncInstanceClassLoader(ClassLoader loader) {
        TILRef.logInfo("Trying to sync CoreAPI instance to {} in the context of {}",loader,
                       Thread.currentThread().getContextClassLoader());
        Hacks.loadOrDefineClass(CoreAPI.class,loader);
    }

    protected final GameVersion version;
    protected final ModLoader modLoader;
    protected final Side side;
    protected final Logger logger;
    protected final boolean dev;
    private final Map<MultiVersionModCandidate,Collection<MultiVersionCoreModInfo>> coreInfo;
    private final Set<CoreEntryPoint> coreInstances;
    private final Map<MultiVersionModCandidate,Collection<MultiVersionModInfo>> modInfo;
    private final Set<String> injectedMods;

    protected CoreAPI(GameVersion version, ModLoader loader, Side side) {
        this.version = version;
        this.modLoader = loader;
        this.side = side;
        this.coreInfo = new HashMap<>();
        this.coreInstances = new HashSet<>();
        this.modInfo = new HashMap<>();
        this.injectedMods = new HashSet<>();
        this.dev = DEV;
        this.logger = buildLogger();
        INSTANCE = this;
        this.logger.info("Successfully initialized!");
        if(this.dev) this.logger.debug("Context ClassLoader is {}",Hacks.contextClassLoader());
    }
    
    public void addSources(Set<String> sources) {
        ClassHelper.addSource(sources,CoreAPI.class);
    }
    
    public boolean addURLToClassLoader(ClassLoader loader, String url) {
        try {
            return addURLToClassLoader(loader,URI.create(url).toURL());
        } catch(Exception ex) {
            this.logger.error("Failed to add url from string ({}) to {}",url,loader,ex);
        }
        return false;
    }
    
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public abstract boolean addURLToClassLoader(ClassLoader loader, URL url);
    
    private Logger buildLogger() {
        String logQualifiers = "["+this.version+"] ["+this.modLoader+"] ["+this.side+"]";
        if(this.dev) logQualifiers+=" [DEV]";
        return TILRef.createLogger(NAME+" CoreAPI "+logQualifiers);
    }
    
    public abstract CommonEntryPoint getClientVersionHandler();
    public abstract CommonEntryPoint getCommonVersionHandler();
    public abstract CoreEntryPoint getCoreVersionHandler();
    
    @IndirectCallers
    public <T> T getLaunguageProvider() {
        String name = ".core.TILLanguageProvider"+this.version.name.replace(".","_");
        name = getPackageName(BASE_PACKAGE)+name;
        Class<T> foundClass = Hacks.findClass(name);
        if(Objects.isNull(foundClass)) {
            this.logger.error("Failed to find language provider class! {}",name);
            return null;
        }
        return Hacks.construct(foundClass);
    }
    
    public abstract MultiVersionLoaderAPI getLoader();

    public Map<String,MultiVersionModData> getModData(File root) {
        Map<String,MultiVersionModData> map = new HashMap<>();
        this.logger.info("Parsing data for {} mod candidate(s)",this.modInfo.size());
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> entry : this.modInfo.entrySet())
            for(MultiVersionModInfo info : entry.getValue())
                map.putIfAbsent(info.getModID(),getModData(root,entry.getKey(),info));
        return map;
    }
    
    public MultiVersionModData getModData(File root, MultiVersionModCandidate candidate, MultiVersionModInfo info) {
        return new MultiVersionModData(root,candidate,getModWriter(info));
    }
    
    @IndirectCallers
    public <T> T getModLocator(ClassLoader loader) {
        String name = ".core.MultiVersionModLocator"+this.version.name.replace(".","_");
        name = getPackageName(BASE_PACKAGE)+name;
        Class<T> foundClass = Hacks.findClass(name,loader);
        if(Objects.isNull(foundClass)) {
            this.logger.error("Failed to find mod locator class! {}",name);
            return null;
        }
        return Hacks.construct(foundClass,this);
    }
    
    protected abstract ModWriter getModWriter(MultiVersionModInfo info);
    
    public String getPackageName(String base) {
        return getVersion().getPackageName(getModLoader().getPackageName(base));
    }

    public abstract void initAPI();
    
    public abstract void injectWrittenMod(Class<?> containerClass, String modid);

    public void instantiateCoreMods() {
        this.logger.info("Instantiating {} coremod candidate(s)",this.coreInfo.size());
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionCoreModInfo>> infos : this.coreInfo.entrySet()) {
            String path = infos.getKey().getFile().getName();
            instantiateCoreMods(path,infos.getValue());
        }
    }
    
    @IndirectCallers
    public void instantiateCoreMods(String containerName, Collection<MultiVersionCoreModInfo> infos) {
        if(infos.isEmpty()) {
            this.logger.info("No coremods to instantiate for {}");
            return;
        }
        for(MultiVersionCoreModInfo info : infos) {
            CoreEntryPoint core = info.getInstance();
            if(Objects.nonNull(core)) {
                this.coreInstances.add(core);
                String modid = info.getModid();
                String name = info.getName();
                this.logger.info("Successfully instantiated coremod for {} as `{}`!",modid,name);
            }
        }
    }
    
    public boolean isClientSide() {
        return getSide().isClient();
    }
    
    public boolean isServerSide() {
        return getSide().isServer();
    }
    
    /**
     * This should only be called in NeoForge 1.20.6+ since it has actual custom mod loading support.
     * Returns a collection of MultiVersionModInfo instances.
     */
    public Collection<?> loadCandidate(MultiVersionModCandidate candidate, MultiVersionLoaderAPI loader,
            ClassLoader classLoader) {
        List<MultiVersionCoreModInfo> coreInfo = new ArrayList<>();
        loader.loadCoreMods(candidate,coreInfo,classLoader);
        if(!coreInfo.isEmpty()) {
            this.coreInfo.put(candidate,coreInfo);
            instantiateCoreMods(candidate.getFile().getName(),coreInfo);
        }
        List<MultiVersionModInfo> modInfos = new ArrayList<>();
        loader.loadMods(candidate,modInfos,classLoader);
        if(!modInfos.isEmpty()) this.modInfo.put(candidate,modInfos);
        return modInfos;
    }
    
    public void loadCoreModInfo(ClassLoader classLoader) {
        getLoader().loadCoreMods(this.coreInfo,classLoader);
    }
    
    protected String mapAsBinary(String mapped, boolean asBinary) {
        return asBinary ? mapped.replace('/','.') : mapped.replace('.','/');
    }
    
    public abstract String mapClassName(String unmapped);
    
    public String mapClassName(String unmapped, boolean asBinary) {
        return mapAsBinary(mapClassName(unmapped),asBinary);
    }
    
    public abstract String mapFieldName(String unmappedClass, String unmappedField, String desc);
    
    public String mapFieldName(String unmappedClass, String unmappedField, String desc, boolean asBinary) {
        return mapAsBinary(mapFieldName(unmappedClass,unmappedField,desc),asBinary);
    }
    
    public abstract String mapMethodName(String unmappedClass, String unmappedMethod, String desc);
    
    @IndirectCallers
    public String mapMethodName(String unmappedClass, String unmappedMethod, String desc, boolean asBinary) {
        return mapAsBinary(mapMethodName(unmappedMethod,unmappedMethod,desc),asBinary);
    }

    @SneakyThrows
    public void modConstructed(Package pkg, String modid, String name, String entryType) {
        if(TextHelper.isBlank(modid) || TextHelper.isBlank(name)) {
            this.logger.fatal("Found CommonEntryPoint instance in package `{}` with a blank modid or name! "+
                    "Things may break or crash very soon.",pkg);
            return;
        }
        if(this.injectedMods.contains(modid))
            this.logger.info("Skipping extra entrypoint for {} in package {}",modid,pkg);
        else {
            Class<?> verified = verifyGeneratedClass(pkg,name.replace(" ",""),entryType);
            if(modConstructed(modid,verified)) this.injectedMods.add(modid);
        }
    }

    /**
     * Mod class
     */
    protected boolean modConstructed(String modid, Class<?> clazz) {
        this.logger.info("Successfully constructed mod class for {} as {}",modid,clazz);
        return true;
    }
    
    /**
     * Coremod pass-through stuff. Returns a new map.
     */
    @IndirectCallers
    public Map<?,?> newMap() {
        return new HashMap<>();
    }
    
    /**
     * Returns the input string with the current ModLoader and GameVersion appended
     */
    public String qualify(String str) {
        String qualified = this.modLoader+" "+this.version;
        return Objects.isNull(str) || str.isEmpty() ? qualified : str+" "+qualified;
    }
    
    @Override public String toString() {
        return getClass().getName()+" "+this.version+" "+this.modLoader+" "+this.side;
    }
    
    public abstract String unmapClass(String className);
    
    protected Class<?> verifyGeneratedClass(Package pkg, String name, String entryType) {
        return Hacks.findClass(pkg,name+"Generated"+entryType+"Mod");
    }
    
    public void writeModContainers(ClassLoader loader) {
        getLoader().loadMods(this.modInfo,loader);
    }

    @Getter
    public enum GameVersion {
        V12_2("1.12.2","v12.m2"),
        V16_5("1.16.5","v16.m5"),
        V18_2("1.18.2","v18.m2"),
        V19_2("1.19.2","v19.m2"),
        V19_4("1.19.4","v19.m4"),
        V20_1("1.20.1","v20.m1"),
        V20_4("1.20.4","v20.m4"),
        V20_6("1.20.6","v20.m6"),
        V21_1("1.21.1","v21.m1");
        
        private static final Map<String,GameVersion> BY_NAME;
        
        static {
            Map<String,GameVersion> byName = new HashMap<>();
            for(GameVersion version : values()) byName.put(version.name,version);
            BY_NAME = Collections.unmodifiableMap(byName);
        }
        
        public static GameVersion parse(String versionStr) {
            if(Objects.isNull(versionStr) || versionStr.isEmpty()) {
                TILRef.logError("Unable to parse version from null or empty string");
                return null;
            }
            switch(versionStr.split("\\.").length) {
                case 1: {
                    TILRef.logWarn("Attempting to parse version without any '.' separators from {}",versionStr);
                    switch(versionStr) {
                        case "12": return V12_2;
                        case "16": return V16_5;
                        case "18": return V18_2;
                        case "19": return V19_2;
                        case "20": return V20_1;
                        case "21": return V21_1;
                        default: return null;
                    }
                }
                case 2: return BY_NAME.get("1."+versionStr);
                case 3: return BY_NAME.get(versionStr);
                default: {
                    TILRef.logError("Unable to guess version from string with more than 2 '.' characters");
                    return null;
                }
            }
        }

        private final String name;
        private final String pkg;

        GameVersion(String name, String pkg) {
            this.name = name;
            this.pkg = pkg;
        }
        
        public String getClassExt() {
            return getClassExt(true);
        }
        
        public String getClassExt(boolean includeMinorVersion) {
            String ext = this.name.replace('.','_');
            return includeMinorVersion ? ext : ext.substring(0,ext.length()-2);
        }
        
        public String getPackageName(ModLoader loader, String base) {
            return getPackageName(loader.getPackageName(base));
        }
        
        @IndirectCallers
        public String getPackageName(ModLoader loader, String base, boolean includeMinorVersion) {
            return getPackageName(loader.getPackageName(base),includeMinorVersion);
        }
        
        public String getPackageName(String base) {
            return base+"."+getPackageName();
        }
        
        public String getPackageName(String base, boolean includeMinorVersion) {
            return base+"."+getPackageName(includeMinorVersion);
        }
        
        public String getPackageName() {
            return getPackageName(true);
        }
        
        public String getPackageName(boolean includeMinorVersion) {
            return includeMinorVersion ? this.pkg : this.pkg.substring(0,this.pkg.length()-3);
        }
        
        @IndirectCallers
        public boolean isCompatibleFabric() {
            return isV16() || isV18() || isV19() || isV20() || isV21();
        }
        
        /**
         * Is this an unecessary check? Yes, but at least it probably ensures future compatibility.
         */
        public boolean isCompatibleForge() {
            return isCompatibleLegacyForge() || isCompatibleModernForge();
        }
        
        public boolean isCompatibleLegacyForge() {
            return isV12();
        }
        
        public boolean isCompatibleModernForge() {
            return isV16() || isV18() || isV19() || isV20() || isV21();
        }
        
        @IndirectCallers
        public boolean isCompatibleNeoForge() {
            return isV20() || isV21();
        }
        
        public boolean isV12() {
            return this==V12_2;
        }
        
        public boolean isV16() {
            return this==V16_5;
        }
        
        public boolean isV18() {
            return this==V18_2;
        }
        
        public boolean isV19() {
            return this==V19_2 || this==V19_4;
        }
        
        public boolean isV19_2() {
            return this==V19_2;
        }
        
        public boolean isV19_4() {
            return this==V19_2;
        }
        
        public boolean isV20() {
            return this==V20_1 || this==V20_4 || this==V20_6;
        }
        
        public boolean isV20_1() {
            return this==V20_1;
        }
        
        public boolean isV20_4() {
            return this==V20_4;
        }
        
        public boolean isV20_6() {
            return this==V20_6;
        }
        
        public boolean isV21() {
            return this==V21_1;
        }
        
        public boolean isV21_1() {
            return this==V20_1;
        }
        
        public boolean isAtLeast(@Nullable String versionStr) {
            return isAtLeast(parse(versionStr));
        }
        
        public boolean isAtLeast(@Nullable GameVersion version) {
            if(Objects.isNull(version)) return false;
            switch(version) {
                case V12_2: return isV12() || isV16() || isV18() || isV19() || isV20() || isV21();
                case V16_5: return isV16() || isV18() || isV19() || isV20() || isV21();
                case V18_2: return isV18() || isV19() || isV20() || isV21();
                case V19_2: return isV19() || isV20() || isV21();
                case V19_4: return isV19_4() || isV20() || isV21();
                case V20_1: return isV20() || isV21();
                case V20_4: return isV20_4() || isV20_6() || isV21();
                case V20_6: return isV20_6() || isV21();
                case V21_1: return isV21();
                default: return false;
            }
        }
        
        public boolean isAtMost(@Nullable String versionStr) {
            return isAtMost(parse(versionStr));
        }
        
        public boolean isAtMost(@Nullable GameVersion version) {
            if(Objects.isNull(version)) return false;
            switch(version) {
                case V12_2: return isV12();
                case V16_5: return isV12() || isV16();
                case V18_2: return isV12() || isV16() || isV18();
                case V19_2: return isV12() || isV16() || isV18() || isV19_2();
                case V19_4: return isV12() || isV16() || isV18() || isV19();
                case V20_1: return isV12() || isV16() || isV18() || isV19() || isV20_1();
                case V20_4: return isV12() || isV16() || isV18() || isV19() || isV20_1() || isV20_4();
                case V20_6: return isV12() || isV16() || isV18() || isV19() || isV20();
                case V21_1: return isV12() || isV16() || isV18() || isV19() || isV20() || isV21();
                default: return false;
            }
        }
        
        public boolean isGreaterThan(@Nullable String versionStr) {
            return isGreaterThan(parse(versionStr));
        }
        
        public boolean isGreaterThan(@Nullable GameVersion version) {
            if(Objects.isNull(version)) return false;
            switch(version) {
                case V12_2: return isV16() || isV18() || isV19() || isV20() || isV21();
                case V16_5: return isV18() || isV19() || isV20() || isV21();
                case V18_2: return isV19() || isV20() || isV21();
                case V19_2: return isV19_4() || isV20() || isV21();
                case V19_4: return isV20() || isV21();
                case V20_1: return isV20_4() || isV20_6() || isV21();
                case V20_4: return isV20_6() || isV21();
                case V20_6: return isV21();
                default: return false;
            }
        }
        
        public boolean isLessThan(@Nullable String versionStr) {
            return isLessThan(parse(versionStr));
        }
        
        public boolean isLessThan(@Nullable GameVersion version) {
            if(Objects.isNull(version)) return false;
            switch(version) {
                case V16_5: return isV12();
                case V18_2: return isV12() || isV16();
                case V19_2: return isV12() || isV16() || isV18();
                case V19_4: return isV12() || isV16() || isV18() || isV19_2();
                case V20_1: return isV12() || isV16() || isV18() || isV19();
                case V20_4: return isV12() || isV16() || isV18() || isV19() || isV20_1();
                case V20_6: return isV12() || isV16() || isV18() || isV19() || isV20_1() || isV20_4();
                case V21_1: return isV12() || isV16() || isV18() || isV19() || isV20();
                default: return false;
            }
        }

        @Override public String toString() {
            return this.name;
        }
        
        public String withClassExt(String className) {
            return className+getClassExt();
        }
        
        public String withClassExt(String className, boolean includeMinorVersion) {
            return className+getClassExt(includeMinorVersion);
        }
    }

    public enum ModLoader {
        FABRIC("Fabric"),
        FORGE("Forge"),
        LEGACY("Legacy"),
        NEOFORGE("NeoForge");

        private final String name;
        private final String pkg;

        ModLoader(String name) {
            this.name = name;
            this.pkg = name.toLowerCase();
        }
        
        /**
         * Assume the base package if no package name is supplied
         */
        public String getPackageName() {
            return BASE_PACKAGE+"."+this.pkg;
        }
        
        public String getPackageName(String base) {
            return base+"."+this.pkg;
        }
        
        public boolean isFabric() {
            return this==FABRIC;
        }
        
        /**
         * Legacy or modern Forge but not NeoForge
         */
        public boolean isForge() {
            return isLegacyForge() || isModernForge();
        }
        
        public boolean isLegacyForge() {
            return this==LEGACY;
        }
        
        public boolean isModernForge() {
            return this==FORGE;
        }
        
        public boolean isNeoForge() {
            return this==NEOFORGE;
        }

        @Override public String toString() {
            return this.name;
        }
    }

    @Getter
    public enum Side {
        ALL(true,true,true,true),
        DEDICATED_BOTH(true,true,true,false),
        DEDICATED_CLIENT(true,false,true,false),
        DEDICATED_SERVER(false,true,true,false),
        LOGICAL_BOTH(true,true,false,true),
        LOGICAL_CLIENT(true,false,false,true),
        LOGICAL_SERVER(false,true,false,true);

        private final boolean client;
        private final boolean server;
        private final boolean dedicated;
        private final boolean logical;

        Side(boolean client, boolean server, boolean dedicated, boolean logical) {
            this.client = client;
            this.server = server;
            this.dedicated = dedicated;
            this.logical = logical;
        }

        @Override public String toString() {
            if(this==ALL) return "all";
            String side = this.client && this.server ? "both" : (this.client ? "client" : "server");
            return (this.dedicated ? "dedicated" : "logical")+"_"+side;
        }
    }
}