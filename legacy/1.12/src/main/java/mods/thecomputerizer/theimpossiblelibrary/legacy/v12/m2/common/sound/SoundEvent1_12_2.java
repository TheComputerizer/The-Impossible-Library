package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.Registry1_12_2;
import net.minecraft.util.SoundEvent;

public class SoundEvent1_12_2 extends RegistryEntry1_12_2<SoundEvent> implements SoundEventAPI<SoundEvent> {

    private final SoundEvent sound;

    public SoundEvent1_12_2(SoundEvent entry) {
        super(entry);
        this.sound = entry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Registry1_12_2<SoundEvent> getRegistry() {
        return (Registry1_12_2<SoundEvent>)(RegistryAPI<?>)RegistryHelper.getSoundRegistry();
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.sound;
    }

    @Override
    public RegistryEntryAPI<?> getEntryAPI() {
        return this;
    }

    @Override
    public Class<? extends SoundEvent> getValueClass() {
        return SoundEvent.class;
    }
}
