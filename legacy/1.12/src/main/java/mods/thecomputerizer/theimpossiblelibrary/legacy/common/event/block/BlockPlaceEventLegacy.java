package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.world.WorldLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.block.BlockStateLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.EntityLegacy;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;

public class BlockPlaceEventLegacy extends BlockPlaceEventWrapper<Block,IBlockState,Entity,World> {

    private final EntityPlaceEvent event;

    public BlockPlaceEventLegacy(EntityPlaceEvent event) {
        super(new EntityLegacy(event.getEntity()),new BlockStateLegacy(event.getPlacedBlock()),
                new BlockStateLegacy(event.getPlacedAgainst()),new WorldLegacy(event.getWorld()));
        this.event = event;
    }
}
