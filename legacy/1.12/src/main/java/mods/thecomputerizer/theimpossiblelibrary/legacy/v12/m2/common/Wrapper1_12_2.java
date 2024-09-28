package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound.Sound1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.advancement.Advancement1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.biome.Biome1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Block1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockSnapShot1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Material1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.entity.ClientPlayer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container.Inventory1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container.PlayerInventory1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect.Effect1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect.EffectInstance1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect.Potion1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Living1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.sound.SoundEvent1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.structure.Structure1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.entity.ServerPlayer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.Dimension1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.Explosion1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.BlockSnapshot;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class Wrapper1_12_2 implements WrapperAPI {

    @Override public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable A advancement) {
        return Objects.nonNull(advancement) ? (AdvancementAPI<A>)new Advancement1_12_2((Advancement)advancement) : null;
    }
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable B biome) {
        return Objects.nonNull(biome) ? (BiomeAPI<B>)new Biome1_12_2((Biome)biome) : null;
    }
    
    @Override public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable B block) {
        return Objects.nonNull(block) ? (BlockAPI<B>)new Block1_12_2((Block)block) : null;
    }

    @Override public @Nullable <BE> BlockEntityAPI<BE,?> wrapBlockEntity(@Nullable BE blockentity) {
        BlockEntity1_12_2 wrapped = null;
        if(blockentity instanceof TileEntity)
            wrapped = new BlockEntity1_12_2((TileEntity)blockentity);
        else if(blockentity instanceof Class<?>)
            wrapped = new BlockEntity1_12_2((Class<? extends TileEntity>)blockentity);
        return Objects.nonNull(wrapped) ? (BlockEntityAPI<BE,?>)wrapped : null;
    }

    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable D dimension) {
        return Objects.nonNull(dimension) ?
                (DimensionAPI<D>)new Dimension1_12_2((World1_12_2)world,(DimensionType)dimension) : null;
    }

    @Override public @Nullable <E> EffectAPI<E> wrapEffect(@Nullable E effect) {
        return Objects.nonNull(effect) ? (EffectAPI<E>)new Effect1_12_2((Potion)effect) : null;
    }

    @Override public @Nullable <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable I instance) {
        return Objects.nonNull(instance) ? (EffectInstanceAPI<I>)new EffectInstance1_12_2((PotionEffect)instance) : null;
    }

    @Override public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable E entity) {
        return Objects.nonNull(entity) ? (EntityAPI<E,?>)new Entity1_12_2((Entity)entity) : null;
    }

    @Override public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return Objects.nonNull(explosion) ? (ExplosionAPI<E>)new Explosion1_12_2(explosion) : null;
    }

    @Override public @Nullable <I> InventoryAPI<I> wrapInventory(@Nullable I inventory) {
        return Objects.nonNull(inventory) ? (InventoryAPI<I>)new Inventory1_12_2((IInventory)inventory) : null;
    }

    @Override public @Nullable <I> ItemAPI<I> wrapItem(@Nullable I item) {
        return Objects.nonNull(item) ? (ItemAPI<I>)new Item1_12_2((Item)item) : null;
    }

    @Override public <S> ItemStackAPI<S> wrapItemStack(@Nullable S stack) {
        return (ItemStackAPI<S>)new ItemStack1_12_2(Objects.nonNull(stack) ? (ItemStack)stack : ItemStack.EMPTY);
    }

    @Override public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable L living) {
        return Objects.nonNull(living) ? (LivingEntityAPI<L,?>)new Living1_12_2((EntityLivingBase)living) : null;
    }

    @Override public <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable M material) {
        return Objects.nonNull(material) ? (MaterialAPI<M>)new Material1_12_2((Material)material) : null;
    }

    @Override public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable P p) {
        if(!(p instanceof EntityPlayer)) return null;
        EntityPlayer player = (EntityPlayer)p;
        return (PlayerAPI<P,?>)(player instanceof EntityPlayerMP ? wrapPlayerServer(player) : wrapPlayerClient(player));
    }

    @Override public @Nullable <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable I inventory) {
        return Objects.nonNull(inventory) ? (PlayerInventoryAPI<I>)new PlayerInventory1_12_2((InventoryPlayer)inventory) : null;
    }
    
    @Override public <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable R resourceLocation) {
        return Objects.nonNull(resourceLocation) ? (ResourceLocationAPI<R>)new ResourceLocation1_12_2(resourceLocation) : null;
    }
    
    @Override public @Nullable <P> PotionAPI<P> wrapPotion(@Nullable P potion) {
        return Objects.nonNull(potion) ? (PotionAPI<P>)new Potion1_12_2((PotionType)potion) : null;
    }

    private <E extends EntityPlayer> PlayerAPI<E,?> wrapPlayerClient(E player) {
        return (PlayerAPI<E,?>)new ClientPlayer1_12_2((EntityPlayerSP)player);
    }

    private <E extends EntityPlayer> PlayerAPI<E,?> wrapPlayerServer(E player) {
        return (PlayerAPI<E,?>)new ServerPlayer1_12_2((EntityPlayerMP)player);
    }

    @Override public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable S snapshot) {
        return Objects.nonNull(snapshot) ? (BlockSnapshotAPI<S>)new BlockSnapShot1_12_2((BlockSnapshot)snapshot) : null;
    }
    
    @Override public @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable S soundEvent) {
        return Objects.nonNull(soundEvent) ? (SoundEventAPI<S>)new SoundEvent1_12_2(soundEvent) : null;
    }
    
    @Override public @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable S sound) {
        return Objects.nonNull(sound) ? (SoundAPI<S>)new Sound1_12_2(sound) : null;
    }
    
    @Override public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable S state) {
        return Objects.nonNull(state) ? (BlockStateAPI<S>)new BlockState1_12_2(state) : null;
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable S structure) {
        return Objects.nonNull(structure) ? (StructureAPI<S>)new Structure1_12_2(structure) : null;
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable W world) {
        return Objects.nonNull(world) ? (WorldAPI<W>)new World1_12_2((World)world) : null;
    }
}
