package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class MaterialAPI<M> extends AbstractWrapped<M> {
    
    protected static final boolean IS_NAMED_ENV = CoreAPI.isNamedEnv();
    protected static final boolean IS_SRG_ENV = CoreAPI.isSrgEnv();

    protected MaterialAPI(M material) {
        super(material);
    }

    public abstract boolean hasCollider();
    public abstract boolean isAir();
    public abstract boolean isDestroyedByPiston();
    public abstract boolean isFlammable(WorldAPI<?> world, BlockPosAPI<?> pos, Facing side);
    public abstract boolean isLiquid();
    public abstract boolean isPushable();
    public abstract boolean isReplaceable();
    public abstract boolean isSolid();
    public abstract boolean isUnderwater();
}