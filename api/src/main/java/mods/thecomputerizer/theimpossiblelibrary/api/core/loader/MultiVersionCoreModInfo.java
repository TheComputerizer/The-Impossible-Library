package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionCoreMod;

import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;

@Getter
public class MultiVersionCoreModInfo {
    
    static {
        ClassHelper.checkBurningWaveInit();
    }

    public static MultiVersionCoreModInfo get(Class<? extends CoreEntryPoint> clazz, MultiVersionCoreMod mod) {
        return new MultiVersionCoreModInfo(clazz,mod.modid(),mod.modName(),mod.modVersion(),mod.client(),mod.server());
    }

    private final Class<? extends CoreEntryPoint> entryClass;
    private final String modid;
    private final String name;
    private final String version;
    private final boolean client;
    private final boolean server;

    private MultiVersionCoreModInfo(Class<? extends CoreEntryPoint> clazz, String modid, String name, String version,
                                    boolean client, boolean server) {
        this.entryClass = clazz;
        this.modid = modid;
        this.name = name;
        this.version = version;
        this.client = client;
        this.server = server;
    }

    public CoreEntryPoint getInstance() {
        return Constructors.newInstanceOf(this.entryClass);
    }
    
    @Override public String toString() {
        return this.modid+"-"+this.version;
    }
}