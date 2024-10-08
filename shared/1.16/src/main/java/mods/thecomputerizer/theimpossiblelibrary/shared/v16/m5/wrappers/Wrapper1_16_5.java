package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.wrappers;

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
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.entity.ClientPlayer1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.sound.Sound1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.advancement.Advancement1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome.Biome1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockSnapShot1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Material1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.blockentity.BlockEntity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container.Inventory1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container.PlayerInventory1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.Effect1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.EffectInstance1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.Potion1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity.Entity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity.Living1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.sound.SoundEvent1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.structure.Structure1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.CommandSender1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.entity.ServerPlayer1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.Dimension1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.Explosion1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nullable;

import static net.minecraft.item.ItemStack.EMPTY;

public class Wrapper1_16_5 implements WrapperAPI {

    @Override public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement) {
        return getAs(advancement,Advancement1_16_5::new);
    }
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable Object biome) {
        return getAs(biome,Biome1_16_5::new);
    }
    
    @Override public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable Object block) {
        return getAs(block,Block1_16_5::new);
    }
    
    @Override public @Nullable <B> BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object blockentity) {
        return getAs(blockentity,BlockEntity1_16_5::get);
    }
    
    @Override public @Nullable <S> CommandSenderAPI<S> wrapCommandSender(@Nullable Object sender) {
        return getAs(sender,CommandSender1_16_5::new);
    }
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAs(dimension,type -> new Dimension1_16_5(world,type));
    }
    
    @Override public @Nullable <E> EffectAPI<E> wrapEffect(@Nullable Object effect) {
        return getAs(effect,Effect1_16_5::new);
    }
    
    @Override public @Nullable <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object instance) {
        return getAs(instance,EffectInstance1_16_5::new);
    }
    
    @Override public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable Object entity) {
        return getAs(entity,Entity1_16_5::new);
    }

    @Override public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable Object explosion) {
        return getAs(explosion,Explosion1_16_5::new);
    }

    @Override public @Nullable <I> InventoryAPI<I> wrapInventory(@Nullable Object inventory) {
        return getAs(inventory,Inventory1_16_5::new);
    }

    @Override public @Nullable <I> ItemAPI<I> wrapItem(@Nullable Object item) {
        return getAs(item,Item1_16_5::new);
    }

    @Override public <S> ItemStackAPI<S> wrapItemStack(@Nullable Object stack) {
        return getAs(stack,ItemStack1_16_5::new,() -> EMPTY);
    }
    
    @Override public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object living) {
        return getAs(living,Living1_16_5::new);
    }

    @Override public <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable Object material) {
        return getAs(material,Material1_16_5::new);
    }
    
    @Override public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable Object player) {
        return getAs(player,p -> p instanceof ServerPlayerEntity ? wrapPlayerServer(p) : wrapPlayerClient(p));
    }
    
    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerClient(@Nullable Object player) {
        return getAs(player,ClientPlayer1_16_5::new);
    }
    
    @Override public @Nullable <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object inventory) {
        return getAs(inventory,PlayerInventory1_16_5::new);
    }
    
    private <E extends PlayerEntity> PlayerAPI<E,?> wrapPlayerServer(@Nullable Object player) {
        return getAs(player,ServerPlayer1_16_5::new);
    }
    
    @Override public <P> BlockPosAPI<P> wrapPosition(@Nullable Object position) {
        return getAs(position, BlockPos1_16_5::get);
    }

    @Override public @Nullable <P> PotionAPI<P> wrapPotion(@Nullable Object potion) {
        return getAs(potion,Potion1_16_5::new);
    }
    
    @Override public <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object resourceLocation) {
        return getAs(resourceLocation,ResourceLocation1_16_5::new);
    }

    @Override public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable Object snapshot) {
        return getAs(snapshot,BlockSnapShot1_16_5::new);
    }
    
    @Override public @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable Object soundEvent) {
        return getAs(soundEvent,SoundEvent1_16_5::new);
    }
    
    @Override public @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable Object sound) {
        return getAs(sound,Sound1_16_5::new);
    }

    @Override public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable Object state) {
        return getAs(state,BlockState1_16_5::new);
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable Object structure) {
        return getAs(structure,Structure1_16_5::new);
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable Object world) {
        return getAs(world,World1_16_5::new);
    }
}