package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.TIL1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.TILClientEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.MultiversionModContainer;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.TILCore1_12_2;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

@Mod(modid = TILRef.MODID,name = TILRef.NAME,version = TILRef.VERSION)
public class TILCommonEntry1_12_2 implements CommonEntryPoint {

    private final CommonEntryPoint clientEntry;
    private final List<MultiversionModContainer<?>> containers;

    public TILCommonEntry1_12_2() {
        TIL1_12_2.init();
        TILRef.logError("COMMON CONSTRUCTOR");
        this.containers = new ArrayList<>();
        this.clientEntry = TILCore1_12_2.LEGACY_REF.isClient() ? new TILClientEntry1_12_2() : null;
    }

    private <F extends FMLEvent> void redistributeFMLEvent(F event, BiConsumer<F,MultiversionModContainer<?>> consumer) {
        for(MultiversionModContainer<?> container : this.containers) consumer.accept(event,container);
    }

    @EventHandler
    public void construct(FMLConstructionEvent event) {
        TILRef.logError("COMMON CONSTRUCT");
        TILCore1_12_2.getRegisteredMods(this.containers,false);
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
