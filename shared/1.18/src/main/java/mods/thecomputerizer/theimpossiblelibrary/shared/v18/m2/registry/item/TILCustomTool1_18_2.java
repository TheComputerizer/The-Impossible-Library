package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Set;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILCustomTool1_18_2 extends TieredItem implements WithItemProperties {
    
    private final ItemProperties properties;
    
    public TILCustomTool1_18_2(Tier tier, float damage, float speed, Set<Block> blocks, ItemProperties properties) {
        super(tier,new Properties().stacksTo(properties.getStackSize()));
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