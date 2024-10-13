package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterSoundsEventWrapper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegisterSoundsEventForge<S extends IForgeRegistryEntry<S>> extends RegisterSoundsEventWrapper<Register<S>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<S> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
