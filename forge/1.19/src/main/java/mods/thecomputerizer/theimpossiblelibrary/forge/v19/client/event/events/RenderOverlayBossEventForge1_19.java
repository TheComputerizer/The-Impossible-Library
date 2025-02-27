package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.BOSS_EVENT_PROGRESS;

public class RenderOverlayBossEventForge1_19 extends RenderOverlayBossEventWrapper<RenderGuiOverlayEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderGuiOverlayEvent event) {
        if(BOSS_EVENT_PROGRESS.type().equals(event.getOverlay())) RENDER_OVERLAY_BOSS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderGuiOverlayEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getPoseStack()));
    }
    
    @Override public void setEvent(RenderGuiOverlayEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(BOSS_EVENT_PROGRESS),ALL);
    }

    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,Integer> wrapIncrementField() {
        return wrapGenericBoth(event -> 0,(event,value) -> {},0);
    }

    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,Integer> wrapXField() {
        return wrapGenericGetter(event -> 0,0);
    }

    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,Integer> wrapYField() {
        return wrapGenericGetter(event -> 0,0);
    }
}