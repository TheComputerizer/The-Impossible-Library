package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.advancement.Advancement1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.Explosion1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.block.Block1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.block.BlockSnapShot1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.blockentity.BlockEntity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity.Entity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity.Living1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity.Player1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.item.Item1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.item.ItemStack1_12_2;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
    public @Nullable <BE> BlockEntityAPI<BE> wrapBlockEntity(@Nullable BE blockentity) {
        return Objects.nonNull(blockentity) ? (BlockEntityAPI<BE>)new BlockEntity1_12_2((TileEntity)blockentity) : null;
    }

    @Override
    public @Nullable <E> EntityAPI<E> wrapEntity(@Nullable E entity) {
        return Objects.nonNull(entity) ? (EntityAPI<E>)new Entity1_12_2((Entity)entity) : null;
    }

    @Override
    public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return Objects.nonNull(explosion) ? (ExplosionAPI<E>)new Explosion1_12_2((Explosion)explosion) : null;
    }

    /**
     * Would probably benefit from a switch statement
     */
    @Override
    public <G,W> @Nullable W wrapGeneric(Class<W> wrapperClass, @Nullable G generic) {
        if(wrapperClass==AdvancementAPI.class) return (W)wrapAdvancement(generic);
        if(wrapperClass==BlockAPI.class) return (W)wrapBlock(generic);
        if(wrapperClass==BlockEntityAPI.class) return (W)wrapBlockEntity(generic);
        if(wrapperClass==EntityAPI.class) return (W)wrapEntity(generic);
        if(wrapperClass==ItemAPI.class) return (W)wrapItem(generic);
        if(wrapperClass==LivingEntityAPI.class) return (W)wrapLivingEntity(generic);
        if(wrapperClass==PlayerAPI.class) return (W)wrapPlayer(generic);
        if(wrapperClass==BlockStateAPI.class) return (W)wrapState(generic);
        if(wrapperClass==WorldAPI.class) return (W)wrapWorld(generic);
        return null;
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
    public @Nullable <L> LivingEntityAPI<L> wrapLivingEntity(@Nullable L living) {
        return Objects.nonNull(living) ? (LivingEntityAPI<L>)new Living1_12_2((EntityLivingBase)living) : null;
    }

    @Override
    public @Nullable <P> PlayerAPI<P> wrapPlayer(@Nullable P player) {
        return Objects.nonNull(player) ? (PlayerAPI<P>)new Player1_12_2((EntityPlayer)player) : null;
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
