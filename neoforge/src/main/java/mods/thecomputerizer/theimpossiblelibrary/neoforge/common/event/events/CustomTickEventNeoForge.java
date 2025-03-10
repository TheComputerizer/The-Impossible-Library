package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CustomTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CUSTOM_TICK;

public class CustomTickEventNeoForge extends CustomTickEventWrapper<CustomTickNeoForge> {
    
    @SubscribeEvent
    public static void onEvent(CustomTickNeoForge event) {
        CUSTOM_TICK.invoke(event);
    }
    
    @Override public void setEvent(CustomTickNeoForge event) {
        super.setEvent(event);
    }
    
    @Override protected CustomTick wrapTicker() {
        return Objects.nonNull(this.event) ? this.event.getTicker() : null;
    }
}