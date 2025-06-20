package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class TILDiscItem1_21 extends Item implements ItemHelpers1_21 {
    
    static Properties optionallyJukeboxPlayable(ItemProperties properties, @Nullable ResourceKey<JukeboxSong> songKey) {
        Properties iProperties = new Properties().stacksTo(properties.getStackSize());
        return Objects.nonNull(songKey) ? iProperties.jukeboxPlayable(songKey) : iProperties;
    }
    
    protected final ItemProperties properties;
    
    @IndirectCallers
    public TILDiscItem1_21(ItemProperties properties, @Nullable ResourceKey<JukeboxSong> songKey) {
        super(optionallyJukeboxPlayable(properties,songKey));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> components,
            TooltipFlag flag) {
        defaultAppendHoverText(stack,components);
    }
    
    @Override public @NotNull InteractionResult useOn(UseOnContext ctx) {
        return defaultUseOn(ctx,super::useOn);
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}