package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionDetonateEventWrapper;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_DETONATE;

public class ExplosionDetonateEventLegacy extends ExplosionDetonateEventWrapper<Detonate> {

    @SubscribeEvent
    public static void onEvent(Detonate event) {
        EXPLOSION_DETONATE.invoke(event);
    }
}