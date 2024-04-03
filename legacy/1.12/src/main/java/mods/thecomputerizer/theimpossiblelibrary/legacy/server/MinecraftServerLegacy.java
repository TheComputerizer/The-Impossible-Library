package mods.thecomputerizer.theimpossiblelibrary.legacy.server;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

@Setter
public class MinecraftServerLegacy implements MinecraftServerAPI<MinecraftServer> {

    @Override
    public MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
}