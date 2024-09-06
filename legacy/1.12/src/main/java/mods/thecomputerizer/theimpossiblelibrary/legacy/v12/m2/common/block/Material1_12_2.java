package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.material.Material;

import static net.minecraft.block.material.EnumPushReaction.BLOCK;
import static net.minecraft.block.material.EnumPushReaction.DESTROY;
import static net.minecraft.block.material.Material.AIR;
import static net.minecraft.block.material.Material.WATER;

public class Material1_12_2 extends MaterialAPI<Material> {

    public Material1_12_2(Material material) {
        super(material);
    }

    @Override public boolean hasCollider() {
        return this.wrapped.blocksMovement();
    }

    @Override public boolean isAir() {
        return this.wrapped==AIR;
    }

    @Override public boolean isDestroyedByPiston() {
        return this.wrapped.getPushReaction()==DESTROY;
    }

    @Override public boolean isFlammable() {
        return this.wrapped.getCanBurn();
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
        return this.wrapped==WATER;
    }
}