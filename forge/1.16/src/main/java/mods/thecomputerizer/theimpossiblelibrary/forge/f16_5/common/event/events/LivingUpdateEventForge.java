package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LivingUpdateEventForge extends LivingUpdateEventWrapper<LivingUpdateEvent> {

    @Override
    protected EventFieldWrapper<LivingUpdateEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingUpdateEvent::getEntityLiving);
    }
}