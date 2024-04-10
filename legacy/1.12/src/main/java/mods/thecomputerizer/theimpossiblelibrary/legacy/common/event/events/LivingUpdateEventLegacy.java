package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_UPDATE;

public class LivingUpdateEventLegacy extends LivingUpdateEventWrapper<LivingUpdateEvent> {

    @SubscribeEvent
    public static void onEvent(LivingUpdateEvent event) {
        LIVING_UPDATE.invoke(event);
    }

    @Override
    protected EventFieldWrapper<LivingUpdateEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingUpdateEvent::getEntityLiving);
    }
}