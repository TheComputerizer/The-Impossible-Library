package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractionEventType.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.BlockPosAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractionEventType.Hand.MAINHAND;

public abstract class ClientPlayerInteractionEventType<E> extends ClientPlayerEventType<E> {

    protected EventFieldWrapper<E,BlockPosAPI<?>> pos;

    protected ClientPlayerInteractionEventType(ClientType<?> type) {
        super(type);
    }

    public abstract Hand getHand();

    public BlockPosAPI<?> getPos() {
        return this.pos.get(this.event);
    }

    public boolean isMainHand() {
        return getHand()==MAINHAND;
    }

    @Override
    protected void populate() {
        super.populate();
        this.pos = wrapPosField();
    }

    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField();
}
