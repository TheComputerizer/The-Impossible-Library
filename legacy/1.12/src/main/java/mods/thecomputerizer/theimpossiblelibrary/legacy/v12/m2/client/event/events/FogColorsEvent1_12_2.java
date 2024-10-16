package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogColorsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_COLORS;

public class FogColorsEvent1_12_2 extends FogColorsEventWrapper<FogColors> {

    @SubscribeEvent
    public static void onEvent(FogColors event) {
        FOG_COLORS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull FogColors event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getRenderPartialTicks()));
    }
    
    @Override public void setEvent(FogColors event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<FogColors,Float> wrapBlue() {
        return wrapGenericBoth(FogColors::getBlue,FogColors::setBlue,0f);
    }

    @Override protected EventFieldWrapper<FogColors,Float> wrapGreen() {
        return wrapGenericBoth(FogColors::getGreen,FogColors::setGreen,0f);
    }

    @Override protected EventFieldWrapper<FogColors,Float> wrapRed() {
        return wrapGenericBoth(FogColors::getRed,FogColors::setRed,0f);
    }

    @Override protected EventFieldWrapper<FogColors,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(FogColors::getEntity);
    }

    @Override protected EventFieldWrapper<FogColors,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FogColors::getState);
    }
}