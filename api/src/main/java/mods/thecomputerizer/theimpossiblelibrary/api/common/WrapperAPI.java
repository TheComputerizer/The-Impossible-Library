package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;

import javax.annotation.Nullable;

public interface WrapperAPI {

    <A> @Nullable AdvancementAPI<A> wrapAdvancement(@Nullable A advancement);
    <B> @Nullable BlockAPI<B> wrapBlock(@Nullable B block);
    <BE> @Nullable BlockEntityAPI<BE> wrapBlockEntity(@Nullable BE blockentity);
    <E> @Nullable EntityAPI<E> wrapEntity(@Nullable E entity);
    <E> @Nullable ExplosionAPI<E> wrapExplosion(@Nullable E explosion);
    <G,W> @Nullable W wrapGeneric(Class<W> wrapperClass, @Nullable G generic);
    <I> @Nullable ItemAPI<I> wrapItem(@Nullable I item);
    <S> @Nullable ItemStackAPI<S> wrapItemStack(@Nullable S stack);
    <L> @Nullable LivingEntityAPI<L> wrapLivingEntity(@Nullable L living);
    <P> @Nullable PlayerAPI<P> wrapPlayer(@Nullable P player);
    <S> @Nullable BlockSnapshotAPI<S> wrapSnapshot(@Nullable S snapshot);
    <S> @Nullable BlockStateAPI<S> wrapState(@Nullable S state);
    <W> @Nullable WorldAPI<W> wrapWorld(@Nullable W world);
}