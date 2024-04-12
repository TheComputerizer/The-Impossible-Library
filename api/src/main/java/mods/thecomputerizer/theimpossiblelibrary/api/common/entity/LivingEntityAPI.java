package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

public abstract class LivingEntityAPI<L,V> extends EntityAPI<L,V> {

    protected LivingEntityAPI(L entity, V type) {
        super(entity,type);
    }

    public abstract float getHealth();

    public float getHealthPercent() {
        return getMaxHealth()/getHealth();
    }

    public abstract float getMaxHealth();
}