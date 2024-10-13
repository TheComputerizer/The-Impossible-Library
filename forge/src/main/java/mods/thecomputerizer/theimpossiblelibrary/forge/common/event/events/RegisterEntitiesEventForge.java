package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterEntitiesEventWrapper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegisterEntitiesEventForge<E extends IForgeRegistryEntry<E>> extends RegisterEntitiesEventWrapper<Register<E>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<E> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
