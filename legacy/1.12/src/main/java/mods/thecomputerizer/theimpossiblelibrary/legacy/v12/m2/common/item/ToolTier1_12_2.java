package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import net.minecraft.item.Item.ToolMaterial;

public class ToolTier1_12_2 extends ToolTierAPI<ToolMaterial> {
    
    public ToolTier1_12_2(Object tier) {
        super(tier);
    }
    
    @Override public float getDamage() {
        return getIfNotNullOrDefault(ToolMaterial::getAttackDamage,0f);
    }
    
    @Override public float getEfficiency() {
        return getIfNotNullOrDefault(ToolMaterial::getEfficiency,0f);
    }
    
    @Override public int getEnchantability() {
        return getIfNotNullOrDefault(ToolMaterial::getEnchantability,0);
    }
    
    @Override public int getLevel() {
        return getIfNotNullOrDefault(ToolMaterial::getHarvestLevel,0);
    }
    
    @Override public int getUses() {
        return getIfNotNullOrDefault(ToolMaterial::getMaxUses,0);
    }
}