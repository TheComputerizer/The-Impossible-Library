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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE;

public class BlockPlaceEventLegacy extends BlockPlaceEventWrapper<EntityPlaceEvent,Block,IBlockState,Entity,World> {

    @SubscribeEvent
    public static void onEvent(EntityPlaceEvent event) {
        BLOCK_PLACE.invoke(event);
    }

    public BlockPlaceEventLegacy() {}

    public void setEvent(EntityPlaceEvent event) {
        if(Objects.nonNull(event.getEntity())) this.entity = new EntityLegacy(event.getEntity());
        setPlaced(new BlockStateLegacy(event.getPlacedBlock()));
        this.placedAgainst = new BlockStateLegacy(event.getPlacedAgainst());
        this.world = new WorldLegacy(event.getWorld());
    }
}
