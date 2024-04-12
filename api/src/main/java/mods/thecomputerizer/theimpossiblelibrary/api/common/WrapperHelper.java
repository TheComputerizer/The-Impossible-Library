package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
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
import java.util.Objects;
import java.util.function.Function;

public class WrapperHelper {

    public static @Nullable WrapperAPI getAPI() {
        return TILRef.getCommonSubAPI("WrapperAPI",CommonAPI::getWrapperAPI);
    }

    private static <W> @Nullable W wrap(Function<WrapperAPI,W> wrapperFunc) {
        WrapperAPI api = getAPI();
        return Objects.nonNull(api) ? wrapperFunc.apply(api) : null;
    }

    public static <A> @Nullable AdvancementAPI<A> wrapAdvancement(@Nullable A advancement) {
        return wrap(api -> api.wrapAdvancement(advancement));
    }

    public static <B> @Nullable BlockAPI<B> wrapBlock(@Nullable B block) {
        return wrap(api -> api.wrapBlock(block));
    }

    public static <BE> @Nullable BlockEntityAPI<BE> wrapBlockEntity(@Nullable BE blockentity) {
        return wrap(api -> api.wrapBlockEntity(blockentity));
    }

    public static <D> @Nullable DimensionAPI<D> wrapDimension(@Nullable D dimension) {
        return wrap(api -> api.wrapDimension(dimension));
    }

    public static <E> @Nullable EntityAPI<E> wrapEntity(@Nullable E entity) {
        return wrap(api -> api.wrapEntity(entity));
    }

    public static <E> @Nullable ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return wrap(api -> api.wrapExplosion(explosion));
    }

    public static <G,W> @Nullable W wrapGeneric(Class<W> wrapperClass, @Nullable G generic) {
        return wrap(api -> api.wrapGeneric(wrapperClass,generic));
    }

    public static <I> @Nullable ItemAPI<I> wrapItem(@Nullable I item) {
        return wrap(api -> api.wrapItem(item));
    }

    public static <S> @Nullable ItemStackAPI<S> wrapItemStack(@Nullable S stack) {
        return wrap(api -> api.wrapItemStack(stack));
    }

    public static <L> @Nullable LivingEntityAPI<L> wrapLivingEntity(@Nullable L living) {
        return wrap(api -> api.wrapLivingEntity(living));
    }

    public static <P> @Nullable PlayerAPI<P> wrapPlayer(@Nullable P player) {
        return wrap(api -> api.wrapPlayer(player));
    }

    public static <S> @Nullable BlockSnapshotAPI<S> wrapSnapshot(@Nullable S state) {
        return wrap(api -> api.wrapSnapshot(state));
    }

    public static <S> @Nullable BlockStateAPI<S> wrapState(@Nullable S state) {
        return wrap(api -> api.wrapState(state));
    }

    public static <W> @Nullable WorldAPI<W> wrapWorld(@Nullable W world) {
        return wrap(api -> api.wrapWorld(world));
    }
}