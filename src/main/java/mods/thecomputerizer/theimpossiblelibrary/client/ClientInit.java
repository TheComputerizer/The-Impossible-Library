package mods.thecomputerizer.theimpossiblelibrary.client;

import mods.thecomputerizer.theimpossiblelibrary.client.test.ClientTest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientInit {

    public static void preInit(boolean isDev, boolean isClientOnly) {
        if(isDev) MinecraftForge.EVENT_BUS.register(ClientTest.class);
    }

    public static void init(boolean isDev, boolean isClientOnly) {
        if(isDev) ClientRegistry.registerKeyBinding(ClientTest.TEST_KEYBIND);
    }
}
