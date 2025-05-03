package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;

public class ItemBlockBuilder1_16_5 extends ItemBlockBuilderAPI {
    
    public ItemBlockBuilder1_16_5(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        BlockAPI<?> block = this.block.get();
        BlockItem item = new TILItemBlock1_16_5(block.unwrap(),buildProperties());
        if(CoreAPI.isClient()) registerTextureProperties(item);
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(Objects.nonNull(this.registryName) ? this.registryName : block.getRegistryName());
        return wrapped;
    }
    
    private void registerTextureProperties(Item item) {
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            net.minecraft.item.ItemModelsProperties.register(item,location,(stack,world,entity) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world)));
        }
    }
}