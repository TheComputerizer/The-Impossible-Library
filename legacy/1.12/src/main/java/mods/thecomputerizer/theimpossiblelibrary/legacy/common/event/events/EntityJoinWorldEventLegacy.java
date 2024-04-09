package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityJoinWorldEventWrapper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_JOIN_WORLD;

public class EntityJoinWorldEventLegacy extends EntityJoinWorldEventWrapper<EntityJoinWorldEvent> {

    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event) {
        ENTITY_JOIN_WORLD.invoke(event);
    }
}