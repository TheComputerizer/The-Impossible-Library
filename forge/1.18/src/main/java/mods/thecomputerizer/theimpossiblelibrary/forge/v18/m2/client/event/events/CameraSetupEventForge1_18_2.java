package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.CameraSetupEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public class CameraSetupEventForge1_18_2 extends CameraSetupEventForge<CameraSetup> {
    
    @SubscribeEvent
    public static void onEvent(CameraSetup event) {
        CAMERA_SETUP.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull CameraSetup event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<CameraSetup,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getCamera().getEntity());
    }
    
    @Override protected EventFieldWrapper<CameraSetup,Float> wrapPitchField() {
        return wrapGenericGetter(CameraSetup::getPitch,0f);
    }
    
    @Override protected EventFieldWrapper<CameraSetup,Float> wrapRollField() {
        return wrapGenericGetter(CameraSetup::getRoll,0f);
    }
    
    @Override protected EventFieldWrapper<CameraSetup,Float> wrapYawField() {
        return wrapGenericGetter(CameraSetup::getYaw,0f);
    }
}