package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.ClientEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayPostEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayPreEventForge1_20_6;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_PRE;

public class ClientEventsForge1_20_6 extends ClientEventsForge1_20 {
    
    @Override protected void defineVersionedEvents() {
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventForge1_20_6());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventForge1_20_6());
    }
}