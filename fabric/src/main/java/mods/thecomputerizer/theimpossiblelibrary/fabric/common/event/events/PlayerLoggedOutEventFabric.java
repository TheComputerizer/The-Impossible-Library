package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedOutEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.lang.reflect.Field;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents.DISCONNECT;

public class PlayerLoggedOutEventFabric extends PlayerLoggedOutEventWrapper<Object[]> implements CommonFabricEvent {
    
    static Field playerField;
    
    @Override public Event<?> getEventInstance() {
        return DISCONNECT;
    }
    
    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(args -> {
            Object handler = args[0];
            if(Objects.isNull(playerField)) playerField = ReflectionHelper.getField(handler.getClass(), DEV ? "player" : "field_14140");
            return ReflectionHelper.getFieldInstance(handler,playerField);
        });
    }
}