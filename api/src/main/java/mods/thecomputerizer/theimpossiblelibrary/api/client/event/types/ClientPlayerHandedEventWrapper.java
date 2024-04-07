package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.PlayerHandedEventWrapper.Hand;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.PlayerHandedEventWrapper.Hand.MAINHAND;

public abstract class ClientPlayerHandedEventWrapper<E> extends ClientPlayerEventWrapper<E> {



    protected ClientPlayerHandedEventWrapper(ClientType<?> type) {
        super(type);
    }

    public abstract Hand getHand();

    public boolean isMainHand() {
        return getHand()==MAINHAND;
    }
}
