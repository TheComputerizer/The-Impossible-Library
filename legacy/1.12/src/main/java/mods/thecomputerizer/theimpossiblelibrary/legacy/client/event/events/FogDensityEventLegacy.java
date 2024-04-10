package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_DENSITY;

public class FogDensityEventLegacy extends FogDensityEventWrapper<FogDensity> {

    @SubscribeEvent
    public static void onEvent(FogDensity event) {
        FOG_DENSITY.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull FogDensity event) {
        return ClientEventsLegacy.initRenderer(event::getDensity);
    }

    @Override
    protected EventFieldWrapper<FogDensity,Float> wrapDensityField() {
        return wrapGenericGetter(FogDensity::getDensity,0f);
    }

    @Override
    protected EventFieldWrapper<FogDensity,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(FogDensity::getEntity);
    }

    @Override
    protected EventFieldWrapper<FogDensity,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FogDensity::getState);
    }
}