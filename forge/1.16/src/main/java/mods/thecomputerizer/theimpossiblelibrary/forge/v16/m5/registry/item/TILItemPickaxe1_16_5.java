package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.item;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TILItemPickaxe1_16_5 extends PickaxeItem implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILItemPickaxe1_16_5(IItemTier tier, int damage, float speed, Properties properties,
            ItemProperties otherProperties) {
        super(tier,damage,speed,properties);
        this.properties = otherProperties;
        setRegistryName(((ResourceLocation1_16_5)otherProperties.getRegistryName()).getInstance());
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> components, ITooltipFlag flag) {
        getTooltipLines(() -> new ItemStack1_16_5(stack), () -> Objects.nonNull(world) ? new World1_16_5(world) : null)
                .forEach(text -> components.add(((Text1_16_5)text).getComponent()));
    }
    
    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        return Events1_16_5.setActionResult(getUseResult(() -> {
            TILItemUseContext tilCtx = new TILItemUseContext(
                    WrapperHelper.wrapPlayer(ctx.getPlayer()), new World1_16_5(ctx.getLevel()),
                    new BlockPos1_16_5(ctx.getClickedPos()), null, Events1_16_5.getHand(ctx.getHand()),
                    Events1_16_5.getFacing(ctx.getClickedFace()));
            tilCtx.setSuperResult(Events1_16_5.getActionResult(super.useOn(ctx)));
            return tilCtx;
        }));
    }
    
    @Nonnull @Override public ItemProperties getProperties() {
        return this.properties;
    }
}
