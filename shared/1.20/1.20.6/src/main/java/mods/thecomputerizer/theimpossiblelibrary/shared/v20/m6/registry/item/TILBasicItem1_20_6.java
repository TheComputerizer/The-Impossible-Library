package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TILBasicItem1_20_6 extends Item implements ItemHelpers1_20_6 {
    
    protected final ItemProperties properties;
    
    public TILBasicItem1_20_6(ItemProperties properties) {
        super(new Properties().stacksTo(properties.getStackSize()));
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