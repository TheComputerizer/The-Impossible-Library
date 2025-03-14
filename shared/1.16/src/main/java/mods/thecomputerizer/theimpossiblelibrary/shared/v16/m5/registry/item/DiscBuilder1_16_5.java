package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import static net.minecraft.util.SoundEvents.EXPERIENCE_ORB_PICKUP;

public class DiscBuilder1_16_5 extends DiscBuilderAPI {
    
    public DiscBuilder1_16_5(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        MusicDiscItem item = new TILDiscItem1_16_5(getSound(),buildProperties());
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            IItemPropertyGetter getter = (stack,world,entity) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world));
            ItemModelsProperties.register(item,location,getter);
        }
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(this.registryName);
        return wrapped;
    }
    
    @SuppressWarnings("unchecked")
    @Override protected <S> S defaultSound() {
        return (S)EXPERIENCE_ORB_PICKUP;
    }
}