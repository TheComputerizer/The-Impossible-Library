package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionStartEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.ExplosionEvent.Start;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_START;

public class ExplosionStartEvent1_12_2 extends ExplosionStartEventWrapper<Start> {

    @SubscribeEvent
    public static void onEvent(Start event) {
        EXPLOSION_START.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Start event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<Start,ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(Start::getExplosion);
    }

    @Override protected EventFieldWrapper<Start,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Start::getWorld);
    }
}