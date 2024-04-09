package mods.thecomputerizer.theimpossiblelibrary.legacy.registry.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryEntryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import net.minecraft.util.SoundEvent;

public class SoundEventLegacy extends RegistryEntryLegacy<SoundEvent> implements SoundEventAPI<SoundEvent> {

    private final SoundEvent sound;

    public SoundEventLegacy(SoundEvent entry) {
        super(entry);
        this.sound = entry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public RegistryLegacy<SoundEvent> getRegistry() {
        return (RegistryLegacy<SoundEvent>)(RegistryAPI<?>)RegistryHelper.getSoundRegistry();
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
