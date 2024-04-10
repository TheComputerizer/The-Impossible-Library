package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogColorsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;

import javax.annotation.Nonnull;

public class FogColorsEventForge extends FogColorsEventWrapper<FogColors> {

    @Override
    protected RenderAPI initRenderer(@Nonnull FogColors event) {
        return ClientEventsForge.initRenderer(() -> (float)event.getRenderPartialTicks(),() -> null);
    }

    @Override
    protected EventFieldWrapper<FogColors,Float> wrapBlue() {
        return wrapGenericBoth(FogColors::getBlue,FogColors::setBlue,0f);
    }

    @Override
    protected EventFieldWrapper<FogColors,Float> wrapGreen() {
        return wrapGenericBoth(FogColors::getGreen,FogColors::setGreen,0f);
    }

    @Override
    protected EventFieldWrapper<FogColors,Float> wrapRed() {
        return wrapGenericBoth(FogColors::getRed,FogColors::setRed,0f);
    }

    @Override
    protected EventFieldWrapper<FogColors,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override
    protected EventFieldWrapper<FogColors,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}