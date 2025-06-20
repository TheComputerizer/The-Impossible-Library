package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TILBasicItem1_21 extends Item implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILBasicItem1_21(ItemProperties properties) {
        super(new Properties().stacksTo(properties.getStackSize()));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> components,
            TooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack),ClientHelper::getWorld)
                .forEach(text -> components.add(text.getAsComponent()));
    }
    
    @Override public @NotNull InteractionResult useOn(UseOnContext ctx) {
        return EventHelper.setActionResult(getUseResult(() -> {
            TILItemUseContext tilCtx = TILItemUseContext.wrap(ctx.getPlayer(),ctx.getLevel(),ctx.getClickedPos(),
                    null,ctx.getHand(),ctx.getClickedFace());
            tilCtx.setSuperResult(EventHelper.getActionResult(super.useOn(ctx)));
            return tilCtx;
        }));
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}