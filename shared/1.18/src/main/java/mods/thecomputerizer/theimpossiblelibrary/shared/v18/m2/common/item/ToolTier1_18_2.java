package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.world.item.Tier;

public class ToolTier1_18_2 extends ToolTierAPI<Tier> {
    
    public ToolTier1_18_2(Tier tier) {
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
    
    @SuppressWarnings("deprecation")
    @Override public int getLevel() {
        return this.wrapped.getLevel();
    }
    
    @Override public int getUses() {
        return this.wrapped.getUses();
    }
}
