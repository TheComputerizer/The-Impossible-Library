package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.advancement.Advancement1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockSnapShot1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Material1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container.Inventory1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.Potion1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.Item1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.Explosion1_16_5;

import javax.annotation.Nullable;

import static net.minecraft.item.ItemStack.EMPTY;

public abstract class Wrapper1_16_5 implements WrapperAPI {

    @Override public @Nullable <A> AdvancementAPI<A> wrapAdvancement(@Nullable Object advancement) {
        return getAs(advancement,Advancement1_16_5::new);
    }
    
    @Override public @Nullable <B> BlockAPI<B> wrapBlock(@Nullable Object block) {
        return getAs(block,Block1_16_5::new);
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

    @Override public <M> @Nullable MaterialAPI<M> wrapMaterial(@Nullable Object material) {
        return getAs(material,Material1_16_5::new);
    }
    
    @Override public <P> BlockPosAPI<P> wrapPosition(@Nullable Object position) {
        return getAs(position, BlockPos1_16_5::get);
    }

    @Override public @Nullable <P> PotionAPI<P> wrapPotion(@Nullable Object potion) {
        return getAs(potion,Potion1_16_5::new);
    }

    @Override public @Nullable <S> BlockSnapshotAPI<S> wrapSnapshot(@Nullable Object snapshot) {
        return getAs(snapshot,BlockSnapShot1_16_5::new);
    }

    @Override public @Nullable <S> BlockStateAPI<S> wrapState(@Nullable Object state) {
        return getAs(state,BlockState1_16_5::new);
    }
}