package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.BossInfo;

import javax.annotation.Nonnull;

public class RenderOverlayBossEventForge extends RenderOverlayBossEventWrapper<BossInfo> {

    @Override
    protected RenderAPI initRenderer(@Nonnull BossInfo event) {
        return ClientEventsForge.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<BossInfo,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsForge.getOverlayElementType(event.getType()),OverlayType.ALL);
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