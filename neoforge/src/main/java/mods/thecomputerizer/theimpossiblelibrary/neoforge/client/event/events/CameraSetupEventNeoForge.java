package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public class CameraSetupEventNeoForge extends CameraSetupEventWrapper<ComputeCameraAngles> {
    
    @SubscribeEvent
    public static void onEvent(ComputeCameraAngles event) {
        CAMERA_SETUP.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(ComputeCameraAngles event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTick()));
    }
    
    @Override public void setEvent(ComputeCameraAngles event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<ComputeCameraAngles,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
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