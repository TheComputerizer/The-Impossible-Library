package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventAPI;

public interface ClientEventAPI<M> extends EventAPI {

    M getMinecraft();
}