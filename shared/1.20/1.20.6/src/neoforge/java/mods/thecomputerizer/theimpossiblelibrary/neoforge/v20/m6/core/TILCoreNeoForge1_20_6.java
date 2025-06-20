package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.TILCoreNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.ClientNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.TILClientEntryPointNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common.CommonNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common.TILCommonEntryPointNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.core.asm.ModWriterNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.core.loader.MultiVersionLoaderNeoForge1_20_6;
import net.neoforged.fml.loading.targets.CommonLaunchHandler;

import java.util.Objects;

@IndirectCallers
public class TILCoreNeoForge1_20_6 extends TILCoreNeoForge1_20 {
    
    public static final Reference NEOFORGE_REF = TILRef.instance(() -> isClient(Launcher.INSTANCE), "");
    
    static boolean isClient(Launcher launcher) {
        final CommonLaunchHandler launch = (CommonLaunchHandler)findLaunchHandler(launcher.environment());
        return Objects.isNull(launch) || launch.getDist().isClient();
    }
    
    public TILCoreNeoForge1_20_6() {
        super(NEOFORGE_REF,false,MultiVersionLoaderNeoForge1_20_6::new);
    }
    
    @Override public CommonEntryPoint getClientVersionHandler() {
        return TILClientEntryPointNeoForge1_20_6.getInstance();
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointNeoForge1_20_6.getInstance();
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterNeoForge1_20_6(this,info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientNeoForge1_20_6() : new CommonNeoForge1_20_6());
    }
}
