package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LootingLevelEventWrapper;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public abstract class LootingLevelEventNeoForge<E extends LivingEvent> extends LootingLevelEventWrapper<E> {
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingEvent::getEntity);
    }
}