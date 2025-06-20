package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_4;

public class TILItemBlock1_19 extends BlockItem implements ItemHelpers1_19 {
    
    static Properties tab(Properties iProperties, ItemProperties properties) {
        return VERSION==V19_4 ? iProperties : tab19_2(iProperties,properties);
    }
    
    static Properties tab19_2(Properties iProperties, ItemProperties properties) {
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        return Objects.nonNull(tab) ? tab.withItemProperties(iProperties) : iProperties;
    }
    
    protected final ItemProperties properties;
    
    @IndirectCallers
    public TILItemBlock1_19(Block block, ItemProperties properties) {
        super(block,tab(new Properties().stacksTo(properties.getStackSize()),properties));
        this.properties = properties;
    }
    
    @Override public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        defaultAppendHoverText(stack,world,components);
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}