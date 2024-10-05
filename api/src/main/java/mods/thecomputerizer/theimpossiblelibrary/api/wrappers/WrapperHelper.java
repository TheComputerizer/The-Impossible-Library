package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;

public class WrapperHelper {

    public static WrapperAPI getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getWrapper);
    }

    public static <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement) {
        return getAPI().wrapAdvancement(advancement);
    }
    
    public static <B> BiomeAPI<B> wrapBiome(@Nullable Object biome) {
        return getAPI().wrapBiome(biome);
    }

    public static <B> BlockAPI<B> wrapBlock(@Nullable Object block) {
        return getAPI().wrapBlock(block);
    }

    public static <B> BlockEntityAPI<B,?> wrapBlockEntity(@Nullable Object blockentity) {
        return getAPI().wrapBlockEntity(blockentity);
    }
    
    public static <S> CommandSenderAPI<S> wrapCommandSender(@Nullable Object sender) {
        return getAPI().wrapCommandSender(sender);
    }

    public static <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAPI().wrapDimension(world,dimension);
    }

    public static <E> EffectAPI<E> wrapEffect(@Nullable Object effect) {
        return getAPI().wrapEffect(effect);
    }

    public static <I> EffectInstanceAPI<I> wrapEffectInstance(@Nullable Object instance) {
        return getAPI().wrapEffectInstance(instance);
    }

    public static <E> EntityAPI<E,?> wrapEntity(@Nullable Object entity) {
        return getAPI().wrapEntity(entity);
    }

    public static <E> ExplosionAPI<E> wrapExplosion(@Nullable Object explosion) {
        return getAPI().wrapExplosion(explosion);
    }
    
    @IndirectCallers
    public static <W> W wrapGeneric(Class<W> wrapperClass, @Nullable Object generic) {
        return getAPI().wrapGeneric(wrapperClass,generic);
    }

    @IndirectCallers
    public static <I> InventoryAPI<I> wrapInventory(@Nullable Object inventory) {
        return getAPI().wrapInventory(inventory);
    }

    public static <I> ItemAPI<I> wrapItem(@Nullable Object item) {
        return getAPI().wrapItem(item);
    }

    public static <S> ItemStackAPI<S> wrapItemStack(@Nullable Object stack) {
        return getAPI().wrapItemStack(stack);
    }

    public static <L> LivingEntityAPI<L,?> wrapLivingEntity(@Nullable Object living) {
        return getAPI().wrapLivingEntity(living);
    }

    public static <M> MaterialAPI<M> wrapMaterial(@Nullable Object material) {
        return getAPI().wrapMaterial(material);
    }

    public static <P> PlayerAPI<P,?> wrapPlayer(@Nullable Object player) {
        return getAPI().wrapPlayer(player);
    }

    public static <I> PlayerInventoryAPI<I> wrapPlayerInventory(@Nullable Object inventory) {
        return getAPI().wrapPlayerInventory(inventory);
    }
    
    @IndirectCallers
    public static <P> PotionAPI<P> wrapPotion(@Nullable Object potion) {
        return getAPI().wrapPotion(potion);
    }
    
    public static <P> BlockPosAPI<P> wrapPosition(@Nullable Object pos) {
        return getAPI().wrapPosition(pos);
    }
    
    public static <R> ResourceLocationAPI<R> wrapResourceLocation(@Nullable Object resourceLocation) {
        return getAPI().wrapResourceLocation(resourceLocation);
    }

    public static <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable Object state) {
        return getAPI().wrapSnapshot(state);
    }
    
    public static @Nullable <S> SoundEventAPI<S> wrapSoundEvent(@Nullable Object soundEvent) {
        return getAPI().wrapSoundEvent(soundEvent);
    }
    
    public static @Nullable <S> SoundAPI<S> wrapSoundInstance(@Nullable Object sound) {
        return getAPI().wrapSoundInstance(sound);
    }

    public static <S> BlockStateAPI<S> wrapState(@Nullable Object state) {
        return getAPI().wrapState(state);
    }
    
    @IndirectCallers
    public static <S> StructureAPI<S> wrapStructure(@Nullable Object structure) {
        return getAPI().wrapStructure(structure);
    }

    public static <W> WorldAPI<W> wrapWorld(@Nullable Object world) {
        return getAPI().wrapWorld(world);
    }
}