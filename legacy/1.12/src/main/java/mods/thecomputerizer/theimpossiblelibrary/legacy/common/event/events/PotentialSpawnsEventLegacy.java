package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PotentialSpawnsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_POTENTIAL_SPAWNS;

public class PotentialSpawnsEventLegacy extends PotentialSpawnsEventWrapper<PotentialSpawns> {

    @SubscribeEvent
    public static void onEvent(PotentialSpawns event) {
        WORLD_POTENTIAL_SPAWNS.invoke(event);
    }

    @Override
    protected EventFieldWrapper<PotentialSpawns,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PotentialSpawns::getPos);
    }

    @Override
    protected EventFieldWrapper<PotentialSpawns,List<SpawnEntryAPI<?>>> wrapSpawnsField() {
        return wrapGenericGetter(event -> event.getList().stream()
                .map(entry -> SpawnHelper.getSpawnEntry(entry.entityClass,entry.itemWeight,entry.maxGroupCount,entry.minGroupCount))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<PotentialSpawns,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(PotentialSpawns::getWorld);
    }
}