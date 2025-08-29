package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.world.item.Tier;

public class ToolTier1_21 extends ToolTierAPI<Tier> {
    
    public ToolTier1_21(Object tier) {
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
        return 0; //TODO Is there some way to still get and/or make use of this?
    }
    
    @Override public int getUses() {
        return getIfNotNullOrDefault(Tier::getUses,0);
    }
}
