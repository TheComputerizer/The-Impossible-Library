package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;

import javax.annotation.Nullable;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_4;

public class ItemBlockBuilder1_19 extends ItemBlockBuilderAPI {
    
    public ItemBlockBuilder1_19(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        BlockAPI<?> block = this.block.get();
        mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties properties = buildProperties();
        BlockItem item = new TILItemBlock1_19(block.unwrap(),properties);
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            ItemProperties.register(item,location,(stack,world,entity,seed) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world)));
        }
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(Objects.nonNull(this.registryName) ? this.registryName : block.getRegistryName());
        if(VERSION==V19_4) {
            CreativeTabAPI<?> tab = properties.getCreativeTab();
            if(Objects.nonNull(tab)) tab.addStack(wrapped.defaultStack());
        }
        return wrapped;
    }
}