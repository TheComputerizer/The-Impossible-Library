package mods.thecomputerizer.theimpossiblelibrary.api.entity;

public interface LivingEntityAPI<L> {

    EntityAPI<?> getEntityAPI();
    float getHealth();
    L getLiving();
    float getMaxHealth();
}