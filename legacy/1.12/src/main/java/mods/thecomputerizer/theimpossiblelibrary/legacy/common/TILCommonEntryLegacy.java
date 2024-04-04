package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.legacy.TILLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.TILClientEntryLegacy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Objects;

@Mod(modid = TILRef.MODID,name = TILRef.NAME,version = TILRef.VERSION)
public class TILCommonEntryLegacy extends CommonEntryPoint {

    private final CommonEntryPoint clientEntry;

    public TILCommonEntryLegacy() {
        TILLegacy.init();
        TILRef.logError("COMMON CONSTRUCT");
        this.clientEntry = TILLegacy.LEGACY_REF.isClient() ? new TILClientEntryLegacy() : null;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TILRef.logError("COMMON PREINIT");
        NetworkHandler.load();
        if(Objects.nonNull(this.clientEntry))
            ((TILClientEntryLegacy)this.clientEntry).preInit(event);
        else EventHelper.getEventsAPI().initDefaultListeners();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        TILRef.logError("COMMON INIT");
        if(Objects.nonNull(this.clientEntry))
            ((TILClientEntryLegacy)this.clientEntry).init(event);
    }

    @EventHandler
    public void postInit(FMLPreInitializationEvent event) {
        TILRef.logError("COMMON POSTINIT");
        if(Objects.nonNull(this.clientEntry))
            ((TILClientEntryLegacy)this.clientEntry).postInit(event);
    }
}
