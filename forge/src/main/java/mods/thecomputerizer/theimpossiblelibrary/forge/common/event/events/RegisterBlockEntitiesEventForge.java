package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlockEntitiesEventWrapper;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;

public abstract class RegisterBlockEntitiesEventForge extends RegisterBlockEntitiesEventWrapper<Register<TileEntityType<?>>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<TileEntityType<?>> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}