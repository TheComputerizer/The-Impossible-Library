package mods.thecomputerizer.theimpossiblelibrary;

import mods.thecomputerizer.theimpossiblelibrary.client.ClientInit;
import mods.thecomputerizer.theimpossiblelibrary.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.util.file.DataUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class TheImpossibleLibrary {

    private static final boolean IS_DEV_ENV = false;
    private static boolean CLIENT_ONLY = false;
    private static boolean DEV_LOG = false;

    public TheImpossibleLibrary() {
        DataUtil.initGlobal();
        if(IS_DEV_ENV) DEV_LOG = true;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        if(!CLIENT_ONLY) NetworkHandler.init();
        if(e.getSide()==Side.CLIENT) ClientInit.preInit(IS_DEV_ENV,CLIENT_ONLY);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        if(e.getSide()==Side.CLIENT) ClientInit.preInit(IS_DEV_ENV,CLIENT_ONLY);
    }

    /**
     * This has to be set before the FMLPreInitializationEvent phase!
     */
    public static void enableClientOnly() {
        CLIENT_ONLY = true;
    }

    public static boolean isClientOnly() {
        return CLIENT_ONLY;
    }

    public static void enableDevLog() {
        DEV_LOG = true;
    }

    public static boolean isDevLogging() {
        return DEV_LOG;
    }
}
