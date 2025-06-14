package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.world.item.Tier;

public class ToolTier1_20 extends ToolTierAPI<Tier> {
    
    public ToolTier1_20(Tier tier) {
        super(tier);
    }
    
    @Override public float getDamage() {
        return this.wrapped.getAttackDamageBonus();
    }
    
    @Override public float getEfficiency() {
        return this.wrapped.getSpeed();
    }
    
    @Override public int getEnchantability() {
        return this.wrapped.getEnchantmentValue();
    }
    
    @Override public int getLevel() {
        return this.wrapped.getLevel();
    }
    
    @Override public int getUses() {
        return this.wrapped.getUses();
    }
}
