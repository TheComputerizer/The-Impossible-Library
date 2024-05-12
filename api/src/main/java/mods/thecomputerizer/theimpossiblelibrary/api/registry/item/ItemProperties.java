package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

public class ItemProperties {
    
    @Getter private final CreativeTabAPI creativeTab;
    private final BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc;
    @Getter private final ResourceLocationAPI<?> registryName;
    @Getter private final int stackSize;
    private final Function<TILItemUseContext,ActionResult> useFunc;
    
    public ItemProperties(CreativeTabAPI creativeTab, int stackSize, ResourceLocationAPI<?> registryName,
            @Nullable BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc,
            @Nullable Function<TILItemUseContext,ActionResult> useFunc) {
        this.creativeTab = creativeTab;
        this.descFunc = descFunc;
        this.registryName = registryName;
        this.stackSize = Math.max(1,stackSize);
        this.useFunc = useFunc;
    }
    
    public Collection<TextAPI<?>> getTooltipLines(ItemStackAPI<?> stack, WorldAPI<?> world) {
        return Objects.nonNull(this.descFunc) ? this.descFunc.apply(stack,world) : Collections.emptyList();
    }
    
    public ActionResult getUseResult(TILItemUseContext ctx) {
        return Objects.nonNull(this.useFunc) ? this.useFunc.apply(ctx) : PASS;
    }
    
    public boolean hasTooltip() {
        return Objects.nonNull(this.descFunc);
    }
    
    public boolean hasUseFunc() {
        return Objects.nonNull(this.useFunc);
    }
}