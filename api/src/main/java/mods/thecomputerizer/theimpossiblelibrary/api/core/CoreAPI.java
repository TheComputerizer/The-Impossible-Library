package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.*;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.NEOFORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.GETSTATIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.INVOKEVIRTUAL;

@SuppressWarnings("unused") @Getter
public abstract class CoreAPI {

    public static CoreAPI INSTANCE;
    static String OWNER = "mods/thecomputerizer/theimpossiblelibrary/api/core/CoreAPI";
    static Type LOADER = Type.getType("Lmods/thecomputerizer/theimpossiblelibrary/api/core/loader/MultiVersionLoaderAPI;");
    
    public static void addClassLoadingURLS(ClassLoader ... loaders) {
        Set<String> urls = new HashSet<>();
        Object instance = findClassLoadingURLS(urls,CoreAPI.class.getClassLoader());
        if(Objects.isNull(instance)) return;
        TILRef.logInfo("Found {} sources to add {}",urls.size(),urls);
        if(!urls.isEmpty()) {
            TILRef.logInfo("Adding the above sources to {}",(Object)loaders);
            Set<ClassLoader> loaderSet = new HashSet<>(Arrays.asList(loaders)); //Remove duplicate loaders
            Method urlHandler = ReflectionHelper.getMethod(instance.getClass(),"addURLToClassLoader",
                                                       ClassLoader.class,URL.class);
            for(String urlString : urls) {
                try {
                    URL url = new URL(urlString);
                    for(ClassLoader loader : loaderSet) ReflectionHelper.invokeMethod(urlHandler,instance,loader,url);
                } catch(MalformedURLException ex) {
                    TILRef.logError("Failed to get URL from source {}",urlString,ex);
                }
            }
        } else TILRef.logWarn("Not adding sources from empty set");
    }
    
    static @Nullable Object findClassLoadingURLS(Set<String> urls, ClassLoader coreLoader) {
        Object coreInstance = findInstance(coreLoader);
        if(Objects.isNull(coreInstance)) {
            TILRef.logError("Cannot find class loading URLs for null instance from {}",coreLoader);
            return null;
        }
        ReflectionHelper.invokeMethod(coreInstance.getClass(),"addSources",coreInstance,new Class<?>[]{
                Set.class},urls);
        return coreInstance;
    }
    
    public static @Nullable Object findInstance(ClassLoader loader) {
        try {
            Class<?> coreClass = loader.loadClass(OWNER.replace('/','.'));
            return ReflectionHelper.invokeStaticMethod(coreClass,"getInstance",new Class<?>[]{});
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Unable to get CoreAPI class from {}",loader,ex);
            return null;
        }
    }
    
    public static String findLoadingClass() {
        return String.valueOf(findInstance(CoreAPI.class.getClassLoader())).split(" ")[0];
    }
    
    public static CoreAPI getInstance(ClassLoader loader) {
        if(Objects.isNull(INSTANCE))
            syncInstanceClassLoader(CoreAPI.class.getClassLoader(),loader);
        return INSTANCE;
    }
    
    public static CoreAPI getInstance() {
        if(Objects.isNull(INSTANCE))
            syncInstanceClassLoader(CoreAPI.class.getClassLoader(),MultiVersionModFinder.class.getClassLoader());
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
    
    public static boolean isLegacy() {
        return getInstance().getModLoader()==LEGACY;
    }
    
    public static boolean isNeoforge() {
        return getInstance().getModLoader()==NEOFORGE;
    }
    
    public static boolean isServer() {
        return getInstance().getSide().isServer();
    }
    
    public static CoreAPI parseFrom(String unparsed, ClassLoader loader) {
        try {
            String className = unparsed.split(" ")[0];
            Method method = ReflectionHelper.getMethod(ClassLoader.class,"findResource",String.class);
            if(Objects.nonNull(method)) {
                String classResource = className.replace('.','/')+".class";
                Object obj = ReflectionHelper.invokeMethod(method,loader,classResource);
            } else TILRef.logError("Why doesn't the findResource method exist??");
            return (CoreAPI)loader.loadClass(className).newInstance();
        } catch(ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            TILRef.logError("Unable to parse CoreAPI instance from {}",unparsed,ex);
        }
        return null;
    }
    
    public static GameVersion parseVersion(String versionStr) {
        String[] splits = versionStr.split("\\.");
        if(splits.length<2 || !splits[0].equals("1")) return null;
        switch(splits[1]) {
            case "12": return GameVersion.V12_2;
            case "16": return GameVersion.V16_5;
            case "18": return GameVersion.V18_2;
            case "19": return splits.length>2 && splits[2].equals("4") ? GameVersion.V19_4 : GameVersion.V19_2;
            case "20": {
                switch(splits.length>2 ? splits[2] : "1") {
                    case "4": return GameVersion.V20_4;
                    case "6": return GameVersion.V20_6;
                    default: return GameVersion.V20_1;
                }
            }
            case "21": return GameVersion.V21_1;
            default: return null;
        }
    }
    
    public static void setInstance(Class<?> clazz) {
        if(Objects.nonNull(INSTANCE)) return;
        try {
            INSTANCE = (CoreAPI)clazz.newInstance();
        } catch(IllegalAccessException | InstantiationException ex) {
            TILRef.logError("Failed to set CoreAPI instance using {}",clazz,ex);
        }
    }
    
    public static void syncInstanceClassLoader(ClassLoader loader, ClassLoader loadFrom) {
        try {
            TILRef.logInfo("Trying to sync CoreAPI instance from {} to {} in the context of {}",loadFrom,loader,Thread.currentThread().getContextClassLoader());
            Class<?> systemClass = loadFrom.loadClass(OWNER.replace('/','.'));
            Object instance = ReflectionHelper.getFieldInstance(systemClass,"INSTANCE");
            INSTANCE = parseFrom(String.valueOf(instance),loader);
            TILRef.logDebug("Synced CoreAPI instance from the system ClassLoader");
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Unable to sync CoreAPI instance from the system ClassLoader",ex);
        }
    }

    protected final GameVersion version;
    protected final ModLoader modLoader;
    protected final Side side;
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
        INSTANCE = this;
        TILDev.logInfo("I am running with `{}` in version `{}` on the `{}` side!",this.modLoader,
                this.version,this.side);
    }
    
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public abstract boolean addURLToClassLoader(ClassLoader loader, URL url);
    public abstract CommonEntryPoint getClientVersionHandler();
    public abstract CommonEntryPoint getCommonVersionHandler();
    public abstract MultiVersionLoaderAPI getLoader();

    public Map<String,MultiVersionModData> getModData(File root) {
        Map<String,MultiVersionModData> map = new HashMap<>();
        TILRef.logInfo("Parsing data for {} mod candidate(s)",this.modInfo.size());
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> entry : this.modInfo.entrySet())
            for(MultiVersionModInfo info : entry.getValue())
                map.putIfAbsent(info.getModID(),new MultiVersionModData(root,entry.getKey(),getModWriter(info)));
        return map;
    }
    
    protected abstract ModWriter getModWriter(MultiVersionModInfo info);
    
    public String getPackageName(String base) {
        return getVersion().getPackageName(getModLoader().getPackageName(base));
    }
    
    public void addSources(Set<String> sources) {
        ClassHelper.addSource(sources,CoreAPI.class);
    }

    public abstract void initAPI();
    
    public void injectGetLoader(MethodVisitor visitor) {
        visitor.visitFieldInsn(GETSTATIC,OWNER,"INSTANCE","L"+OWNER+";");
        visitor.visitMethodInsn(INVOKEVIRTUAL,OWNER,"getLoader",Type.getMethodDescriptor(LOADER),false);
    }
    
    public abstract void injectWrittenMod(Class<?> containerClass, String modid);

    public void instantiateCoreMods() {
        TILRef.logInfo("Instantiating {} coremod candidate(s)",this.coreInfo.size());
        for(Collection<MultiVersionCoreModInfo> infos : this.coreInfo.values()) {
            for(MultiVersionCoreModInfo info : infos) {
                CoreEntryPoint core = info.getInstance();
                if(Objects.nonNull(core)) {
                    this.coreInstances.add(core);
                    TILRef.logInfo("Successfully instantiated coremod `{}`!",info.getName());
                }
            }
        }
    }

    public void loadCoreModInfo(ClassLoader classLoader) {
        loadCoreModInfo(classLoader,true);
    }
    
    public void loadCoreModInfo(ClassLoader classLoader, boolean loadSources) {
        getLoader().loadCoreMods(this.coreInfo,classLoader,loadSources);
    }

    @SneakyThrows
    public void modConstructed(Package pkg, String modid, String name, String entryType) {
        if(StringUtils.isBlank(modid) || StringUtils.isBlank(name)) {
            TILRef.logFatal("Found CommonEntryPoint instance in package `{}` with a blank modid or name! "+
                    "Things may break or crash very soon.",pkg);
            return;
        }
        if(this.injectedMods.contains(modid)) TILRef.logInfo("Skipping extra entrypoint for `{}` in `{}`",modid,pkg);
        else if(modConstructed(modid,ClassHelper.findClassFrom(pkg,name+"Generated"+entryType+"Mod")))
            this.injectedMods.add(modid);
    }

    /**
     * Mod class
     */
    protected abstract boolean modConstructed(String modid, Class<?> clazz);
    
    @Override public String toString() {
        return getClass().getName()+" "+this.version+" "+this.modLoader+" "+this.side;
    }

    public void writeModContainers(ClassLoader classLoader) {
        writeModContainers(classLoader,true);
    }
    
    public void writeModContainers(ClassLoader classLoader, boolean loadSources) {
        getLoader().loadMods(this.modInfo,classLoader,loadSources);
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

        private final String name;
        private final String pkg;

        GameVersion(String name, String pkg) {
            this.name = name;
            this.pkg = pkg;
        }
        
        public String getPackageName(ModLoader loader, String base) {
            return getPackageName(loader.getPackageName(base));
        }
        
        public String getPackageName(String base) {
            return base+"."+this.pkg;
        }
        
        public boolean isCompatibleFabric() {
            return !isV12();
        }
        
        public boolean isCompatibleForge() {
            return isCompatibleLegacyForge() || isCompatibleModernForge();
        }
        
        public boolean isCompatibleLegacyForge() {
            return isV12();
        }
        
        public boolean isCompatibleModernForge() {
            return isV16() || isV18() || isV19() || this==V20_1;
        }
        
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
        
        public boolean isV20() {
            return this==V20_1 || this==V20_4 || this==V20_6;
        }
        
        public boolean isV21() {
            return this==V21_1;
        }

        @Override
        public String toString() {
            return this.name;
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

        @Override
        public String toString() {
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

        @Override
        public String toString() {
            if(this==ALL) return "all";
            String side = this.client && this.server ? "both" : (this.client ? "client" : "server");
            return (this.dedicated ? "dedicated" : "logical")+"_"+side;
        }
    }
}