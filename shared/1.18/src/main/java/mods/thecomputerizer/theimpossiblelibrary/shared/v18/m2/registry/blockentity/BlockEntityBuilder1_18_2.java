package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI.BlockEntityCreator;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.blockentity.BlockEntity1_18_2;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.core.Registry.BLOCK_ENTITY_TYPE;

public class BlockEntityBuilder1_18_2 extends BlockEntityBuilderAPI {
    
    public BlockEntityBuilder1_18_2(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @SuppressWarnings("deprecation")
    @Override public BlockEntityAPI<?,?> build() { //Stupid backwards reference
        final Block[] blocks = buildBlockArray(this.validBlocks.get());
        final Function<BlockEntityType<?>,BlockEntityCreator> creatorFunc = buildCreatorFunc();
        final Supplier<BlockEntityType<?>> typeSupplier = () -> BLOCK_ENTITY_TYPE.get((ResourceLocation)this.registryName.unwrap());
        BlockEntityAPI<?,?> entity = BlockEntity1_18_2.get(buildType((pos,state) -> (BlockEntity)creatorFunc.apply(typeSupplier.get())
                .create(null,WrapperHelper.wrapPosition(pos),WrapperHelper.wrapState(state)).getEntity(),blocks));
        entity.setCreator(creatorFunc.apply(entity.unwrap()));
        entity.setRegistryName(this.registryName);
        return entity;
    }
    
    protected Block[] buildBlockArray(Collection<BlockAPI<?>> blocks) {
        Block[] array = new Block[blocks.size()];
        int i = 0;
        for(BlockAPI<?> block : blocks) {
            array[i] = block.unwrap();
            i++;
        }
        return array;
    }
    
    @SuppressWarnings({"unchecked","DataFlowIssue"})
    <T extends BlockEntity> BlockEntityType<T> buildType(BiFunction<BlockPos,BlockState,BlockEntity> supplier, Block ...blocks) {
        return Builder.of((pos,state) -> (T)supplier.apply(pos,state),blocks).build(null);
    }
    
    Function<BlockEntityType<?>,BlockEntityCreator> buildCreatorFunc() {
        return type -> (world,pos,state) -> WrapperHelper.wrapBlockEntity((Objects.nonNull(this.onTick) ?
                new TILTickableBlockEntity1_18_2(type,pos.unwrap(),state.unwrap(),this.onTick) :
                new TILBasicBlockEntity1_18_2(type,pos.unwrap(),state.unwrap())));
    }
}