package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.PlayerHandedEventWrapper.Hand.MAINHAND;

public abstract class PlayerHandedEventWrapper<E> extends PlayerEventWrapper<E> {

    protected PlayerHandedEventWrapper(CommonType<?> type) {
        super(type);
    }

    public abstract Hand getHand();

    public boolean isMainHand() {
        return getHand()==MAINHAND;
    }

    public enum Hand { MAINHAND, OFFHAND }
}
