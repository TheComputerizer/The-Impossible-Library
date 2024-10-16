package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests.TEST_KEY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;

public class ClientEventHelper {

    public static ClientEventsAPI getEventsAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getClientEvents);
    }

    public static void initTILClientListeners(boolean test) {
        EventHelper.addListener(TICK_CLIENT,wrapper -> {
            if(wrapper.isPhase(END)) RenderHelper.tickRenderables();
        });
        EventHelper.addListener(RENDER_OVERLAY_POST,wrapper -> {
            if(wrapper.isType(ALL)) RenderHelper.renderAllBackgroundStuff(wrapper.getRenderer());
        });
        if(test && Objects.nonNull(TEST_KEY)) {
            TILDev.logInfo("Registering keybind test");
            EventHelper.addListener(KEY_INPUT,wrapper -> {
                if(TEST_KEY.isDown()) ClientTests.runTests();
            });
        }
    }
}