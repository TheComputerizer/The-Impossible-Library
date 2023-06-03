package mods.thecomputerizer.theimpossiblelibrary;

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
    private static final boolean IS_DEV_ENV = true;

    public TheImpossibleLibrary() {
        //early loading stuff like directory generation
        DataUtil.initGlobal();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        if(!CLIENT_ONLY) NetworkHandler.init();
        //only register testing stuff in a dev environment
        if(IS_DEV_ENV) {
            if(e.getSide()==Side.CLIENT)
                preInitClientTestClass(MinecraftForge.EVENT_BUS);
            preInitCommonTestClass(MinecraftForge.EVENT_BUS);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        //only register testing stuff in a dev environment
        if(IS_DEV_ENV) {
            if(e.getSide()==Side.CLIENT) initClientTestClass();
            initCommonTestClass();
        }
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
