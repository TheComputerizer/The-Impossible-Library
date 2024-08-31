package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PotentialSpawnsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PotentialSpawnsEventFabric extends PotentialSpawnsEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PotentialSpawns::getPos);
    }

    @Override protected EventFieldWrapper<Object[],List<SpawnEntryAPI<?>>> wrapSpawnsField() {
        return wrapGenericGetter(event -> event.getList().stream()
                .map(entry -> SpawnHelper.getSpawnEntry(entry.type.getClass(),entry.weight,entry.maxCount,entry.minCount))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(PotentialSpawns::getWorld);
    }
}