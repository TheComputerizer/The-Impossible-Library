package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public class ScreenHelper {

    public static @Nullable ScreenHelperAPI getAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getScreenHelper);
    }
    
    public static void open(ScreenAPI screen) {
        ScreenHelperAPI api = getAPI();
        if(Objects.nonNull(api)) api.open(screen);
    }
}