package mods.thecomputerizer.theimpossiblelibrary.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.level.ServerPlayer;

public class AdvancmentEvents {

    public static Event<ServerGranted> SERVER_GRANTED = EventFactory.createArrayBacked(ServerGranted.class, events -> (player,advancement) -> {
        for (ServerGranted event : events)
            event.register(player,advancement);
    });

    @Environment(EnvType.CLIENT)
    public static Event<ClientGranted> CLIENT_GRANTED = EventFactory.createArrayBacked(ClientGranted.class, events -> advancement -> {
        for (ClientGranted event : events)
            event.register(advancement);
    });

    public interface ServerGranted {

        /**
         * Called when an advancement is granted to player on the server side
         */
        void register(ServerPlayer player, Advancement advancement);
    }

    @Environment(EnvType.CLIENT)
    public interface ClientGranted {

        /**
         * Called when an advancement is granted to player on the client side
         */
        void register(Advancement advancement);
    }
}
