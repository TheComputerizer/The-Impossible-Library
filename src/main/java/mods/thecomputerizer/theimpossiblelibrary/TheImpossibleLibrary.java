package mods.thecomputerizer.theimpossiblelibrary;


import mods.thecomputerizer.theimpossiblelibrary.client.render.Renderer;
import mods.thecomputerizer.theimpossiblelibrary.client.test.ClientTest;
import mods.thecomputerizer.theimpossiblelibrary.util.file.DataUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MODID)
public class TheImpossibleLibrary {

    public TheImpossibleLibrary() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        DataUtil.initGlobal();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.register(Renderer.class);
            initClientTestClass(MinecraftForge.EVENT_BUS);
        }
    }
    private void clientSetup(final FMLClientSetupEvent ev) {
        ClientRegistry.registerKeyBinding(ClientTest.TEST_KEYBIND);
    }

    public static void initClientTestClass(IEventBus bus) {
        bus.register(ClientTest.class);
    }
}
