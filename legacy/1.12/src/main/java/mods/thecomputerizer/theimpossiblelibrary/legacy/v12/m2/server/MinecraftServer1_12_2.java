package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

@Setter
public class MinecraftServer1_12_2 implements MinecraftServerAPI<MinecraftServer> {

    @Override
    public MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
}