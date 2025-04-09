package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.block.Block;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

public class ToolBuilder1_12_2 extends ToolBuilderAPI {
    
    public ToolBuilder1_12_2(@Nullable ItemBuilderAPI builder, ToolType type) {
        super(builder,type);
    }
    
    @Override public ItemAPI<?> build() {
        ItemProperties properties = buildProperties();
        Item item = getItem(properties,this.toolTier.unwrap());
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        if(Objects.nonNull(tab)) tab.addStack(() -> WrapperHelper.wrapItemStack(new ItemStack(item)));
        if(CoreAPI.isClient()) registerTextureProperties(item);
        return WrapperHelper.wrapItem(item);
    }
    
    private Item getItem(ItemProperties properties, ToolMaterial material) {
        switch(this.toolType) {
            case AXE: return new TILItemAxe1_12_2(material,this.damageModifier,this.speedModifier,properties);
            case HOE: return new TILItemHoe1_12_2(material,properties);
            case PICKAXE: return new TILItemPickaxe1_12_2(material,properties);
            case SHOVEL: return new TILItemShovel1_12_2(material,properties);
            case SWORD: return new TILItemSword1_12_2(material,properties);
            default: {
                Set<Block> blocks = new HashSet<>();
                this.effectiveBlocks.forEach(block -> blocks.add(block.unwrap()));
                return new TILCustomTool1_12_2(this.damageModifier,this.speedModifier,material,blocks,properties);
            }
        }
    }
    
    private void registerTextureProperties(Item item) {
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            IItemPropertyGetter getter = (stack,world,entity) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world));
            item.addPropertyOverride(location,getter);
        }
    }
}