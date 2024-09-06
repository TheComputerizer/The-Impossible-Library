package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.entity.ClientPlayerForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound.SoundForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.biome.BiomeForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity.BlockEntityForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.container.PlayerInventoryForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.effect.EffectForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.effect.EffectInstanceForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.EntityForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.LivingForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.sound.SoundEventForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.structure.StructureForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocationForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity.ServerPlayerForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.DimensionForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.WorldForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.Wrapper1_16_5;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class WrapperForge1_16_5 extends Wrapper1_16_5 {
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable B biome) {
        return Objects.nonNull(biome) ? (BiomeAPI<B>)new BiomeForge1_16_5((Biome)biome) : null;
    }
    
    @Override public @Nullable <BE> BlockEntityAPI<BE,?> wrapBlockEntity(@Nullable BE blockentity) {
        return Objects.nonNull(blockentity) ? (BlockEntityAPI<BE,?>)new BlockEntityForge1_16_5((TileEntity)blockentity) : null;
    }
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable D dimension) {
        return Objects.nonNull(dimension) ?
                (DimensionAPI<D>)new DimensionForge1_16_5((WorldForge1_16_5)world,(DimensionType)dimension) : null;
    }
    
    @Override public @Nullable <E> EffectAPI<E> wrapEffect(@Nullable E effect) {
        return Objects.nonNull(effect) ? (EffectAPI<E>)new EffectForge1_16_5((Effect)effect) : null;
    }
    
    @Override public @Nullable <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable I instance) {
        return Objects.nonNull(instance) ? (EffectInstanceAPI<I>)new EffectInstanceForge1_16_5((EffectInstance)instance) : null;
    }
    
    @Override public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable E entity) {
        return Objects.nonNull(entity) ? (EntityAPI<E,?>)new EntityForge1_16_5((Entity)entity) : null;
    }
    
    @Override public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable L living) {
        return Objects.nonNull(living) ? (LivingEntityAPI<L,?>)new LivingForge1_16_5((LivingEntity)living) : null;
    }
    
    @Override public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable P p) {
        if(!(p instanceof PlayerEntity)) return null;
        PlayerEntity player = (PlayerEntity)p;
        return (PlayerAPI<P,?>)(player instanceof ServerPlayerEntity ? wrapPlayerServer(player) : wrapPlayerClient(player));
    }
    
    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerClient(E player) {
        return (PlayerAPI<E,?>)new ClientPlayerForge1_16_5((ClientPlayerEntity)player);
    }
    
    @Override public @Nullable <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable I inventory) {
        return Objects.nonNull(inventory) ? (PlayerInventoryAPI<I>)new PlayerInventoryForge1_16_5((PlayerInventory)inventory) : null;
    }
    
    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerServer(E player) {
        return (PlayerAPI<E,?>)new ServerPlayerForge1_16_5((ServerPlayerEntity)player);
    }
    
    @Override public <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable R resourceLocation) {
        return Objects.nonNull(resourceLocation) ? (ResourceLocationAPI<R>)new ResourceLocationForge1_16_5(resourceLocation) : null;
    }
    
    @Override public @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable S soundEvent) {
        return Objects.nonNull(soundEvent) ? (SoundEventAPI<S>)new SoundEventForge1_16_5(soundEvent) : null;
    }
    
    @Override public @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable S sound) {
        return Objects.nonNull(sound) ? (SoundAPI<S>)new SoundForge1_16_5(sound) : null;
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable S structure) {
        return Objects.nonNull(structure) ? (StructureAPI<S>)new StructureForge1_16_5(structure) : null;
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable W world) {
        return Objects.nonNull(world) ? (WorldAPI<W>)new WorldForge1_16_5((IWorld)world) : null;
    }
}
