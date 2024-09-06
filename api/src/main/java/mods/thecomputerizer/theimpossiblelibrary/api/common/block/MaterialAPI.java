package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class MaterialAPI<M> extends AbstractWrapped<M> {

    protected MaterialAPI(M material) {
        super(material);
    }

    public abstract boolean hasCollider();
    public abstract boolean isAir();
    public abstract boolean isDestroyedByPiston();
    public abstract boolean isFlammable();
    public abstract boolean isLiquid();
    public abstract boolean isPushable();
    public abstract boolean isReplaceable();
    public abstract boolean isSolid();
    public abstract boolean isUnderwater();
}