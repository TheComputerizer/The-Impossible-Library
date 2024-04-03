package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Consumer;

public class CommonEventsHelper {

    private static Set<EventEntry<?,?,?>> EVENT_ENTRIES;

    public static <E extends EventAPI> void addListener(Class<E> clazz, Consumer<E> invoker) {
        if(Objects.isNull(EVENT_ENTRIES)) defineEventClasses();
        for(EventEntry<?,?,?> entry : EVENT_ENTRIES) {
            if(entry.isAPIClass(clazz)) {
                subscribe(entry,invoker);
                return;
            }
        }
        TILRef.logError("Cannot listen to non event class {}!",clazz);
    }

    private static void defineEventClasses() {
        EVENT_ENTRIES = new HashSet<>();
        CommonEventsAPI api = getEventsAPI();
        if(Objects.nonNull(api)) api.defineEventClasses(EVENT_ENTRIES);
    }

    public static CommonEventsAPI getEventsAPI() {
        return TILRef.getCommonSubAPI("CommonEventsAPI",CommonAPI::getCommonEventsAPI);
    }

    public static void invoke(Object event) {
        if(Objects.isNull(EVENT_ENTRIES)) defineEventClasses();
        for(EventEntry<?,?,?> entry : EVENT_ENTRIES)
            if(entry.isValidConnectorObj(event)) entry.invokeFromConnecting(event);
    }

    @SuppressWarnings("unchecked")
    private static <E,A extends EventAPI,I extends A> void subscribe(EventEntry<E,A,I> entry, Consumer<?> invoker) {
        entry.subscribe((Consumer<A>)invoker);
    }

    public static class EventEntry<E,A extends EventAPI,I extends A> {

        private final Class<E> connectingClass; //Backend Forge/Fabric event class
        private final Class<A> apiClass; //API WrapperClass
        private final Class<I> implClass; //Version implementation of the wrapper class
        private final List<Consumer<A>> subscribers;

        public EventEntry(Class<E> connectingClass, Class<A> apiClass, Class<I> implClass) {
            this.connectingClass = connectingClass;
            this.apiClass = apiClass;
            this.implClass = implClass;
            this.subscribers = new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        public void invokeFromConnecting(Object event) {
            if(this.subscribers.isEmpty()) return;
            Constructor<I> implConstructor = (Constructor<I>)Misc.findConstructor(this.implClass,event.getClass());
            try {
                I implInstance = Objects.nonNull(implConstructor) ? implConstructor.newInstance(event) : null;
                if(Objects.nonNull(implInstance))
                    for(Consumer<A> consumer : this.subscribers)
                        consumer.accept(implInstance);
            } catch(Exception ex) {
                TILRef.logError("Failed to instantiate API event class `{}` from event of class `{}`",
                        this.apiClass,event.getClass(),ex);
            }
        }

        public boolean isAPIClass(Class<?> clazz) {
            return clazz==this.apiClass;
        }

        public boolean isValidConnectorObj(Object event) {
            return isValidConnector(event.getClass());
        }

        public boolean isValidConnector(Class<?> clazz) {
            return clazz==this.connectingClass;
        }

        public void subscribe(Consumer<A> subscriber) {
            this.subscribers.add(subscriber);
        }
    }
}