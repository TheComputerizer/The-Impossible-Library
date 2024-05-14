package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.item.Item.ToolMaterial;

public class ToolTier1_12_2 extends ToolTierAPI<ToolMaterial> {
    
    public ToolTier1_12_2(ToolMaterial tier) {
        super(tier);
    }
    
    @Override public float getDamage() {
        return this.tier.getAttackDamage();
    }
    
    @Override public float getEfficiency() {
        return this.tier.getEfficiency();
    }
    
    @Override public int getEnchantability() {
        return this.tier.getEnchantability();
    }
    
    @Override public int getLevel() {
        return this.tier.getHarvestLevel();
    }
    
    @Override public int getUses() {
        return this.tier.getMaxUses();
    }
}
