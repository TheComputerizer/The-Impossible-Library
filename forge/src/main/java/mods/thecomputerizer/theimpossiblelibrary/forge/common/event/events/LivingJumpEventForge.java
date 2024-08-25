package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingJumpEventWrapper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_JUMP;

public class LivingJumpEventForge extends LivingJumpEventWrapper<LivingJumpEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingJumpEvent event) {
        LIVING_JUMP.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingJumpEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<LivingJumpEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingJumpEvent::getEntityLiving);
    }
}