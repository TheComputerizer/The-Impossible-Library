package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PotentialSpawnsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent.PotentialSpawns;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_POTENTIAL_SPAWNS;

public class PotentialSpawnsEventNeoForge extends PotentialSpawnsEventWrapper<PotentialSpawns> {
    
    @SubscribeEvent
    public static void onEvent(PotentialSpawns event) {
        WORLD_POTENTIAL_SPAWNS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PotentialSpawns event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
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
