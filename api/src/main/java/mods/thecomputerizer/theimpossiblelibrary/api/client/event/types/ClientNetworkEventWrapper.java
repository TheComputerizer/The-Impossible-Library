package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;

public abstract class ClientNetworkEventWrapper<E> extends ClientEventWrapper<E> {

    protected NetworkAPI<?,?> network;

    protected ClientNetworkEventWrapper(ClientType<?> type) {
        super(type);
    }

    @Override
    protected void populate() {
        this.network = NetworkHelper.getNetworkAPI();
    }
}
