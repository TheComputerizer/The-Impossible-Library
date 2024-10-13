package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlockEntitiesEventWrapper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegisterBlockEntitiesEventForge<T extends IForgeRegistryEntry<T>> extends RegisterBlockEntitiesEventWrapper<Register<T>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<T> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}