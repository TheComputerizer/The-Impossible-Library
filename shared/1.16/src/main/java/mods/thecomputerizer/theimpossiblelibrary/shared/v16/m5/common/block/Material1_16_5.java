package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.material.Material;

import static net.minecraft.block.material.Material.AIR;
import static net.minecraft.block.material.Material.REPLACEABLE_WATER_PLANT;
import static net.minecraft.block.material.Material.WATER;
import static net.minecraft.block.material.Material.WATER_PLANT;
import static net.minecraft.block.material.PushReaction.BLOCK;
import static net.minecraft.block.material.PushReaction.DESTROY;

public class Material1_16_5 extends MaterialAPI<Material> {

    public Material1_16_5(Material material) {
        super(material);
    }

    @Override public boolean hasCollider() {
        return this.wrapped.blocksMotion();
    }

    @Override public boolean isAir() {
        return this.wrapped==AIR;
    }

    @Override public boolean isDestroyedByPiston() {
        return this.wrapped.getPushReaction()==DESTROY;
    }

    @Override public boolean isFlammable() {
        return this.wrapped.isFlammable();
    }

    @Override public boolean isLiquid() {
        return this.wrapped.isLiquid();
    }

    @Override public boolean isPushable() {
        return this.wrapped.getPushReaction()!=BLOCK;
    }

    @Override public boolean isReplaceable() {
        return this.wrapped.isReplaceable();
    }

    @Override public boolean isSolid() {
        return this.wrapped.isSolid();
    }

    @Override public boolean isUnderwater() {
        return this.wrapped==WATER || this.wrapped==WATER_PLANT || this.wrapped==REPLACEABLE_WATER_PLANT;
    }
}