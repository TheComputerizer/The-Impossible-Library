package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterSoundsEventForge;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;

public class RegisterSoundsEventForge1_18_2 extends RegisterSoundsEventForge<SoundEvent> {
    
    @SubscribeEvent
    public static void onEvent(Register<SoundEvent> event) {
        REGISTER_SOUNDS.invoke(event);
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        this.event.getRegistry().register(entry.unwrap());
    }
}