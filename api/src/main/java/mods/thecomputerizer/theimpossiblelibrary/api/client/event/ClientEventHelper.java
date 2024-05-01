package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;


import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests.TEST_KEY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;

public class ClientEventHelper {

    public static EventsAPI getEventsAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getClientEvents);
    }

    public static void initTILClientListeners(boolean test) {
        EventHelper.addListener(TICK_CLIENT,wrapper -> {
            if(wrapper.isPhase(END)) RenderHelper.tickRenderables();
        });
        EventHelper.addListener(RENDER_OVERLAY_POST,wrapper -> {
            if(wrapper.isType(ALL)) RenderHelper.renderAllBackgroundStuff(RenderHelper.getRenderer());
        });
        if(test) {
            TILDev.logInfo("Registering keybind test");
            EventHelper.addListener(KEY_INPUT,wrapper -> {
                if(TEST_KEY.isDown()) ClientTests.runTests();
            });
        }
    }
}