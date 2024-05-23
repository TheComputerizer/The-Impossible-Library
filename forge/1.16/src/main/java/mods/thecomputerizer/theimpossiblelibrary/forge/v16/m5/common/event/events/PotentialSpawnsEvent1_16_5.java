package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PotentialSpawnsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PotentialSpawnsEvent1_16_5 extends PotentialSpawnsEventWrapper<PotentialSpawns> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PotentialSpawns event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<PotentialSpawns,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PotentialSpawns::getPos);
    }

    @Override
    protected EventFieldWrapper<PotentialSpawns,List<SpawnEntryAPI<?>>> wrapSpawnsField() {
        return wrapGenericGetter(event -> event.getList().stream()
                .map(entry -> SpawnHelper.getSpawnEntry(entry.type.getClass(),entry.weight,entry.maxCount,entry.minCount))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<PotentialSpawns,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(PotentialSpawns::getWorld);
    }
}