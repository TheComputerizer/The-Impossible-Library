package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.TILForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.TILClientEntryForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

@Mod(TILRef.MODID)
public class TILCommonEntryForge extends CommonEntryPoint {

    public TILCommonEntryForge() {
        TILForge.init();
        TILRef.logError("COMMON CONSTRUCT");
        CommonEntryPoint clientEntry = TILForge.FORGE_REF.isClient() ? new TILClientEntryForge() : null;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        if(Objects.nonNull(clientEntry)) bus.addListener(((TILClientEntryForge) clientEntry)::clientSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        TILRef.logError("COMMON SETUP");
        EventHelper.initTILListeners(false,true);
    }
}
