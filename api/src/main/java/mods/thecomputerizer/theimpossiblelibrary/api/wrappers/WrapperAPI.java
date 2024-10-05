package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
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

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public interface WrapperAPI {
    
    default <A> A getAs(@Nullable Object toWrap, Function<Object,Object> wrapper) {
        return getAs(toWrap,wrapper,null);
    }
    
    @SuppressWarnings("unchecked")
    default <A> A getAs(@Nullable Object toWrap, Function<Object,Object> wrapper, @Nullable Supplier<Object> ifNull) {
        return Objects.nonNull(toWrap) ? (A)wrapper.apply(toWrap) :
                (Objects.nonNull(ifNull) ? (A)wrapper.apply(ifNull.get()) : null);
    }

    <A> @Nullable AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement);
    <B> @Nullable BiomeAPI<B> wrapBiome(@Nullable Object biome);
    <B> @Nullable BlockAPI<B> wrapBlock(@Nullable Object block);
    <B> @Nullable BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object blockentity);
    <S> @Nullable CommandSenderAPI<S> wrapCommandSender(@Nullable Object sender);
    <D> @Nullable DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension);
    <E> @Nullable EffectAPI<E> wrapEffect(@Nullable Object effect);
    <I> @Nullable EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object instance);
    <E> @Nullable EntityAPI<E,?> wrapEntity(@Nullable Object entity);
    <E> @Nullable ExplosionAPI<E> wrapExplosion(@Nullable Object explosion);

    @SuppressWarnings("unchecked") //TODO This is wrong since it checks the wrapper class instead of the wrapped class
    default <G,W> @Nullable W wrapGeneric(Class<W> wrapperClass, @Nullable Object generic) {
        if(AdvancementAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapAdvancement(generic);
        if(BiomeAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapBiome(generic);
        if(BlockAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapBlock(generic);
        if(BlockEntityAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapBlockEntity(generic);
        if(EffectAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapEffect(generic);
        if(EffectInstanceAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapEffectInstance(generic);
        if(PlayerAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapPlayer(generic);
        if(LivingEntityAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapLivingEntity(generic);
        if(EntityAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapEntity(generic);
        if(InventoryAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapInventory(generic);
        if(ItemAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapItem(generic);
        if(ItemAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapItem(generic);
        if(ItemStackAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapItemStack(generic);
        if(MaterialAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapMaterial(generic);
        if(PlayerInventoryAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapPlayerInventory(generic);
        if(PotionAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapPotion(generic);
        if(BlockPosAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapPosition(generic);
        if(ResourceLocationAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapResourceLocation(generic);
        if(BlockSnapshotAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapSnapshot(generic);
        if(SoundEventAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapSoundEvent(generic);
        if(SoundAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapSoundInstance(generic);
        if(BlockStateAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapState(generic);
        if(StructureAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapStructure(generic);
        if(WorldAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapWorld(generic);
        return null;
    }

    <I> @Nullable InventoryAPI<I> wrapInventory(@Nullable Object inventory);
    <I> @Nullable ItemAPI<I> wrapItem(@Nullable Object item);
    <S> ItemStackAPI<S> wrapItemStack(@Nullable Object stack);
    <L> @Nullable LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object living);
    <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable Object material);
    <P> @Nullable PlayerAPI<P,?> wrapPlayer(@Nullable Object player);
    <I> @Nullable PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object inventory);
    <P> BlockPosAPI<P> wrapPosition(@Nullable Object position);
    <P> @Nullable PotionAPI<P> wrapPotion(@Nullable Object potion);
    <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object resourceLocation);
    <S> @Nullable BlockSnapshotAPI<S> wrapSnapshot(@Nullable Object snapshot);
    <S> @Nullable SoundEventAPI<S> wrapSoundEvent(@Nullable Object soundEvent);
    <S> @Nullable SoundAPI<S> wrapSoundInstance(@Nullable Object sound);
    <S> @Nullable BlockStateAPI<S> wrapState(@Nullable Object state);
    <S> StructureAPI<S> wrapStructure(@Nullable Object structure);
    <W> @Nullable WorldAPI<W> wrapWorld(@Nullable Object world);
}