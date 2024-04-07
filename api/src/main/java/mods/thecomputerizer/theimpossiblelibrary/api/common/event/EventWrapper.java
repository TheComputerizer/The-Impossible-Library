package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventPriority.NORMAL;

@Getter
public abstract class EventWrapper<E> {

    private final EventType<?> type;
    protected E event;
    @Setter private EventPriority priority; //TODO dynamic priority?
    private boolean canceled;

    protected EventWrapper(EventType<?> type) {
        this.type = type;
        this.priority = NORMAL;
    }

    protected <V> EventFieldWrapper<E,V> getFieldWrapperGetter(Function<E,V> getter, V defVal) {
        return new EventFieldWrapper<>(getter,defVal);
    }

    protected <V> EventFieldWrapper<E,V> getFieldWrapperSetter(BiConsumer<E,V> setter, V defVal) {
        return new EventFieldWrapper<>(setter,defVal);
    }

    protected <V> EventFieldWrapper<E,V> getFieldWrapperBoth(Function<E,V> getter, BiConsumer<E,V> setter, V defVal) {
        return new EventFieldWrapper<>(getter,setter,defVal);
    }

    public boolean hasInvokers() {
        return this.type.hasInvokers();
    }

    public <V> V initPrimitive(@Nullable Function<E,V> func, V defVal) {
        return Objects.nonNull(this.event) && Objects.nonNull(func) ? func.apply(this.event) : defVal;
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

    protected <V> @Nullable AdvancementAPI<V> wrapAdvancement(@Nullable Function<E,V> advancementFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(advancementFunc) ?
                WrapperHelper.wrapAdvancement(advancementFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable BlockAPI<V> wrapBlock(@Nullable Function<E,V> blockFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(blockFunc) ?
                WrapperHelper.wrapBlock(blockFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable BlockEntityAPI<V> wrapBlockEntity(@Nullable Function<E,V> blockEntityFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(blockEntityFunc) ?
                WrapperHelper.wrapBlockEntity(blockEntityFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable EntityAPI<V> wrapEntity(@Nullable Function<E,V> entityFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(entityFunc) ?
                WrapperHelper.wrapEntity(entityFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable ItemAPI<V> wrapItem(@Nullable Function<E,V> itemFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(itemFunc) ?
                WrapperHelper.wrapItem(itemFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable ItemStackAPI<V> wrapItemStack(@Nullable Function<E,V> itemStackFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(itemStackFunc) ?
                WrapperHelper.wrapItemStack(itemStackFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable LivingEntityAPI<V> wrapLiving(@Nullable Function<E,V> livingFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(livingFunc) ?
                WrapperHelper.wrapLivingEntity(livingFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable PlayerAPI<V> wrapPlayer(@Nullable Function<E,V> playerFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(playerFunc) ?
                WrapperHelper.wrapPlayer(playerFunc.apply(this.event)) : null;
    }

    protected <V> BlockPosAPI<?> wrapPos(@Nullable Function<E,V> posFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(posFunc) ?
                PosHelper.getPos(posFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable BlockStateAPI<V> wrapState(@Nullable Function<E,V> stateFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(stateFunc) ?
                WrapperHelper.wrapState(stateFunc.apply(this.event)) : null;
    }

    protected <V> @Nullable WorldAPI<V> wrapWorld(@Nullable Function<E,V> worldFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(worldFunc) ?
                WrapperHelper.wrapWorld(worldFunc.apply(this.event)) : null;
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