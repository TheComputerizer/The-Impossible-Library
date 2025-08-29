package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.world.item.Tier;

public class ToolTier1_19 extends ToolTierAPI<Tier> {
    
    public ToolTier1_19(Object tier) {
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
