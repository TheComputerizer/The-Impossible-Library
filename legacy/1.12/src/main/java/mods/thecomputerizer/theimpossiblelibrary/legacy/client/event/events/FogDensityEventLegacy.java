package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;

import javax.annotation.Nonnull;

public class FogDensityEventLegacy extends FogDensityEventWrapper<FogDensity> {

    @Override
    protected RenderAPI initRenderer(@Nonnull FogDensity event) {
        return ClientEventsLegacy.initRenderer(event::getDensity);
    }

    @Override
    protected EventFieldWrapper<FogDensity,Float> wrapDensityField() {
        return getFieldWrapperGetter(FogDensity::getDensity,0f);
    }

    @Override
    protected EventFieldWrapper<FogDensity,EntityAPI<?>> wrapEntityField() {
        return getFieldWrapperGetter(event -> wrapEntity(FogDensity::getEntity),null);
    }

    @Override
    protected EventFieldWrapper<FogDensity,BlockStateAPI<?>> wrapStateField() {
        return getFieldWrapperGetter(event -> wrapState(FogDensity::getState),null);
    }
}