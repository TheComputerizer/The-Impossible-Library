package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterSoundEventsFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.SOUND_EVENT;

public class RegisterSoundEventsFabric1_16_5 extends RegisterSoundEventsFabric {
    
    @Override public Registry<?> getRegistry() {
        return SOUND_EVENT;
    }
}