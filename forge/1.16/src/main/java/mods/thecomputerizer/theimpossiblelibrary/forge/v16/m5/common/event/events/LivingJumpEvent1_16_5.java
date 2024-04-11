package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingJumpEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class LivingJumpEvent1_16_5 extends LivingJumpEventWrapper<LivingJumpEvent> {

    @Override
    protected EventFieldWrapper<LivingJumpEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingJumpEvent::getEntityLiving);
    }
}