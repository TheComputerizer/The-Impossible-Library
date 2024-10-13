package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILItemBlock1_18_2 extends BlockItem implements WithItemProperties {
    
    protected final ItemProperties properties;
    
    public TILItemBlock1_18_2(Block block, ItemProperties properties) {
        super(block,new Properties().stacksTo(properties.getStackSize()));
        this.properties = properties;
        ResourceLocation name = block.getRegistryName();
        if(Objects.nonNull(name)) setRegistryName(name);
    }
    
    @Override public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack),() -> WrapperHelper.wrapWorld(world))
                .forEach(text -> components.add(text.getAsComponent()));
    }
    
    @Override public @Nonnull ItemProperties getProperties() {
        return this.properties;
    }
}