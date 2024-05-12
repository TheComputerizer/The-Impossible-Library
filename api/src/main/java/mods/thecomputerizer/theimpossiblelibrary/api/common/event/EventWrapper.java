package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

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
    @Setter private boolean canceled;
    @Setter private EventPriority priority; //TODO dynamic priority?
    @Setter private Result result;
    protected E event;

    protected EventWrapper(EventType<?> type) {
        this.type = type;
        this.priority = NORMAL;
    }

    public boolean hasInvokers() {
        return this.type.hasInvokers();
    }

    public boolean hasResult() {
        return this.type.hasResult;
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

    public void setEvent(E event) {
        this.event = event;
        populate();
    }

    protected <V> @Nullable AdvancementAPI<V> wrapAdvancement(@Nullable Function<E,V> advancementFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(advancementFunc) ?
                WrapperHelper.wrapAdvancement(advancementFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,AdvancementAPI<?>> wrapAdvancementBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapAdvancement(getter),
                (event,api) -> setter.accept(event,((AdvancementAPI<V>)api).getAdvancement()),null);
    }

    protected <V> EventFieldWrapper<E,AdvancementAPI<?>> wrapAdvancementGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapAdvancement(getter),null);
    }

    protected <V> @Nullable BlockAPI<V> wrapBlock(@Nullable Function<E,V> blockFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(blockFunc) ?
                WrapperHelper.wrapBlock(blockFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,BlockAPI<?>> wrapBlockBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapBlock(getter),
                (event,api) -> setter.accept(event,((BlockAPI<V>)api).getBlock()),null);
    }

    protected <V> EventFieldWrapper<E,BlockAPI<?>> wrapBlockGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapBlock(getter),null);
    }

    protected <V> @Nullable BlockEntityAPI<V,?> wrapBlockEntity(@Nullable Function<E,V> blockEntityFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(blockEntityFunc) ?
                WrapperHelper.wrapBlockEntity(blockEntityFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,BlockEntityAPI<?,?>> wrapBlockEntityBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapBlockEntity(getter),
                (event,api) -> setter.accept(event,((BlockEntityAPI<V,?>)api).getEntity()),null);
    }

    protected <V> EventFieldWrapper<E,BlockEntityAPI<?,?>> wrapBlockEntityGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapBlockEntity(getter),null);
    }

    protected <V> @Nullable EntityAPI<V,?> wrapEntity(@Nullable Function<E,V> entityFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(entityFunc) ?
                WrapperHelper.wrapEntity(entityFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,EntityAPI<?,?>> wrapEntityBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapEntity(getter),
                (event,api) -> setter.accept(event,((EntityAPI<V,?>)api).getEntity()),null);
    }

    protected <V> EventFieldWrapper<E,EntityAPI<?,?>> wrapEntityGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapEntity(getter),null);
    }

    protected <V> @Nullable ExplosionAPI<V> wrapExplosion(@Nullable Function<E,V> explosionFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(explosionFunc) ?
                WrapperHelper.wrapExplosion(explosionFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,ExplosionAPI<?>> wrapExplosionBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapExplosion(getter),
                (event,api) -> setter.accept(event,((ExplosionAPI<V>)api).getExplosion()),null);
    }

    protected <V> EventFieldWrapper<E,ExplosionAPI<?>> wrapExplosionGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapExplosion(getter),null);
    }

    protected <V> @Nullable ItemAPI<V> wrapItem(@Nullable Function<E,V> itemFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(itemFunc) ?
                WrapperHelper.wrapItem(itemFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,ItemAPI<?>> wrapItemBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapItem(getter),
                (event,api) -> setter.accept(event,((ItemAPI<V>)api).getItem()),null);
    }

    protected <V> EventFieldWrapper<E,ItemAPI<?>> wrapItemGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapItem(getter),null);
    }

    protected <V> @Nullable ItemStackAPI<V> wrapItemStack(@Nullable Function<E,V> itemStackFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(itemStackFunc) ?
                WrapperHelper.wrapItemStack(itemStackFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,ItemStackAPI<?>> wrapItemStackBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapItemStack(getter),
                (event,api) -> setter.accept(event,((ItemStackAPI<V>)api).getStack()),null);
    }

    protected <V> EventFieldWrapper<E,ItemStackAPI<?>> wrapItemStackGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapItemStack(getter),null);
    }

    protected <V> @Nullable LivingEntityAPI<V,?> wrapLiving(@Nullable Function<E,V> livingFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(livingFunc) ?
                WrapperHelper.wrapLivingEntity(livingFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapLiving(getter),
                (event,api) -> setter.accept(event,((LivingEntityAPI<V,?>)api).getEntity()),null);
    }

    protected <V> EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapLiving(getter),null);
    }

    protected <V> @Nullable PlayerAPI<V,?> wrapPlayer(@Nullable Function<E,V> playerFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(playerFunc) ?
                WrapperHelper.wrapPlayer(playerFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,PlayerAPI<?,?>> wrapPlayerBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapPlayer(getter),
                (event,api) -> setter.accept(event,((PlayerAPI<V,?>)api).getEntity()),null);
    }

    protected <V> EventFieldWrapper<E,PlayerAPI<?,?>> wrapPlayerGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapPlayer(getter),null);
    }

    protected <V> BlockPosAPI<?> wrapPos(@Nullable Function<E,V> posFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(posFunc) ?
                PosHelper.getPos(posFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,BlockPosAPI<?>> wrapPosBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapPos(getter),
                (event,api) -> setter.accept(event,((BlockPosAPI<V>)api).getPos()),null);
    }

    protected <V> EventFieldWrapper<E,BlockPosAPI<?>> wrapPosGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapPos(getter),null);
    }

    protected <V> EventFieldWrapper<E,V> wrapGenericBoth(Function<E,V> getter, BiConsumer<E,V> setter, V defVal) {
        return new EventFieldWrapper<>(getter,setter,defVal);
    }

    protected <V> EventFieldWrapper<E,V> wrapGenericGetter(Function<E,V> getter, V defVal) {
        return new EventFieldWrapper<>(getter,defVal);
    }

    protected <V> @Nullable BlockSnapshotAPI<V> wrapSnapshot(@Nullable Function<E,V> snapshotFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(snapshotFunc) ?
                WrapperHelper.wrapSnapshot(snapshotFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,BlockSnapshotAPI<?>> wrapSnapshotBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapSnapshot(getter),
                (event,api) -> setter.accept(event,((BlockSnapshotAPI<V>)api).getSnapshot()),null);
    }

    protected <V> EventFieldWrapper<E,BlockSnapshotAPI<?>> wrapSnapshotGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapSnapshot(getter),null);
    }

    protected <V> @Nullable BlockStateAPI<V> wrapState(@Nullable Function<E,V> stateFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(stateFunc) ?
                WrapperHelper.wrapState(stateFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,BlockStateAPI<?>> wrapStateBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapState(getter),
                (event,api) -> setter.accept(event,((BlockStateAPI<V>)api).getState()),null);
    }

    protected <V> EventFieldWrapper<E,BlockStateAPI<?>> wrapStateGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapState(getter),null);
    }

    protected <V> @Nullable WorldAPI<V> wrapWorld(@Nullable Function<E,V> worldFunc) {
        return Objects.nonNull(this.event) && Objects.nonNull(worldFunc) ?
                WrapperHelper.wrapWorld(worldFunc.apply(this.event)) : null;
    }

    @SuppressWarnings("unchecked")
    protected <V> EventFieldWrapper<E,WorldAPI<?>> wrapWorldBoth(Function<E,V> getter, BiConsumer<E,V> setter) {
        return new EventFieldWrapper<>(event -> wrapWorld(getter),
                (event,api) -> setter.accept(event,((WorldAPI<V>)api).getWorld()),null);
    }

    protected <V> EventFieldWrapper<E,WorldAPI<?>> wrapWorldGetter(Function<E,V> getter) {
        return new EventFieldWrapper<>(event -> wrapWorld(getter),null);
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