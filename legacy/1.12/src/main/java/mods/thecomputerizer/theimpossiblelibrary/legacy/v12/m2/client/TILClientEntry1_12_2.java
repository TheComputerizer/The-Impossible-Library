package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.TIL1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.test.ClientTests1_12_2;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
public class TILClientEntry1_12_2 extends ClientEntryPoint {

    public TILClientEntry1_12_2() {
        TIL1_12_2.init();
        TILRef.logError("CLIENT CONSTRUCT");
    }

    public void preInit(FMLPreInitializationEvent event) {
        TILRef.logError("CLIENT PREINIT");
        EventHelper.initTILListeners(true,true);
        ClientTests1_12_2.initClientTests();
    }

    public void init(FMLInitializationEvent event) {
        TILRef.logError("CLIENT INIT");
        ClientRegistry.registerKeyBinding(ClientTests1_12_2.TEST_KEYBIND);
    }

    public void postInit(FMLPreInitializationEvent event) {
        TILRef.logError("CLIENT POSTINIT");

    }
}