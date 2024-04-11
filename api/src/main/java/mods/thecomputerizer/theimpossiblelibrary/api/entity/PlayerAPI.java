package mods.thecomputerizer.theimpossiblelibrary.api.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import javax.annotation.Nullable;
import java.util.UUID;

public interface PlayerAPI<P> {

    EntityAPI<?> getEntityAPI();
    LivingEntityAPI<?> getLivingAPI();
    P getPlayer();
    UUID getUUID();
    boolean isClientPlayer();

    default void sendMessage(TextAPI<?> text) {
        sendMessage(text,null);
    }

    void sendMessage(TextAPI<?> text, @Nullable UUID uuid);
    void sendStatusMessage(TextAPI<?> text, boolean actionBar);
}