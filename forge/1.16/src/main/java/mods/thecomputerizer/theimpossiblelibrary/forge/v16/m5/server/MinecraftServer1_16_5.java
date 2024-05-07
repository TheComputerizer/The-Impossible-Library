package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity.ServerPlayer1_16_5;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MinecraftServer1_16_5 extends MinecraftServerAPI<MinecraftServer> {

    @Override
    public void registerCommand(CommandAPI cmd) {}

    @Override
    public void executeCommandLiteral(String command) {
        MinecraftServer server = getServer();
        if(Objects.nonNull(server)) server.getCommands().performCommand(server.createCommandSourceStack(),command);
    }
    
    @Nullable @Override
    public PlayerAPI<?,?> getPlayerByUUID(String uuid) {
        ServerPlayerEntity player = getServer().getPlayerList().getPlayer(UUID.fromString(uuid));
        return Objects.nonNull(player) ? new ServerPlayer1_16_5(player) : null;
    }
    
    @Override public List<PlayerAPI<?,?>> getPlayers() {
        List<PlayerAPI<?,?>> players = new ArrayList<>();
        for(ServerPlayerEntity player : getServer().getPlayerList().getPlayers())
            players.add(new ServerPlayer1_16_5(player));
        return players;
    }
    
    @Override
    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
