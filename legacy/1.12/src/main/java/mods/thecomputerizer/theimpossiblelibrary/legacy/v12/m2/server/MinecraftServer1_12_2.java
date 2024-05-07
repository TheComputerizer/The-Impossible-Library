package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.entity.ServerPlayer1_12_2;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MinecraftServer1_12_2 extends MinecraftServerAPI<MinecraftServer> {

    @Override
    public void registerCommand(CommandAPI cmd) {
        ((CommandHandler)getServer().commandManager).registerCommand(new WrappedCommand1_12_2(cmd));
    }

    @Override
    public void executeCommandLiteral(String command) {
        MinecraftServer server = getServer();
        if(Objects.nonNull(server)) server.commandManager.executeCommand(server,command);
    }
    
    @SuppressWarnings({"ConstantValue","UnreachableCode"}) @Nullable @Override
    public PlayerAPI<?,?> getPlayerByUUID(String uuid) {
        EntityPlayerMP player = getServer().getPlayerList().getPlayerByUUID(UUID.fromString(uuid));
        return Objects.nonNull(player) ? new ServerPlayer1_12_2(player) : null;
    }
    
    @Override public List<PlayerAPI<?,?>> getPlayers() {
        List<PlayerAPI<?,?>> players = new ArrayList<>();
        for(EntityPlayerMP player : getServer().getPlayerList().getPlayers())
            players.add(new ServerPlayer1_12_2(player));
        return players;
    }
    
    @Override
    public MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
}