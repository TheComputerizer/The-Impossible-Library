package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.Map.Entry;
import java.util.function.BiFunction;

public class ItemBuilder1_18_2 extends ItemBuilderAPI {
    
    public ItemBuilder1_18_2(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        Item item = new TILBasicItem1_18_2(buildProperties());
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            ItemProperties.register(item,location,(stack,world,entity,seed) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world)));
        }
        return WrapperHelper.wrapItem(item);
    }
}