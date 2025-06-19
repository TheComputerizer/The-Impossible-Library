package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;

import java.util.function.Consumer;

public interface ClientEventsAPI extends CommonEventsAPI {
    
    default <T> OverlayType getOverlayBlockType(T blockType) {
        return (OverlayType)blockType;
    }

    default <T> OverlayType getOverlayElementType(T elementType) {
        return (OverlayType)elementType;
    }
    
    RenderContext initRenderer(Consumer<RenderContext> setters);
}