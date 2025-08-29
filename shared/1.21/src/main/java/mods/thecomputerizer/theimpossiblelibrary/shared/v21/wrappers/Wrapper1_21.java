package mods.thecomputerizer.theimpossiblelibrary.shared.v21.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.entity.ClientPlayer1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.sound.Sound1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.advancement.Advancement1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.biome.Biome1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block.Block1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block.BlockSnapShot1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block.BlockState1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block.Material1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.blockentity.BlockEntity1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.container.Inventory1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.container.PlayerInventory1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.effect.Effect1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.effect.EffectInstance1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.effect.Potion1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Damage1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Entity1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Living1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item.Item1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item.ItemStack1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.sound.SoundEvent1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.structure.Structure1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.tab.CreativeTab1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.resource.ResourceLocation1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.CommandSender1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.entity.ServerPlayer1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.world.BlockPos1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.world.Dimension1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.world.Explosion1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.world.World1_21;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;


import static net.minecraft.world.item.ItemStack.EMPTY;

public class Wrapper1_21 implements WrapperAPI {

    @Override public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement) {
        return getAs(advancement,Advancement1_21::new);
    }
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable Object biome) {
        return getAs(biome,Biome1_21::new);
    }
    
    @Override public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable Object block) {
        return getAs(block,Block1_21::new);
    }
    
    @Override public @Nullable <B> BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object blockentity) {
        return getAs(blockentity,BlockEntity1_21::get);
    }
    
    @Override public @Nullable <S> CommandSenderAPI<S> wrapCommandSender(@Nullable Object sender) {
        return getAs(sender,CommandSender1_21::new);
    }
    
    @Override public <S> @Nullable DamageAPI<S> wrapDamage(@Nullable Object source, float amount) {
        return getAs(source,s -> new Damage1_21(s,amount));
    }
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAs(dimension,type -> new Dimension1_21(world,type));
    }
    
    @Override public @Nullable <E> EffectAPI<E> wrapEffect(@Nullable Object effect) {
        return getAs(effect,Effect1_21::new);
    }
    
    @Override public @Nullable <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object instance) {
        return getAs(instance,EffectInstance1_21::new);
    }
    
    @Override public @Nullable <E> EntityAPI<E,?> wrapEntity(@Nullable Object entity) {
        return getAs(entity,Entity1_21::new);
    }

    @Override public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable Object explosion) {
        return getAs(explosion,Explosion1_21::new);
    }

    @Override public @Nullable <I> InventoryAPI<I> wrapInventory(@Nullable Object inventory) {
        return getAs(inventory,Inventory1_21::new);
    }

    @Override public @Nullable <I> ItemAPI<I> wrapItem(@Nullable Object item) {
        return getAs(item,Item1_21::new);
    }

    @Override public <S> ItemStackAPI<S> wrapItemStack(@Nullable Object stack) {
        return getAs(stack,ItemStack1_21::new,() -> EMPTY);
    }
    
    @Override public @Nullable <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object living) {
        return getAs(living,Living1_21::new);
    }

    @Override public <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable Object material) {
        return getAs(material,Material1_21::new);
    }
    
    @Override public @Nullable <P> PlayerAPI<P,?> wrapPlayer(@Nullable Object player) {
        return getAs(player,p -> p instanceof ServerPlayer ? wrapPlayerServer(p) : wrapPlayerClient(p));
    }
    
    private <E extends Player> PlayerAPI<E,?> wrapPlayerClient(@Nullable Object player) {
        return getAs(player,ClientPlayer1_21::new);
    }
    
    @Override public @Nullable <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object inventory) {
        return getAs(inventory,PlayerInventory1_21::new);
    }
    
    private <E extends Player> PlayerAPI<E,?> wrapPlayerServer(@Nullable Object player) {
        return getAs(player,ServerPlayer1_21::new);
    }
    
    @Override public <P> BlockPosAPI<P> wrapPosition(@Nullable Object position) {
        return getAs(position, BlockPos1_21::get);
    }

    @Override public @Nullable <P> PotionAPI<P> wrapPotion(@Nullable Object potion) {
        return getAs(potion,Potion1_21::new);
    }
    
    @Override public <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object resourceLocation) {
        return getAs(resourceLocation,ResourceLocation1_21::new);
    }

    @Override public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable Object snapshot) {
        return getAs(snapshot,BlockSnapShot1_21::new);
    }
    
    @Override public @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable Object soundEvent) {
        return getAs(soundEvent,SoundEvent1_21::new);
    }
    
    @Override public @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable Object sound) {
        return getAs(sound,Sound1_21::new);
    }

    @Override public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable Object state) {
        return getAs(state,BlockState1_21::new);
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable Object structure) {
        return getAs(structure,Structure1_21::new);
    }
    
    @Override public <T> CreativeTabAPI<T> wrapTab(@Nullable Object tab) {
        return getAs(tab,CreativeTab1_21::new);
    }
    
    @Override @Nullable public <W> WorldAPI<W> wrapWorld(@org.jetbrains.annotations.Nullable Object world) {
        return getAs(world,World1_21::new);
    }
}