package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class ToolTierAPI<T> extends AbstractWrapped<T> {
    
    protected ToolTierAPI(T tier) {
        super(tier);
    }
    
    public abstract float getDamage();
    @IndirectCallers public abstract float getEfficiency();
    @IndirectCallers public abstract int getEnchantability();
    public abstract int getLevel();
    @IndirectCallers public abstract int getUses();
}