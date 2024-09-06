package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.advancement.Advancement1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockSnapShot1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Material1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container.Inventory1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.Potion1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.Explosion1_16_5;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.BlockSnapshot;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.item.ItemStack.EMPTY;

@SuppressWarnings("unchecked")
public abstract class Wrapper1_16_5 implements WrapperAPI {

    @Override public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable A advancement) {
        return Objects.nonNull(advancement) ? (AdvancementAPI<A>)new Advancement1_16_5((Advancement)advancement) : null;
    }
    
    @Override public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable B block) {
        return Objects.nonNull(block) ? (BlockAPI<B>)new Block1_16_5((Block)block) : null;
    }

    @Override public @Nullable <E> ExplosionAPI<E> wrapExplosion(@Nullable E explosion) {
        return Objects.nonNull(explosion) ? (ExplosionAPI<E>)new Explosion1_16_5(explosion) : null;
    }

    @Override public @Nullable <I> InventoryAPI<I> wrapInventory(@Nullable I inventory) {
        return Objects.nonNull(inventory) ? (InventoryAPI<I>)new Inventory1_16_5((IInventory)inventory) : null;
    }

    @Override public @Nullable <I> ItemAPI<I> wrapItem(@Nullable I item) {
        return Objects.nonNull(item) ? (ItemAPI<I>)new Item1_16_5((Item)item) : null;
    }

    @Override public <S> ItemStackAPI<S> wrapItemStack(@Nullable S stack) {
        return (ItemStackAPI<S>)new ItemStack1_16_5(Objects.nonNull(stack) ? stack : EMPTY);
    }

    @Override public <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable M material) {
        return Objects.nonNull(material) ? (MaterialAPI<M>)new Material1_16_5((Material)material) : null;
    }

    @Override public @Nullable <P> PotionAPI<P> wrapPotion(@Nullable P potion) {
        return Objects.nonNull(potion) ? (PotionAPI<P>)new Potion1_16_5((Potion)potion) : null;
    }

    @Override public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable S snapshot) {
        return Objects.nonNull(snapshot) ? (BlockSnapshotAPI<S>)new BlockSnapShot1_16_5((BlockSnapshot)snapshot) : null;
    }

    @Override public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable S state) {
        return Objects.nonNull(state) ? (BlockStateAPI<S>)new BlockState1_16_5(state) : null;
    }
}