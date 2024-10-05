package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.wrappers;

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
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server.CommandSenderFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server.entity.ServerPlayerFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.world.DimensionFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.world.WorldFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.wrappers.Wrapper1_16_5;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class WrapperFabric1_16_5 extends Wrapper1_16_5 {//TODO A lot of these wrappers can be abstracted to the shared module
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable Object biome) {
        return getAs(biome,BiomeFabric1_16_5::new);
    }
    
    @Override public @Nullable <B> BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object blockentity) {
        return getAs(blockentity,BlockEntityFabric1_16_5::get);
    }
    
    @Override public @Nullable <S> CommandSenderAPI<S> wrapCommandSender(@Nullable Object sender) {
        return getAs(sender,CommandSenderFabric1_16_5::new);
    }
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAs(dimension,type -> new DimensionFabric1_16_5(world,type));
    }
    
    @Override public @Nullable <E> EffectAPI<E> wrapEffect(@Nullable Object effect) {
        return getAs(effect,EffectFabric1_16_5::new);
    }
    
    @Override public @Nullable <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object instance) {
        return getAs(instance,EffectInstanceFabric1_16_5::new);
    }
    
    @Override public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable Object entity) {
        return getAs(entity,EntityFabric1_16_5::new);
    }
    
    @Override public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object living) {
        return getAs(living,LivingFabric1_16_5::new);
    }
    
    @Override public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable Object player) {
        return getAs(player,p -> p instanceof ServerPlayer ? wrapPlayerServer(p) : wrapPlayerClient(p));
    }
    
    private <E extends Player> PlayerAPI<E,?> wrapPlayerClient(@Nullable Object player) {
        return getAs(player,ClientPlayerFabric1_16_5::new);
    }
    
    @Override public @Nullable <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object inventory) {
        return getAs(inventory,PlayerInventoryFabric1_16_5::new);
    }
    
    private <E extends Player> PlayerAPI<E,?> wrapPlayerServer(@Nullable Object player) {
        return getAs(player,ServerPlayerFabric1_16_5::new);
    }
    
    @Override public <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object resourceLocation) {
        return getAs(resourceLocation,ResourceLocationFabric1_16_5::new);
    }
    
    @Override public @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable Object soundEvent) {
        return getAs(soundEvent,SoundEventFabric1_16_5::new);
    }
    
    @Override public @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable Object sound) {
        return getAs(sound,SoundFabric1_16_5::new);
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable Object structure) {
        return getAs(structure,StructureFabric1_16_5::new);
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable Object world) {
        return getAs(world,WorldFabric1_16_5::new);
    }
}