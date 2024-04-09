package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityStruckByLightningEventWrapper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_SMITTEN;

public class EntityStruckByLightningEventLegacy extends EntityStruckByLightningEventWrapper<EntityStruckByLightningEvent> {

    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event) {
        ENTITY_SMITTEN.invoke(event);
    }
}