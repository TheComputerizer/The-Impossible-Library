package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionCoreModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;

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
    }

    public Iterable<CoreEntryPoint> coreInstances() {
        return this.coreInstances;
    }

    public abstract MultiLoaderAPI getLoader();

    public abstract void initAPI();

    public void instantiateCoreMods() {
        for(MultiVersionCoreModInfo info : this.coreInfo) {
            CoreEntryPoint core = info.getInstance();
            if(Objects.nonNull(core)) this.coreInstances.add(core);
        }
    }

    public void instantiateMods() {
        for(MultiVersionModInfo info : this.modInfo) {
            CommonEntryPoint mod = info.getInstance();
            if(Objects.nonNull(mod)) this.modInstances.add(mod);
        }
    }

    public void loadCoreModInfo(Consumer<URL> sourceConsumer) {
        getLoader().loadCoreMods(this.coreInfo,sourceConsumer);
    }

    public void loadModInfo(Consumer<URL> sourceConsumer) {
        getLoader().loadMods(this.modInfo,sourceConsumer);
    }

    public Iterable<CommonEntryPoint> modInstances() {
        return this.modInstances;
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