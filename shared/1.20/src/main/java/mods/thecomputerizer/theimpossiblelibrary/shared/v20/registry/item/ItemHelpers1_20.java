package mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface ItemHelpers1_20 extends WithItemProperties {
    
    default void defaultAppendHoverText(ItemStack stack, @Nullable Level level, List<Component> components) {
        Collection<TextAPI<?>> lines = getTooltipLines(() -> wrapStack(stack),() -> wrapWorld(level));
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
    
    default WorldAPI<?> wrapWorld(Level level) {
        return WrapperHelper.wrapWorld(level);
    }
}