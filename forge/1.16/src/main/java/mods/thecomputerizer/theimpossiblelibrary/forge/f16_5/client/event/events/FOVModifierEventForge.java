package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVModifierEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;

import javax.annotation.Nonnull;

public class FOVModifierEventForge extends FOVModifierEventWrapper<FOVModifier> {

    @Override
    protected EventFieldWrapper<FOVModifier,Float> wrapFOVField() {
        return wrapGenericBoth(event -> (float)event.getFOV(),(event,fov) -> event.setFOV(fov),0f);
    }

    @Override
    protected EventFieldWrapper<FOVModifier,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override
    protected EventFieldWrapper<FOVModifier,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull FOVModifier event) {
        return ClientEventsForge.initRenderer(() -> (float)event.getRenderPartialTicks(),() -> null);
    }
}