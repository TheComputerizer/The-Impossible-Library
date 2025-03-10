package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerInteractItemEventForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ITEM;

public class PlayerInteractItemEventForge1_18_2 extends PlayerInteractItemEventForge {
    
    @SubscribeEvent
    public static void onEvent(RightClickItem event) {
        PLAYER_INTERACT_ITEM.invoke(event);
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(RightClickItem::getWorld);
    }
}