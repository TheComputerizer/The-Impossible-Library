package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import lombok.Getter;

@SuppressWarnings("unused") @Getter
public abstract class ToolTierAPI<T> {
    
    protected final T tier;
    
    protected ToolTierAPI(T tier) {
        this.tier = tier;
    }
    
    public abstract float getDamage();
    public abstract float getEfficiency();
    public abstract int getEnchantability();
    public abstract int getLevel();
    public abstract int getUses();
}