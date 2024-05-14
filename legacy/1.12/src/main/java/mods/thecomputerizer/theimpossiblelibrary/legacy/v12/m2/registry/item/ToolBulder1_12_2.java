package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.block.Block;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

public class ToolBulder1_12_2 extends ToolBuilderAPI {
    
    public ToolBulder1_12_2(@Nullable ItemBuilderAPI builder, ToolType type) {
        super(builder,type);
    }
    
    @Override public Item1_12_2 build() {
        Item item = getItem(buildProperties(),(ToolMaterial)this.toolTier.getTier());
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = (ResourceLocation)property.getKey().getInstance();
            IItemPropertyGetter getter = (stack,world,entity) ->
                    property.getValue().apply(new ItemStack1_12_2(stack), new World1_12_2(world));
            item.addPropertyOverride(location,getter);
        }
        return new Item1_12_2(item);
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
                this.effectiveBlocks.forEach(block -> blocks.add((Block)block.getBlock()));
                return new TILCustomTool1_12_2(this.damageModifier,this.speedModifier,material,blocks,properties);
            }
        }
    }
}
