package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface ItemHelpers1_12_2 extends WithItemProperties {
    
    default void defaultAppendHoverText(ItemStack stack, @Nullable World world, List<String> components) {
        Collection<TextAPI<?>> lines = getTooltipLines(() -> wrapStack(stack),() -> wrapWorld(world));
        lines.forEach(text -> components.add(text.getApplied()));
    }
    
    default @NotNull EnumActionResult defaultUseOn(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
            EnumFacing facing, Supplier<EnumActionResult> superUse) {
        return EventHelper.setActionResult(getUseResult(() -> {
            TILItemUseContext tilCtx = TILItemUseContext.wrap(player,world,pos,null,hand,facing);
            tilCtx.setSuperResult(EventHelper.getActionResult(superUse.get()));
            return tilCtx;
        }));
    }
    
    default ItemStackAPI<?> wrapStack(ItemStack stack) {
        return WrapperHelper.wrapItemStack(stack);
    }
    
    default WorldAPI<?> wrapWorld(World world) {
        return WrapperHelper.wrapWorld(world);
    }
}