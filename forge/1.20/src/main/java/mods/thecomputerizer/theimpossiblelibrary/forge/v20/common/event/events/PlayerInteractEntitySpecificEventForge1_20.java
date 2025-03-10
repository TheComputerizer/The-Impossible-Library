package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerInteractEntitySpecificEventForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY_AT;

public class PlayerInteractEntitySpecificEventForge1_20 extends PlayerInteractEntitySpecificEventForge {
    
    @SubscribeEvent
    public static void onEvent(EntityInteractSpecific event) {
        PLAYER_INTERACT_ENTITY_AT.invoke(event);
    }

    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(EntityInteractSpecific::getLevel);
    }
}