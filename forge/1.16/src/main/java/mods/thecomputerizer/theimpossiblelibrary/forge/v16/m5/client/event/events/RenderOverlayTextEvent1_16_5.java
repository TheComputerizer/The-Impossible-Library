package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RenderOverlayTextEvent1_16_5 extends RenderOverlayTextEventWrapper<Text> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Text event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected RenderContext initRenderer(@Nonnull Text event) {
        return ClientEvents1_16_5.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<Text,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEvents1_16_5.getOverlayElementType(event.getType()),OverlayType.ALL);
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