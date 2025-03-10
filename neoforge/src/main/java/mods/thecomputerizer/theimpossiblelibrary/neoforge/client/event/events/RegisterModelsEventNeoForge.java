package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RegisterModelsEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.REGISTER_MODELS;

public class RegisterModelsEventNeoForge extends RegisterModelsEventWrapper<RegisterAdditional> {
    
    @SubscribeEvent
    public static void onEvent(RegisterAdditional event) {
        REGISTER_MODELS.invoke(event);
    }
    
    @Override public void setEvent(RegisterAdditional event) {
        super.setEvent(event);
    }    
}