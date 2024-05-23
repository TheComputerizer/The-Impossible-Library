package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RegisterModelsEventWrapper;
import net.minecraftforge.client.event.ModelRegistryEvent;

public class RegisterModelsEvent1_16_5 extends RegisterModelsEventWrapper<ModelRegistryEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ModelRegistryEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }    
}