package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockBreakEventWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

public class BlockBreakEventLegacy extends BlockBreakEventWrapper<BreakEvent> {

    @SubscribeEvent
    public static void onEvent(BreakEvent event) {
        BLOCK_BREAK.invoke(event);
    }

    @Override
    protected Function<BreakEvent,EntityPlayer> getPlayerFunc() {
        return BreakEvent::getPlayer;
    }

    @Override
    protected Function<BreakEvent,IBlockState> getStateFunc() {
        return BreakEvent::getState;
    }

    @Override
    protected Function<BreakEvent,World> getWorldFunc() {
        return BreakEvent::getWorld;
    }

    @Override
    public void setXP(int xp) {
        this.xp = xp;
        this.event.setExpToDrop(xp);
    }
}
