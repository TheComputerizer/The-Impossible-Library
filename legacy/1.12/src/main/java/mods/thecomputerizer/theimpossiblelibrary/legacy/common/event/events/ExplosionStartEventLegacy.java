package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionStartEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import net.minecraftforge.event.world.ExplosionEvent.Start;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_START;

public class ExplosionStartEventLegacy extends ExplosionStartEventWrapper<Start> {

    @SubscribeEvent
    public static void onEvent(Start event) {
        EXPLOSION_START.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Start,ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(Start::getExplosion);
    }

    @Override
    protected EventFieldWrapper<Start,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Start::getWorld);
    }
}