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

public class BlockBreakEventLegacy extends BlockBreakEventWrapper<Block,IBlockState,EntityPlayer,World> {

    private final BreakEvent event;

    public BlockBreakEventLegacy(BreakEvent event) {
        super(new PlayerLegacy(event.getPlayer()),new BlockStateLegacy(event.getState()),
                new WorldLegacy(event.getWorld()),event.getExpToDrop());
        this.event = event;
    }

    @Override
    public void setXP(int xp) {
        this.xp = xp;
        this.event.setExpToDrop(xp);
    }
}
