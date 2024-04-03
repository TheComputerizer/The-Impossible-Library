package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventAPI;

import java.util.*;
import java.util.function.Consumer;

public class ClientEventsHelper {

    private static Set<CommonEventsHelper.EventEntry<?,?,?>> EVENT_ENTRIES;

    public static <E extends EventAPI> void addListener(Class<E> clazz, Consumer<E> invoker) {
        if(Objects.isNull(EVENT_ENTRIES)) defineEventClasses();
        for(CommonEventsHelper.EventEntry<?,?,?> entry : EVENT_ENTRIES) {
            if(entry.isAPIClass(clazz)) {
                subscribe(entry,invoker);
                return;
            }
        }
        TILRef.logError("Cannot listen to non event class {}!",clazz);
    }

    private static void defineEventClasses() {
        EVENT_ENTRIES = new HashSet<>();
        ClientEventsAPI api = getEventsAPI();
        if(Objects.nonNull(api)) api.defineEventClasses(EVENT_ENTRIES);
    }

    public static ClientEventsAPI getEventsAPI() {
        return TILRef.getClientSubAPI("ClientEventsAPI",ClientAPI::getClientEventsAPI);
    }

    public static void invoke(Object event) {
        if(Objects.isNull(EVENT_ENTRIES)) defineEventClasses();
        for(CommonEventsHelper.EventEntry<?,?,?> entry : EVENT_ENTRIES)
            if(entry.isValidConnectorObj(event)) entry.invokeFromConnecting(event);
    }

    @SuppressWarnings("unchecked")
    private static <E,A extends EventAPI,I extends A> void subscribe(CommonEventsHelper.EventEntry<E,A,I> entry, Consumer<?> invoker) {
        entry.subscribe((Consumer<A>)invoker);
    }
}