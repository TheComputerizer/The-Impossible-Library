package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.sound.SoundEvent1_16_5;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class DiscBuilder1_16_5 extends DiscBuilderAPI {
    
    public DiscBuilder1_16_5(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Item1_16_5 build() {
        ItemProperties otherProperties = buildProperties();
        Properties properties = new Properties().stacksTo(otherProperties.getStackSize());
        SoundEvent sound = ((SoundEvent1_16_5)this.sound).getSound();
        return new Item1_16_5(new TILDiscItem1_16_5(properties,sound,otherProperties));
    }
}
