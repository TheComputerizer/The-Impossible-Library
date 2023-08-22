package mods.thecomputerizer.theimpossiblelibrary.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ResourcesLoadedEvent {

    Event<ResourcesLoadedEvent> EVENT = EventFactory.createArrayBacked(ResourcesLoadedEvent.class, events -> () -> {
        for (ResourcesLoadedEvent event : events)
            event.register();
    });

    /**
     * Called after resources are finished loading.
     */
    void register();
}
