package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RegisterModelsEventWrapper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.REGISTER_MODELS;

public class RegisterModelsEventLegacy extends RegisterModelsEventWrapper<ModelRegistryEvent> {

    @SubscribeEvent
    public static void onEvent(ModelRegistryEvent event) {
        REGISTER_MODELS.invoke(event);
    }
}