package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;

import javax.annotation.Nullable;
import java.util.UUID;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

@SuppressWarnings("unused") public abstract class PlayerAPI<P,V> extends LivingEntityAPI<P,V> {

    protected PlayerAPI(P entity, V type) {
        super(entity,type);
    }

    public abstract int getAir();
    public abstract BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension);
    public abstract int getGamemodeOrdinal();
    public abstract PlayerInventoryAPI<?> getInventory();
    public abstract ItemStackAPI<?> getMainHandStack();
    public abstract ItemStackAPI<?> getOffHandStack();

    public ItemStackAPI<?> getStackInHand(Hand hand) {
        return hand==MAINHAND ? getMainHandStack() : getOffHandStack();
    }

    public abstract UUID getUUID();
    public abstract boolean isClientPlayer();
    public abstract boolean isFishing();
    public abstract boolean isFlying();

    public boolean isGamemodeAdventure() {
        return getGamemodeOrdinal()==2;
    }

    public boolean isGamemodeCreative() {
        return getGamemodeOrdinal()==1;
    }

    public boolean isGamemodeSpectator() {
        return getGamemodeOrdinal()==3;
    }

    public boolean isGamemodeSurvival() {
        return getGamemodeOrdinal()==0;
    }

    public void sendMessage(TextAPI<?> text) {
        sendMessage(text,null);
    }

    public abstract void sendMessage(TextAPI<?> text, @Nullable UUID uuid);
    public abstract void sendStatusMessage(TextAPI<?> text, boolean actionBar);
}