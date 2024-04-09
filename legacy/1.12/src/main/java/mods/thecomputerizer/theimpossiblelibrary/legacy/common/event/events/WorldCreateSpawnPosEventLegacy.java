package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldCreateSpawnPosEventWrapper;
import net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_CREATE_SPAWN_POS;

public class WorldCreateSpawnPosEventLegacy extends WorldCreateSpawnPosEventWrapper<CreateSpawnPosition> {

    @SubscribeEvent
    public static void onEvent(CreateSpawnPosition event) {
        WORLD_CREATE_SPAWN_POS.invoke(event);
    }
}