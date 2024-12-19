package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogColorsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_COLORS;

public abstract class FogColorsEventForge extends FogColorsEventWrapper<FogColors> {
    
    @SubscribeEvent
    public static void onEvent(FogColors event) {
        FOG_COLORS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
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

    @Override protected EventFieldWrapper<FogColors,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}