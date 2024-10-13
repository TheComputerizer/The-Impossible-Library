package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server;

import com.mojang.brigadier.context.CommandContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;
import java.util.Objects;

public class CommandSender1_18_2 extends CommandSenderAPI<CommandContext<CommandSourceStack>> {

    @SuppressWarnings("unchecked")
    public CommandSender1_18_2(Object context) {
        super((CommandContext<CommandSourceStack>)context);
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