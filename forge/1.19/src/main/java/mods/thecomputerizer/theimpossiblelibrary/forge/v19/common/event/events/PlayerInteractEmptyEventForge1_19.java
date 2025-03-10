package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerInteractEmptyEventForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_EMPTY;

public class PlayerInteractEmptyEventForge1_19 extends PlayerInteractEmptyEventForge {
    
    @SubscribeEvent
    public static void onEvent(RightClickEmpty event) {
        PLAYER_INTERACT_EMPTY.invoke(event);
    }
  
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(RightClickEmpty::getLevel);
    }
}