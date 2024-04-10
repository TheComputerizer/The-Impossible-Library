package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.EventType;

import java.util.*;
import java.util.function.Consumer;

public class EventHelper {

    public static <E extends EventWrapper<?>> void addListener(EventType<E> type, Consumer<E> invoker) {
        EventsAPI api = getEventsAPI(type.isClient());
        if(Objects.nonNull(api)) {
            if(!api.isDefined()) api.defineEvents();
            type.addInvoker(invoker);
        }
    }

    public static EventsAPI getCommonEventsAPI() {
        return TILRef.getCommonSubAPI("EventsAPI",CommonAPI::getCommonEventsAPI);
    }

    public static EventsAPI getEventsAPI(boolean client) {
        return client ? ClientEventHelper.getEventsAPI() : getCommonEventsAPI();
    }

    public static void initTILListeners(boolean client, boolean test) {
        if(client) ClientEventHelper.initTILClientListeners(test);
    }

    public static <E extends EventWrapper<?>> void registerWrapperImpl(E wrapper) {
        EventsAPI api = getEventsAPI(wrapper.isClient());
        if(Objects.nonNull(api)) api.register(wrapper);
    }
}