package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntitySpecificEventWrapper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY_AT;

public class PlayerInteractEntitySpecificEventLegacy extends PlayerInteractEntitySpecificEventWrapper<EntityInteractSpecific> {

    @SubscribeEvent
    public static void onEvent(EntityInteractSpecific event) {
        PLAYER_INTERACT_ENTITY_AT.invoke(event);
    }
}