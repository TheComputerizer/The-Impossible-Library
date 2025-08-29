package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.EventType;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.fabricmc.fabric.api.event.Event;

import java.lang.reflect.InvocationHandler;
import java.util.Objects;
import java.util.function.Function;

//TODO Fabric events aren't fully implemented and the current implementation kinda sucks
public interface CommonFabricEvent {
    
    static <T> void register(EventWrapper<?> wrapper, Event<T> event, EventType<?> type) {
        if(Objects.isNull(event)) return;
        Object invoker = event.invoker();
        Class<T> eventType = GenericUtils.cast(invoker.getClass().getInterfaces()[0]);
        TILRef.logInfo("Event invoker class is {}",eventType);
        event.register(ClassHelper.newProxy(eventType,((CommonFabricEvent)wrapper).createEventProxy(type)));
    }
    
    @IndirectCallers
    default void cancel() {
        EventWrapper<?> wrapper = (EventWrapper<?>)this;
        if(wrapper.isCancelable()) wrapper.setCanceled(true);
    }
    
    @SuppressWarnings("SuspiciousInvocationHandlerImplementation")
    default InvocationHandler createEventProxy(EventType<?> type) {
        return (proxy,method,args) -> {
            if(method.getReturnType()==Boolean.class) return (Boolean)registerReturn(type,args);
            registerInvoke(type,args);
            return null;
        };
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
    
    default <T> Function<Object[],T> wrapArrayGetter(int index) {
        return args -> GenericUtils.cast(args.length>index ? args[index] : null);
    }
}