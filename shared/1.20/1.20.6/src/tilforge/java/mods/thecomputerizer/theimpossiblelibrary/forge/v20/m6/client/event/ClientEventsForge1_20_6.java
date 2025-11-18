package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.ClientEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayBlockEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayBossEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayChatEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayPostEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayPreEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderOverlayTextEventForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events.RenderWorldLastEventForge1_20_6;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;

public class ClientEventsForge1_20_6 extends ClientEventsForge1_20 {
    
    @Override protected void defineVersionedEvents() {
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventForge1_20_6());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventForge1_20_6());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventForge1_20_6());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventForge1_20_6());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventForge1_20_6());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventForge1_20_6());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventForge1_20_6());
    }
}