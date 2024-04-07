package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerHandedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.BlockPosAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;

public abstract class PlayerPunchEmptyEventWrapper<E> extends ClientPlayerHandedEventWrapper<E> {

    protected EventFieldWrapper<E,BlockPosAPI<?>> pos;

    protected PlayerPunchEmptyEventWrapper() {
        super(PLAYER_PUNCH_EMPTY);
    }

    public BlockPosAPI<?> getPos() {
        return this.pos.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.pos = wrapPosField();
    }

    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField();
}