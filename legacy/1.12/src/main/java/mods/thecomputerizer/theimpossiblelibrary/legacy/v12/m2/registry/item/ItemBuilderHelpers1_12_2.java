package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

public interface ItemBuilderHelpers1_12_2 {
    
    default ItemAPI<?> defaultBuild(ItemProperties properties) {
        return defaultBuild(properties,TILBasicItem1_12_2::new);
    }
    
    default ItemAPI<?> defaultBuild(ItemProperties properties, Function<ItemProperties,Item> itemMaker) {
        Item item = itemMaker.apply(properties);
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        if(Objects.nonNull(tab)) tab.addStack(() -> wrapStack(item));
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
        if(CoreAPI.isClient()) ItemRegistryHelper1_12_2.registerItemModelVariants(item,getPropertyMapField());
    }
    
    default ItemAPI<?> wrapAndConfigure(Item item, ItemProperties properties) {
        item.setMaxStackSize(properties.getStackSize());
        ResourceLocationAPI<?> name = getRegistryNameField();
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(name);
        if(!(item instanceof ItemBlock)) item.setTranslationKey(name.getNamespace()+"."+name.getPath());
        return wrapped;
    }
    
    default ItemStackAPI<?> wrapStack(Item item) {
        return WrapperHelper.wrapItemStack(new ItemStack(item));
    }
}