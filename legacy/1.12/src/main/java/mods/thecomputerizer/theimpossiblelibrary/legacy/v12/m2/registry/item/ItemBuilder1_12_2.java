package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map.Entry;
import java.util.function.BiFunction;

public class ItemBuilder1_12_2 extends ItemBuilderAPI {
    
    public ItemBuilder1_12_2(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Item1_12_2 build() {
        Item item = new TILBasicItem1_12_2(buildProperties());
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = (ResourceLocation)property.getKey().getInstance();
            IItemPropertyGetter getter = (stack,world,entity) ->
                    property.getValue().apply(new ItemStack1_12_2(stack), new World1_12_2(world));
            item.addPropertyOverride(location,getter);
        }
        return new Item1_12_2(item);
    }
}
