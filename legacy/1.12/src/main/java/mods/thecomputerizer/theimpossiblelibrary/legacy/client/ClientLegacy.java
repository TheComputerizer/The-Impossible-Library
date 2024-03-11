package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui.ScreenHelperLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.CommonLegacy;

public class ClientLegacy extends CommonLegacy implements ClientAPI {

    private final ClientEventsLegacy events;
    public final ScreenHelperLegacy screenHelper;

    public ClientLegacy() {
        this.events = new ClientEventsLegacy();
        this.screenHelper = new ScreenHelperLegacy();
    }

    @Override
    public ClientEventsAPI getClientEventsAPI() {
        return this.events;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return MinecraftLegacy.getInstance();
    }

    @Override
    public ScreenHelperAPI getScreenHelperAPI() {
        return this.screenHelper;
    }
}
