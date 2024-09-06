package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity.BlockEntityForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity.BlockEntityBuilder1_16_5;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.registries.ForgeRegistries.TILE_ENTITIES;

public class BlockEntityBuilderForge1_16_5 extends BlockEntityBuilder1_16_5 {
    
    public BlockEntityBuilderForge1_16_5(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockEntityAPI<?,?> build() { //Stupid backwards reference
        final Block[] blocks = buildBlockArray(this.validBlocks.get(),Block.class);
        final Function<TileEntityType<?>,Supplier<TileEntity>> creatorFunc = buildCreatorFunc();
        final Supplier<TileEntityType<?>> typeSupplier = () -> TILE_ENTITIES.getValue(this.registryName.unwrap());
        BlockEntityAPI<?,?> entity = new BlockEntityForge1_16_5(buildType(() -> creatorFunc.apply(typeSupplier.get()).get(),blocks));
        entity.setCreator(t -> new BlockEntityForge1_16_5(creatorFunc.apply((TileEntityType<?>)t).get()));
        entity.setRegistryName(this.registryName);
        return entity;
    }
    
    @SuppressWarnings({"unchecked","DataFlowIssue"})
    <T extends TileEntity> TileEntityType<T> buildType(Supplier<? extends TileEntity> supplier, Block ...blocks) {
        return Builder.of(() -> (T)supplier.get(),blocks).build(null);
    }
    
    Function<TileEntityType<?>,Supplier<TileEntity>> buildCreatorFunc() {
        return type -> () -> (Objects.nonNull(this.onTick) ?
                new TILTickableBlockEntityForge1_16_5(type,this.onTick) : new TILBasicBlockEntityForge1_16_5(type));
    }
}