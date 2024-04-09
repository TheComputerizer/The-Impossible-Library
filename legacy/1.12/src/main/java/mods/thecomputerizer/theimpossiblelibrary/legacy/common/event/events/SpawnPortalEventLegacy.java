package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SpawnPortalEventWrapper;
import net.minecraftforge.event.world.BlockEvent.PortalSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_SPAWN_PORTAL;

public class SpawnPortalEventLegacy extends SpawnPortalEventWrapper<PortalSpawnEvent> {

    @SubscribeEvent
    public static void onEvent(PortalSpawnEvent event) {
        BLOCK_SPAWN_PORTAL.invoke(event);
    }
}