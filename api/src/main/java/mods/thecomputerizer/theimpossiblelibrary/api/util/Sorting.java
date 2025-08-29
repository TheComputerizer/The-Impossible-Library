package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 Potentially useful for passing into methods
 */
public enum Sorting {
    
    ALPHABETICAL(Comparator.naturalOrder()),
    REVERSE(Comparator.reverseOrder());
    
    @IndirectCallers
    public static <T,C extends Collection<T>> C applyAll(C collection, Sorting ... sorters) {
        for(Sorting sorter : sorters) collection = sorter.sorted(collection);
        return collection;
    }
    
    final Comparator<?> comparator;
    
    Sorting(Comparator<?> comparator) {
        this.comparator = comparator;
    }
    
    <T,C extends Collection<T>> C sorted(C collection) {
        Comparator<T> comparator = GenericUtils.cast(this.comparator);
        if(Objects.nonNull(comparator)) {
            
            if(collection instanceof List<?>) {
                List<T> list = GenericUtils.cast(collection);
                if(Objects.nonNull(list)) list.sort(comparator);
            }
            else if(collection instanceof Set<?>) {
                Set<T> set = GenericUtils.cast(collection);
                if(Objects.nonNull(set)) {
                    set = set.stream().sorted(comparator).collect(Collectors.toCollection(LinkedHashSet::new));
                    collection = GenericUtils.cast(set);
                }
            }
        }
        return collection;
    }
}