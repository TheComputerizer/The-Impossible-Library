package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
public class TILItemBlock1_12_2 extends ItemBlock implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILItemBlock1_12_2(Block block, ItemProperties properties) {
        super(block);
        this.properties = properties;
        this.setMaxStackSize(properties.getStackSize());
        ResourceLocation1_12_2 name = (ResourceLocation1_12_2)properties.getRegistryName();
        if(Objects.isNull(name)) name = new ResourceLocation1_12_2(block.getRegistryName());
        setRegistryName(name.getInstance());
        setTranslationKey(name.getNamespace()+"."+name.getPath());
    }
    
    @Override
    @SideOnly(CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        getTooltipLines(() -> new ItemStack1_12_2(stack), () -> Objects.nonNull(world) ? new World1_12_2(world) : null)
                .forEach(text -> tooltip.add(text.getApplied()));
    }
    
    @Nonnull @Override public ItemProperties getProperties() {
        return this.properties;
    }
}
