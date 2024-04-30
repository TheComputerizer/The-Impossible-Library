package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import com.mojang.brigadier.context.CommandContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Entity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity.ServerPlayer1_16_5;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;

public class CommandHelper1_16_5 extends CommandHelperAPI { //TODO Arg names?

    @Override
    public EntityAPI<?,?> parseEntity(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception {
        CommandContext<CommandSource> ctx = ((CommandSender1_16_5)sender).getSender();
        return new Entity1_16_5(EntityArgument.getEntity(ctx,"entity"));
    }

    @Override
    public PlayerAPI<?,?> parsePlayer(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception {
        CommandContext<CommandSource> ctx = ((CommandSender1_16_5)sender).getSender();
        return new ServerPlayer1_16_5(EntityArgument.getPlayer(ctx,"player"));
    }
}