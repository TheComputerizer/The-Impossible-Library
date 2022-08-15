package mods.thecomputerizer.theimpossiblelibrary;

import mods.thecomputerizer.theimpossiblelibrary.client.visual.Renderer;
import mods.thecomputerizer.theimpossiblelibrary.test.ClientTest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES)
public class TheImpossibleLibrary {

    public TheImpossibleLibrary() {
        //generate config txt file for early loading stuff;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        //registration stuff has to happen here!
        EventBus bus = MinecraftForge.EVENT_BUS;
        if(e.getSide()==Side.CLIENT) {
            bus.register(Renderer.class);
            bus.register(ClientTest.class);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        //keybindings and other less important stuff can go here
        if(e.getSide()==Side.CLIENT) {
            ClientRegistry.registerKeyBinding(ClientTest.TEST_KEYBIND);
        }
    }

    public static void logInfo(String message) {
        Constants.LOGGER.info(message);
    }

    public static void logWarning(String message, @Nullable Throwable throwable) {
        if(throwable!=null) Constants.LOGGER.warn(message, throwable);
        Constants.LOGGER.warn(message);
    }

    public static void logError(String message, @Nullable Throwable throwable) {
        if(throwable!=null) Constants.LOGGER.error(message, throwable);
        Constants.LOGGER.warn(message);
    }

    public static void logFatal(String message, @Nullable Throwable throwable) {
        if(throwable!=null) Constants.LOGGER.fatal(message, throwable);
        Constants.LOGGER.warn(message);
    }
}
