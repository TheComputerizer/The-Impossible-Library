package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.advancement.Advancement1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockSnapShot1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.Material1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity.BlockEntity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.entity.ClientPlayer1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Entity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Living1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity.ServerPlayer1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.Dimension1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.Explosion1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class Wrapper1_16_5 implements WrapperAPI {

    @Override
    public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable A advancement) {
        return Objects.nonNull(advancement) ? (AdvancementAPI<A>)new Advancement1_16_5((Advancement)advancement) : null;
    }

    @Override
    public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable B block) {
        return Objects.nonNull(block) ? (BlockAPI<B>)new Block1_16_5((Block)block) : null;
    }

    @Override
    public @Nullable <BE> BlockEntityAPI<BE,?> wrapBlockEntity(@Nullable BE blockentity) {
        return Objects.nonNull(blockentity) ? (BlockEntityAPI<BE,?>)new BlockEntity1_16_5((TileEntity)blockentity) : null;
    }

    @Override
    public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable D dimension) {
        return Objects.nonNull(dimension) ?
                (DimensionAPI<D>)new Dimension1_16_5((World1_16_5)world,(DimensionType)dimension) : null;
    }

    @Override
    public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable E entity) {
        return Objects.nonNull(entity) ? (EntityAPI<E,?>)new Entity1_16_5((Entity)entity) : null;
    }

    @Override
    public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return Objects.nonNull(explosion) ? (ExplosionAPI<E>)new Explosion1_16_5((Explosion)explosion) : null;
    }

    @Override
    public @Nullable <I> ItemAPI<I> wrapItem(@Nullable I item) {
        return Objects.nonNull(item) ? (ItemAPI<I>)new Item1_16_5((Item)item) : null;
    }

    @Override
    public @Nullable <S> ItemStackAPI<S> wrapItemStack(@Nullable S stack) {
        return Objects.nonNull(stack) ? (ItemStackAPI<S>)new ItemStack1_16_5((ItemStack)stack) : null;
    }

    @Override
    public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable L living) {
        return Objects.nonNull(living) ? (LivingEntityAPI<L,?>)new Living1_16_5((LivingEntity)living) : null;
    }

    @Override
    public <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable M material) {
        return Objects.nonNull(material) ? (MaterialAPI<M>)new Material1_16_5((Material)material) : null;
    }

    @Override
    public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable P p) {
        if(!(p instanceof PlayerEntity)) return null;
        PlayerEntity player = (PlayerEntity)p;
        return (PlayerAPI<P,?>)(player instanceof ServerPlayerEntity ? wrapPlayerServer(player) : wrapPlayerClient(player));
    }

    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerClient(E player) {
        return (PlayerAPI<E,?>)new ClientPlayer1_16_5((ClientPlayerEntity)player);
    }

    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerServer(E player) {
        return (PlayerAPI<E,?>)new ServerPlayer1_16_5((ServerPlayerEntity)player);
    }

    @Override
    public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable S snapshot) {
        return Objects.nonNull(snapshot) ? (BlockSnapshotAPI<S>)new BlockSnapShot1_16_5((BlockSnapshot)snapshot) : null;
    }

    @Override
    public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable S state) {
        return Objects.nonNull(state) ? (BlockStateAPI<S>)new BlockState1_16_5((BlockState)state) : null;
    }

    @Override
    public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable W world) {
        return Objects.nonNull(world) ? (WorldAPI<W>)new World1_16_5((World)world) : null;
    }
}
