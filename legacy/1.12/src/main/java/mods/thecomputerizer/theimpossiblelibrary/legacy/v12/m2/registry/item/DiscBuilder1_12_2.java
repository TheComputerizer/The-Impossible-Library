package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;

import static net.minecraft.init.SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;

public class DiscBuilder1_12_2 extends DiscBuilderAPI {
    
    public DiscBuilder1_12_2(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        ItemProperties properties = buildProperties();
        Item item = new TILDiscItem1_12_2(this.nameSupplier,getSound(),properties);
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        if(Objects.nonNull(tab)) tab.addStack(() -> WrapperHelper.wrapItemStack(new ItemStack(item)));
        if(CoreAPI.isClient()) registerTextureProperties(item);
        return WrapperHelper.wrapItem(item);
    }
    
    @SuppressWarnings("unchecked")
    @Override protected <S> S defaultSound() {
        return (S)ENTITY_EXPERIENCE_ORB_PICKUP;
    }
    
    private void registerTextureProperties(Item item) {
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            IItemPropertyGetter getter = (stack,world,entity) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world));
            item.addPropertyOverride(location,getter);
        }
    }
}