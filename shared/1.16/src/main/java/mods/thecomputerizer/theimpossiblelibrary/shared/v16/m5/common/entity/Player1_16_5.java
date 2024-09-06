package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.util.math.BlockPos;

public abstract class Player1_16_5<P,T> extends PlayerAPI<P,T> {

    protected Player1_16_5(P player, T type) {
        super(player,type);
    }
    
    @Override public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) { //TODO 1.16.5 dimension specific respawn pos
        return null;
    }

    protected abstract Box getBoundingBox(Object box);

    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(getWorld(),getDimensionType(this.entity));
    }
    
    protected abstract <D> D getDimensionType(P player);

    @Override public BlockPosAPI<?> getPosRounded() {
        return PosHelper.getPos(new BlockPos((int)(Math.round(x()*2d)/2d),(int)(Math.round(y()*2d)/2d),
                (int)(Math.round(z()*2d)/2d)));
    }

    @Override public WorldAPI<?> getWorld() {
        return WrapperHelper.wrapWorld(getWorld(this.entity));
    }
    
    protected abstract <W> W getWorld(P player);

    @Override public boolean isAnimal() {
        return false;
    }

    @Override public boolean isLiving() {
        return true;
    }

    @Override public boolean isPlayer() {
        return true;
    }

    @Override public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return false;
    }
}