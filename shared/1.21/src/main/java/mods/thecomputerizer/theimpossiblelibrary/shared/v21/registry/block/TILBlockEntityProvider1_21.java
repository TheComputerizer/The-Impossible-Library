package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.blockentity.TILTickableBlockEntity1_21;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_21 extends TILBasicBlock1_21 implements EntityBlock {
    
    public static TILBlockEntityProvider1_21 tileFrom(BlockProperties properties) {
        return new TILBlockEntityProvider1_21(Properties.of(),properties);
    }
    
    public TILBlockEntityProvider1_21(Properties vanillaProperties, BlockProperties properties) {
        super(vanillaProperties,properties);
    }
    
    @Override public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState s,
            BlockEntityType<T> type) {
        return (level,pos,state,entity) -> {
            if(entity instanceof TILTickableBlockEntity1_21) ((TILTickableBlockEntity1_21)entity).tick();
        };
    }
    
    @Override public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return (BlockEntity)this.properties.createBlockEntity(null,WrapperHelper.wrapPosition(pos),
                WrapperHelper.wrapState(state)).getEntity();
    }
}