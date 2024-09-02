package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionMod;

@Getter
public class MultiVersionModInfo {

    public static MultiVersionModInfo get(Class<? extends CommonEntryPoint> clazz, MultiVersionMod mod) {
        return new MultiVersionModInfo(clazz,mod.modid(),mod.modName(),mod.modVersion(),mod.modDescription(),
                                       mod.client(),mod.server());
    }

    private final Class<? extends CommonEntryPoint> entryClass;
    private final String modID;
    private final String name;
    private final String version;
    private final String description;
    private final boolean client;
    private final boolean server;
    private final String modClasspath;
    private final String containerClasspath;

    private MultiVersionModInfo(Class<? extends CommonEntryPoint> clazz, String modID, String name, String version,
                                String description, boolean client, boolean server) {
        this.entryClass = clazz;
        this.modID = modID;
        this.name = name;
        this.version = version;
        this.description = description;
        this.client = client;
        this.server = server;
        String baseName = clazz.getPackage().getName()+"."+name.replace(" ","");
        this.modClasspath = baseName+"GeneratedCommonMod";
        this.containerClasspath = baseName+"GeneratedModContainer";
    }
    
    @Override public String toString() {
        return super.toString()+"("+getModID()+")";
    }
}
