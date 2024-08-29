package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.blockentity.BlockEntity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockEntityBuilder1_16_5 extends BlockEntityBuilderAPI {
    
    public BlockEntityBuilder1_16_5(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override
    public BlockEntity1_16_5 build() { //Stupid backwards reference
        final Block[] blocks = buildBlockArray(this.validBlocks.get());
        final Function<TileEntityType<?>,Supplier<TileEntity>> creatorFunc = buildCreatorFunc();
        final Supplier<TileEntityType<?>> typeSupplier = () -> ForgeRegistries.TILE_ENTITIES.getValue(
                ((ResourceLocation1_16_5)this.registryName).getInstance());
        BlockEntity1_16_5 entity = new BlockEntity1_16_5(buildType(() -> creatorFunc.apply(typeSupplier.get()).get(),blocks));
        entity.setCreator(t -> new BlockEntity1_16_5(creatorFunc.apply(t).get()));
        entity.setRegistryName(this.registryName);
        return entity;
    }
    
    Block[] buildBlockArray(Collection<BlockAPI<?>> blocks) {
        Block[] array = new Block[blocks.size()];
        int i = 0;
        for(BlockAPI<?> block : blocks) {
            array[i] = ((Block1_16_5)block).getBlock();
            i++;
        }
        return array;
    }
    
    Function<TileEntityType<?>,Supplier<TileEntity>> buildCreatorFunc() {
        return type -> () -> (Objects.nonNull(this.onTick) ?
                new TILTickableBlockEntity1_16_5(type, this.onTick) : new TILBasicBlockEntity1_16_5(type));
    }
    
    @SuppressWarnings({"unchecked","DataFlowIssue"})
    <T extends TileEntity> TileEntityType<T> buildType(Supplier<? extends TileEntity> supplier, Block ...blocks) {
        return TileEntityType.Builder.of(() -> (T)supplier.get(),blocks).build(null);
    }
}