package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import static net.minecraft.world.level.block.Blocks.FIRE;
import static net.minecraft.world.level.material.Fluids.FLOWING_WATER;
import static net.minecraft.world.level.material.Fluids.WATER;
import static net.minecraft.world.level.material.PushReaction.BLOCK;
import static net.minecraft.world.level.material.PushReaction.DESTROY;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * As of 1.20, the Material class no longer exists...
 */
public class Material1_21 extends MaterialAPI<BlockState> {

    public Material1_21(Object state) {
        super((BlockState)state);
    }
    
    @SuppressWarnings("deprecation")
    @Override public boolean hasCollider() {
        return this.wrapped.blocksMotion();
    }

    @Override public boolean isAir() {
        return this.wrapped.isAir();
    }

    @Override public boolean isDestroyedByPiston() {
        return this.wrapped.getPistonPushReaction()==DESTROY;
    }

    @Override public boolean isFlammable(WorldAPI<?> world, BlockPosAPI<?> pos, Facing side) {
        return Methods.invokeDirect(FIRE,"canBurn",this.wrapped);
    }
    
    @SuppressWarnings("deprecation")
    @Override public boolean isLiquid() {
        return this.wrapped.liquid();
    }

    @Override public boolean isPushable() {
        return this.wrapped.getPistonPushReaction()!=BLOCK;
    }

    @Override public boolean isReplaceable() {
        return this.wrapped.canBeReplaced();
    }

    @SuppressWarnings("deprecation")
    @Override public boolean isSolid() {
        return this.wrapped.isSolid();
    }

    @Override public boolean isUnderwater() {
        Fluid fluid = this.wrapped.getFluidState().getType();
        return fluid==WATER || fluid==FLOWING_WATER;
    }
}