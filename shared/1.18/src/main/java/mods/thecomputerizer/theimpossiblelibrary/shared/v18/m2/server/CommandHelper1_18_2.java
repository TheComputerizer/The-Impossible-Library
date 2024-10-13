package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.commands.arguments.EntityArgument;

public class CommandHelper1_18_2 extends CommandHelperAPI {

    @Override public EntityAPI<?,?> parseEntity(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender,
            String unparsed) throws Exception {
        return WrapperHelper.wrapEntity(EntityArgument.getEntity(sender.unwrap(),"entity"));
    }

    @Override public PlayerAPI<?,?> parsePlayer(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender,
            String unparsed) throws Exception {
        return WrapperHelper.wrapPlayer(EntityArgument.getPlayer(sender.unwrap(),"player"));
    }
}