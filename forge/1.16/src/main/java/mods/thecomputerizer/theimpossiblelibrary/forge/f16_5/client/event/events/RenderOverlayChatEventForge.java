package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayChatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;

import javax.annotation.Nonnull;

public class RenderOverlayChatEventForge extends RenderOverlayChatEventWrapper<Chat> {

    @Override
    protected RenderAPI initRenderer(@Nonnull Chat event) {
        return ClientEventsForge.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<Chat,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsForge.getOverlayElementType(event.getType()),OverlayType.ALL);
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