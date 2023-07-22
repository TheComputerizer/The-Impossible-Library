package mods.thecomputerizer.theimpossiblelibrary;


import mods.thecomputerizer.theimpossiblelibrary.client.render.Renderer;
import mods.thecomputerizer.theimpossiblelibrary.client.test.ClientTest;
import mods.thecomputerizer.theimpossiblelibrary.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.util.file.DataUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MODID)
public class TheImpossibleLibrary {

    public static boolean CLIENT_ONLY = false;
    private static final boolean IS_DEV_ENV = true;

    public TheImpossibleLibrary() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        if (FMLEnvironment.dist.isClient()) {
            MinecraftForge.EVENT_BUS.register(Renderer.class);
            if(IS_DEV_ENV) MinecraftForge.EVENT_BUS.register(ClientTest.class);
        }
        DataUtil.initGlobal();
    }

    private void commonSetup(final FMLClientSetupEvent ev) {
        if(!CLIENT_ONLY) NetworkHandler.init();
    }

    private void clientSetup(final FMLClientSetupEvent ev) {
        if(IS_DEV_ENV) ClientRegistry.registerKeyBinding(ClientTest.TEST_KEYBIND);
    }
}
