package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.test.ClientTests1_12_2;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TILClientEntry1_12_2 extends ClientEntryPoint {

    @Override
    public void onPreRegistration() {
        ClientTests1_12_2.initClientTests();
    }

    @Override
    public void onClientSetup() {
        ClientRegistry.registerKeyBinding(ClientTests1_12_2.TEST_KEYBIND);
    }
}