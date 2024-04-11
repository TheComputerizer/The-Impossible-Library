package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.TIL1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.TILClientEntry1_12_2;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Objects;

@Mod(modid = TILRef.MODID,name = TILRef.NAME,version = TILRef.VERSION)
public class TILCommonEntry1_12_2 extends CommonEntryPoint {

    private final CommonEntryPoint clientEntry;

    public TILCommonEntry1_12_2() {
        TIL1_12_2.init();
        TILRef.logError("COMMON CONSTRUCT");
        this.clientEntry = TIL1_12_2.LEGACY_REF.isClient() ? new TILClientEntry1_12_2() : null;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TILRef.logError("COMMON PREINIT");
        NetworkHandler.load();
        if(Objects.nonNull(this.clientEntry)) ((TILClientEntry1_12_2)this.clientEntry).preInit(event);
        else EventHelper.initTILListeners(false,true);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        TILRef.logError("COMMON INIT");
        if(Objects.nonNull(this.clientEntry))
            ((TILClientEntry1_12_2)this.clientEntry).init(event);
    }

    @EventHandler
    public void postInit(FMLPreInitializationEvent event) {
        TILRef.logError("COMMON POSTINIT");
        if(Objects.nonNull(this.clientEntry))
            ((TILClientEntry1_12_2)this.clientEntry).postInit(event);
    }
}
