package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientNetworkEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_CONNECTED;

public abstract class ClientConnectedEventWrapper<E> extends ClientNetworkEventWrapper<E> {

    protected EventFieldWrapper<E,Boolean> local;
    protected EventFieldWrapper<E,String> connectionType;

    protected ClientConnectedEventWrapper() {
        super(CLIENT_CONNECTED);
    }

    public String getConnectionType() {
        return this.connectionType.get(this.event);
    }

    public boolean isLocal() {
        return this.local.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.local = wrapLocalField();
        this.connectionType = wrapConnectionTypeField();
    }

    protected abstract EventFieldWrapper<E,Boolean> wrapLocalField();
    protected abstract EventFieldWrapper<E,String> wrapConnectionTypeField();
}