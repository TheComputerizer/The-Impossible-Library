package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.item.ItemTier;

public class ToolTier1_16_5 extends ToolTierAPI<ItemTier> {
    
    public ToolTier1_16_5(ItemTier tier) {
        super(tier);
    }
    
    @Override public float getDamage() {
        return this.tier.getAttackDamageBonus();
    }
    
    @Override public float getEfficiency() {
        return this.tier.getSpeed();
    }
    
    @Override public int getEnchantability() {
        return this.tier.getEnchantmentValue();
    }
    
    @Override public int getLevel() {
        return this.tier.getLevel();
    }
    
    @Override public int getUses() {
        return this.tier.getUses();
    }
}
