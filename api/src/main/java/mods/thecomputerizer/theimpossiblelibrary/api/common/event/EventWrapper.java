package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventPriority.NORMAL;

@Getter
public abstract class EventWrapper<E> {

    private final EventType<?> type;
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

    public void prepareInvoke() {}

    public void setCanceled(boolean canceled) {
        if(this.type.cancelable) this.canceled = canceled;
    }

    public abstract void setEvent(E event);

    public enum Result { ALLOW, DEFAULT, DENY }

    public static class EventType<E extends EventWrapper<?>> {

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

        public <C> void invoke(C connector) {
            if(Objects.nonNull(this.connector)) {
                if(!this.invokers.isEmpty()) this.connector.prepareInvoke();
                for(Consumer<E> invoker : this.invokers) invoker.accept(this.connector);
            }
        }

        public boolean isClient() {
            return false;
        }

        public <I extends E> void setConnector(I impl) {
            this.connector = impl;
        }
    }
}