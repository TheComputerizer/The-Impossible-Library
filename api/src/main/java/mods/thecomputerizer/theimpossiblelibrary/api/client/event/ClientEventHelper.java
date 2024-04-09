package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;


import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickPhase.END;

public class ClientEventHelper {

    public static EventsAPI getEventsAPI() {
        return TILRef.getClientSubAPI("EventsAPI",ClientAPI::getClientEventsAPI);
    }

    public static void initTILClientListeners() {
        EventHelper.addListener(TICK_CLIENT,wrapper -> {
            if(wrapper.isPhase(END)) RenderHelper.tickRenderables();
        });
        EventHelper.addListener(RENDER_OVERLAY_POST,wrapper -> {
            if(wrapper.isType(ALL)) RenderHelper.renderAllBackgroundStuff(RenderHelper.getRenderer());
        });
    }
}