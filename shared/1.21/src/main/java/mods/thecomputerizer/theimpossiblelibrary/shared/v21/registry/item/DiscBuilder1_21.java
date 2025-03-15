package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;

import javax.annotation.Nullable;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;

import static net.minecraft.sounds.SoundEvents.EXPERIENCE_ORB_PICKUP;

public class DiscBuilder1_21 extends DiscBuilderAPI {
    
    public DiscBuilder1_21(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties properties = buildProperties();
        Item item = new TILDiscItem1_21(properties,getJukeboxSong());
        for(Entry<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> property : this.propertyMap.entrySet()) {
            ResourceLocation location = property.getKey().unwrap();
            ItemProperties.register(item,location,(stack,world,entity,seed) ->
                    property.getValue().apply(WrapperHelper.wrapItemStack(stack),WrapperHelper.wrapWorld(world)));
        }
        ItemAPI<?> wrapped = WrapperHelper.wrapItem(item);
        wrapped.setRegistryName(this.registryName);
        CreativeTabAPI<?> tab = properties.getCreativeTab();
        if(Objects.nonNull(tab)) tab.addStack(wrapped::defaultStack);
        return wrapped;
    }
    
    ResourceKey<JukeboxSong> getJukeboxSong() { //TODO Figure how to to convert a SoundEvent to a JukeboxSong
        SoundEvent sound = getSound();
        return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override protected <S> S defaultSound() {
        return (S)EXPERIENCE_ORB_PICKUP;
    }
}