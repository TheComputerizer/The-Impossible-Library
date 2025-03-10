package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterSoundsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;
import static net.minecraft.core.registries.Registries.SOUND_EVENT;

public class RegisterSoundsEventNeoForge extends RegisterSoundsEventWrapper<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(SOUND_EVENT)) REGISTER_SOUNDS.invoke(event);
    }
    
    @Override public void setEvent(RegisterEvent event) {
        super.setEvent(event);
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        this.event.register(SOUND_EVENT,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}