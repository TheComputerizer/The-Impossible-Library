package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent.BossInfo;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;

public class RenderOverlayBossEventForge extends RenderOverlayBossEventWrapper<BossInfo> {
    
    @SubscribeEvent
    public static void onEvent(BossInfo event) {
        RENDER_OVERLAY_BOSS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull BossInfo event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getMatrixStack()));
    }
    
    @Override public void setEvent(BossInfo event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<BossInfo,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(event.getType()),ALL);
    }

    @Override protected EventFieldWrapper<BossInfo,Integer> wrapIncrementField() {
        return wrapGenericBoth(BossInfo::getIncrement,BossInfo::setIncrement,0);
    }

    @Override protected EventFieldWrapper<BossInfo,Integer> wrapXField() {
        return wrapGenericGetter(BossInfo::getX,0);
    }

    @Override protected EventFieldWrapper<BossInfo,Integer> wrapYField() {
        return wrapGenericGetter(BossInfo::getY,0);
    }
}