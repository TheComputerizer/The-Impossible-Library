package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;
import java.util.Objects;

public class CommandSender1_12_2 extends CommandSenderAPI<ICommandSender> {

    public CommandSender1_12_2(Object sender) {
        super((ICommandSender)sender);
    }

    @Override public @Nullable EntityAPI<?,?> getEntity() {
        Entity entity = this.wrapped.getCommandSenderEntity();
        return Objects.nonNull(entity) ? new Entity1_12_2(entity) : null;
    }

    @Override public String getName() {
        return this.wrapped.getName();
    }

    @Override public WorldAPI<?> getWorld() {
        return WrapperHelper.wrapWorld(this.wrapped.getEntityWorld());
    }

    @Override public void sendMessage(TextAPI<?> text) {
        this.wrapped.sendMessage(text.getAsComponent());
    }
}