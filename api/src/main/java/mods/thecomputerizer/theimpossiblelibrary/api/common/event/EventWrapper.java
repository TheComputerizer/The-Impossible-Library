package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.Wrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventPriority.NORMAL;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

@SuppressWarnings("unused") @Getter
public abstract class EventWrapper<E> implements CoreStateAccessor {

    private final EventType<?> type;
    @Setter private boolean canceled;
    @Setter private EventPriority priority; //TODO dynamic priority?
    @Setter private Result result;
    protected E event;

    protected EventWrapper(EventType<?> type) {
        this.type = type;
        this.priority = NORMAL;
    }
    
    protected Function<E,?> actionResultGetter(String getterMethod) {
        return getter(getterMethod,EventHelper::getActionResult);
    }
    
    protected <T> BiConsumer<E,T> actionResultSetter(String setterMethod) {
        return setter(setterMethod,EventHelper::setActionResult);
    }
    
    public void cancel() {}
    
    protected <T> T cast(Object obj) {
        return GenericUtils.cast(obj);
    }
    
    protected <I,O> @Nullable O getter(@Nullable I input, String method) {
        return getter(input,method,null);
    }
    
    protected <I,O> @Nullable O getter(@Nullable I input, String method, @Nullable Function<?,O> transformer) {
        if(Objects.isNull(input)) return null;
        if(Objects.isNull(transformer)) return Hacks.invoke(input,method);
        return transformer.apply(Hacks.invoke(input,method));
    }
    
    protected Function<E,Object> getter(String getterMethod) {
        return Misc.safeFunction(event -> getter(event,getterMethod));
    }
    
    protected Function<E,Object> getter(String getterMethod, @Nullable Function<?,?> transformer) {
        return Misc.safeFunction(event -> getter(event,getterMethod,transformer));
    }
    
    protected Function<E,?> eventResultGetter(String getterMethod) {
        return getter(getterMethod,EventHelper::getEventResult);
    }
    
    protected <T> BiConsumer<E,T> eventResultSetter(String setterMethod) {
        return setter(setterMethod,EventHelper::setEventResult);
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
    
    protected <I,O> @Nullable O nestedGetter(@Nullable I input, String ... methods) {
        return nestedGetter(input,null,methods);
    }
    
    protected <I,O> @Nullable O nestedGetter(@Nullable I input, @Nullable Function<?,O> transformer, String ... methods) {
        if(Objects.isNull(input)) return null;
        Object result = null;
        for(String method : methods) result = Hacks.invoke(input,method);
        return Objects.nonNull(transformer) ? transformer.apply(cast(result)) : cast(result);
    }
    
    @IndirectCallers
    protected Function<E,Object> nestedGetter(String ... getterMethods) {
        return Misc.safeFunction(event -> nestedGetter(event,getterMethods));
    }
    
    protected Function<E,Object> nestedGetter(@Nullable Function<?,?> transformer, String ... getterMethods) {
        return Misc.safeFunction(event -> nestedGetter(event,transformer,getterMethods));
    }
    
    protected abstract void populate();

    public void setEvent(E event) {
        this.event = event;
        populate();
    }
    
    protected <T> void setter(@Nullable E event, T result, String method) {
        setter(event,result,method,null);
    }
    
    protected <T> void setter(@Nullable E event, T result, String method, @Nullable Function<?,T> transformer) {
        if(Objects.isNull(event)) return;
        Hacks.invoke(event,method,Objects.nonNull(transformer) ? transformer.apply(cast(result)) : result);
    }
    
    @IndirectCallers
    protected <V> BiConsumer<E,V> setter(String setterMethod) {
        return (event,result) -> setter(event,result,setterMethod);
    }
    
    @IndirectCallers
    protected <V> BiConsumer<E,V> setter(String setterMethod, @Nullable Function<?,V> transformer) {
        return (event,result) -> setter(event,result,setterMethod,transformer);
    }
    
    private <T> T unwrap(Object obj) {
        return GenericUtils.cast(obj);
    }
    
    private <V> BiConsumer<E,V> wrappedSetter(BiConsumer<?,?> generic) {
        final Function<Object,V> unwrapper = wrapped -> {
            if(wrapped instanceof BlockEntityAPI<?,?>) return unwrap(((BlockEntityAPI<?,?>)wrapped).getEntity());
            if(wrapped instanceof EntityAPI<?,?>) return unwrap(((EntityAPI<?,?>)wrapped).getEntity());
            return ((Wrapped<?>)wrapped).unwrap();
        };
        return (event,api) -> generic.accept(unwrap(event),unwrap(unwrapper.apply(api)));
    }

    protected @Nullable AdvancementAPI<?> wrapAdvancement(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapAdvancement(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,AdvancementAPI<?>> wrapAdvancementBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapAdvancement(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,AdvancementAPI<?>> wrapAdvancementGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapAdvancement(getter),null);
    }

    protected @Nullable BlockAPI<?> wrapBlock(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapBlock(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,BlockAPI<?>> wrapBlockBoth(Function<E,?> getter, 
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapBlock(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,BlockAPI<?>> wrapBlockGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapBlock(getter),null);
    }

    protected @Nullable BlockEntityAPI<?,?> wrapBlockEntity(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapBlockEntity(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,BlockEntityAPI<?,?>> wrapBlockEntityBoth(Function<E,?> getter, 
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapBlockEntity(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,BlockEntityAPI<?,?>> wrapBlockEntityGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapBlockEntity(getter),null);
    }
    
    @SuppressWarnings("SameParameterValue")
    protected  EventFieldWrapper<E,ActionResult> wrapActionResultBoth(String getterMethod, String setterMethod) {
        return wrapBoth("Generic",actionResultGetter(getterMethod),actionResultSetter(setterMethod),PASS);
    }
    
    @IndirectCallers
    protected  <V,T> EventFieldWrapper<E,V> wrapBoth(String type, Function<E,?> getter, BiConsumer<E,T> setter) {
        return Hacks.invoke(this,"wrap"+type+"Both",getter,setter);
    }
    
    @SuppressWarnings("SameParameterValue")
    protected <V,T> EventFieldWrapper<E,V> wrapBoth(String type, Function<E,?> getter, BiConsumer<E,T> setter,
            V defVal) {
        return Hacks.invoke(this,"wrap"+type+"Both",getter,setter,defVal);
    }

    protected @Nullable EntityAPI<?,?> wrapEntity(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapEntity(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,EntityAPI<?,?>> wrapEntityBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapEntity(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,EntityAPI<?,?>> wrapEntityGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapEntity(getter),null);
    }
    
    protected EventFieldWrapper<E,Result> wrapEventResultBoth(String getterMethod, String setterMethod) {
        return wrapBoth("Generic",eventResultGetter(getterMethod),eventResultSetter(setterMethod),DEFAULT);
    }

    protected @Nullable ExplosionAPI<?> wrapExplosion(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapExplosion(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,ExplosionAPI<?>> wrapExplosionBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapExplosion(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,ExplosionAPI<?>> wrapExplosionGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapExplosion(getter),null);
    }
    
    @IndirectCallers
    protected  <V> EventFieldWrapper<E,V> wrapGetter(String type, Function<E,?> getter) {
        return Hacks.invoke(this,"wrap"+type+"Getter",getter);
    }
    
    @IndirectCallers
    protected <V> EventFieldWrapper<E,V> wrapGetter(String type, Function<E,?> getter, V defVal) {
        return Hacks.invoke(this,"wrap"+type+"Getter",getter,defVal);
    }

    protected @Nullable ItemAPI<?> wrapItem(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapItem(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,ItemAPI<?>> wrapItemBoth(Function<E,?> getter, BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapItem(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,ItemAPI<?>> wrapItemGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapItem(getter),null);
    }

    protected @Nullable ItemStackAPI<?> wrapItemStack(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapItemStack(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,ItemStackAPI<?>> wrapItemStackBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapItemStack(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,ItemStackAPI<?>> wrapItemStackGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapItemStack(getter),null);
    }

    protected @Nullable LivingEntityAPI<?,?> wrapLiving(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapLivingEntity(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapLiving(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapLiving(getter),null);
    }

    protected @Nullable PlayerAPI<?,?> wrapPlayer(@Nullable Function<E,?> getter) {
        return WrapperHelper.wrapPlayer(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,PlayerAPI<?,?>> wrapPlayerBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapPlayer(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,PlayerAPI<?,?>> wrapPlayerGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapPlayer(getter),null);
    }

    protected @Nullable BlockPosAPI<?> wrapPos(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapPosition(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,BlockPosAPI<?>> wrapPosBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapPos(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,BlockPosAPI<?>> wrapPosGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapPos(getter),null);
    }

    protected <V,T> EventFieldWrapper<E,V> wrapGenericBoth(Function<E,?> getter, BiConsumer<E,T> setter, V defVal) {
        return new EventFieldWrapper<>(GenericUtils.castFunction(getter),GenericUtils.castBiConsumer(setter),defVal);
    }

    protected <V> EventFieldWrapper<E,V> wrapGenericGetter(Function<E,?> getter, V defVal) {
        return new EventFieldWrapper<>(GenericUtils.castFunction(getter),defVal);
    }

    protected @Nullable BlockSnapshotAPI<?> wrapSnapshot(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapSnapshot(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,BlockSnapshotAPI<?>> wrapSnapshotBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapSnapshot(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,BlockSnapshotAPI<?>> wrapSnapshotGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapSnapshot(getter),null);
    }

    protected @Nullable BlockStateAPI<?> wrapState(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapState(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,BlockStateAPI<?>> wrapStateBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapState(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,BlockStateAPI<?>> wrapStateGetter(Function<E,?> getter) {
        return new EventFieldWrapper<>(event -> wrapState(getter),null);
    }

    protected @Nullable WorldAPI<?> wrapWorld(@Nullable Function<?,?> getter) {
        return WrapperHelper.wrapWorld(this.event,getter);
    }

    protected <V,T> EventFieldWrapper<E,WorldAPI<?>> wrapWorldBoth(Function<E,?> getter,
            BiConsumer<E,T> setter) {
        return new EventFieldWrapper<>(event -> wrapWorld(getter),wrappedSetter(setter),null);
    }

    protected <V> EventFieldWrapper<E,WorldAPI<?>> wrapWorldGetter(Function<E,?> getter) {
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
            } else TILRef.logError("Cannot add invoker to unconnected event type ({})",getClass().getName());
        }

        private boolean hasInvokers() {
            return Objects.nonNull(this.connector) && !this.invokers.isEmpty();
        }

        public <C> void invoke(C event) {
            if(Objects.nonNull(this.connector) && !this.invokers.isEmpty()) {
                if(setWrapperEvent(event,this.connector)) {
                    for(Consumer<E> invoker : this.invokers) {
                        invoker.accept(this.connector);
                        if(this.connector.isCancelable() && this.connector.isCanceled()) {
                            this.connector.cancel();
                            break;
                        }
                    }
                }
            }
        }

        public abstract boolean isClient();
        public abstract boolean isCommon();
        public abstract boolean isServer();
        
        public void removeInvoker(Consumer<E> invoker) {
            this.invokers.remove(invoker);
        }

        public <I extends E> void setConnector(I impl) {
            this.connector = impl;
        }

        /**
         * Stupid generic casting
         */
        @SuppressWarnings("unchecked")
        private <C> boolean setWrapperEvent(C event, EventWrapper<?> wrapper) {
            ((EventWrapper<C>)wrapper).setEvent(event);
            return !wrapper.canceled;
        }
    }
}