package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedInEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents.JOIN;

public class PlayerLoggedInEventFabric extends PlayerLoggedInEventWrapper<Object[]> implements CommonFabricEvent {
    
    static final String PLAYER_FIELD_NAME = DEV ? "player" : "field_14140";
    
    @Override public Event<?> getEventInstance() {
        return JOIN;
    }
    
    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(args -> Hacks.getField(args[0],PLAYER_FIELD_NAME));
    }
}