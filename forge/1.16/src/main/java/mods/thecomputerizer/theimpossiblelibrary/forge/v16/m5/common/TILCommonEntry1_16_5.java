package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPointDistributor;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPointDistributor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPointDistributor.INSTANCE;

public class TILCommonEntry1_16_5 extends CommonEntryPoint {

    public TILCommonEntry1_16_5() {
        new CommonEntryPointDistributor();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Side side = CoreAPI.INSTANCE.getSide();
        bus.addListener(this::commonSetup);
        if(side.isServer()) bus.addListener(this::dedicatedServerSetup);
        if(side.isClient()) bus.addListener(this::clientSetup);
        bus.addListener(this::interModEnqueue);
        bus.addListener(this::interModProcess);
        bus.addListener(this::loadComplete);
        bus.addListener(this::serverAboutToStart);
        bus.addListener(this::serverStarting);
        bus.addListener(this::serverStarted);
        bus.addListener(this::serverStopping);
        bus.addListener(this::serverStopped);
        INSTANCE.onConstructed();
        INSTANCE.onPreRegistration();
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        INSTANCE.onCommonSetup();
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public void dedicatedServerSetup(final FMLDedicatedServerSetupEvent event) {
        INSTANCE.onDedicatedServerSetup();
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(final FMLDedicatedServerSetupEvent event) {
        ClientEntryPointDistributor.init();
    }

    public void interModEnqueue(final InterModEnqueueEvent event) {
        INSTANCE.onInterModEnqueue();
    }

    public void interModProcess(final InterModProcessEvent event) {
        INSTANCE.onInterModProcess();
    }

    public void loadComplete(final FMLLoadCompleteEvent event) {
        INSTANCE.onLoadComplete();
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        INSTANCE.onServerAboutToStart();
    }

    public void serverStarting(FMLServerStartingEvent event) {
        INSTANCE.onServerStarting();
    }

    public void serverStarted(FMLServerStartedEvent event) {
        INSTANCE.onServerStarted();
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        INSTANCE.onServerStopping();
    }

    public void serverStopped(FMLServerStoppedEvent event) {
        INSTANCE.onServerStopped();
    }
}
