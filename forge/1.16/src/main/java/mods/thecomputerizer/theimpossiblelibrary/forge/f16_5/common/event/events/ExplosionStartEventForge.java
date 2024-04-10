package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.ExplosionStartEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.ExplosionEvent.Start;

public class ExplosionStartEventForge extends ExplosionStartEventWrapper<Start> {

    @Override
    protected EventFieldWrapper<Start,ExplosionAPI<?>> wrapExplosionField() {
        return wrapExplosionGetter(Start::getExplosion);
    }

    @Override
    protected EventFieldWrapper<Start,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Start::getWorld);
    }
}