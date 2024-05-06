package mods.thecomputerizer.theimpossiblelibrary.api.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 Potentially useful for passing into methods
 */
public enum Sorting {
    ALPHABETICAL(Comparator.naturalOrder()),
    REVERSE(Comparator.reverseOrder());
    
    public static <T,C extends Collection<T>> C applyAll(C collection, Sorting ... sorters) {
        for(Sorting sorter : sorters) collection = sorter.sorted(collection);
        return collection;
    }
    
    final Comparator<?> comparator;
    
    Sorting(Comparator<?> comparator) {
        this.comparator = comparator;
    }
    
    @SuppressWarnings("unchecked") <T,C extends Collection<T>> C sorted(C collection) {
        Comparator<T> comparator = (Comparator<T>)this.comparator;
        if(collection instanceof List<?>) ((List<T>)collection).sort(comparator);
        else if(collection instanceof Set<?>)
            collection = (C)collection.stream().sorted((Comparator<? super T>)this.comparator).collect(
                    Collectors.toCollection(LinkedHashSet::new));
        return collection;
    }
}