package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;

public interface ClientAPI extends CommonAPI {

    ClientEventsAPI getClientEventsAPI();
    <S> ScreenAPI<S> getScreenAPI(S screen);
}
