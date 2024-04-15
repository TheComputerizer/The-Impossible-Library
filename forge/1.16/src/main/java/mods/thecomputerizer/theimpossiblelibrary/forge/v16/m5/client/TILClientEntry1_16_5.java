package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.TIL1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.test.ClientTests1_16_5;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TILClientEntry1_16_5 implements ClientEntryPoint {

    public TILClientEntry1_16_5() {
        TIL1_16_5.init(CoreAPI.getInstance());
        TILRef.logError("CLIENT CONSTRUCT");
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        TILRef.logError("CLIENT SETUP");
        EventHelper.initTILListeners(true,true);
        ClientTests1_16_5.initClientTests();
        ClientRegistry.registerKeyBinding(ClientTests1_16_5.TEST_KEYBIND);
    }
}