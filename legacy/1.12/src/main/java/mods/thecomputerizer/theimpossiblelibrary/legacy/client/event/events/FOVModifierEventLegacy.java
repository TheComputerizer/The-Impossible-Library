package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVModifierEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;

import javax.annotation.Nonnull;

public class FOVModifierEventLegacy extends FOVModifierEventWrapper<FOVModifier> {

    @Override
    protected EventFieldWrapper<FOVModifier,Float> wrapFOVField() {
        return getFieldWrapperBoth(FOVModifier::getFOV,FOVModifier::setFOV,0f);
    }

    @Override
    protected EventFieldWrapper<FOVModifier,EntityAPI<?>> wrapEntityField() {
        return getFieldWrapperGetter(event -> wrapEntity(FOVModifier::getEntity),null);
    }

    @Override
    protected EventFieldWrapper<FOVModifier,BlockStateAPI<?>> wrapStateField() {
        return getFieldWrapperGetter(event -> wrapState(FOVModifier::getState),null);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull FOVModifier event) {
        return ClientEventsLegacy.initRenderer(() -> (float)event.getRenderPartialTicks());
    }
}