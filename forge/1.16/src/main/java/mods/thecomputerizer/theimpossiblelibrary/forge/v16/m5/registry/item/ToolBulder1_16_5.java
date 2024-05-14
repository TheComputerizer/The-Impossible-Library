package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.block.Block;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

public class ToolBulder1_16_5 extends ToolBuilderAPI {
    
    public ToolBulder1_16_5(@Nullable ItemBuilderAPI builder, ToolType type) {
        super(builder,type);
    }
    
    @Override public Item1_16_5 build() {
        Item item = getItem(buildProperties(), (ItemTier)this.toolTier.getTier());
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = (ResourceLocation)property.getKey().getInstance();
            IItemPropertyGetter getter = (stack,world,entity) ->
                    property.getValue().apply(new ItemStack1_16_5(stack),new World1_16_5(world));
            ItemModelsProperties.register(item, location, getter);
        }
        return new Item1_16_5(item);
    }
    
    private Item getItem(ItemProperties properties, ItemTier tier) {
        switch(this.toolType) {
            case AXE: return new TILItemAxe1_16_5(tier,this.damageModifier,this.speedModifier,properties);
            case HOE: return new TILItemHoe1_16_5(tier,(int)this.damageModifier,this.speedModifier,properties);
            case PICKAXE: return new TILItemPickaxe1_16_5(tier,(int)this.damageModifier,this.speedModifier,properties);
            case SHOVEL: return new TILItemShovel1_16_5(tier,this.damageModifier,this.speedModifier,properties);
            case SWORD: return new TILItemSword1_16_5(tier,(int)this.damageModifier,this.speedModifier,properties);
            default: {
                Set<Block> blocks = new HashSet<>();
                this.effectiveBlocks.forEach(block -> blocks.add((Block)block.getBlock()));
                return new TILCustomTool1_16_5(tier,this.damageModifier,this.speedModifier,blocks,properties);
            }
        }
    }
}
