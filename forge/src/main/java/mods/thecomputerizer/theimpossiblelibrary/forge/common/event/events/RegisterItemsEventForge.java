package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterItemsEventWrapper;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;

public abstract class RegisterItemsEventForge extends RegisterItemsEventWrapper<Register<Item>> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<Item> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
