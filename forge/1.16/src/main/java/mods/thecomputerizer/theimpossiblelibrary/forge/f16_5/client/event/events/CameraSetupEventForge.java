package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;

import javax.annotation.Nonnull;

public class CameraSetupEventForge extends CameraSetupEventWrapper<CameraSetup> {

    @Override
    protected RenderAPI initRenderer(@Nonnull CameraSetup event) {
        return ClientEventsForge.initRenderer(() -> (float)event.getRenderPartialTicks(),() -> null);
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
    protected EventFieldWrapper<CameraSetup,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override
    protected EventFieldWrapper<CameraSetup,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}