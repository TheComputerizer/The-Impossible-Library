package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.EventType;
import net.fabricmc.fabric.api.event.Event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;

public interface CommonFabricEvent {
    
    @SuppressWarnings("unchecked")
    static <T> void register(EventWrapper<?> wrapper, Event<T> event, EventType<?> type) {
        if(Objects.isNull(event)) return;
        Class<T> eventType = (Class<T>)event.invoker().getClass();
        event.register((T)Proxy.newProxyInstance(eventType.getClassLoader(),new Class<?>[]{eventType},
                                                             ((CommonFabricEvent)wrapper).createEventProxy(type)));
    }
    
    default void register() {
        EventWrapper<?> wrapper = (EventWrapper<?>)this;
        register(wrapper,getEventInstance(),wrapper.getType());
    }
    
    default void cancel() {
        EventWrapper<?> wrapper = (EventWrapper<?>)this;
        if(wrapper.isCancelable()) wrapper.setCanceled(true);
    }
    
    @SuppressWarnings("SuspiciousInvocationHandlerImplementation")
    default InvocationHandler createEventProxy(EventType<?> type) {
        return ((proxy,method,args) -> {
            if(method.getReturnType()==Boolean.class) return (Boolean)registerReturn(type,args);
            registerInvoke(type,args);
            return null;
        });
    }
    
    Event<?> getEventInstance();
    
    default void registerInvoke(EventType<?> type, Object ... args) {
        type.invoke(args);
    }
    
    default boolean registerReturn(EventType<?> type, Object ... args) {
        type.invoke(args);
        EventWrapper<?> wrapper = (EventWrapper<?>)this;
        return !wrapper.isCancelable() || (wrapper.isCancelable() && !wrapper.isCanceled());
    }
}