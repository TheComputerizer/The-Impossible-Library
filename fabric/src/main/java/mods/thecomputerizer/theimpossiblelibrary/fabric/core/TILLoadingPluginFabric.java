package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.FabricLoaderImpl;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FABRIC;

public class TILLoadingPluginFabric implements PreLaunchEntrypoint {
    
    static {
        FabricLoaderImpl loader = FabricLoaderImpl.INSTANCE;
        GameVersion version = CoreAPI.parseVersion(loader.getGameProvider().getNormalizedGameVersion().split("-")[0]);
        if(Objects.nonNull(version)) {
            String versionName = version.getName().replace('.','_');
            String className = version.getPackageName(FABRIC,BASE_PACKAGE)+".TILCoreFabric"+versionName;
            ClassHelper.initialize(ClassHelper.findClass(className));
        }
    }
    
    @Override public void onPreLaunch() {
        TILRef.logInfo("Running Fabric loading plugin");
    }
}
