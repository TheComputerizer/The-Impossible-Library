package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class TILDiscItem1_16_5 extends RecordItem implements WithItemProperties {
    
    static Properties tab(Properties iProperties, ItemProperties properties) {
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        return Objects.nonNull(tab) ? tab.withItemProperties(iProperties) : iProperties;
    }
    
    protected final ItemProperties properties;
    
    public TILDiscItem1_16_5(SoundEvent sound, ItemProperties properties) {
        super(0,sound,tab(new Properties().stacksTo(properties.getStackSize()),properties));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack),() -> WrapperHelper.wrapWorld(world))
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
    
    @Override public @NotNull ItemProperties getProperties() {
        return this.properties;
    }
}