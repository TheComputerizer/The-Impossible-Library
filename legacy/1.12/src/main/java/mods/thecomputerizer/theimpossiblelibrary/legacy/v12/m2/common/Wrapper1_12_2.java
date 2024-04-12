package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

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
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.advancement.Advancement1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Block1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockSnapShot1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Material1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.entity.ClientPlayer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Living1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.entity.ServerPlayer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.Dimension1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.Explosion1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
public class Wrapper1_12_2 implements WrapperAPI {

    @Override
    public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable A advancement) {
        return Objects.nonNull(advancement) ? (AdvancementAPI<A>)new Advancement1_12_2((Advancement)advancement) : null;
    }

    @Override
    public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable B block) {
        return Objects.nonNull(block) ? (BlockAPI<B>)new Block1_12_2((Block)block) : null;
    }

    @Override
    public @Nullable <BE> BlockEntityAPI<BE,?> wrapBlockEntity(@Nullable BE blockentity) {
        return Objects.nonNull(blockentity) ? (BlockEntityAPI<BE,?>)new BlockEntity1_12_2((TileEntity)blockentity) : null;
    }

    @Override
    public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable D dimension) {
        return Objects.nonNull(dimension) ?
                (DimensionAPI<D>)new Dimension1_12_2((World1_12_2)world,(DimensionType)dimension) : null;
    }

    @Override
    public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable E entity) {
        return Objects.nonNull(entity) ? (EntityAPI<E,?>)new Entity1_12_2((Entity)entity) : null;
    }

    @Override
    public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return Objects.nonNull(explosion) ? (ExplosionAPI<E>)new Explosion1_12_2((Explosion)explosion) : null;
    }

    @Override
    public @Nullable <I> ItemAPI<I> wrapItem(@Nullable I item) {
        return Objects.nonNull(item) ? (ItemAPI<I>)new Item1_12_2((Item)item) : null;
    }

    @Override
    public @Nullable <S> ItemStackAPI<S> wrapItemStack(@Nullable S stack) {
        return Objects.nonNull(stack) ? (ItemStackAPI<S>)new ItemStack1_12_2((ItemStack)stack) : null;
    }

    @Override
    public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable L living) {
        return Objects.nonNull(living) ? (LivingEntityAPI<L,?>)new Living1_12_2((EntityLivingBase)living) : null;
    }

    @Override
    public <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable M material) {
        return Objects.nonNull(material) ? (MaterialAPI<M>)new Material1_12_2((Material)material) : null;
    }

    @Override
    public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable P p) {
        if(!(p instanceof EntityPlayer)) return null;
        EntityPlayer player = (EntityPlayer)p;
        return (PlayerAPI<P,?>)(player instanceof EntityPlayerMP ? wrapPlayerServer(player) : wrapPlayerClient(player));
    }

    private <E extends EntityPlayer> PlayerAPI<E,?> wrapPlayerClient(E player) {
        return (PlayerAPI<E,?>)new ClientPlayer1_12_2((EntityPlayerSP)player);
    }

    private <E extends EntityPlayer> PlayerAPI<E,?> wrapPlayerServer(E player) {
        return (PlayerAPI<E,?>)new ServerPlayer1_12_2((EntityPlayerMP)player);
    }

    @Override
    public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable S snapshot) {
        return Objects.nonNull(snapshot) ? (BlockSnapshotAPI<S>)new BlockSnapShot1_12_2((BlockSnapshot)snapshot) : null;
    }

    @Override
    public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable S state) {
        return Objects.nonNull(state) ? (BlockStateAPI<S>)new BlockState1_12_2((IBlockState)state) : null;
    }

    @Override
    public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable W world) {
        return Objects.nonNull(world) ? (WorldAPI<W>)new World1_12_2((World)world) : null;
    }
}
