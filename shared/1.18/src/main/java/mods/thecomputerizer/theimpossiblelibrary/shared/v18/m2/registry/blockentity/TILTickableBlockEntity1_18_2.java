package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.function.Consumer;

public class TILTickableBlockEntity1_18_2 extends TILBasicBlockEntity1_18_2 {
    
    protected final Consumer<BlockEntityAPI<?,?>> onTick;
    
    public TILTickableBlockEntity1_18_2(BlockEntityType<?> type, BlockPos pos, BlockState state,
            Consumer<BlockEntityAPI<?,?>> onTick) {
        super(type,pos,state);
        this.onTick = onTick;
    }
    
    public void tick() {
        if(Objects.nonNull(this.onTick)) this.onTick.accept(WrapperHelper.wrapBlockEntity(this));
    }
}