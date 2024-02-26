package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.CommonForge;
import net.minecraft.client.gui.screen.Screen;

public class ClientForge extends CommonForge implements ClientAPI {

    private final ClientEventsForge events;

    public ClientForge() {
        this.events = new ClientEventsForge();
    }

    @Override
    public ClientEventsAPI getClientEventsAPI() {
        return this.events;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> ScreenAPI<S> getScreenAPI(S screen) {
        return (ScreenAPI<S>)new ScreenForge((Screen)screen);
    }
}
