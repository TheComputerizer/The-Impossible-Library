package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.advancement.AdvancementLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.world.ExplosionLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.world.WorldLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.block.BlockLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.block.BlockSnapShotLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.block.BlockStateLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.blockentity.BlockEntityLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.EntityLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.PlayerLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.item.ItemLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.item.ItemStackLegacy;
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
public class WrapperLegacy implements WrapperAPI {

    @Override
    public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable A advancement) {
        return Objects.nonNull(advancement) ? (AdvancementAPI<A>)new AdvancementLegacy((Advancement)advancement) : null;
    }

    @Override
    public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable B block) {
        return Objects.nonNull(block) ? (BlockAPI<B>)new BlockLegacy((Block)block) : null;
    }

    @Override
    public @Nullable <BE> BlockEntityAPI<BE> wrapBlockEntity(@Nullable BE blockentity) {
        return Objects.nonNull(blockentity) ? (BlockEntityAPI<BE>)new BlockEntityLegacy((TileEntity)blockentity) : null;
    }

    @Override
    public @Nullable <E> EntityAPI<E> wrapEntity(@Nullable E entity) {
        return Objects.nonNull(entity) ? (EntityAPI<E>)new EntityLegacy((Entity)entity) : null;
    }

    @Override
    public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return Objects.nonNull(explosion) ? (ExplosionAPI<E>)new ExplosionLegacy((Explosion)explosion) : null;
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
        return Objects.nonNull(item) ? (ItemAPI<I>)new ItemLegacy((Item)item) : null;
    }

    @Override
    public @Nullable <S> ItemStackAPI<S> wrapItemStack(@Nullable S stack) {
        return Objects.nonNull(stack) ? (ItemStackAPI<S>)new ItemStackLegacy((ItemStack)stack) : null;
    }

    @Override
    public @Nullable <L> LivingEntityAPI<L> wrapLivingEntity(@Nullable L living) {
        return Objects.nonNull(living) ? (LivingEntityAPI<L>)new LivingLegacy((EntityLivingBase)living) : null;
    }

    @Override
    public @Nullable <P> PlayerAPI<P> wrapPlayer(@Nullable P player) {
        return Objects.nonNull(player) ? (PlayerAPI<P>)new PlayerLegacy((EntityPlayer)player) : null;
    }

    @Override
    public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable S snapshot) {
        return Objects.nonNull(snapshot) ? (BlockSnapshotAPI<S>)new BlockSnapShotLegacy((BlockSnapshot)snapshot) : null;
    }

    @Override
    public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable S state) {
        return Objects.nonNull(state) ? (BlockStateAPI<S>)new BlockStateLegacy((IBlockState)state) : null;
    }

    @Override
    public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable W world) {
        return Objects.nonNull(world) ? (WorldAPI<W>)new WorldLegacy((World)world) : null;
    }
}
