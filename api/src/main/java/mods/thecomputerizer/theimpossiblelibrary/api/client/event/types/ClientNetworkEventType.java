package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;

public abstract class ClientNetworkEventType<E> extends ClientEventWrapper<E> {

    protected NetworkAPI<?,?> network;

    protected ClientNetworkEventType(ClientType<?> type) {
        super(type);
    }

    @Override
    protected void populate() {
        this.network = NetworkHelper.getNetworkAPI();
    }
}
