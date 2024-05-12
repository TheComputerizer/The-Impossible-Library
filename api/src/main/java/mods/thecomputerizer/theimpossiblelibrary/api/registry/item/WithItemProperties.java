package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

public interface WithItemProperties {
    
    @Nonnull ItemProperties getProperties();
    
    default Collection<TextAPI<?>> getTooltipLines(Supplier<ItemStackAPI<?>> stack, Supplier<WorldAPI<?>> world) {
        ItemProperties properties = getProperties();
        return properties.hasTooltip() ? properties.getTooltipLines(stack.get(),world.get()) : Collections.emptyList();
    }
    
    default ActionResult getUseResult(Supplier<TILItemUseContext> ctx) {
        ItemProperties properties = getProperties();
        return properties.hasUseFunc() ? properties.getUseResult(ctx.get()) : PASS;
    }
}