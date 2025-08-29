package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.block.material.Material;

import static net.minecraft.block.material.EnumPushReaction.BLOCK;
import static net.minecraft.block.material.EnumPushReaction.DESTROY;
import static net.minecraft.block.material.Material.AIR;
import static net.minecraft.block.material.Material.WATER;

public class Material1_12_2 extends MaterialAPI<Material> {

    public Material1_12_2(Object material) {
        super(material);
    }

    @Override public boolean hasCollider() {
        return getIfNotNullOrDefault(Material::blocksMovement,false);
    }

    @Override public boolean isAir() {
        return this.wrapped==AIR;
    }

    @Override public boolean isDestroyedByPiston() {
        return getIfNotNullOrDefault(w -> w.getPushReaction()==DESTROY,false);
    }

    @Override public boolean isFlammable(WorldAPI<?> world, BlockPosAPI<?> pos, Facing side) {
        return getIfNotNullOrDefault(Material::getCanBurn,false);
    }

    @Override public boolean isLiquid() {
        return getIfNotNullOrDefault(Material::isLiquid,false);
    }

    @Override public boolean isPushable() {
        return getIfNotNullOrDefault(w -> w.getPushReaction()!=BLOCK,false);
    }

    @Override public boolean isReplaceable() {
        return getIfNotNullOrDefault(Material::isReplaceable,false);
    }

    @Override public boolean isSolid() {
        return getIfNotNullOrDefault(Material::isSolid,false);
    }

    @Override public boolean isUnderwater() {
        return this.wrapped==WATER;
    }
}