package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.world.item.Tier;

public class ToolTier1_20 extends ToolTierAPI<Tier> {
    
    public ToolTier1_20(Object tier) {
        super(tier);
    }
    
    @Override public float getDamage() {
        return getIfNotNullOrDefault(Tier::getAttackDamageBonus,0f);
    }
    
    @Override public float getEfficiency() {
        return getIfNotNullOrDefault(Tier::getSpeed,0f);
    }
    
    @Override public int getEnchantability() {
        return getIfNotNullOrDefault(Tier::getEnchantmentValue,0);
    }
    
    @Override public int getLevel() {
        return getIfNotNullOrDefault(Tier::getLevel,0);
    }
    
    @Override public int getUses() {
        return getIfNotNullOrDefault(Tier::getUses,0);
    }
}
