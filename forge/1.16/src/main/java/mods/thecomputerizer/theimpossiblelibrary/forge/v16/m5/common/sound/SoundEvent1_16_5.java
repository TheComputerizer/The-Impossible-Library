package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryEntry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.Registry1_16_5;
import net.minecraft.util.SoundEvent;

public class SoundEvent1_16_5 extends RegistryEntry1_16_5<SoundEvent> implements SoundEventAPI<SoundEvent> {

    private final SoundEvent sound;

    public SoundEvent1_16_5(SoundEvent entry) {
        super(entry);
        this.sound = entry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Registry1_16_5<SoundEvent> getRegistry() {
        return (Registry1_16_5<SoundEvent>)(RegistryAPI<?>)RegistryHelper.getSoundRegistry();
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
