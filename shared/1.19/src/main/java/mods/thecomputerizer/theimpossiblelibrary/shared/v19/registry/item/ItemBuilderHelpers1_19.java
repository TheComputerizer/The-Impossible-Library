package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.item;

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

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties.VERSION;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

public interface ItemBuilderHelpers1_19 {
    
    default ItemAPI<?> defaultBuild(ItemProperties properties) {
        return defaultBuild(properties,TILBasicItem1_19::new);
    }
    
    default ItemAPI<?> defaultBuild(ItemProperties properties, Function<ItemProperties,Item> itemMaker) {
        Item item = itemMaker.apply(properties);
        registerVariants(item);
        return wrapAndConfigure(item,properties);
    }
    
    default Map<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> getPropertyMapField() {
        return Fields.getDirect(this,"propertyMap");
    }
    
    default ResourceLocationAPI<?> getRegistryNameField() {
        return Fields.getDirect(this,"registryName");
    }
    
    default void registerVariants(Item item) {
        if(CoreAPI.isClient()) ItemRegistryHelper1_19.registerItemModelVariants(item,getPropertyMapField());
    }
    
    default ItemAPI<?> wrapAndConfigure(Item item, ItemProperties properties) {
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(getRegistryNameField());
        if(VERSION==V19_4) {
            CreativeTabAPI<?> tab = properties.getCreativeTab();
            if(Objects.nonNull(tab)) tab.addStack(wrapped::defaultStack);
        }
        return wrapped;
    }
}