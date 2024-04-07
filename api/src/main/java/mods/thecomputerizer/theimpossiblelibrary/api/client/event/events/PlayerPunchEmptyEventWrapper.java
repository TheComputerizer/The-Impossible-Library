package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;

public abstract class PlayerPunchEmptyEventWrapper<E> extends ClientPlayerEventWrapper<E> {

    protected PlayerPunchEmptyEventWrapper() {
        super(PLAYER_PUNCH_EMPTY);
    }

    @Override
    protected void populate() {
        super.populate();
    }
}