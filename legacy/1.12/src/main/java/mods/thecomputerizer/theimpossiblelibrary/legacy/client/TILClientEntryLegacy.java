package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.TILLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.test.ClientTestsLegacy;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
public class TILClientEntryLegacy extends ClientEntryPoint {

    public TILClientEntryLegacy() {
        TILLegacy.init();
        TILRef.logError("CLIENT CONSTRUCT");
    }

    public void preInit(FMLPreInitializationEvent event) {
        TILRef.logError("CLIENT PREINIT");
        EventHelper.initTILListeners(true,true);
        ClientTestsLegacy.initClientTests();
    }

    public void init(FMLInitializationEvent event) {
        TILRef.logError("CLIENT INIT");
        ClientRegistry.registerKeyBinding(ClientTestsLegacy.TEST_KEYBIND);
    }

    public void postInit(FMLPreInitializationEvent event) {
        TILRef.logError("CLIENT POSTINIT");

    }
}