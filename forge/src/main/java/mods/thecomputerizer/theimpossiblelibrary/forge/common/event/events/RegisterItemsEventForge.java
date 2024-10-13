package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterItemsEventWrapper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegisterItemsEventForge<I extends IForgeRegistryEntry<I>> extends RegisterItemsEventWrapper<Register<I>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<I> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
