package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CustomTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.util.CustomTick1_12_2;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CUSTOM_TICK;

public class CustomTickEvent1_12_2 extends CustomTickEventWrapper<CustomTick1_12_2> {

    @SubscribeEvent
    public static void onEvent(CustomTick1_12_2 event) {
        CUSTOM_TICK.invoke(event);
    }

    @Override protected CustomTick wrapTicker() {
        return Objects.nonNull(this.event) ? this.event.getTicker() : null;
    }
}