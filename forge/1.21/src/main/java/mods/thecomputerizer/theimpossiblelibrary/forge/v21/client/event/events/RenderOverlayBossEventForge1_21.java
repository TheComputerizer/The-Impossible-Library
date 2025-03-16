package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.BOSSINFO;

public class RenderOverlayBossEventForge1_21 extends RenderOverlayBossEventWrapper<BossEventProgress> {
    
    @SubscribeEvent
    public static void onEvent(BossEventProgress event) {
        RENDER_OVERLAY_BOSS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull BossEventProgress event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getGuiGraphics()));
    }
    
    @Override public void setEvent(BossEventProgress event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<BossEventProgress,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(BOSSINFO),ALL);
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