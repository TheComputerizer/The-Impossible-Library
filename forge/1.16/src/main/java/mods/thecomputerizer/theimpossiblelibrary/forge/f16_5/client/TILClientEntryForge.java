package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.TILForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.test.ClientTestsForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TILClientEntryForge extends ClientEntryPoint {

    public TILClientEntryForge() {
        TILForge.init();
        TILRef.logError("CLIENT CONSTRUCT");
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        TILRef.logError("CLIENT SETUP");
        ClientEventsHelper.getEventsAPI().initDefaultListeners();
        ClientTestsForge.initClientTests();
        ClientRegistry.registerKeyBinding(ClientTestsForge.TEST_KEYBIND);
    }
}