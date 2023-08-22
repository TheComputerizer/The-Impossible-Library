package mods.thecomputerizer.theimpossiblelibrary.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;

public interface ServerPlayerLoginEvent {

    Event<ServerPlayerLoginEvent> EVENT = EventFactory.createArrayBacked(ServerPlayerLoginEvent.class, events -> player -> {
        for(ServerPlayerLoginEvent event : events)
            event.register(player);
    });

    /**
     * Called when a player spawns after initially logging in.
     */
    void register(ServerPlayer player);
}
