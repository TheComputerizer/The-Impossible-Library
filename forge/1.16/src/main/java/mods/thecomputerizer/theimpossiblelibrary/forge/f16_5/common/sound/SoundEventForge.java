package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryEntryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import net.minecraft.util.SoundEvent;

public class SoundEventForge extends RegistryEntryForge<SoundEvent> implements SoundEventAPI<SoundEvent> {

    private final SoundEvent sound;

    public SoundEventForge(SoundEvent entry) {
        super(entry);
        this.sound = entry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public RegistryForge<SoundEvent> getRegistry() {
        return (RegistryForge<SoundEvent>)(RegistryAPI<?>)RegistryHelper.getSoundRegistry();
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
