package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterSoundEventsFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT;

public class RegisterSoundEventsFabric1_19_4 extends RegisterSoundEventsFabric {
    
    @Override public Registry<?> getRegistry() {
        return SOUND_EVENT;
    }
}
