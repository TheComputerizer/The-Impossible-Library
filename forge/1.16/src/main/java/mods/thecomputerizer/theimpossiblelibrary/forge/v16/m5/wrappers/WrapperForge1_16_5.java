package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.wrappers;

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
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.CommandSenderForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity.ServerPlayerForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.DimensionForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.WorldForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.wrappers.Wrapper1_16_5;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nullable;

public class WrapperForge1_16_5 extends Wrapper1_16_5 { //TODO A lot of these wrappers can be abstracted to the shared module
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable Object biome) {
        return getAs(biome,BiomeForge1_16_5::new);
    }
    
    @Override public @Nullable <B> BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object blockentity) {
        return getAs(blockentity,BlockEntityForge1_16_5::get);
    }
    
    @Override public @Nullable <S> CommandSenderAPI<S> wrapCommandSender(@Nullable Object sender) {
        return getAs(sender,CommandSenderForge1_16_5::new);
    }
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAs(dimension,type -> new DimensionForge1_16_5(world,type));
    }
    
    @Override public @Nullable <E> EffectAPI<E> wrapEffect(@Nullable Object effect) {
        return getAs(effect,EffectForge1_16_5::new);
    }
    
    @Override public @Nullable <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object instance) {
        return getAs(instance,EffectInstanceForge1_16_5::new);
    }
    
    @Override public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable Object entity) {
        return getAs(entity,EntityForge1_16_5::new);
    }
    
    @Override public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object living) {
        return getAs(living,LivingForge1_16_5::new);
    }
    
    @Override public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable Object player) {
        return getAs(player,p -> p instanceof ServerPlayerEntity ? wrapPlayerServer(p) : wrapPlayerClient(p));
    }
    
    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerClient(Object player) {
        return getAs(player,ClientPlayerForge1_16_5::new);
    }
    
    @Override public @Nullable <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object inventory) {
        return getAs(inventory,PlayerInventoryForge1_16_5::new);
    }
    
    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerServer(Object player) {
        return getAs(player,ServerPlayerForge1_16_5::new);
    }
    
    @Override public <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object resourceLocation) {
        return getAs(resourceLocation,ResourceLocationForge1_16_5::new);
    }
    
    @Override public @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable Object soundEvent) {
        return getAs(soundEvent,SoundEventForge1_16_5::new);
    }
    
    @Override public @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable Object sound) {
        return getAs(sound,SoundForge1_16_5::new);
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable Object structure) {
        return getAs(structure,StructureForge1_16_5::new);
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable Object world) {
        return getAs(world,WorldForge1_16_5::new);
    }
}