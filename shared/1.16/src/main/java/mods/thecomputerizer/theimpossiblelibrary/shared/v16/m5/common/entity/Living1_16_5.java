package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public abstract class Living1_16_5<L,T> extends LivingEntityAPI<L,T> {

    public Living1_16_5(L living, T type) {
        super(living,type);
    }
    protected abstract Box getBoundingBox(Object box);

    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(getWorld(),getDimensionType(this.entity));
    }
    
    protected abstract <D> D getDimensionType(L living);
    
    @Override public WorldAPI<?> getWorld() {
        return WrapperHelper.wrapWorld(getWorld(this.entity));
    }
    
    protected abstract <W> W getWorld(L living);
}