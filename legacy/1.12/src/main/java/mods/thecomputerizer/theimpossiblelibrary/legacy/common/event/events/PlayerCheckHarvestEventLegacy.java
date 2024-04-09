package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCheckHarvestEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHECK_HARVEST;

public class PlayerCheckHarvestEventLegacy extends PlayerCheckHarvestEventWrapper<HarvestCheck> {

    @SubscribeEvent
    public static void onEvent(HarvestCheck event) {
        PLAYER_CHECK_HARVEST.invoke(event);
    }
}