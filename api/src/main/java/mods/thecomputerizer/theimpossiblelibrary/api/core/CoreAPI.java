package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionCoreModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModWriter;

import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public abstract class CoreAPI {

    public static CoreAPI INSTANCE;

    protected final GameVersion version;
    protected final ModLoader modLoader;
    protected final Side side;
    private final Set<MultiVersionCoreModInfo> coreInfo;
    private final Set<CoreEntryPoint> coreInstances;
    private final Set<MultiVersionModInfo> modInfo;
    private final Set<CommonEntryPoint> modInstances;

    protected CoreAPI(GameVersion version, ModLoader loader, Side side) {
        this.version = version;
        this.modLoader = loader;
        this.side = side;
        this.coreInfo = new HashSet<>();
        this.coreInstances = new HashSet<>();
        this.modInfo = new HashSet<>();
        this.modInstances = new HashSet<>();
        INSTANCE = this;
        TILRef.logInfo("Instantiated Core API");
    }

    public abstract MultiVersionLoaderAPI getLoader();
    public abstract CommonEntryPoint getClientVersionHandler();
    public abstract CommonEntryPoint getCommonVersionHandler();
    public abstract void initAPI();

    public void instantiateCoreMods() {
        TILRef.logInfo("Instantiating {} coremods",this.coreInfo.size());
        for(MultiVersionCoreModInfo info : this.coreInfo) {
            CoreEntryPoint core = info.getInstance();
            if(Objects.nonNull(core)) {
                this.coreInstances.add(core);
                TILRef.logInfo("Successfully instantiated coremod `{}`",info.getName());
            }
        }
    }

    public void writeMods(ClassLoader loader, int javaVer) {
        this.modInfo.add(MultiVersionModInfo.API_INFO);
        TILRef.logInfo("Writing {} mods",this.modInfo.size());
        for(MultiVersionModInfo info : this.modInfo) MultiVersionModWriter.writeMod(loader,javaVer,this.modLoader,info);
    }

    public void loadCoreModInfo(Consumer<URL> sourceConsumer) {
        getLoader().loadCoreMods(this.coreInfo,sourceConsumer);
    }

    public enum GameVersion { V12, V16, V18, V19, V20, V21 }
    public enum ModLoader { FABRIC, FORGE, LEGACY, NEOFORGE }

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
    }
}