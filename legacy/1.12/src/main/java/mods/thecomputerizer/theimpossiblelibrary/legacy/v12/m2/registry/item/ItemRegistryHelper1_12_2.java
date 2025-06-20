package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemRegistryHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

public class ItemRegistryHelper1_12_2 extends ItemRegistryHelperAPI {

    private static ItemRegistryHelper1_12_2 INSTANCE;
    
    public static ItemRegistryHelper1_12_2 getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new ItemRegistryHelper1_12_2();
        return INSTANCE;
    }
    
    public static void registerItemModelVariants(Item item,
            Map<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> propertyMap) {
        getInstance().registerItemModelVariants(item,propertyMap.entrySet());
    }
    
    private ItemRegistryHelper1_12_2() {}
    
    public IItemPropertyGetter defaultItemPropertyFunc(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> property) {
        return (stack,world,entity) ->
                property.apply(wrapStack(stack),wrapWorld(world));
    }
    
    public void registerItemModelVariants(Item item,
            Set<Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>>> propertyMap) {
        propertyMap.forEach((entry) ->
                registerItemModelVariant(item,entry.getKey().unwrap(),defaultItemPropertyFunc(entry.getValue())));
    }
    
    protected void registerItemModelVariant(Item item, ResourceLocation location, IItemPropertyGetter func) {
        item.addPropertyOverride(location,func);
    }
}