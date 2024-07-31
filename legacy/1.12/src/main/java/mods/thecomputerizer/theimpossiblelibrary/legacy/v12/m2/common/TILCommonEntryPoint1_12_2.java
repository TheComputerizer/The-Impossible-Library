package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.custom.RegisterCommands1_12_2;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public final class TILCommonEntryPoint1_12_2 extends CommonEntryPoint {

    @Override
    public @Nullable ClientEntryPoint delegatedClientEntry() {
        return null;
    }

    @Override
    protected String getModID() {
        return MODID;
    }

    @Override
    protected String getModName() {
        return NAME;
    }

    @Override
    public void onServerStarting() {
        EVENT_BUS.post(new RegisterCommands1_12_2(FMLCommonHandler.instance().getMinecraftServerInstance()));
    }
}