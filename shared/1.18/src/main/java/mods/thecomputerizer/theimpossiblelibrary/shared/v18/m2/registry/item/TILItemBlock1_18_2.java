package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
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
    
    static Properties tab(Properties iProperties, ItemProperties properties) {
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        return Objects.nonNull(tab) ? iProperties.tab(tab.unwrap()) : iProperties;
    }
    
    protected final ItemProperties properties;
    
    public TILItemBlock1_18_2(Block block, ItemProperties properties) {
        super(block,tab(new Properties().stacksTo(properties.getStackSize()),properties));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack),() -> WrapperHelper.wrapWorld(world))
                .forEach(text -> components.add(text.getAsComponent()));
    }
    
    @Override public @Nonnull ItemProperties getProperties() {
        return this.properties;
    }
}