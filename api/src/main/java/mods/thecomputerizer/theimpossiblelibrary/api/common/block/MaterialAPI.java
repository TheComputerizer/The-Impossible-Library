package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;

@Getter
public abstract class MaterialAPI<M> {

    protected final M material;

    protected MaterialAPI(M material) {
        this.material = material;
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