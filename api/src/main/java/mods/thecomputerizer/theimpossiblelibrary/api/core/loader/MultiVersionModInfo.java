package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import javax.annotation.Nullable;

@Getter
public class MultiVersionModInfo {

    public static final MultiVersionModInfo API_INFO = new MultiVersionModInfo(TILCommonEntryPoint.class,
            TILRef.MODID,TILRef.NAME,TILRef.VERSION,true,true);

    public static MultiVersionModInfo get(Class<? extends CommonEntryPoint> clazz, MultiVersionMod mod) {
        return new MultiVersionModInfo(clazz,mod.modid(),mod.modName(),mod.modVersion(),mod.client(),mod.server());
    }

    private final Class<? extends CommonEntryPoint> entryClass;
    private final String modID;
    private final String name;
    private final String version;
    private final boolean client;
    private final boolean server;
    private final String containerClasspath;

    private MultiVersionModInfo(Class<? extends CommonEntryPoint> clazz, String modID, String name, String version,
                                boolean client, boolean server) {
        this.entryClass = clazz;
        this.modID = modID;
        this.name = name;
        this.version = version;
        this.client = client;
        this.server = server;
        this.containerClasspath = clazz.getPackage().getName()+"."+name.replace(" ","")+"GeneratedModContainer";
    }
}
