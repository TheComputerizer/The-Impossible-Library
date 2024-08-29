package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_TEXT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;

public class RenderOverlayTextEventForge extends RenderOverlayTextEventWrapper<Text> {
    
    @SubscribeEvent
    public static void onEvent(Text event) {
        RENDER_OVERLAY_TEXT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull Text event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getMatrixStack()));
    }
    
    @Override public void setEvent(Text event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<Text,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(event.getType()),ALL);
    }

    @Override protected EventFieldWrapper<Text,List<String>> wrapLeftField() {
        return wrapGenericGetter(Text::getLeft,new ArrayList<>());
    }

    @Override protected EventFieldWrapper<Text,List<String>> wrapRightField() {
        return wrapGenericGetter(Text::getRight,new ArrayList<>());
    }
}