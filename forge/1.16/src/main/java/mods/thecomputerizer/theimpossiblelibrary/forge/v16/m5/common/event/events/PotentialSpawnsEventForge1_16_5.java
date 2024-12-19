package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PotentialSpawnsEventForge;
import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PotentialSpawnsEventForge1_16_5 extends PotentialSpawnsEventForge {
    
    @Override protected EventFieldWrapper<PotentialSpawns,List<SpawnEntryAPI<?>>> wrapSpawnsField() {
        return wrapGenericGetter(event -> event.getList().stream()
                .map(entry -> SpawnHelper.getSpawnEntry(entry.type.getClass(),entry.weight,entry.maxCount,entry.minCount))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), new ArrayList<>());
    }
}
