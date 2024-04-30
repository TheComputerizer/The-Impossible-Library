package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.custom.RegisterCommands1_12_2;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

public final class TILCommonEntryPoint1_12_2 extends CommonEntryPoint {

    @Override
    public @Nullable ClientEntryPoint delegatedClientEntry() {
        return null;
    }

    @Override
    protected String getModID() {
        return TILRef.MODID;
    }

    @Override
    protected String getModName() {
        return TILRef.NAME;
    }

    @Override
    public void onServerStarting() {
        MinecraftForge.EVENT_BUS.post(new RegisterCommands1_12_2(FMLCommonHandler.instance().getMinecraftServerInstance()));
    }
}