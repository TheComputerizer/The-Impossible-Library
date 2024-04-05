package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventPriority.NORMAL;

@Getter
public abstract class EventWrapper<E> {

    private final EventType<?> type;
    @Setter protected E event;
    @Setter private EventPriority priority; //TODO dynamic priority?
    private boolean canceled;

    protected EventWrapper(EventType<?> type) {
        this.type = type;
        this.priority = NORMAL;
    }

    public boolean hasInvokers() {
        return this.type.hasInvokers();
    }

    public boolean isCancelable() {
        return this.type.cancelable;
    }

    public abstract boolean isClient();
    public abstract boolean isCommon();
    public abstract boolean isServer();
    protected abstract void populate();

    public void setCanceled(boolean canceled) {
        if(this.type.cancelable) this.canceled = canceled;
    }

    public void setEvent(E event) {
        this.event = event;
        populate();
    }

    protected <V,A> @Nullable A setNullableWrapper(Function<E,V> valFunc, Function<V,A> wrapperFunc) {
        V val = Objects.nonNull(this.event) ? valFunc.apply(this.event) : null;
        return Objects.nonNull(val) ? wrapperFunc.apply(val) : null;
    }

    public enum Result { ALLOW, DEFAULT, DENY }

    public static abstract class EventType<E extends EventWrapper<?>> {

        private final boolean cancelable;
        private final boolean hasResult;
        private final List<Consumer<E>> invokers;
        private E connector;

        protected EventType(boolean cancelable, boolean hasResult) {
            this.cancelable = cancelable;
            this.hasResult = hasResult;
            this.invokers = new ArrayList<>();
        }

        public void addInvoker(Consumer<E> invoker) {
            if(Objects.nonNull(this.connector)) {
                if(this.invokers.isEmpty()) EventHelper.registerWrapperImpl(this.connector);
                this.invokers.add(invoker);
            } else TILRef.logError("Cannot add invoker to unconnected event type!");
        }

        private boolean hasInvokers() {
            return Objects.nonNull(this.connector) && !this.invokers.isEmpty();
        }

        public <C> void invoke(C event) {
            if(Objects.nonNull(this.connector) && !this.invokers.isEmpty()) {
                setWrapperEvent(event,this.connector);
                for(Consumer<E> invoker : this.invokers) invoker.accept(this.connector);
            }
        }

        public abstract boolean isClient();
        public abstract boolean isCommon();
        public abstract boolean isServer();

        public <I extends E> void setConnector(I impl) {
            this.connector = impl;
        }

        /**
         * Stupid generic casting
         */
        @SuppressWarnings("unchecked")
        private <C> void setWrapperEvent(C event, EventWrapper<?> wrapper) {
            ((EventWrapper<C>)wrapper).setEvent(event);
        }
    }
}