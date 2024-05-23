package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_DENSITY;

public class FogDensityEvent1_12_2 extends FogDensityEventWrapper<FogDensity> {

    @SubscribeEvent
    public static void onEvent(FogDensity event) {
        FOG_DENSITY.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(FogDensity event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected RenderContext initRenderer(@Nonnull FogDensity event) {
        return ClientEvents1_12_2.initRenderer(event::getDensity);
    }

    @Override
    protected EventFieldWrapper<FogDensity,Float> wrapDensityField() {
        return wrapGenericGetter(FogDensity::getDensity,0f);
    }

    @Override
    protected EventFieldWrapper<FogDensity,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(FogDensity::getEntity);
    }

    @Override
    protected EventFieldWrapper<FogDensity,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FogDensity::getState);
    }
}