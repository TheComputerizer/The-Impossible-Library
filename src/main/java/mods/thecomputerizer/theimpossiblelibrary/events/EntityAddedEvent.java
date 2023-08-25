package mods.thecomputerizer.theimpossiblelibrary.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;

public interface EntityAddedEvent {

    Event<EntityAddedEvent> EVENT = EventFactory.createArrayBacked(EntityAddedEvent.class, events -> entity -> {
        for (EntityAddedEvent event : events)
            if(!event.register(entity)) return false;
        return true;
    });

    /**
     * Called when an entity successfully spawns on the server side. Can return false to block the spawn.
     */
    boolean register(Entity entity);
}