package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RegisterModelsEventWrapper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.REGISTER_MODELS;

public class RegisterModelsEventForge extends RegisterModelsEventWrapper<ModelRegistryEvent> {
    
    @SubscribeEvent
    public static void onEvent(ModelRegistryEvent event) {
        REGISTER_MODELS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ModelRegistryEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }    
}