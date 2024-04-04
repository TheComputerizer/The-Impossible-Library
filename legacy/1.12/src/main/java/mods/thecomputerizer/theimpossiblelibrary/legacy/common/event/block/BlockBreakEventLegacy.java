package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.world.WorldLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.block.BlockStateLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.PlayerLegacy;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

public class BlockBreakEventLegacy extends BlockBreakEventWrapper<BreakEvent,Block,IBlockState,EntityPlayer,World> {

    @SubscribeEvent
    public static void onEvent(BreakEvent event) {
        BLOCK_BREAK.invoke(event);
    }

    private BreakEvent event;

    public BlockBreakEventLegacy() {}

    public void setEvent(BreakEvent event) {
        this.event = event;
        this.player = new PlayerLegacy(event.getPlayer());
        setState(new BlockStateLegacy(event.getState()));
        this.world = new WorldLegacy(event.getWorld());
        this.xp = event.getExpToDrop();
    }

    @Override
    public void setXP(int xp) {
        this.xp = xp;
        this.event.setExpToDrop(xp);
    }
}
