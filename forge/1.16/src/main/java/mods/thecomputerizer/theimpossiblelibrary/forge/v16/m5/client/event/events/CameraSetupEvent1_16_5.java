package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;

import javax.annotation.Nonnull;

public class CameraSetupEvent1_16_5 extends CameraSetupEventWrapper<CameraSetup> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(CameraSetup event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected RenderContext initRenderer(@Nonnull CameraSetup event) {
        return ClientEvents1_16_5.initRenderer(() -> (float)event.getRenderPartialTicks(),() -> null);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,Float> wrapPitchField() {
        return wrapGenericGetter(CameraSetup::getPitch,0f);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,Float> wrapRollField() {
        return wrapGenericGetter(CameraSetup::getRoll,0f);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,Float> wrapYawField() {
        return wrapGenericGetter(CameraSetup::getYaw,0f);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override
    protected EventFieldWrapper<CameraSetup,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}