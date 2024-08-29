package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server;

import com.mojang.brigadier.context.CommandContext;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity.Entity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.Text1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;
import java.util.Objects;

public class CommandSender1_16_5 extends CommandSenderAPI<CommandContext<CommandSource>> {

    public CommandSender1_16_5(CommandContext<CommandSource> sender) {
        super(sender);
    }

    @Override
    public @Nullable Entity1_16_5 getEntity() {
        Entity entity = this.sender.getSource().getEntity();
        return Objects.nonNull(entity) ? new Entity1_16_5(entity) : null;
    }

    @Override
    public String getName() {
        return this.sender.getSource().getTextName();
    }

    @Override
    public MinecraftServer1_16_5 getServer() {
        return new MinecraftServer1_16_5();
    }

    @Override
    public World1_16_5 getWorld() {
        Entity entity = this.sender.getSource().getEntity();
        return Objects.nonNull(entity) ? new World1_16_5(entity.getCommandSenderWorld()) : null;
    }

    @Override
    public void sendMessage(TextAPI<?> text) {
        this.sender.getSource().sendSuccess(((Text1_16_5)text).getComponent(),true);
    }
}
