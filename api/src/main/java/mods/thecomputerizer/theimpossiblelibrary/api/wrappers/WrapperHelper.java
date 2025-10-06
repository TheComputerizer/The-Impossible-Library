package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class WrapperHelper {
    
    static final Logger LOGGER = TILRef.createLogger("TIL Wrapper Helper");
    
    public static <W,G> Collector<W,G,Collection<W>> defaultCollector() {
        return GenericUtils.cast(Collectors.toList());
    }
    
    private static @Nullable Object fixGenericGetter(@Nullable Object source, @Nullable Function<?,?> getter) {
        return Objects.nonNull(source) && Objects.nonNull(getter) ? getter.apply(GenericUtils.cast(source)) : null;
    }

    public static WrapperAPI getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getWrapper);
    }
    
    public static WrapperType getWrapperType(String name) {
        return WrapperType.getByName(name);
    }
    
    public static <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapAdvancement(fixGenericGetter(source,getter));
    }
    
    public static <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement) {
        return getAPI().wrapAdvancement(advancement);
    }
    
    public static <A> BiomeAPI<A> wrapBiome(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapBiome(fixGenericGetter(source,getter));
    }
    
    public static <B> BiomeAPI<B> wrapBiome(@Nullable Object biome) {
        return getAPI().wrapBiome(biome);
    }
    
    public static <B> BlockAPI<B> wrapBlock(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapBlock(fixGenericGetter(source,getter));
    }

    public static <B> BlockAPI<B> wrapBlock(@Nullable Object block) {
        return getAPI().wrapBlock(block);
    }
    
    public static <B> BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapBlockEntity(fixGenericGetter(source,getter));
    }

    public static <B> BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object blockentity) {
        return getAPI().wrapBlockEntity(blockentity);
    }
    
    @IndirectCallers
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            String typeName) {
        return wrapCollection(collection,getWrapperType(typeName));
    }
    
    @IndirectCallers
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            String typeName, boolean mutableIfEmpty) {
        return wrapCollection(collection,getWrapperType(typeName),mutableIfEmpty);
    }
    
    @IndirectCallers
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            String typeName, @Nullable Collector<A,?,Collection<A>> collector) {
        return wrapCollection(collection,getWrapperType(typeName),collector);
    }
    
    @IndirectCallers
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            String typeName, boolean mutableIfEmpty, @Nullable Collector<A,?,Collection<A>> collector) {
        return wrapCollection(collection,getWrapperType(typeName),mutableIfEmpty,collector);
    }
    
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            WrapperType type) {
        return wrapCollection(collection,type::wrap);
    }
    
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            WrapperType type, boolean mutableIfEmpty) {
        return wrapCollection(collection,type::wrap,mutableIfEmpty);
    }
    
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            WrapperType type, @Nullable Collector<A,?,Collection<A>> collector) {
        return wrapCollection(collection,type::wrap,collector);
    }
    
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            WrapperType type, boolean mutableIfEmpty, @Nullable Collector<A,?,Collection<A>> collector) {
        return wrapCollection(collection,type::wrap,mutableIfEmpty,collector);
    }
    
    @IndirectCallers
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            Function<Object,A> wrapperFunc) {
        return wrapCollection(collection,wrapperFunc,false,null);
    }
    
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            Function<Object,A> wrapperFunc, boolean mutableIfEmpty) {
        return wrapCollection(collection,wrapperFunc,mutableIfEmpty,null);
    }
    
    @IndirectCallers
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            Function<Object,A> wrapperFunc, @Nullable Collector<A,?,Collection<A>> collector) {
        return wrapCollection(collection,wrapperFunc,false,collector);
    }
    
    public static <A extends AbstractWrapped<?>> Collection<A> wrapCollection(@Nullable Collection<?> collection,
            Function<Object,A> wrapperFunc, boolean mutableIfEmpty, @Nullable Collector<A,?,Collection<A>> collector) {
        if(Objects.isNull(collection) || collection.isEmpty())
            return mutableIfEmpty ? new ArrayList<>() : Collections.emptyList();
        if(Objects.isNull(collector)) collector = defaultCollector();
        return collection.stream().map(wrapperFunc).collect(collector);
    }
    
    public static <S> CommandSenderAPI<S> wrapCommandSender(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapCommandSender(fixGenericGetter(source,getter));
    }
    
    public static <S> CommandSenderAPI<S> wrapCommandSender(@Nullable Object sender) {
        return getAPI().wrapCommandSender(sender);
    }
    
    public static <S> DamageAPI<S> wrapDamage(@Nullable Object source, @Nullable Function<?,?> getter,
            @Nullable Function<?,Float> getAmount) {
        return wrapDamage(fixGenericGetter(source,getter),getAmount);
    }
    
    public static <S> DamageAPI<S> wrapDamage(@Nullable Object source, @Nullable Function<?,?> getter, float amount) {
        return wrapDamage(fixGenericGetter(source,getter),amount);
    }
    
    public static <S> DamageAPI<S> wrapDamage(@Nullable Object source, @Nullable Function<?,Float> getAmount) {
        float amount = 0f;
        if(Objects.nonNull(source) && Objects.nonNull(getAmount)) amount = getAmount.apply(GenericUtils.cast(source));
        return wrapDamage(source,amount);
    }
    
    public static <S> DamageAPI<S> wrapDamage(@Nullable Object source, float amount) {
        return getAPI().wrapDamage(source,amount);
    }
    
    public static <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object source,
            @Nullable Function<?,?> getter) {
        return wrapDimension(world,fixGenericGetter(source,getter));
    }

    public static <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAPI().wrapDimension(world,dimension);
    }
    
    public static <E> EffectAPI<E> wrapEffect(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapEffect(fixGenericGetter(source,getter));
    }

    public static <E> EffectAPI<E> wrapEffect(@Nullable Object effect) {
        return getAPI().wrapEffect(effect);
    }
    
    @IndirectCallers
    public static <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapEffectInstance(fixGenericGetter(source,getter));
    }

    public static <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object instance) {
        return getAPI().wrapEffectInstance(instance);
    }
    
    public static <E> EntityAPI<E,?> wrapEntity(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapEntity(fixGenericGetter(source,getter));
    }

    public static <E> EntityAPI<E,?> wrapEntity(@Nullable Object entity) {
        return getAPI().wrapEntity(entity);
    }
    
    @IndirectCallers
    public static <E> ExplosionAPI<E> wrapExplosion(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapExplosion(fixGenericGetter(source,getter));
    }

    public static <E> ExplosionAPI<E> wrapExplosion(@Nullable Object explosion) {
        return getAPI().wrapExplosion(explosion);
    }
    
    @IndirectCallers
    public static <W> W wrapGeneric(Class<W> wrapperClass, @Nullable Object generic) {
        return getAPI().wrapGeneric(wrapperClass,generic);
    }
    
    @IndirectCallers
    public static <I> InventoryAPI<I> wrapInventory(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapInventory(fixGenericGetter(source,getter));
    }

    @IndirectCallers
    public static <I> InventoryAPI<I> wrapInventory(@Nullable Object inventory) {
        return getAPI().wrapInventory(inventory);
    }
    
    public static <I> ItemAPI<I> wrapItem(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapItem(fixGenericGetter(source,getter));
    }

    public static <I> ItemAPI<I> wrapItem(@Nullable Object item) {
        return getAPI().wrapItem(item);
    }
    
    public static <S> ItemStackAPI<S> wrapItemStack(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapItemStack(fixGenericGetter(source,getter));
    }

    public static <S> ItemStackAPI<S> wrapItemStack(@Nullable Object stack) {
        return getAPI().wrapItemStack(stack);
    }
    
    public static <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapLivingEntity(fixGenericGetter(source,getter));
    }

    public static <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object living) {
        return getAPI().wrapLivingEntity(living);
    }
    
    public static <M> MaterialAPI<M> wrapMaterial(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapMaterial(fixGenericGetter(source,getter));
    }

    public static <M> MaterialAPI<M> wrapMaterial(@Nullable Object material) {
        return getAPI().wrapMaterial(material);
    }
    
    public static <P> PlayerAPI<P,?> wrapPlayer(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapPlayer(fixGenericGetter(source,getter));
    }

    public static <P> PlayerAPI<P,?> wrapPlayer(@Nullable Object player) {
        return getAPI().wrapPlayer(player);
    }
    
    public static <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapPlayerInventory(fixGenericGetter(source,getter));
    }

    public static <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object inventory) {
        return getAPI().wrapPlayerInventory(inventory);
    }
    
    @IndirectCallers
    public static <P> PotionAPI<P> wrapPotion(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapPotion(fixGenericGetter(source,getter));
    }
    
    @IndirectCallers
    public static <P> PotionAPI<P> wrapPotion(@Nullable Object potion) {
        return getAPI().wrapPotion(potion);
    }
    
    public static <P> BlockPosAPI<P> wrapPosition(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapPosition(fixGenericGetter(source,getter));
    }
    
    public static <P> BlockPosAPI<P> wrapPosition(@Nullable Object pos) {
        return getAPI().wrapPosition(pos);
    }
    
    public static <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapResourceLocation(fixGenericGetter(source,getter));
    }
    
    public static <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object resourceLocation) {
        return getAPI().wrapResourceLocation(resourceLocation);
    }
    
    public static <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapSnapshot(fixGenericGetter(source,getter));
    }

    public static <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable Object state) {
        return getAPI().wrapSnapshot(state);
    }
    
    public static <S> @Nullable SoundEventAPI<S> wrapSoundEvent(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapSoundEvent(fixGenericGetter(source,getter));
    }
    
    public static <S> @Nullable SoundEventAPI<S> wrapSoundEvent(@Nullable Object soundEvent) {
        return getAPI().wrapSoundEvent(soundEvent);
    }
    
    public static <S> @Nullable SoundAPI<S> wrapSoundInstance(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapSoundInstance(fixGenericGetter(source,getter));
    }
    
    public static <S> @Nullable SoundAPI<S> wrapSoundInstance(@Nullable Object sound) {
        return getAPI().wrapSoundInstance(sound);
    }
    
    public static <S> BlockStateAPI<S> wrapState(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapState(fixGenericGetter(source,getter));
    }

    public static <S> BlockStateAPI<S> wrapState(@Nullable Object state) {
        return getAPI().wrapState(state);
    }
    
    public static <S> StructureAPI<S> wrapStructure(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapStructure(fixGenericGetter(source,getter));
    }
    
    @IndirectCallers
    public static <S> StructureAPI<S> wrapStructure(@Nullable Object structure) {
        return getAPI().wrapStructure(structure);
    }
    
    public static <T> CreativeTabAPI<T> wrapTab(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapTab(fixGenericGetter(source,getter));
    }
    
    public static <T> CreativeTabAPI<T> wrapTab(@Nullable Object tab) {
        return getAPI().wrapTab(tab);
    }
    
    public static <W> WorldAPI<W> wrapWorld(@Nullable Object source, @Nullable Function<?,?> getter) {
        return wrapWorld(fixGenericGetter(source,getter));
    }

    public static <W> WorldAPI<W> wrapWorld(@Nullable Object world) {
        return getAPI().wrapWorld(world);
    }
    
    public enum WrapperType {
        
        ADVANCEMENT("advancement"),
        BIOME("biome"),
        BLOCK("block"),
        BLOCK_ENTITY("block_entity","blockentity"),
        BLOCK_SNAPSHOT("snapshot","block_snapshot","blocksnapshot"),
        BLOCK_STATE("state","block_state","blockstate"),
        COMMAND_SENDER("command_sender","commandsender"),
        CREATIVE_MODE_TAB("tab","creative_mode_tab","creative_modetab","creativemode_tab",
                          "creativemodetab","creative_tab","creativetab"),
        DAMAGE("damage"),
        EFFECT("effect"),
        EFFECT_INSTANCE("effect_instance","effectinstance"),
        ENTITY("entity"),
        EXPLOSION("explosion"),
        INVENTORY("inventory"),
        ITEM("item"),
        ITEM_STACK("item_stack","itemstack"),
        LIVING_ENTITY("living_entity","livingentity","living"),
        MATERIAL("material"),
        PLAYER("player","server_player","serverplayer","remote_player","remoteplayer","local_player",
               "localplayer","client_player","clientplayer"),
        PLAYER_INVENTORY("player_inventory","playerinventory"),
        POTION("potion"),
        POSITION("position","block_position","blockposition","block_pos","blockpos","pos"),
        RESOURCE_LOCATION("resource_location","resourcelocation","resource_name","resourcename",
                          "resource","name","identifier","id"),
        SOUND_EVENT("sound_event","soundevent"),
        SOUND_INSTANCE("sound_instance","soundinstance","sound"),
        STRUCTURE("structure","structure_feature","structurefeature","configured_structure",
                  "configuredstructure","configured_structure_feature","configured_structurefeature",
                  "configuredstructure_feature","configuredstructurefeature"),
        WORLD("world","level","server_world","serverworld","server_level","serverlevel","remote_world",
              "remoteworld","remote_level","remotelevel","local_world","localworld","local_level","locallevel",
              "client_world","clientworld","client_level","clientlevel");
        
        static final Map<String,WrapperType> ALIAS_MAP = new HashMap<>();
        
        /**
         * Returns the first WrapperType that has a name or alias matching the input or null if nothing is found
         */
        public static WrapperType getByName(String alias) {
            if(Objects.isNull(alias) || alias.isEmpty()) return null;
            if(ALIAS_MAP.containsKey(alias)) return ALIAS_MAP.get(alias);
            for(WrapperType type : values()) { //Check only the names first
                if(type.name.equals(alias)) {
                    ALIAS_MAP.put(alias,type);
                    return type;
                }
            }
            for(WrapperType type : values()) { //Check the aliases after all names have been checked
                for(String typeAlias : type.aliases) {
                    if(typeAlias.equals(alias)) {
                        ALIAS_MAP.put(alias,type);
                        return type;
                    }
                }
            }
            return null; //No names or aliases match
        }
        
        static String wrapperMethodName(final String name) {
            if(!name.contains("_")) return "wrap"+TextHelper.capitalize(name);
            StringBuilder methodName = new StringBuilder("wrap");
            for(String part : name.split("_")) methodName.append(TextHelper.capitalize(part));
            return methodName.toString();
        }
        
        final String name;
        final String[] aliases;
        final String wrapperMethod;
        WrapperType(final String name, final String ... aliases) {
            this.name = name;
            this.aliases = aliases;
            this.wrapperMethod = wrapperMethodName(name);
        }
        
        public <A extends AbstractWrapped<?>> A wrap(@Nullable Object toWrap) {
            A wrapped = Hacks.invokeStatic(WrapperHelper.class,this.wrapperMethod,toWrap);
            if(Objects.isNull(wrapped)) LOGGER.error("Failed to wrap {} using type {}",toWrap,this.name);
            return wrapped;
        }
        
        public <A extends AbstractWrapped<?>> A wrap(@Nullable Object source, @Nullable Function<?,?> getter) {
            A wrapped = Hacks.invokeStatic(WrapperHelper.class,this.wrapperMethod,source,getter);
            if(Objects.isNull(wrapped))
                LOGGER.error("Failed to wrap {} via getter {} using type {}",source,getter,this.name);
            return wrapped;
        }
        
        public <A extends AbstractWrapped<?>> Collection<A> wrapCollectionGetter(@Nullable Object source,
                @Nullable Function<Object,Collection<?>> getter) {
            Collection<?> collection = Objects.nonNull(source) && Objects.nonNull(getter) ? getter.apply(source) : null;
            return wrapCollection(collection,this);
        }
    }
}