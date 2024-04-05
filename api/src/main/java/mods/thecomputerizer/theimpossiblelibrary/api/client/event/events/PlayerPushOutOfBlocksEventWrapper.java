package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUSH_OUT_OF_BLOCKS;

public abstract class PlayerPushOutOfBlocksEventWrapper<E> extends ClientEventWrapper<E> {

    protected PlayerPushOutOfBlocksEventWrapper() {
        super(PLAYER_PUSH_OUT_OF_BLOCKS);
    }

    @Override
    protected void populate() {

    }
}