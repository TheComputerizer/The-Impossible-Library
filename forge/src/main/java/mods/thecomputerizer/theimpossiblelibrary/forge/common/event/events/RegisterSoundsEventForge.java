package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterSoundsEventWrapper;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;

public abstract class RegisterSoundsEventForge extends RegisterSoundsEventWrapper<Register<SoundEvent>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<SoundEvent> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
