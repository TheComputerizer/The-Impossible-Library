package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server;

import com.mojang.brigadier.context.CommandContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CommandSender1_18_2 extends CommandSenderAPI<CommandContext<CommandSourceStack>> {

    public CommandSender1_18_2(Object context) {
        super(context);
    }
    
    @Override public @Nullable EntityAPI<?,?> getEntity() {
        return getIfNotNull(w -> WrapperHelper.wrapEntity(w.getSource().getEntity()));
    }
    
    @Override public String getName() {
        return getIfNotNull(w -> w.getSource().getTextName());
    }
    
    @Override public WorldAPI<?> getWorld() {
        Entity entity = getIfNotNull(w -> w.getSource().getEntity());
        return Objects.nonNull(entity) ? WrapperHelper.wrapWorld(entity.getCommandSenderWorld()) : null;
    }
    
    @Override public void sendMessage(TextAPI<?> text) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.getSource().sendSuccess(text.getAsComponent(),true);
    }
}