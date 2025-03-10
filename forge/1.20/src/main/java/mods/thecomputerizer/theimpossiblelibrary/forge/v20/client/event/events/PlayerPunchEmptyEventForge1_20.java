package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.PlayerPunchEmptyEventForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;

public class PlayerPunchEmptyEventForge1_20 extends PlayerPunchEmptyEventForge {
    
    @SubscribeEvent
    public static void onEvent(LeftClickEmpty event) {
        PLAYER_PUNCH_EMPTY.invoke(event);
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(LeftClickEmpty::getLevel);
    }
}