package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayChatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.Chat;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.CHAT;

public class RenderOverlayChatEventNeoForge1_20_6 extends RenderOverlayChatEventWrapper<Chat> {
    
    @SubscribeEvent
    public static void onEvent(Chat event) {
        RENDER_OVERLAY_CHAT.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(Chat event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getGuiGraphics()));
    }
    
    @Override public void setEvent(Chat event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<Chat,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(CHAT),ALL);
    }
    
    @Override protected EventFieldWrapper<Chat,Integer> wrapPosXField() {
        return wrapGenericGetter(Chat::getPosX,0);
    }
    
    @Override protected EventFieldWrapper<Chat,Integer> wrapPosYField() {
        return wrapGenericGetter(Chat::getPosY,0);
    }
}