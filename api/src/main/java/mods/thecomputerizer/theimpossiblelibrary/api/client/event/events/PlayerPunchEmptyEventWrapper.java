package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerInteractionEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractionEventType.Hand;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractionEventType.Hand.MAINHAND;

public abstract class PlayerPunchEmptyEventWrapper<E> extends ClientPlayerInteractionEventType<E> {

    protected PlayerPunchEmptyEventWrapper() {
        super(PLAYER_PUNCH_EMPTY);
    }

    @Override
    public Hand getHand() {
        return MAINHAND;
    }
}