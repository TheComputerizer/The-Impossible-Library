package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlocksEventWrapper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegisterBlocksEventForge<B extends IForgeRegistryEntry<B>> extends RegisterBlocksEventWrapper<Register<B>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<B> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
