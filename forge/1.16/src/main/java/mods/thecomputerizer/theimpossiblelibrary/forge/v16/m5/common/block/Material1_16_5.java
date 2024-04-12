package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.material.Material;

import static net.minecraft.block.material.Material.*;
import static net.minecraft.block.material.PushReaction.BLOCK;
import static net.minecraft.block.material.PushReaction.DESTROY;

public class Material1_16_5 extends MaterialAPI<Material> {

    public Material1_16_5(Material material) {
        super(material);
    }

    @Override
    public boolean hasCollider() {
        return this.material.blocksMotion();
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
        return this.material.isFlammable();
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
        return this.material==WATER || this.material==WATER_PLANT || this.material==REPLACEABLE_WATER_PLANT;
    }
}