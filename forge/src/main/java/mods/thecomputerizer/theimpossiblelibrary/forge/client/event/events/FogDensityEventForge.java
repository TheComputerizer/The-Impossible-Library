package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_DENSITY;

public abstract class FogDensityEventForge extends FogDensityEventWrapper<FogDensity> {
    
    @SubscribeEvent
    public static void onEvent(FogDensity event) {
        FOG_DENSITY.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(FogDensity event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<FogDensity,Float> wrapDensityField() {
        return wrapGenericGetter(FogDensity::getDensity,0f);
    }

    @Override protected EventFieldWrapper<FogDensity,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}