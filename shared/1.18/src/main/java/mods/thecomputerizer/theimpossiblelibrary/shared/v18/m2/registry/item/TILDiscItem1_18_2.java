package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILDiscItem1_18_2 extends RecordItem implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILDiscItem1_18_2(SoundEvent sound, ItemProperties properties) {
        super(0,() -> sound,new Properties().stacksTo(properties.getStackSize()));
        this.properties = properties;
        setRegistryName((ResourceLocation)properties.getRegistryName().unwrap());
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
    
    @Override public @Nonnull ItemProperties getProperties() {
        return this.properties;
    }
}