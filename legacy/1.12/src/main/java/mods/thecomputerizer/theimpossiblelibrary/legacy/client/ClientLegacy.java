package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.CommonLegacy;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;

public class ClientLegacy extends CommonLegacy implements ClientAPI {

    private final ClientEventsLegacy events;

    public ClientLegacy() {
        this.events = new ClientEventsLegacy();
    }

    @Override
    public ClientEventsAPI getClientEventsAPI() {
        return this.events;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> ScreenAPI<S> getScreenAPI(S screen) {
        return (ScreenAPI<S>)new ScreenLegacy((GuiScreen)screen);
    }
}
