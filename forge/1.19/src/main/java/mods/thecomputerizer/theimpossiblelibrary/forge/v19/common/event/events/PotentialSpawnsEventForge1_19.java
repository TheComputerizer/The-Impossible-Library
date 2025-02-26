package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.PotentialSpawnsEventForge;
import net.minecraftforge.event.level.LevelEvent.PotentialSpawns;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_POTENTIAL_SPAWNS;

public class PotentialSpawnsEventForge1_19 extends PotentialSpawnsEventForge<PotentialSpawns> {
    
    @SubscribeEvent
    public static void onEvent(PotentialSpawns event) {
        WORLD_POTENTIAL_SPAWNS.invoke(event);
    }
    
    @Override protected EventFieldWrapper<PotentialSpawns,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PotentialSpawns::getPos);
    }
    
    @Override protected EventFieldWrapper<PotentialSpawns,List<SpawnEntryAPI<?>>> wrapSpawnsField() {
        return wrapGenericGetter(event -> event.getSpawnerDataList().stream()
                .map(entry -> SpawnHelper.getSpawnEntry(entry.type.getClass(),entry.getWeight().asInt(),entry.maxCount,entry.minCount))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), new ArrayList<>());
    }
    
    @Override protected EventFieldWrapper<PotentialSpawns,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(PotentialSpawns::getLevel);
    }
}
