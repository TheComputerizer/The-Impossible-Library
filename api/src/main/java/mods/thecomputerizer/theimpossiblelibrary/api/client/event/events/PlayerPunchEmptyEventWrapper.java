package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerInteractEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public abstract class PlayerPunchEmptyEventWrapper<E> extends ClientPlayerInteractEventType<E> {

    protected PlayerPunchEmptyEventWrapper() {
        super(PLAYER_PUNCH_EMPTY);
    }

    @Override protected EventFieldWrapper<E,Hand> wrapHandField() {
        return wrapGenericGetter(e -> MAINHAND,MAINHAND);
    }
}