package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPointDistributor;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPointDistributor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPointDistributor.INSTANCE;

@Mod(modid = TILRef.MODID,name = TILRef.NAME,version = TILRef.VERSION)
public class TILCommonEntry1_12_2 {

    public TILCommonEntry1_12_2() {
        new CommonEntryPointDistributor();
        INSTANCE.onConstructed();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        INSTANCE.onPreRegistration();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        INSTANCE.onCommonSetup();
        INSTANCE.onDedicatedServerSetup();
        ClientEntryPointDistributor.init();
    }

    @EventHandler
    public void postInit(FMLPreInitializationEvent event) {
        INSTANCE.onInterModEnqueue();
        INSTANCE.onInterModProcess();
    }

    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        INSTANCE.onServerAboutToStart();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        INSTANCE.onServerStarting();
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        INSTANCE.onServerStarted();
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        INSTANCE.onServerStopping();
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        INSTANCE.onServerStopped();
    }
}
