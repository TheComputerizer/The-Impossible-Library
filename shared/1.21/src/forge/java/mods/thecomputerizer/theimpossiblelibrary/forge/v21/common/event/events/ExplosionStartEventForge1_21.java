package mods.thecomputerizer.theimpossiblelibrary.forge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.ExplosionStartEventForge;
import net.minecraftforge.event.level.ExplosionEvent.Start;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_START;

public class ExplosionStartEventForge1_21 extends ExplosionStartEventForge<Start> {
    
    @SubscribeEvent
    public static void onEvent(Start event) {
        EXPLOSION_START.invoke(event);
    }
    
    @Override protected EventFieldWrapper<Start,ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(Start::getExplosion);
    }

    @Override protected EventFieldWrapper<Start,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Start::getLevel);
    }
}