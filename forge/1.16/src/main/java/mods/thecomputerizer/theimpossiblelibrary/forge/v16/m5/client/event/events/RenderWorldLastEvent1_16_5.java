package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderWorldLastEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import javax.annotation.Nonnull;

public class RenderWorldLastEvent1_16_5 extends RenderWorldLastEventWrapper<RenderWorldLastEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(RenderWorldLastEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected RenderContext initRenderer(@Nonnull RenderWorldLastEvent event) {
        return ClientEvents1_16_5.initRenderer(() -> 0f,event::getMatrixStack);
    }
}