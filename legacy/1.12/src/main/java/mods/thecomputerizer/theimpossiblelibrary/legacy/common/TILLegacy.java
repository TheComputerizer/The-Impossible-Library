package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.legacy.TILLegacyRef;
import net.minecraftforge.fml.common.event.*;

public class TILLegacy implements CommonEntryPoint<FMLConstructionEvent,FMLPreInitializationEvent,FMLInitializationEvent,
        FMLPostInitializationEvent,FMLLoadCompleteEvent,FMLServerStartingEvent,FMLServerStartedEvent> {

    static {
        new TILLegacyRef();
    }


    @Override
    public void onModConstruction(FMLConstructionEvent event) {

    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void onInit(FMLInitializationEvent event) {

    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void onLoadComplete(FMLLoadCompleteEvent event) {

    }

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Override
    public void onServerStarted(FMLServerStartedEvent event) {

    }
}
