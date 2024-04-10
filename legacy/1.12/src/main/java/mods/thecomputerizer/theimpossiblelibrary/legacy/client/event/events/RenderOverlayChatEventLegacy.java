package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayChatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;

public class RenderOverlayChatEventLegacy extends RenderOverlayChatEventWrapper<Chat> {

    @SubscribeEvent
    public static void onEvent(Chat event) {
        RENDER_OVERLAY_CHAT.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull Chat event) {
        return ClientEventsLegacy.initRenderer(event::getPartialTicks);
    }

    @Override
    protected EventFieldWrapper<Chat,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsLegacy.getOverlayElementType(event.getType()),OverlayType.ALL);
    }

    @Override
    protected EventFieldWrapper<Chat,Integer> wrapPosXField() {
        return wrapGenericBoth(Chat::getPosX,Chat::setPosX,0);
    }

    @Override
    protected EventFieldWrapper<Chat,Integer> wrapPosYField() {
        return wrapGenericBoth(Chat::getPosY,Chat::setPosY,0);
    }
}