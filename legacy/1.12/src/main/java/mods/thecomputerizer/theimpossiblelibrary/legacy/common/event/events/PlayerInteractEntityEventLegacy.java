package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntityEventWrapper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY;

public class PlayerInteractEntityEventLegacy extends PlayerInteractEntityEventWrapper<EntityInteract> {

    @SubscribeEvent
    public static void onEvent(EntityInteract event) {
        PLAYER_INTERACT_ENTITY.invoke(event);
    }
}