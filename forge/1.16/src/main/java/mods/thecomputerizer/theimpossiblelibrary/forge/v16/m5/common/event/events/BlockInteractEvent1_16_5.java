package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockInteractEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent;

public class BlockInteractEvent1_16_5 extends BlockInteractEventWrapper<BlockToolInteractEvent> { //TODO Finish implementing this


    @Override
    protected EventFieldWrapper<BlockToolInteractEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(BlockToolInteractEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<BlockToolInteractEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BlockToolInteractEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<BlockToolInteractEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(BlockToolInteractEvent::getState);
    }

    @Override
    protected EventFieldWrapper<BlockToolInteractEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(BlockToolInteractEvent::getWorld);
    }
    
}