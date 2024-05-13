package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;

@SuppressWarnings("unused") public class WrapperHelper {

    public static WrapperAPI getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getWrapper);
    }

    public static <A> AdvancementAPI<A> wrapAdvancement(@Nullable A advancement) {
        return getAPI().wrapAdvancement(advancement);
    }

    public static <B> BlockAPI<B> wrapBlock(@Nullable B block) {
        return getAPI().wrapBlock(block);
    }

    public static <BE> BlockEntityAPI<BE,?> wrapBlockEntity(@Nullable BE blockentity) {
        return getAPI().wrapBlockEntity(blockentity);
    }

    public static <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable D dimension) {
        return getAPI().wrapDimension(world,dimension);
    }

    public static <E> EffectAPI<E> wrapEffect(@Nullable E effect) {
        return getAPI().wrapEffect(effect);
    }

    public static <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable I instance) {
        return getAPI().wrapEffectInstance(instance);
    }

    public static <E> EntityAPI<E,?> wrapEntity(@Nullable E entity) {
        return getAPI().wrapEntity(entity);
    }

    public static <E> ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return getAPI().wrapExplosion(explosion);
    }

    public static <G,W> W wrapGeneric(Class<W> wrapperClass, @Nullable G generic) {
        return getAPI().wrapGeneric(wrapperClass,generic);
    }

    public static <I> InventoryAPI<I> wrapInventory(@Nullable I inventory) {
        return getAPI().wrapInventory(inventory);
    }

    public static <I> ItemAPI<I> wrapItem(@Nullable I item) {
        return getAPI().wrapItem(item);
    }

    public static <S> ItemStackAPI<S> wrapItemStack(@Nullable S stack) {
        return getAPI().wrapItemStack(stack);
    }

    public static <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable L living) {
        return getAPI().wrapLivingEntity(living);
    }

    public static <M> MaterialAPI<M> wrapMaterial(@Nullable M material) {
        return getAPI().wrapMaterial(material);
    }

    public static <P> PlayerAPI<P,?> wrapPlayer(@Nullable P player) {
        return getAPI().wrapPlayer(player);
    }

    public static <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable I inventory) {
        return getAPI().wrapPlayerInventory(inventory);
    }

    public static <P> PotionAPI<P> wrapPotion(@Nullable P potion) {
        return getAPI().wrapPotion(potion);
    }

    public static <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable S state) {
        return getAPI().wrapSnapshot(state);
    }

    public static <S> BlockStateAPI<S> wrapState(@Nullable S state) {
        return getAPI().wrapState(state);
    }

    public static <W> WorldAPI<W> wrapWorld(@Nullable W world) {
        return getAPI().wrapWorld(world);
    }
}