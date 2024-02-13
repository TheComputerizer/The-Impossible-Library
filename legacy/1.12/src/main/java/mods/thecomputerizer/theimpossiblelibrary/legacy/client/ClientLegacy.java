package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.CommonLegacy;
import net.minecraft.client.gui.GuiScreen;

public class ClientLegacy extends CommonLegacy implements ClientAPI {

    private final ScreenAPI<GuiScreen> screen;

    public ClientLegacy() {
        this.screen = new ScreenLegacy();
    }

    @Override
    public ScreenAPI<?> getScreenAPI() {
        return this.screen;
    }
}
