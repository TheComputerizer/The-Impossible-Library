package mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TILItemPickaxe1_20 extends PickaxeItem implements ItemHelpers1_20 {
    
    protected final ItemProperties properties;
    
    @IndirectCallers
    public TILItemPickaxe1_20(Tier tier, int damage, float speed, ItemProperties properties) {
        super(tier,damage,speed,new Properties().stacksTo(properties.getStackSize()));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        defaultAppendHoverText(stack,world,components);
    }
    
    @Override public @NotNull InteractionResult useOn(UseOnContext ctx) {
        return defaultUseOn(ctx,super::useOn);
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}