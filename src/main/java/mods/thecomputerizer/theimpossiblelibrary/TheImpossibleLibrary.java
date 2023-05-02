package mods.thecomputerizer.theimpossiblelibrary;

import mods.thecomputerizer.theimpossiblelibrary.client.render.Renderer;
import mods.thecomputerizer.theimpossiblelibrary.client.test.ClientTest;
import mods.thecomputerizer.theimpossiblelibrary.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.util.file.DataUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class TheImpossibleLibrary {

    //This has to be set before the preInit phase
    public static boolean CLIENT_ONLY = false;
    private static final boolean IS_DEV_ENV = false;

    public TheImpossibleLibrary() {
        //early loading stuff like directory generation
        DataUtil.initGlobal();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        //registration stuff has to happen here
        if(!CLIENT_ONLY) NetworkHandler.init();
        //only register testing stuff in a dev environment
        if(e.getSide()==Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(Renderer.class);
            if(IS_DEV_ENV) preInitClientTestClass(MinecraftForge.EVENT_BUS);
        }
        if(IS_DEV_ENV) preInitCommonTestClass(MinecraftForge.EVENT_BUS);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        //keybindings and other less important stuff can go here
        //only register testing stuff in a dev environment
        if(e.getSide()==Side.CLIENT)
            if(IS_DEV_ENV) initClientTestClass();
        if(IS_DEV_ENV) initCommonTestClass();
    }

    public static void preInitCommonTestClass(EventBus bus) {

    }

    public static void initCommonTestClass() {

    }

    public static void preInitClientTestClass(EventBus bus) {
        bus.register(ClientTest.class);
    }

    public static void initClientTestClass() {
        ClientRegistry.registerKeyBinding(ClientTest.TEST_KEYBIND);
    }
}
