package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogColorsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_COLORS;

public class FogColorsEventLegacy extends FogColorsEventWrapper<FogColors> {

    @SubscribeEvent
    public static void onEvent(FogColors event) {
        FOG_COLORS.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull FogColors event) {
        return ClientEventsLegacy.initRenderer(() -> (float)event.getRenderPartialTicks());
    }

    @Override
    protected EventFieldWrapper<FogColors,Float> wrapBlue() {
        return getFieldWrapperBoth(FogColors::getBlue,FogColors::setBlue,0f);
    }

    @Override
    protected EventFieldWrapper<FogColors,Float> wrapGreen() {
        return getFieldWrapperBoth(FogColors::getGreen,FogColors::setGreen,0f);
    }

    @Override
    protected EventFieldWrapper<FogColors,Float> wrapRed() {
        return getFieldWrapperBoth(FogColors::getRed,FogColors::setRed,0f);
    }

    @Override
    protected EventFieldWrapper<FogColors,EntityAPI<?>> wrapEntityField() {
        return getFieldWrapperGetter(event -> wrapEntity(FogColors::getEntity),null);
    }

    @Override
    protected EventFieldWrapper<FogColors,BlockStateAPI<?>> wrapStateField() {
        return getFieldWrapperGetter(event -> wrapState(FogColors::getState),null);
    }
}