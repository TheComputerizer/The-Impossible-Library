package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay.BOSS_EVENT_PROGRESS;

public class RenderOverlayBossEventNeoForge extends RenderOverlayBossEventWrapper<BossEventProgress> {
    
    @SubscribeEvent
    public static void onEvent(BossEventProgress event) {
        RENDER_OVERLAY_BOSS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(BossEventProgress event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getGuiGraphics()));
    }
    
    @Override public void setEvent(BossEventProgress event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<BossEventProgress,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(BOSS_EVENT_PROGRESS),ALL);
    }

    @Override protected EventFieldWrapper<BossEventProgress,Integer> wrapIncrementField() {
        return wrapGenericBoth(BossEventProgress::getIncrement,BossEventProgress::setIncrement,0);
    }

    @Override protected EventFieldWrapper<BossEventProgress,Integer> wrapXField() {
        return wrapGenericGetter(BossEventProgress::getX,0);
    }

    @Override protected EventFieldWrapper<BossEventProgress,Integer> wrapYField() {
        return wrapGenericGetter(BossEventProgress::getY,0);
    }
}