package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks.CallStrategy.DIRECT;
import static net.minecraft.world.level.block.Blocks.FIRE;
import static net.minecraft.world.level.material.Fluids.FLOWING_WATER;
import static net.minecraft.world.level.material.Fluids.WATER;
import static net.minecraft.world.level.material.PushReaction.BLOCK;
import static net.minecraft.world.level.material.PushReaction.DESTROY;

/**
 * As of 1.20, the Material class no longer exists...
 */
public class Material1_21 extends MaterialAPI<BlockState> {
    
    private static final String CAN_BURN = NAMED_ENV ? "canBurn" : (SRG_ENV ? "m_7599_" : "method_10195");

    public Material1_21(Object state) {
        super(state);
    }
    
    @SuppressWarnings("deprecation")
    @Override public boolean hasCollider() {
        return getIfNotNullOrDefault(BlockState::blocksMotion,false);
    }
    
    @Override public boolean isAir() {
        return getIfNotNullOrDefault(BlockState::isAir,false);
    }
    
    @Override public boolean isDestroyedByPiston() {
        return getIfNotNullOrDefault(w -> w.getPistonPushReaction()==DESTROY,false);
    }
    
    @Override public boolean isFlammable(WorldAPI<?> world, BlockPosAPI<?> pos, Facing side) {
        return DIRECT.invoke(FIRE,CAN_BURN,this.wrapped);
    }
    
    @SuppressWarnings("deprecation")
    @Override public boolean isLiquid() {
        return getIfNotNullOrDefault(BlockState::liquid,false);
    }
    
    @Override public boolean isPushable() {
        return getIfNotNullOrDefault(w -> w.getPistonPushReaction()!=BLOCK,false);
    }
    
    @Override public boolean isReplaceable() {
        return getIfNotNullOrDefault(BlockState::canBeReplaced,false);
    }
    
    @SuppressWarnings("deprecation")
    @Override public boolean isSolid() {
        return getIfNotNullOrDefault(BlockState::isSolid,false);
    }
    
    @Override public boolean isUnderwater() {
        if(Objects.isNull(this.wrapped)) return false;
        Fluid fluid = this.wrapped.getFluidState().getType();
        return fluid==WATER || fluid==FLOWING_WATER;
    }
}