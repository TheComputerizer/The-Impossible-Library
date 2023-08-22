package mods.thecomputerizer.theimpossiblelibrary;

import mods.thecomputerizer.theimpossiblelibrary.common.test.CommonEventTest;
import mods.thecomputerizer.theimpossiblelibrary.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.util.file.DataUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class TheImpossibleLibrary implements ModInitializer {

    private static final boolean IS_DEV_ENV = true;
    private static boolean CLIENT_ONLY = false;
    private static boolean DEV_LOG = false;

    @Override
    public void onInitialize() {
        DataUtil.initGlobal();
        if(IS_DEV_ENV) enableDevLog();
        setUpCommonEvents();
    }

    private static void setUpCommonEvents() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            if(!CLIENT_ONLY) NetworkHandler.init();
        });
        if(isDevEnv()) CommonEventTest.init();
    }

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

    public static boolean isDevEnv() {
        return IS_DEV_ENV;
    }
}
