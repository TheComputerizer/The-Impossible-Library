package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.item.ItemTier;

public class ToolTier1_16_5 extends ToolTierAPI<ItemTier> {
    
    public ToolTier1_16_5(ItemTier tier) {
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
