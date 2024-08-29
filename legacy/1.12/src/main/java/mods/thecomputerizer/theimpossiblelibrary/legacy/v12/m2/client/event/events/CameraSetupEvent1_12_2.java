package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public class CameraSetupEvent1_12_2 extends CameraSetupEventWrapper<CameraSetup> {

    @SubscribeEvent
    public static void onEvent(CameraSetup event) {
        CAMERA_SETUP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override
    protected RenderContext initRenderer(@Nonnull CameraSetup event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getRenderPartialTicks()));
    }
    
    @Override public void setEvent(CameraSetup event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
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
        return wrapEntityGetter(CameraSetup::getEntity);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(CameraSetup::getState);
    }
}