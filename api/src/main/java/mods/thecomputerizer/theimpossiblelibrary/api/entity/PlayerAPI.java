package mods.thecomputerizer.theimpossiblelibrary.api.entity;

import java.util.UUID;

public interface PlayerAPI<P> {

    EntityAPI<?> getEntityAPI();
    LivingEntityAPI<?> getLivingAPI();
    P getPlayer();
    UUID getUUID();
    boolean isClientPlayer();
}