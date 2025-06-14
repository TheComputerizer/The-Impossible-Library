package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.CameraSetupEventForge;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public class CameraSetupEventForge1_19 extends CameraSetupEventForge<ComputeCameraAngles> {
    
    @SubscribeEvent
    public static void onEvent(ComputeCameraAngles event) {
        CAMERA_SETUP.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(ComputeCameraAngles event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTick()));
    }
    
    @Override protected EventFieldWrapper<ComputeCameraAngles,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getCamera().getEntity());
    }
    
    @Override protected EventFieldWrapper<ComputeCameraAngles,Float> wrapPitchField() {
        return wrapGenericGetter(ComputeCameraAngles::getPitch,0f);
    }
    
    @Override protected EventFieldWrapper<ComputeCameraAngles,Float> wrapRollField() {
        return wrapGenericGetter(ComputeCameraAngles::getRoll,0f);
    }
    
    @Override protected EventFieldWrapper<ComputeCameraAngles,Float> wrapYawField() {
        return wrapGenericGetter(ComputeCameraAngles::getYaw,0f);
    }
}