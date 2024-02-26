package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.TILForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.TILClientEntryForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

@Mod(TILRef.MODID)
public class TILCommonEntryForge extends CommonEntryPoint {

    private final CommonEntryPoint clientEntry;

    public TILCommonEntryForge() {
        TILForge.init();
        TILRef.logError("COMMON CONSTRUCT");
        this.clientEntry = TILForge.FORGE_REF.isClient() ? new TILClientEntryForge() : null;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        if(Objects.nonNull(this.clientEntry)) bus.addListener(((TILClientEntryForge)this.clientEntry)::clientSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        TILRef.logError("COMMON SETUP");
        if(Objects.isNull(this.clientEntry)) CommonEventsHelper.getEventsAPI().initDefaultListeners();
    }
}
