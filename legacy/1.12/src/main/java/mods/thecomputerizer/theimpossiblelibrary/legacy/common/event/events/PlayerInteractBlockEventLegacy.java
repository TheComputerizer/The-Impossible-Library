package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractBlockEventWrapper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_BLOCK;

public class PlayerInteractBlockEventLegacy extends PlayerInteractBlockEventWrapper<RightClickBlock> {

    @SubscribeEvent
    public static void onEvent(RightClickBlock event) {
        PLAYER_INTERACT_BLOCK.invoke(event);
    }
}