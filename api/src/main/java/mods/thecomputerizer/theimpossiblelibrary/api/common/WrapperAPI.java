package mods.thecomputerizer.theimpossiblelibrary.api.common;

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

public interface WrapperAPI {

    <A> @Nullable AdvancementAPI<A> wrapAdvancement(@Nullable A advancement);
    <B> @Nullable BiomeAPI<B> wrapBiome(@Nullable B biome);
    <B> @Nullable BlockAPI<B> wrapBlock(@Nullable B block);
    <BE> @Nullable BlockEntityAPI<BE,?> wrapBlockEntity(@Nullable BE blockentity);
    <D> @Nullable DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable D dimension);
    <E> @Nullable EffectAPI<E> wrapEffect(@Nullable E effect);
    <I> @Nullable EffectInstanceAPI<I> wrapEffectInstance(@Nullable I instance);
    <E> @Nullable EntityAPI<E,?> wrapEntity(@Nullable E entity);
    <E> @Nullable ExplosionAPI<E> wrapExplosion(@Nullable E explosion);

    @SuppressWarnings("unchecked")
    default <G,W> @Nullable W wrapGeneric(Class<W> wrapperClass, @Nullable G generic) {
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
        if(ResourceLocationAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapResourceLocation(generic);
        if(BlockSnapshotAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapSnapshot(generic);
        if(SoundEventAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapSoundEvent(generic);
        if(SoundAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapSoundInstance(generic);
        if(BlockStateAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapState(generic);
        if(StructureAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapStructure(generic);
        if(WorldAPI.class.isAssignableFrom(wrapperClass)) return (W)wrapWorld(generic);
        return null;
    }

    <I> @Nullable InventoryAPI<I> wrapInventory(@Nullable I inventory);
    <I> @Nullable ItemAPI<I> wrapItem(@Nullable I item);
    <S> ItemStackAPI<S> wrapItemStack(@Nullable S stack);
    <L> @Nullable LivingEntityAPI<L,?> wrapLivingEntity(@Nullable L living);
    <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable M material);
    <P> @Nullable PlayerAPI<P,?> wrapPlayer(@Nullable P player);
    <I> @Nullable PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable I inventory);
    <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable R resourceLocation);
    <P> @Nullable PotionAPI<P> wrapPotion(@Nullable P potion);
    <S> @Nullable BlockSnapshotAPI<S> wrapSnapshot(@Nullable S snapshot);
    <S> @Nullable SoundEventAPI<S> wrapSoundEvent(@Nullable S soundEvent);
    <S> @Nullable SoundAPI<S> wrapSoundInstance(@Nullable S sound);
    <S> @Nullable BlockStateAPI<S> wrapState(@Nullable S state);
    <S> StructureAPI<S> wrapStructure(@Nullable S structure);
    <W> @Nullable WorldAPI<W> wrapWorld(@Nullable W world);
}