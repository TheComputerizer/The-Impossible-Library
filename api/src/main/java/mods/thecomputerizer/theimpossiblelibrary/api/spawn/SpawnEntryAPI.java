package mods.thecomputerizer.theimpossiblelibrary.api.spawn;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper.WeightedEntry;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

@Setter @Getter
public abstract class SpawnEntryAPI<E> extends WeightedEntry {

    protected Class<? extends E> entityClass;
    protected int maxGroupCount;
    protected int minGroupCount;

    protected SpawnEntryAPI(Class<? extends E> clazz, int weight, int maxGroup, int minGroup) {
        super(weight);
        this.entityClass = clazz;
        this.maxGroupCount = maxGroup;
        this.minGroupCount = minGroup;
    }

    public abstract E newInstance(WorldAPI<?> worldAPI) throws Exception;

    @Override
    public String toString() {
        return this.entityClass.getSimpleName()+"*("+this.minGroupCount+"-"+this.maxGroupCount+"):"+this.weight;
    }
}