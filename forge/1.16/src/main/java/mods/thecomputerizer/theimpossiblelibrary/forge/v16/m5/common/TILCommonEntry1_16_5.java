package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.TIL1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.TILClientEntry1_16_5;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.TILCore1_16_5.FORGE_REF;

@Mod(TILRef.MODID)
public class TILCommonEntry1_16_5 implements CommonEntryPoint {

    public TILCommonEntry1_16_5() {
        TIL1_16_5.init(CoreAPI.getInstance());
        TILRef.logError("COMMON CONSTRUCT");
        CommonEntryPoint clientEntry = FORGE_REF.isClient() ? new TILClientEntry1_16_5() : null;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        if(Objects.nonNull(clientEntry)) bus.addListener(((TILClientEntry1_16_5) clientEntry)::clientSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        TILRef.logError("COMMON SETUP");
        EventHelper.initTILListeners(false,true);
    }
}
