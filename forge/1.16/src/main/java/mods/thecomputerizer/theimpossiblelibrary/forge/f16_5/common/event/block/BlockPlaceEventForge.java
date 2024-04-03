package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.world.WorldForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.block.BlockStateForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.EntityForge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;

import java.util.Objects;

public class BlockPlaceEventForge extends BlockPlaceEventWrapper<Block,BlockState,Entity,IWorld> {

    private final EntityPlaceEvent event;

    public BlockPlaceEventForge(EntityPlaceEvent event) {
        super(Objects.nonNull(event.getEntity()) ? new EntityForge(event.getEntity()) : null,
                new BlockStateForge(event.getPlacedBlock()),new BlockStateForge(event.getPlacedAgainst()),
                new WorldForge(event.getWorld()));
        this.event = event;
    }
}
