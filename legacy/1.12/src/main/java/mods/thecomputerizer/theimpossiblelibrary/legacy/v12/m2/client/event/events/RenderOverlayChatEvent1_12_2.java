package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayChatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;

public class RenderOverlayChatEvent1_12_2 extends RenderOverlayChatEventWrapper<Chat> {

    @SubscribeEvent
    public static void onEvent(Chat event) {
        RENDER_OVERLAY_CHAT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull Chat event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks(event.getPartialTicks()));
    }
    
    @Override public void setEvent(Chat event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<Chat,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(event.getType()),ALL);
    }

    @Override protected EventFieldWrapper<Chat,Integer> wrapPosXField() {
        return wrapGenericBoth(Chat::getPosX,Chat::setPosX,0);
    }

    @Override protected EventFieldWrapper<Chat,Integer> wrapPosYField() {
        return wrapGenericBoth(Chat::getPosY,Chat::setPosY,0);
    }
}