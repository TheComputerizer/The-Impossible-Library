package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;

import java.util.function.Consumer;

public interface ClientEventsAPI extends CommonEventsAPI {
    
    <B> OverlayType getOverlayBlockType(B blockType);
    <E> OverlayType getOverlayElementType(E elementType);
    RenderContext initRenderer(Consumer<RenderContext> setters);
}