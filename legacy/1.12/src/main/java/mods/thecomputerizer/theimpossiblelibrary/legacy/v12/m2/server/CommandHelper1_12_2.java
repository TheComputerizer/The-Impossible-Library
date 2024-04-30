package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.entity.ServerPlayer1_12_2;
import net.minecraft.command.CommandBase;

public class CommandHelper1_12_2 extends CommandHelperAPI {

    @Override
    public EntityAPI<?,?> parseEntity(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception {
        return new Entity1_12_2(CommandBase.getEntity(((MinecraftServer1_12_2)server).getServer(),
                ((CommandSender1_12_2)sender).getSender(),unparsed));
    }

    @Override
    public PlayerAPI<?,?> parsePlayer(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception {
        return new ServerPlayer1_12_2(CommandBase.getPlayer(((MinecraftServer1_12_2)server).getServer(),
                ((CommandSender1_12_2)sender).getSender(),unparsed));
    }
}