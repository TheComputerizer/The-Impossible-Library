package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterEntitiesEventWrapper;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent.Register;

public abstract class RegisterEntitiesEventForge extends RegisterEntitiesEventWrapper<Register<EntityType<?>>> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<EntityType<?>> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
