package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;
import lombok.SneakyThrows;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.*;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.GETSTATIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.INVOKEVIRTUAL;

@Getter
public abstract class CoreAPI {

    public static CoreAPI INSTANCE;
    static String OWNER = "mods/thecomputerizer/theimpossiblelibrary/api/core/CoreAPI";
    static Type LOADER = Type.getType("Lmods/thecomputerizer/theimpossiblelibrary/api/core/loader/MultiVersionLoaderAPI;");

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

    public abstract CommonEntryPoint getClientVersionHandler();
    public abstract CommonEntryPoint getCommonVersionHandler();
    public abstract MultiVersionLoaderAPI getLoader();

    public Map<String,MultiVersionModData> getModData(File root) {
        Map<String,MultiVersionModData> map = new HashMap<>();
        TILRef.logInfo("Parsing data for {} mod candidate(s)",this.modInfo.size());
        for(Entry<MultiVersionModCandidate,Collection<MultiVersionModInfo>> entry : this.modInfo.entrySet())
            for(MultiVersionModInfo info : entry.getValue())
                map.putIfAbsent(info.getModID(),new MultiVersionModData(root,entry.getKey(),info));
        return map;
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
        getLoader().loadCoreMods(this.coreInfo,classLoader);
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

    public void writeModContainers(ClassLoader classLoader) {
        getLoader().loadMods(this.modInfo,classLoader);
    }

    @Getter
    public enum GameVersion {
        V12("1.12"),
        V16("1.16"),
        V18("1.18"),
        V19("1.19"),
        V20("1.20"),
        V21("1.21");

        private final String name;

        GameVersion(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum ModLoader {
        FABRIC("Fabric"),
        FORGE("Forge"),
        LEGACY("Forge"),
        NEOFORGE("NeoForge");

        private final String name;

        ModLoader(String name) {
            this.name = name;
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
            return isClient() ? (isServer() ? "Both" : "Client") : "Server";
        }
    }
}