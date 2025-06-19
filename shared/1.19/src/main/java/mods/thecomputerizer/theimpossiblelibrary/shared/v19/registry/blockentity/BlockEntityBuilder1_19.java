package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI.BlockEntityCreator;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.blockentity.BlockEntity1_19;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockEntityBuilder1_19 extends BlockEntityBuilderAPI {
    
    public BlockEntityBuilder1_19(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockEntityAPI<?,?> build() { //Stupid backwards reference
        final Block[] blocks = buildBlockArray(this.validBlocks.get());
        final Function<BlockEntityType<?>,BlockEntityCreator> creatorFunc = buildCreatorFunc();
        final Supplier<BlockEntityType<?>> typeSupplier = () -> getRegistry().get((ResourceLocation)this.registryName.unwrap());
        BlockEntityAPI<?,?> entity = BlockEntity1_19.get(buildType((pos,state) -> (BlockEntity)creatorFunc.apply(typeSupplier.get())
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
    
    @SuppressWarnings("unchecked")
    <T extends BlockEntity> BlockEntityType<T> buildType(BiFunction<BlockPos,BlockState,BlockEntity> supplier, Block ...blocks) {
        return Builder.of((pos,state) -> (T)supplier.apply(pos,state),blocks).build(null);
    }
    
    Function<BlockEntityType<?>,BlockEntityCreator> buildCreatorFunc() {
        return type -> (world,pos,state) -> WrapperHelper.wrapBlockEntity((Objects.nonNull(this.onTick) ?
                new TILTickableBlockEntity1_19(type,pos.unwrap(),state.unwrap(),this.onTick) :
                new TILBasicBlockEntity1_19(type,pos.unwrap(),state.unwrap())));
    }
    
    protected Registry<BlockEntityType<?>> getRegistry() {
        return Registry.BLOCK_ENTITY_TYPE;
    }
}