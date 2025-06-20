package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemRegistryHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

public abstract class ItemRegistryHelper1_16_5 extends ItemRegistryHelperAPI {

    private static final String POST = "v16.m5.registry.item.ItemRegistryHelper";
    private static ItemRegistryHelper1_16_5 INSTANCE;
    
    public static ItemRegistryHelper1_16_5 getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = CoreAPI.getModLoaderExtension(POST,true);
        return INSTANCE;
    }
    
    public static void registerItemModelVariants(Item item,
            Map<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> propertyMap) {
        getInstance().registerItemModelVariants(item,propertyMap.entrySet());
    }
    
    public ItemPropertyFunction defaultItemPropertyFunc(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> property) {
        return (stack,world,entity) ->
                property.apply(wrapStack(stack),wrapWorld(world));
    }
    
    public void registerItemModelVariants(Item item,
            Set<Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>>> propertyMap) {
        propertyMap.forEach((entry) ->
                registerItemModelVariant(item,entry.getKey().unwrap(),defaultItemPropertyFunc(entry.getValue())));
    }
    
    protected abstract void registerItemModelVariant(Item item, ResourceLocation location,
            ItemPropertyFunction func);
}