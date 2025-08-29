package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CommandSender1_12_2 extends CommandSenderAPI<ICommandSender> {

    public CommandSender1_12_2(Object sender) {
        super(sender);
    }

    @Override public @Nullable EntityAPI<?,?> getEntity() {
        Entity entity = getIfNotNull(ICommandSender::getCommandSenderEntity);
        return Objects.nonNull(entity) ? new Entity1_12_2(entity) : null;
    }

    @Override public String getName() {
        return getIfNotNull(ICommandSender::getName);
    }

    @Override public WorldAPI<?> getWorld() {
        return getIfNotNull(w -> WrapperHelper.wrapWorld(w.getEntityWorld()));
    }

    @Override public void sendMessage(TextAPI<?> text) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.sendMessage(text.getAsComponent());
    }
}