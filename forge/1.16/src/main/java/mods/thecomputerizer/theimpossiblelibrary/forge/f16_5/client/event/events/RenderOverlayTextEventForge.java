package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RenderOverlayTextEventForge extends RenderOverlayTextEventWrapper<Text> {

    @Override
    protected RenderAPI initRenderer(@Nonnull Text event) {
        return ClientEventsForge.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<Text,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsForge.getOverlayElementType(event.getType()),OverlayType.ALL);
    }

    @Override
    protected EventFieldWrapper<Text,List<String>> wrapLeftField() {
        return wrapGenericGetter(Text::getLeft,new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<Text,List<String>> wrapRightField() {
        return wrapGenericGetter(Text::getRight,new ArrayList<>());
    }
}