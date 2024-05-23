package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterSoundsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.sound.SoundEvent1_16_5;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;

public class RegisterSoundsEvent1_16_5 extends RegisterSoundsEventWrapper<Register<SoundEvent>> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<SoundEvent> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override public void register(SoundEventAPI<?> entry) {
        this.event.getRegistry().register(((SoundEvent1_16_5)entry).getValue());
    }
}
