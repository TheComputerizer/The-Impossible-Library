package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUSH_OUT_OF_BLOCKS;

@Getter
public abstract class PlayerPushOutOfBlocksEventWrapper<E> extends ClientPlayerEventType<E> {

    protected Box entityBB;

    protected PlayerPushOutOfBlocksEventWrapper() {
        super(PLAYER_PUSH_OUT_OF_BLOCKS);
    }

    @Override
    protected void populate() {
        super.populate();
        this.entityBB = wrapEntityBB();
    }

    protected abstract Box wrapEntityBB();
}