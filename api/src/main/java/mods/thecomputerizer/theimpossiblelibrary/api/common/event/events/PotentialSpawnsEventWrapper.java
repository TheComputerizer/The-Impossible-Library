package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonWorldEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_POTENTIAL_SPAWNS;

public abstract class PotentialSpawnsEventWrapper<E> extends CommonWorldEventType<E> { //TODO Enum?

    protected EventFieldWrapper<E,BlockPosAPI<?>> pos;
    protected EventFieldWrapper<E,List<SpawnEntryAPI<?>>> spawns;

    protected PotentialSpawnsEventWrapper() {
        super(WORLD_POTENTIAL_SPAWNS);
    }

    public BlockPosAPI<?> getPos() {
        return this.pos.get(this.event);
    }

    public List<SpawnEntryAPI<?>> getSpawns() {
        return this.spawns.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.pos = wrapPosField();
        this.spawns = wrapSpawnsField();
    }

    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField();
    protected abstract EventFieldWrapper<E,List<SpawnEntryAPI<?>>> wrapSpawnsField();
}