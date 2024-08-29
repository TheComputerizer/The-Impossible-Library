package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.blockentity.BlockEntity1_16_5;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Objects;
import java.util.function.Consumer;

public class TILTickableBlockEntity1_16_5 extends TILBasicBlockEntity1_16_5 implements ITickableTileEntity {
    
    protected final Consumer<BlockEntityAPI<?,?>> onTick;
    
    public TILTickableBlockEntity1_16_5(TileEntityType<?> type, Consumer<BlockEntityAPI<?,?>> onTick) {
        super(type);
        this.onTick = onTick;
    }
    
    @Override
    public void tick() {
        if(Objects.nonNull(this.onTick)) this.onTick.accept(new BlockEntity1_16_5(this));
    }
}