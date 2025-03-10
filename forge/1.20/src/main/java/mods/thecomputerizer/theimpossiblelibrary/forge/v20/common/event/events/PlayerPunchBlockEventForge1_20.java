package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PlayerPunchBlockEventForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_BLOCK;

public class PlayerPunchBlockEventForge1_20 extends PlayerPunchBlockEventForge {
    
    @SubscribeEvent
    public static void onEvent(LeftClickBlock event) {
        PLAYER_PUNCH_BLOCK.invoke(event);
    }

    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(LeftClickBlock::getLevel);
    }
}