package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterSoundsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.sound.SoundEvent1_12_2;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_SOUNDS;

public class RegisterSoundsEvent1_12_2 extends RegisterSoundsEventWrapper<Register<SoundEvent>> {
    
    @SubscribeEvent
    public static void onEvent(Register<SoundEvent> event) {
        REGISTER_SOUNDS.invoke(event);
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        this.event.getRegistry().register(((SoundEvent1_12_2)entry).getValue());
    }
}
