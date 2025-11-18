package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface ItemHelpers1_20_6 extends WithItemProperties {
    
    default void defaultAppendHoverText(ItemStack stack, List<Component> components) {
        Collection<TextAPI<?>> lines = getTooltipLines(() -> wrapStack(stack),ClientHelper::getWorld);
        lines.forEach(text -> components.add(text.getAsComponent()));
    }
    
    default @NotNull InteractionResult defaultUseOn(UseOnContext ctx, Function<UseOnContext,InteractionResult> superUse) {
        return EventHelper.setActionResult(getUseResult(() -> {
            TILItemUseContext tilCtx = TILItemUseContext.wrap(ctx.getPlayer(),ctx.getLevel(),ctx.getClickedPos(),
                    null,ctx.getHand(),ctx.getClickedFace());
            tilCtx.setSuperResult(EventHelper.getActionResult(superUse.apply(ctx)));
            return tilCtx;
        }));
    }
    
    default ItemStackAPI<?> wrapStack(ItemStack stack) {
        return WrapperHelper.wrapItemStack(stack);
    }
}