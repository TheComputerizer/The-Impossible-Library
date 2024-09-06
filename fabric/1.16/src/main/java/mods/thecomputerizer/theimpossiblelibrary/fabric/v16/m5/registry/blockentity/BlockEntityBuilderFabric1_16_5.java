package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.blockentity.BlockEntityFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity.BlockEntityBuilder1_16_5;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.core.Registry.BLOCK_ENTITY_TYPE;

public class BlockEntityBuilderFabric1_16_5 extends BlockEntityBuilder1_16_5 {
    
    public BlockEntityBuilderFabric1_16_5(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockEntityAPI<?,?> build() { //Stupid backwards reference
        final Block[] blocks = buildBlockArray(this.validBlocks.get(),Block.class);
        final Function<BlockEntityType<?>,Supplier<BlockEntity>> creatorFunc = buildCreatorFunc();
        final Supplier<BlockEntityType<?>> typeSupplier = () -> BLOCK_ENTITY_TYPE.get((ResourceLocation)this.registryName.unwrap());
        BlockEntityAPI<?,?> entity = new BlockEntityFabric1_16_5(buildType(() -> creatorFunc.apply(typeSupplier.get()).get(), blocks));
        entity.setCreator(t -> new BlockEntityFabric1_16_5(creatorFunc.apply((BlockEntityType<?>)t).get()));
        entity.setRegistryName(this.registryName);
        return entity;
    }
    
    @SuppressWarnings("unchecked")
    <T extends BlockEntity> BlockEntityType<T> buildType(Supplier<? extends BlockEntity> supplier, Block ...blocks) {
        return Builder.of(() -> (T)supplier.get(),blocks).build(null);
    }
    
    Function<BlockEntityType<?>,Supplier<BlockEntity>> buildCreatorFunc() {
        return type -> () -> (Objects.nonNull(this.onTick) ?
                new TILTickableBlockEntityFabric1_16_5(type, this.onTick) : new TILBasicBlockEntityFabric1_16_5(type));
    }
}