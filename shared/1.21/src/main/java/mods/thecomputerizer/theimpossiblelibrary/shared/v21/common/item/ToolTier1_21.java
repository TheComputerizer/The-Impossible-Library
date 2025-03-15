package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.world.item.Tier;

public class ToolTier1_21 extends ToolTierAPI<Tier> {
    
    public ToolTier1_21(Tier tier) {
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
        return 0; //TODO Is there some way to still get and/or make use of this?
    }
    
    @Override public int getUses() {
        return this.wrapped.getUses();
    }
}
