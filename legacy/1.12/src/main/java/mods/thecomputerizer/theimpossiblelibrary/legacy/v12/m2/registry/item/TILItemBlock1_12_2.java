package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILItemBlock1_12_2 extends ItemBlock implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILItemBlock1_12_2(Block block, ItemProperties properties) {
        super(block);
        this.properties = properties;
        this.setMaxStackSize(properties.getStackSize());
        ResourceLocation name = block.getRegistryName();
        if(Objects.nonNull(name)) setRegistryName(name);
    }
    
    @SideOnly(CLIENT)
    @Override public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack), () -> Objects.nonNull(world) ?
                WrapperHelper.wrapWorld(world) : null).forEach(text -> tooltip.add(text.getApplied()));
    }
    
    @Override public @Nonnull ItemProperties getProperties() {
        return this.properties;
    }
}