package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui.ScreenHelperForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.CommonForge;

public class ClientForge extends CommonForge implements ClientAPI {

    private final ClientEventsForge events;
    private final ScreenHelperForge screenHelper;

    public ClientForge() {
        this.events = new ClientEventsForge();
        this.screenHelper = new ScreenHelperForge();
    }

    @Override
    public ClientEventsAPI getClientEventsAPI() {
        return this.events;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return MinecraftForgeTIL.getInstance();
    }

    @Override
    public ScreenHelperAPI getScreenHelperAPI() {
        return this.screenHelper;
    }
}
