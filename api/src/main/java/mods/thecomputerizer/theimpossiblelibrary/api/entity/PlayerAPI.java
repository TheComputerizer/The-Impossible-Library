package mods.thecomputerizer.theimpossiblelibrary.api.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import org.joml.Vector3d;
import org.joml.Vector3i;

import javax.annotation.Nullable;
import java.util.UUID;

public interface PlayerAPI<P> {

    int getAir();
    BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension);
    DimensionAPI<?> getDimension();

    default double getDistanceTo(EntityAPI<?> entity) {
        return getEntityAPI().getDistanceTo(entity);
    }

    default double getDistanceTo(BlockPosAPI<?> pos) {
        return getEntityAPI().getDistanceTo(pos);
    }

    default double getDistanceTo(Vector3i pos) {
        return getEntityAPI().getDistanceTo(pos);
    }

    EntityAPI<?> getEntityAPI();

    default float getHealth() {
        return getLivingAPI().getHealth();
    }

    default float getHealthPercent() {
        return getLivingAPI().getHealthPercent();
    }

    LivingEntityAPI<?> getLivingAPI();

    default float getMaxHealth() {
        return getLivingAPI().getMaxHealth();
    }

    P getPlayer();

    default BlockPosAPI<?> getPos() {
        return getEntityAPI().getPos();
    }

    Vector3d getPosExact();

    default BlockPosAPI<?> getPosRounded() {
        return getEntityAPI().getPosRounded();
    }

    UUID getUUID();
    boolean isAdventureMode();
    boolean isClientPlayer();
    boolean isCreativeMode();
    boolean isFishing();
    boolean isFlying();
    boolean isSpectatorMode();
    boolean isSurvivalMode();

    default void sendMessage(TextAPI<?> text) {
        sendMessage(text,null);
    }

    void sendMessage(TextAPI<?> text, @Nullable UUID uuid);
    void sendStatusMessage(TextAPI<?> text, boolean actionBar);
}