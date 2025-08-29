package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.level.material.Material;

import static net.minecraft.world.level.material.Material.AIR;
import static net.minecraft.world.level.material.Material.REPLACEABLE_WATER_PLANT;
import static net.minecraft.world.level.material.Material.WATER;
import static net.minecraft.world.level.material.Material.WATER_PLANT;
import static net.minecraft.world.level.material.PushReaction.BLOCK;
import static net.minecraft.world.level.material.PushReaction.DESTROY;

public class Material1_18_2 extends MaterialAPI<Material> {
    
    public Material1_18_2(Object material) {
        super(material);
    }
    
    @Override public boolean hasCollider() {
        return getIfNotNullOrDefault(Material::blocksMotion, false);
    }
    
    @Override public boolean isAir() {
        return this.wrapped==AIR;
    }
    
    @Override public boolean isDestroyedByPiston() {
        return getIfNotNullOrDefault(w -> w.getPushReaction()==DESTROY, false);
    }
    
    @Override public boolean isFlammable(WorldAPI<?> world, BlockPosAPI<?> pos, Facing side) {
        return getIfNotNullOrDefault(Material::isFlammable, false);
    }
    
    @Override public boolean isLiquid() {
        return getIfNotNullOrDefault(Material::isLiquid, false);
    }
    
    @Override public boolean isPushable() {
        return getIfNotNullOrDefault(w -> w.getPushReaction()!=BLOCK, false);
    }
    
    @Override public boolean isReplaceable() {
        return getIfNotNullOrDefault(Material::isReplaceable, false);
    }
    
    @Override public boolean isSolid() {
        return getIfNotNullOrDefault(Material::isSolid, false);
    }
    
    @Override public boolean isUnderwater() {
        return this.wrapped==WATER || this.wrapped==WATER_PLANT || this.wrapped==REPLACEABLE_WATER_PLANT;
    }
}