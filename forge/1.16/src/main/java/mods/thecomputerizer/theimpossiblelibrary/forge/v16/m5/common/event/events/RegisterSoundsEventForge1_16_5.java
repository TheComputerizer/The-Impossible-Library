package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterSoundsEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.sound.SoundEvent1_16_5;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;

public class RegisterSoundsEventForge1_16_5 extends RegisterSoundsEventForge {
    
    @SubscribeEvent
    public static void onEvent(Register<SoundEvent> event) {
        REGISTER_SOUNDS.invoke(event);
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        this.event.getRegistry().register(((SoundEvent1_16_5)entry).getValue());
    }
}
