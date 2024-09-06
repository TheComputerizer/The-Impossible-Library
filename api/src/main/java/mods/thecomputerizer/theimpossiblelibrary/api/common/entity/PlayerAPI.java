package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;

import javax.annotation.Nullable;
import java.util.UUID;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public abstract class PlayerAPI<P,V> extends LivingEntityAPI<P,V> {

    protected PlayerAPI(P entity, V type) {
        super(entity,type);
    }

    @IndirectCallers public abstract int getAir();
    @IndirectCallers public abstract BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension);
    public abstract int getGamemodeOrdinal();
    public abstract PlayerInventoryAPI<?> getInventory();
    public abstract ItemStackAPI<?> getMainHandStack();
    public abstract ItemStackAPI<?> getOffHandStack();

    public ItemStackAPI<?> getStackInHand(Hand hand) {
        return hand==MAINHAND ? getMainHandStack() : getOffHandStack();
    }

    public abstract UUID getUUID();
    @IndirectCallers public abstract boolean isClientPlayer();
    @IndirectCallers public abstract boolean isFishing();
    @IndirectCallers public abstract boolean isFlying();
    
    @IndirectCallers
    public boolean isGamemodeAdventure() {
        return getGamemodeOrdinal()==2;
    }
    
    @IndirectCallers
    public boolean isGamemodeCreative() {
        return getGamemodeOrdinal()==1;
    }
    
    @IndirectCallers
    public boolean isGamemodeSpectator() {
        return getGamemodeOrdinal()==3;
    }
    
    @IndirectCallers
    public boolean isGamemodeSurvival() {
        return getGamemodeOrdinal()==0;
    }
    
    @IndirectCallers
    public void sendMessage(TextAPI<?> text) {
        sendMessage(text,null);
    }

    public abstract void sendMessage(TextAPI<?> text, @Nullable UUID uuid);
    public abstract void sendStatusMessage(TextAPI<?> text, boolean actionBar);
}