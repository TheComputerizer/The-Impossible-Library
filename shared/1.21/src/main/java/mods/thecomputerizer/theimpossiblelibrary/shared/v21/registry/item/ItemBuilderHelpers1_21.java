package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks.CallStrategy.DIRECT;

public interface ItemBuilderHelpers1_21 {
    
    default ItemAPI<?> defaultBuild(ItemProperties properties) {
        return defaultBuild(properties,TILBasicItem1_21::new);
    }
    
    default ItemAPI<?> defaultBuild(ItemProperties properties, Function<ItemProperties,Item> itemMaker) {
        Item item = itemMaker.apply(properties);
        registerVariants(item);
        return wrapAndConfigure(item,properties);
    }
    
    default Map<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> getPropertyMapField() {
        return DIRECT.get(this,"propertyMap");
    }
    
    default ResourceLocationAPI<?> getRegistryNameField() {
        return DIRECT.get(this,"registryName");
    }
    
    default void registerVariants(Item item) {
        if(CoreAPI.isClient()) ItemRegistryHelper1_21.registerItemModelVariants(item,getPropertyMapField());
    }
    
    default ItemAPI<?> wrapAndConfigure(Item item, ItemProperties properties) {
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(getRegistryNameField());
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        if(Objects.nonNull(tab)) tab.addStack(wrapped::defaultStack);
        return wrapped;
    }
}