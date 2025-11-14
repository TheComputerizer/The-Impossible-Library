package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterSoundsEventForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;
import static net.minecraft.core.registries.Registries.SOUND_EVENT;

public class RegisterSoundsEventForge1_20_6 extends RegisterSoundsEventForge<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(SOUND_EVENT)) REGISTER_SOUNDS.invoke(event);
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        this.event.register(SOUND_EVENT,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}