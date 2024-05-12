package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.BlockPos1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TILItemAxe1_12_2 extends ItemAxe implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILItemAxe1_12_2(ToolMaterial material, ItemProperties properties) {
        super(material);
        this.properties = properties;
        this.setMaxStackSize(properties.getStackSize());
    }
    
    @Override
    @SideOnly(CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        getTooltipLines(() -> new ItemStack1_12_2(stack), () -> Objects.nonNull(world) ? new World1_12_2(world) : null)
                .forEach(text -> tooltip.add(text.getApplied()));
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        return Events1_12_2.setActionResult(getUseResult(() -> {
            TILItemUseContext ctx = new TILItemUseContext(
                    WrapperHelper.wrapPlayer(player),new World1_12_2(world),new BlockPos1_12_2(pos),null,
                    Events1_12_2.getHand(hand),Events1_12_2.getFacing(facing));
            ctx.setSuperResult(Events1_12_2.getActionResult(super.onItemUse(player,world,pos,hand,facing,hitX,hitY,hitZ)));
            return ctx;
        }));
    }
    
    @Nonnull @Override public ItemProperties getProperties() {
        return this.properties;
    }
}
