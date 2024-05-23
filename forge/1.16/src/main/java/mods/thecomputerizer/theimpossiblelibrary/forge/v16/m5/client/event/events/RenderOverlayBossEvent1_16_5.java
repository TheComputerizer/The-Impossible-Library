package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.RenderGameOverlayEvent.BossInfo;

import javax.annotation.Nonnull;

public class RenderOverlayBossEvent1_16_5 extends RenderOverlayBossEventWrapper<BossInfo> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(BossInfo event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected RenderContext initRenderer(@Nonnull BossInfo event) {
        return ClientEvents1_16_5.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<BossInfo,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEvents1_16_5.getOverlayElementType(event.getType()),OverlayType.ALL);
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