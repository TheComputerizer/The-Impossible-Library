package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;

import java.util.*;
import java.util.function.Consumer;

public class CommonEventsHelper {

    private static Set<Class<?>> EVENT_CLASSES;
    private static Map<Class<?>,List<Consumer<?>>> EVENT_SUBSCRIBERS;

    public static <E> void addListener(Class<E> clazz, Consumer<E> invoker) {
        if(isRegisteredEventClass(clazz)) {
            checkEventSubscriberInit(clazz);
            EVENT_SUBSCRIBERS.get(clazz).add(invoker);
        } else TILRef.logError("Cannot listen to non event class {}!",clazz);
    }

    private static void checkEventSubscriberInit(Class<?> clazz) {
        if(Objects.isNull(EVENT_SUBSCRIBERS)) EVENT_SUBSCRIBERS = new HashMap<>();
        EVENT_SUBSCRIBERS.putIfAbsent(clazz,new ArrayList<>());
    }

    private static void defineEventClasses() {
        EVENT_CLASSES = new HashSet<>();
        CommonEventsAPI api = getEventsAPI();
        if(Objects.nonNull(api)) api.defineEventClasses(EVENT_CLASSES);
    }

    public static CommonEventsAPI getEventsAPI() {
        return TILRef.getCommonSubAPI("CommonEventsAPI",CommonAPI::getCommonEventsAPI);
    }

    @SuppressWarnings("unchecked")
    public static <E> void invoke(E event) {
        Class<?> clazz = event.getClass();
        if(isInvokable(clazz))
            for(Consumer<?> listener : EVENT_SUBSCRIBERS.get(clazz))
                ((Consumer<E>)listener).accept(event);
    }

    public static <E> boolean isInvokable(E event) {
        return isInvokable(event.getClass());
    }

    public static boolean isInvokable(Class<?> clazz) {
        return Objects.nonNull(EVENT_SUBSCRIBERS) && EVENT_SUBSCRIBERS.containsKey(clazz);
    }

    public static boolean isRegisteredEventClass(Class<?> clazz) {
        if(Objects.isNull(EVENT_CLASSES)) defineEventClasses();
        return EVENT_CLASSES.contains(clazz);
    }
}