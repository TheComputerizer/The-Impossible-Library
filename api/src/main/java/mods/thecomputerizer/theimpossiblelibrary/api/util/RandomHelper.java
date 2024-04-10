package mods.thecomputerizer.theimpossiblelibrary.api.util;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Random;

public class RandomHelper {

    public static <E extends WeightedEntry> @Nullable E getEntryAt(Iterable<E> entries, int weight) {
        for(E entry : entries) {
            weight-=entry.getWeight();
            if(weight<0) return entry;
        }
        return null;
    }

    public static <E extends WeightedEntry> int getTotalWeight(Iterable<E> entries) {
        int weight = 0;
        for(E entry : entries) weight+=entry.getWeight();
        return weight;
    }

    public static <E extends WeightedEntry> @Nullable E getWeightedEntry(Random rand, Iterable<E> entries) {
        return getEntryAt(entries,rand.nextInt(getTotalWeight(entries)));
    }

    @Getter
    public static class WeightedEntry {

        protected final int weight;

        public WeightedEntry(int weight) {
            this.weight = weight;
        }
    }
}