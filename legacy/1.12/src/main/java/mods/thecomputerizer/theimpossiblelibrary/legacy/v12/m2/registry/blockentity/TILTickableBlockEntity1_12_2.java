package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;
import net.minecraft.util.ITickable;

import java.util.Objects;
import java.util.function.Consumer;

public class TILTickableBlockEntity1_12_2 extends TILBasicBlockEntity1_12_2 implements ITickable {
    
    protected final Consumer<BlockEntityAPI<?,?>> onTick;
    
    public TILTickableBlockEntity1_12_2(Consumer<BlockEntityAPI<?,?>> onTick) {
        this.onTick = onTick;
    }
    
    @Override public void update() {
        if(Objects.nonNull(this.onTick)) this.onTick.accept(new BlockEntity1_12_2(this));
    }
}