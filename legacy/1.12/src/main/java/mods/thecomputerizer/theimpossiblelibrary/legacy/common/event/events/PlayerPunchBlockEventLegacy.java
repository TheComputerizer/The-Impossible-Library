package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPunchBlockEventWrapper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_BLOCK;

public class PlayerPunchBlockEventLegacy extends PlayerPunchBlockEventWrapper<LeftClickBlock> {

    @SubscribeEvent
    public static void onEvent(LeftClickBlock event) {
        PLAYER_PUNCH_BLOCK.invoke(event);
    }
}