package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import com.mojang.brigadier.context.CommandContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.CommandSender1_16_5;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;
import java.util.Objects;

public class CommandSenderForge1_16_5 extends CommandSender1_16_5<CommandContext<CommandSource>> {

    public CommandSenderForge1_16_5(CommandContext<CommandSource> sender) {
        super(sender);
    }

    @Override public @Nullable EntityAPI<?,?> getEntity() {
        return Objects.nonNull(this.wrapped) ? WrapperHelper.wrapEntity(this.wrapped.getSource().getEntity()) : null;
    }

    @Override public String getName() {
        return this.wrapped.getSource().getTextName();
    }

    @Override public WorldAPI<?> getWorld() {
        Entity entity = this.wrapped.getSource().getEntity();
        return Objects.nonNull(entity) ? WrapperHelper.wrapWorld(entity.getCommandSenderWorld()) : null;
    }

    @Override public void sendMessage(TextAPI<?> text) {
        this.wrapped.getSource().sendSuccess(text.getAsComponent(),true);
    }
}