package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILDiscItem1_21 extends Item implements WithItemProperties {
    
    static Properties optionallyJukeboxPlayable(ItemProperties properties, @Nullable ResourceKey<JukeboxSong> songKey) {
        Properties iProperties = new Properties().stacksTo(properties.getStackSize());
        return Objects.nonNull(songKey) ? iProperties.jukeboxPlayable(songKey) : iProperties;
    }
    
    protected final ItemProperties properties;
    
    public TILDiscItem1_21(ItemProperties properties, @Nullable ResourceKey<JukeboxSong> songKey) {
        super(optionallyJukeboxPlayable(properties,songKey));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> components,
            TooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack),() -> WrapperHelper.wrapWorld(ctx.level()))
                .forEach(text -> components.add(text.getAsComponent()));
    }
    
    @Override public InteractionResult useOn(UseOnContext ctx) {
        return EventHelper.setActionResult(getUseResult(() -> {
            TILItemUseContext tilCtx = TILItemUseContext.wrap(ctx.getPlayer(),ctx.getLevel(),ctx.getClickedPos(),
                    null,ctx.getHand(),ctx.getClickedFace());
            tilCtx.setSuperResult(EventHelper.getActionResult(super.useOn(ctx)));
            return tilCtx;
        }));
    }
    
    @Override public @Nonnull ItemProperties getProperties() {
        return this.properties;
    }
}