package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.world.WorldForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.block.BlockStateForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.PlayerForge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class BlockBreakEventForge extends BlockBreakEventWrapper<Block,BlockState,PlayerEntity,IWorld> {

    private final BreakEvent event;

    public BlockBreakEventForge(BreakEvent event) {
        super(new PlayerForge(event.getPlayer()),new BlockStateForge(event.getState()),
                new WorldForge(event.getWorld()),event.getExpToDrop());
        this.event = event;
    }

    @Override
    public void setXP(int xp) {
        this.xp = xp;
        this.event.setExpToDrop(xp);
    }
}
