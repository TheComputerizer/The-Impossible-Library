package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderGameOverlayEvent.BossInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;

public class RenderOverlayBossEventLegacy extends RenderOverlayBossEventWrapper<BossInfo> {

    @SubscribeEvent
    public static void onEvent(BossInfo event) {
        RENDER_OVERLAY_BOSS.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull BossInfo event) {
        return ClientEventsLegacy.initRenderer(event::getPartialTicks);
    }

    @Override
    protected EventFieldWrapper<BossInfo,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsLegacy.getOverlayElementType(event.getType()),OverlayType.ALL);
    }

    @Override
    protected EventFieldWrapper<BossInfo,Integer> wrapIncrementField() {
        return wrapGenericBoth(BossInfo::getIncrement,BossInfo::setIncrement,0);
    }

    @Override
    protected EventFieldWrapper<BossInfo,Integer> wrapXField() {
        return wrapGenericGetter(BossInfo::getX,0);
    }

    @Override
    protected EventFieldWrapper<BossInfo,Integer> wrapYField() {
        return wrapGenericGetter(BossInfo::getY,0);
    }
}