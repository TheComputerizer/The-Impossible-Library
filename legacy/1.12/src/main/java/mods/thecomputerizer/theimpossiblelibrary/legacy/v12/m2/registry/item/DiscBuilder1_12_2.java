package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.sound.SoundEvent1_12_2;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class DiscBuilder1_12_2 extends DiscBuilderAPI {
    
    public DiscBuilder1_12_2(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Item1_12_2 build() {
        SoundEvent sound = ((SoundEvent1_12_2)this.sound).getSound();
        return new Item1_12_2(new TILDiscItem1_12_2(this.name,sound,buildProperties()));
    }
}
