package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI.BlockEntityCreator;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.blockentity.BlockEntity1_16_5;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.core.Registry.BLOCK_ENTITY_TYPE;

public class BlockEntityBuilder1_16_5 extends BlockEntityBuilderAPI {
    
    public BlockEntityBuilder1_16_5(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockEntityAPI<?,?> build() { //Stupid backwards reference
        final Block[] blocks = buildBlockArray(this.validBlocks.get(), Block.class);
        final Function<BlockEntityType<?>,BlockEntityCreator> creatorFunc = buildCreatorFunc();
        final Supplier<BlockEntityType<?>> typeSupplier = () ->
                BLOCK_ENTITY_TYPE.get((ResourceLocation)this.registryName.unwrap());
        BlockEntityAPI<?,?> entity = BlockEntity1_16_5.get(buildType(() -> (BlockEntity)creatorFunc.apply(typeSupplier.get())
                .create(null,null,null).getEntity(),blocks));
        entity.setCreator(creatorFunc.apply(entity.unwrap()));
        entity.setRegistryName(this.registryName);
        return entity;
    }
    
    @SuppressWarnings("SameParameterValue")
    protected <T> T[] buildBlockArray(Collection<BlockAPI<?>> blocks, Class<T> blockClass) {
        T[] array = ArrayHelper.create(blockClass,blocks.size());
        int i = 0;
        for(BlockAPI<?> block : blocks) {
            array[i] = block.unwrap();
            i++;
        }
        return array;
    }
    
    @SuppressWarnings("unchecked")
    <T extends BlockEntity> BlockEntityType<T> buildType(Supplier<? extends BlockEntity> supplier, Block ... blocks) {
        return Builder.of(() -> (T)supplier.get(),blocks).build(null);
    }
    
    Function<BlockEntityType<?>,BlockEntityCreator> buildCreatorFunc() {
        return type -> (world,pos,state) -> WrapperHelper.wrapBlockEntity(Objects.nonNull(this.onTick) ?
                new TILTickableBlockEntity1_16_5(type,this.onTick) : new TILBasicBlockEntity1_16_5(type));
    }
}