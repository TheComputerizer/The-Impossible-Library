package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerInteractEntityEventForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY;

public class PlayerInteractEntityEventForge1_20 extends PlayerInteractEntityEventForge {
    
    @SubscribeEvent
    public static void onEvent(EntityInteract event) {
        PLAYER_INTERACT_ENTITY.invoke(event);
    }

    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(EntityInteract::getLevel);
    }
}