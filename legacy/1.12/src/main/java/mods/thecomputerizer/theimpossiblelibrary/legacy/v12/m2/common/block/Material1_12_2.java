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

    @Override
    public boolean hasCollider() {
        return this.material.blocksMovement();
    }

    @Override
    public boolean isAir() {
        return this.material==AIR;
    }

    @Override
    public boolean isDestroyedByPiston() {
        return this.material.getPushReaction()==DESTROY;
    }

    @Override
    public boolean isFlammable() {
        return this.material.getCanBurn();
    }

    @Override
    public boolean isLiquid() {
        return this.material.isLiquid();
    }

    @Override
    public boolean isPushable() {
        return this.material.getPushReaction()!=BLOCK;
    }

    @Override
    public boolean isReplaceable() {
        return this.material.isReplaceable();
    }

    @Override
    public boolean isSolid() {
        return this.material.isSolid();
    }

    @Override
    public boolean isUnderwater() {
        return this.material==WATER;
    }
}