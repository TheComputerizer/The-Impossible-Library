package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public class CameraSetupEventLegacy extends CameraSetupEventWrapper<CameraSetup> {

    @SubscribeEvent
    public static void onEvent(CameraSetup event) {
        CAMERA_SETUP.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull CameraSetup event) {
        return ClientEventsLegacy.initRenderer(() -> (float)event.getRenderPartialTicks());
    }

    @Override
    protected EventFieldWrapper<CameraSetup,Float> wrapPitchField() {
        return getFieldWrapperGetter(CameraSetup::getPitch,0f);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,Float> wrapRollField() {
        return getFieldWrapperGetter(CameraSetup::getRoll,0f);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,Float> wrapYawField() {
        return getFieldWrapperGetter(CameraSetup::getYaw,0f);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,EntityAPI<?>> wrapEntityField() {
        return getFieldWrapperGetter(event -> wrapEntity(CameraSetup::getEntity),null);
    }

    @Override
    protected EventFieldWrapper<CameraSetup,BlockStateAPI<?>> wrapStateField() {
        return getFieldWrapperGetter(event -> wrapState(CameraSetup::getState),null);
    }
}