package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.TILCoreNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client.ClientNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client.TILClientEntryPointNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.CommonNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.TILCommonEntryPointNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.core.loader.MultiVersionLoaderNeoForge1_20_4;
import net.neoforged.fml.loading.targets.CommonLaunchHandler;

import java.util.Objects;

@IndirectCallers
public class TILCoreNeoForge1_20_4 extends TILCoreNeoForge1_20 {
    
    public static final Reference NEOFORGE_REF = TILRef.instance(() -> isClient(Launcher.INSTANCE), "");
    
    static boolean isClient(Launcher launcher) {
        final CommonLaunchHandler launch = (CommonLaunchHandler)findLaunchHandler(launcher.environment());
        return Objects.isNull(launch) || launch.getDist().isClient();
    }
    
    public TILCoreNeoForge1_20_4() {
        super(NEOFORGE_REF,true,MultiVersionLoaderNeoForge1_20_4::new);
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return TILClientEntryPointNeoForge1_20_4.getInstance();
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointNeoForge1_20_4.getInstance();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientNeoForge1_20_4() : new CommonNeoForge1_20_4());
    }
}