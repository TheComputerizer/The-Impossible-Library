package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickableBlockEntity;

import java.util.Objects;
import java.util.function.Consumer;

public class TILTickableBlockEntity1_16_5 extends TILBasicBlockEntity1_16_5 implements TickableBlockEntity {
    
    protected final Consumer<BlockEntityAPI<?,?>> onTick;
    
    public TILTickableBlockEntity1_16_5(BlockEntityType<?> type, Consumer<BlockEntityAPI<?,?>> onTick) {
        super(type);
        this.onTick = onTick;
    }
    
    @Override public void tick() {
        if(Objects.nonNull(this.onTick)) this.onTick.accept(WrapperHelper.wrapBlockEntity(this));
    }
}