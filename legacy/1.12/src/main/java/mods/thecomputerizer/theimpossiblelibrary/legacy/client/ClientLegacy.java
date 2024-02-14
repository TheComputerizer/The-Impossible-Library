package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.CommonLegacy;
import net.minecraft.client.gui.GuiScreen;

public class ClientLegacy extends CommonLegacy implements ClientAPI {

    public ClientLegacy() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> ScreenAPI<S> getScreenAPI(S screen) {
        return (ScreenAPI<S>)new ScreenLegacy((GuiScreen)screen);
    }
}
