package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_RESPAWN;

public abstract class ClientRespawnEventWrapper<E> extends ClientPlayerEventType<E> {

    protected EventFieldWrapper<E,PlayerAPI<?,?>> oldPlayer;

    protected ClientRespawnEventWrapper() {
        super(CLIENT_RESPAWN);
    }

    public PlayerAPI<?,?> getOldPlayer() {
        return this.oldPlayer.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.oldPlayer = wrapOldPlayerField();
    }

    protected abstract EventFieldWrapper<E,PlayerAPI<?,?>> wrapOldPlayerField();
}