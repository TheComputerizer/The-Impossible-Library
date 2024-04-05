package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockBreakEventWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import java.util.function.Function;

public class BlockBreakEventForge extends BlockBreakEventWrapper<BreakEvent> {

    @Override
    protected Function<BreakEvent,PlayerEntity> getPlayerFunc() {
        return BreakEvent::getPlayer;
    }

    @Override
    protected Function<BreakEvent,BlockState> getStateFunc() {
        return BreakEvent::getState;
    }

    @Override
    protected Function<BreakEvent,IWorld> getWorldFunc() {
        return BreakEvent::getWorld;
    }

    @Override
    public void setXP(int xp) {
        this.xp = xp;
        this.event.setExpToDrop(xp);
    }
}
