package mods.thecomputerizer.theimpossiblelibrary.api.registry.entity;

public interface LivingEntityAPI<L> {

    EntityAPI<?> getEntityAPI();
    float getHealth();
    L getLiving();
    float getMaxHealth();
}