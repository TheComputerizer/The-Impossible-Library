package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public abstract class CameraSetupEventForge extends CameraSetupEventWrapper<CameraSetup> {
    
    @SubscribeEvent
    public static void onEvent(CameraSetup event) {
        CAMERA_SETUP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(CameraSetup event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
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

    @Override protected EventFieldWrapper<CameraSetup,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}