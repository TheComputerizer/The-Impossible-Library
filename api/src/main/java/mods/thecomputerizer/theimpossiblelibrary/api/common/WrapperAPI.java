package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemAPI;

import javax.annotation.Nullable;

public interface WrapperAPI {

    <A> @Nullable AdvancementAPI<A> wrapAdvancement(@Nullable A advancement);
    <B> @Nullable BlockAPI<B> wrapBlock(@Nullable B block);
    <BE> @Nullable BlockEntityAPI<BE> wrapBlockEntity(@Nullable BE blockentity);
    <E> @Nullable EntityAPI<E> wrapEntity(@Nullable E entity);
    <G,W> @Nullable W wrapGeneric(Class<W> wrapperClass, @Nullable G generic);
    <I> @Nullable ItemAPI<I> wrapItem(@Nullable I item);
    <L> @Nullable LivingEntityAPI<L> wrapLivingEntity(@Nullable L living);
    <P> @Nullable PlayerAPI<P> wrapPlayer(@Nullable P player);
    <S> @Nullable BlockStateAPI<S> wrapState(@Nullable S state);
    <W> @Nullable WorldAPI<W> wrapWorld(@Nullable W world);
}