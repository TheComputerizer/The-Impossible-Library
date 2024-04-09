package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PotentialSpawnsEventWrapper;
import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_POTENTIAL_SPAWNS;

public class PotentialSpawnsEventLegacy extends PotentialSpawnsEventWrapper<PotentialSpawns> {

    @SubscribeEvent
    public static void onEvent(PotentialSpawns event) {
        WORLD_POTENTIAL_SPAWNS.invoke(event);
    }
}