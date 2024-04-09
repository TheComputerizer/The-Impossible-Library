package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;

public interface ClientAPI extends CommonAPI {

    EventsAPI getClientEventsAPI();
    MinecraftAPI getMinecraft();
    ScreenHelperAPI getScreenHelperAPI();
    SoundHelperAPI<?> getSoundHelperAPI();
}
