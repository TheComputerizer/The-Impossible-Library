package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionStartEventWrapper;
import net.minecraftforge.event.world.ExplosionEvent.Start;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_START;

public class ExplosionStartEventLegacy extends ExplosionStartEventWrapper<Start> {

    @SubscribeEvent
    public static void onEvent(Start event) {
        EXPLOSION_START.invoke(event);
    }
}