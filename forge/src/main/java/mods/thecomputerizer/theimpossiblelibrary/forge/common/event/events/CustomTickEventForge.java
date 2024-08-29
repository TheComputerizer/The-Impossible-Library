package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CustomTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CUSTOM_TICK;

public class CustomTickEventForge extends CustomTickEventWrapper<CustomTickForge> {
    
    @SubscribeEvent
    public static void onEvent(CustomTickForge event) {
        CUSTOM_TICK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(CustomTickForge event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected CustomTick wrapTicker() {
        return Objects.nonNull(this.event) ? this.event.getTicker() : null;
    }
}