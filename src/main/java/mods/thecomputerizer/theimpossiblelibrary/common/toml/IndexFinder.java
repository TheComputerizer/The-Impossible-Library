package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Inner helper class used when calculating the absolute index for a new element or an element that is getting moved.
 */
public class IndexFinder {

    /**
     * The parent table of the type. If this is null, the type is assumed to be top-level.
     */
    private final Table parent;

    /**
     * A separate type that is already indexed which the new or moving element will be relative to. The relative
     * type must exist in the same level as the element, or it will be ignored.
     */
    private final AbstractType relativeType;

    /**
     * The new index the element will be assigned to relative to either an existing type or the parent table.
     * Use negative numbers for lower relative indices and positive numbers for higher relative indices. If the
     * relative type does not exist, negative numbers will decrease from the bottom of the parent table, whereas
     * positive numbers will increase from the top of the table. The default of 0 will replace the index of the
     * relative type whose index will then be incremented or set the index to the bottom of the parent table.
     * Cannot exceed the boundaries of the parent table or indexed list.
     */
    private final int relativeIndex;

    /**
     * If the parent exists, the relative type is not set, and the relative index is at its default value of 0, this
     * will determine whether the bottom of the parent table is before or after any child table the parent may
     * have.
     */
    private final boolean beforeNested;

    public IndexFinder() {
        this(null, null, 0, true);
    }

    public IndexFinder(Table parent) {
        this(parent, null, 0, true);
    }

    public IndexFinder(AbstractType type) {
        this(null, type, 0, true);
    }

    public IndexFinder(Table parent, AbstractType type) {
        this(parent, type, 0, true);
    }

    public IndexFinder(Table parent, int relativeIndex) {
        this(parent, null, relativeIndex, true);
    }

    public IndexFinder(Table parent, boolean beforeNested) {
        this(parent, null, 0, beforeNested);
    }

    public IndexFinder(AbstractType type, int relativeIndex) {
        this(null, type, relativeIndex, true);
    }

    public IndexFinder(Table parent, AbstractType type, int relativeIndex) {
        this(parent, null, relativeIndex, true);
    }

    public IndexFinder(Table parent, AbstractType type, boolean beforeNested) {
        this(parent, null, 0, beforeNested);
    }

    public IndexFinder(Table parent, int relativeIndex, boolean beforeNested) {
        this(parent, null, relativeIndex, beforeNested);
    }

    public IndexFinder(AbstractType relativeType, int relativeIndex, boolean beforeNested) {
        this(null, relativeType, relativeIndex, beforeNested);
    }

    public IndexFinder(@Nullable Table parent, @Nullable AbstractType relativeType, int relativeIndex,
                       boolean beforeNested) {
        this.parent = parent;
        this.relativeType = relativeType;
        this.relativeIndex = relativeIndex;
        this.beforeNested = beforeNested;
    }

    public void addToPotentialParent(AbstractType type) {
        if (Objects.nonNull(this.parent)) this.parent.addItem(type);
    }

    public int findPotentialIndex(List<AbstractType> topLevelElements, int max) {
        if (Objects.nonNull(this.parent)) {
            if (Objects.nonNull(this.relativeType) && this.parent.has(this.relativeType))
                return this.parent.adjustForChildren(this.relativeType.getAbsoluteIndex() +
                        this.relativeIndex, this.relativeIndex > 0);
            int parentMax = this.parent.getMaxIndex(true, this.beforeNested);
            return this.relativeIndex <= 0 ? this.parent.adjustForChildren(parentMax + this.relativeIndex, false) :
                    this.parent.adjustForChildren(this.parent.getAbsoluteIndex() + this.relativeIndex, true);
        }
        int typeAdd = Objects.nonNull(this.relativeType) && topLevelElements.contains(this.relativeType) ?
                this.relativeType.getAbsoluteIndex() : 0;
        int index = typeAdd == 0 ? this.relativeIndex <= 0 ? max + this.relativeIndex : this.relativeIndex - 1 : typeAdd;
        return Math.max(0, Math.min(max, index));
    }

    private int adjustForTopTables(int query, int max, boolean above, List<AbstractType> topLevelElements) {
        if (query <= 0) return 0;
        if (query > max) return max;
        List<Integer> indices = topLevelElements.stream().map(AbstractType::getAbsoluteIndex).toList();
        return indices.contains(query) ? query : above ? getNextHighest(query, max, topLevelElements) :
                getNextLowest(query, topLevelElements);
    }

    /**
     * Internal method used for obtaining the next highest index above a failed query
     */
    private int getNextHighest(int query, int max, List<AbstractType> topLevelElements) {
        List<AbstractType> filteredContents = topLevelElements.stream().filter(type -> type.getAbsoluteIndex() >= query)
                .collect(Collectors.toList());
        return filteredContents.isEmpty() ? max : getMinIndexInternal(filteredContents);
    }

    /**
     * Internal method used for obtaining the next highest index above a failed query
     */
    private int getNextLowest(int query, List<AbstractType> topLevelElements) {
        List<AbstractType> filteredContents = topLevelElements.stream().filter(type -> type.getAbsoluteIndex() <= query)
                .collect(Collectors.toList());
        return filteredContents.isEmpty() ? 0 : getMaxIndexInternal(filteredContents);
    }

    /**
     * Internal call so the big one-liners aren't as hard to read
     */
    private int getMaxIndexInternal(List<AbstractType> contents) {
        return Collections.max(contents, Comparator.comparing(AbstractType::getAbsoluteIndex)).getAbsoluteIndex();
    }

    /**
     * Internal call so the big one-liners aren't as hard to read
     */
    private int getMinIndexInternal(List<AbstractType> contents) {
        return Collections.min(contents, Comparator.comparing(AbstractType::getAbsoluteIndex)).getAbsoluteIndex();
    }
}
