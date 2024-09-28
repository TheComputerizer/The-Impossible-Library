package mods.thecomputerizer.theimpossiblelibrary.api.spawn;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper.WeightedEntry;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

@SuppressWarnings("unused")
public abstract class SpawnEntryAPI<E> implements WeightedEntry {

     @Setter @Getter protected Class<? extends E> entityClass;
     @Setter @Getter protected int maxGroupCount;
     @Setter @Getter protected int minGroupCount;
    protected int weight;

    protected SpawnEntryAPI(Class<? extends E> clazz, int weight, int maxGroup, int minGroup) {
        this.entityClass = clazz;
        this.maxGroupCount = maxGroup;
        this.minGroupCount = minGroup;
    }
    
    @Override public int getWeight() {
        return this.weight;
    }

    public abstract E newInstance(WorldAPI<?> worldAPI) throws Exception;
    
    @Override public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override public String toString() {
        return this.entityClass.getSimpleName()+"*("+this.minGroupCount+"-"+this.maxGroupCount+"):"+this.weight;
    }
}