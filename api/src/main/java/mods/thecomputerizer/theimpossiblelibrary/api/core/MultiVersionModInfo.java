package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;

@Getter
public class MultiVersionModInfo {

    public static MultiVersionModInfo get(Class<? extends CommonEntryPoint> clazz, MultiversionMod mod) {
        return new MultiVersionModInfo(clazz,mod.modid(),mod.modName(),mod.modVersion(),mod.client(),mod.server());
    }

    private final Class<? extends CommonEntryPoint> modClass;
    private final String modid;
    private final String name;
    private final String version;
    private final boolean client;
    private final boolean server;

    private MultiVersionModInfo(Class<? extends CommonEntryPoint> clazz, String modid, String name, String version,
                                boolean client, boolean server) {
        this.modClass = clazz;
        this.modid = modid;
        this.name = name;
        this.version = version;
        this.client = client;
        this.server = server;
    }
}
