package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map.Entry;
import java.util.function.BiFunction;

public class ItemBlockBuilder1_16_5 extends ItemBlockBuilderAPI {
    
    public ItemBlockBuilder1_16_5(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Item1_16_5 build() {
        BlockItem item = new TILItemBlock1_16_5(((Block1_16_5)this.block.get()).getBlock(),buildProperties());
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = (ResourceLocation)property.getKey().getInstance();
            IItemPropertyGetter getter = (stack,world,entity) ->
                    property.getValue().apply(new ItemStack1_16_5(stack), new World1_16_5(world));
            ItemModelsProperties.register(item, location, getter);
        }
        return new Item1_16_5(item);
    }
}
