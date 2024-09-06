package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common;

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
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.entity.ClientPlayerFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.sound.SoundFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.biome.BiomeFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.blockentity.BlockEntityFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.container.PlayerInventoryFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.effect.EffectFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.effect.EffectInstanceFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.entity.EntityFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.entity.LivingFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.sound.SoundEventFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.structure.StructureFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.resource.ResourceLocationFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server.entity.ServerPlayerFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.world.DimensionFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.world.WorldFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.Wrapper1_16_5;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.dimension.DimensionType;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class WrapperFabric1_16_5 extends Wrapper1_16_5 {
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable B biome) {
        return Objects.nonNull(biome) ? (BiomeAPI<B>)new BiomeFabric1_16_5((Biome)biome) : null;
    }
    
    @Override public @Nullable <BE> BlockEntityAPI<BE,?> wrapBlockEntity(@Nullable BE blockentity) {
        return Objects.nonNull(blockentity) ? (BlockEntityAPI<BE,?>)new BlockEntityFabric1_16_5((BlockEntity)blockentity) : null;
    }
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable D dimension) {
        return Objects.nonNull(dimension) ?
                (DimensionAPI<D>)new DimensionFabric1_16_5((WorldFabric1_16_5)world,(DimensionType)dimension) : null;
    }
    
    @Override public @Nullable <E> EffectAPI<E> wrapEffect(@Nullable E effect) {
        return Objects.nonNull(effect) ? (EffectAPI<E>)new EffectFabric1_16_5((MobEffect)effect) : null;
    }
    
    @Override public @Nullable <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable I instance) {
        return Objects.nonNull(instance) ? (EffectInstanceAPI<I>)new EffectInstanceFabric1_16_5((MobEffectInstance)instance) : null;
    }
    
    @Override public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable E entity) {
        return Objects.nonNull(entity) ? (EntityAPI<E,?>)new EntityFabric1_16_5((Entity)entity) : null;
    }
    
    @Override public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable L living) {
        return Objects.nonNull(living) ? (LivingEntityAPI<L,?>)new LivingFabric1_16_5((LivingEntity)living) : null;
    }
    
    @Override public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable P p) {
        if(!(p instanceof Player)) return null;
        Player player = (Player)p;
        return (PlayerAPI<P,?>)(player instanceof ServerPlayer ? wrapPlayerServer(player) : wrapPlayerClient(player));
    }
    
    private <E extends Player> PlayerAPI<E,?> wrapPlayerClient(E player) {
        return (PlayerAPI<E,?>)new ClientPlayerFabric1_16_5((LocalPlayer)player);
    }
    
    @Override public @Nullable <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable I inventory) {
        return Objects.nonNull(inventory) ? (PlayerInventoryAPI<I>)new PlayerInventoryFabric1_16_5((Inventory)inventory) : null;
    }
    
    private <E extends Player> PlayerAPI<E,?> wrapPlayerServer(E player) {
        return (PlayerAPI<E,?>)new ServerPlayerFabric1_16_5((ServerPlayer)player);
    }
    
    @Override public <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable R resourceLocation) {
        return Objects.nonNull(resourceLocation) ? (ResourceLocationAPI<R>)new ResourceLocationFabric1_16_5(resourceLocation) : null;
    }
    
    @Override public @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable S soundEvent) {
        return Objects.nonNull(soundEvent) ? (SoundEventAPI<S>)new SoundEventFabric1_16_5(soundEvent) : null;
    }
    
    @Override public @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable S sound) {
        return Objects.nonNull(sound) ? (SoundAPI<S>)new SoundFabric1_16_5(sound) : null;
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable S structure) {
        return Objects.nonNull(structure) ? (StructureAPI<S>)new StructureFabric1_16_5(structure) : null;
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable W world) {
        return Objects.nonNull(world) ? (WorldAPI<W>)new WorldFabric1_16_5((LevelAccessor)world) : null;
    }
}