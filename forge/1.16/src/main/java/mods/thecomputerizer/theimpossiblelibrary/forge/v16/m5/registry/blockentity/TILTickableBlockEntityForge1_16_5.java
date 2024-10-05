package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Objects;
import java.util.function.Consumer;

public class TILTickableBlockEntityForge1_16_5 extends TILBasicBlockEntityForge1_16_5 implements ITickableTileEntity {
    
    protected final Consumer<BlockEntityAPI<?,?>> onTick;
    
    public TILTickableBlockEntityForge1_16_5(TileEntityType<?> type, Consumer<BlockEntityAPI<?,?>> onTick) {
        super(type);
        this.onTick = onTick;
    }
    
    @Override public void tick() {
        if(Objects.nonNull(this.onTick)) this.onTick.accept(WrapperHelper.wrapBlockEntity(this));
    }
}