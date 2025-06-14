package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;


import java.util.List;

public class TILItemBlock1_21 extends BlockItem implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILItemBlock1_21(Block block, ItemProperties properties) {
        super(block,new Properties().stacksTo(properties.getStackSize()));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> components,
            TooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack),() -> WrapperHelper.wrapWorld(ctx.level()))
                .forEach(text -> components.add(text.getAsComponent()));
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}