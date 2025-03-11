package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.custom.RegisterCommands1_12_2;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public final class TILCommonEntryPoint1_12_2 extends CommonEntryPoint {
    
    private static TILCommonEntryPoint1_12_2 INSTANCE;
    
    public static TILCommonEntryPoint1_12_2 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_12_2();
    }
    
    private TILCommonEntryPoint1_12_2() {
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }

    @Override protected String getModName() {
        return NAME;
    }

    @Override public void onServerStarting() {
        EVENT_BUS.post(new RegisterCommands1_12_2(FMLCommonHandler.instance().getMinecraftServerInstance()));
    }
}