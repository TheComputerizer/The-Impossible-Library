package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingJumpEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_JUMP;

public class LivingJumpEventNeoForge extends LivingJumpEventWrapper<LivingJumpEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingJumpEvent event) {
        LIVING_JUMP.invoke(event);
    }
    
    @Override public void setEvent(LivingJumpEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<LivingJumpEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingJumpEvent::getEntity);
    }
}