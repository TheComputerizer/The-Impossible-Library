package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.Text1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;

public class CommandSender1_12_2 extends CommandSenderAPI<ICommandSender> {

    public CommandSender1_12_2(ICommandSender sender) {
        super(sender);
    }

    @Override
    public @Nullable EntityAPI<?,?> getEntity() {
        Entity entity = this.sender.getCommandSenderEntity();
        return Objects.nonNull(entity) ? new Entity1_12_2(entity) : null;
    }

    @Override
    public String getName() {
        return this.sender.getName();
    }

    @Override
    public @Nullable MinecraftServerAPI<MinecraftServer> getServer() {
        MinecraftServer server = this.sender.getServer();
        return Objects.nonNull(server) ? new MinecraftServer1_12_2() : null;
    }

    @SuppressWarnings({"ConstantValue","UnreachableCode"}) //Not sure why it's saying the world is always null?
    @Override
    public WorldAPI<World> getWorld() {
        World world = this.sender.getEntityWorld();
        return Objects.nonNull(world) ? new World1_12_2(world) : null;
    }

    @Override
    public void sendMessage(TextAPI<?> text) {
        this.sender.sendMessage(((Text1_12_2)text).getComponent());
    }
}
