package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.item;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

import static net.minecraftforge.api.distmarker.Dist.CLIENT;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TILItemBlock1_16_5 extends BlockItem implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    @SuppressWarnings("DataFlowIssue")
    public TILItemBlock1_16_5(Block block, ItemProperties properties) {
        super(block,new Properties().stacksTo(properties.getStackSize()));
        this.properties = properties;
        setRegistryName(block.getRegistryName());
    }
    
    @Override
    @OnlyIn(CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> components, ITooltipFlag flag) {
        getTooltipLines(() -> new ItemStack1_16_5(stack), () -> Objects.nonNull(world) ? new World1_16_5(world) : null)
                .forEach(text -> components.add(((Text1_16_5)text).getComponent()));
    }
    
    @Nonnull @Override public ItemProperties getProperties() {
        return this.properties;
    }
}