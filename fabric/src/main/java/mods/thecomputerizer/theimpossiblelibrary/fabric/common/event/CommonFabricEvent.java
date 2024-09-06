package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.EventType;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.fabricmc.fabric.api.event.Event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.function.Function;

public interface CommonFabricEvent {
    
    @SuppressWarnings("unchecked")
    static <T> void register(EventWrapper<?> wrapper, Event<T> event, EventType<?> type) {
        if(Objects.isNull(event)) return;
        Object invoker = event.invoker();
        TILRef.logInfo("Event invoker is {}",invoker);
        Class<T> eventType = (Class<T>)invoker.getClass().getInterfaces()[0];
        TILRef.logInfo("Event invoker clas is {}",eventType);
        event.register((T)Proxy.newProxyInstance(eventType.getClassLoader(),new Class<?>[]{eventType},
                                                             ((CommonFabricEvent)wrapper).createEventProxy(type)));
    }
    
    default void cancel() {
        EventWrapper<?> wrapper = (EventWrapper<?>)this;
        if(wrapper.isCancelable()) wrapper.setCanceled(true);
    }
    
    default InvocationHandler createEventProxy(EventType<?> type) {
        return ((proxy,method,args) -> {
            if(method.getReturnType()==Boolean.class) return (Boolean)registerReturn(type,args);
            registerInvoke(type,args);
            return null;
        });
    }
    
    Event<?> getEventInstance();
    
    default void register() {
        EventWrapper<?> wrapper = (EventWrapper<?>)this;
        register(wrapper,getEventInstance(),wrapper.getType());
    }
    
    default void registerInvoke(EventType<?> type, Object ... args) {
        type.invoke(args);
    }
    
    default boolean registerReturn(EventType<?> type, Object ... args) {
        type.invoke(args);
        EventWrapper<?> wrapper = (EventWrapper<?>)this;
        return !wrapper.isCancelable() || (wrapper.isCancelable() && !wrapper.isCanceled());
    }
    
    default <T> Function<Object[],T> wrapArrayGetter(int index, Class<T> clazz) {
        return args -> {
            Object arg = args.length>index ? args[index] : null;
            return Objects.nonNull(arg) ? clazz.cast(arg) : null;
        };
    }
    
    @SuppressWarnings("unchecked") default <T> Function<Object[],T> wrapArrayGetter(int index) {
        return args -> {
            Object arg = args.length>index ? args[index] : null;
            return Objects.nonNull(arg) ? (T)arg : null;
        };
    }
}