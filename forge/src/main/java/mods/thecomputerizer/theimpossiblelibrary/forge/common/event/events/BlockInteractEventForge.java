package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockInteractEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_INTERACT;

public class BlockInteractEventForge extends BlockInteractEventWrapper<BlockToolInteractEvent> { //TODO Finish implementing this
    
    @SubscribeEvent
    public static void onEvent(BlockToolInteractEvent event) {
        BLOCK_INTERACT.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(BlockToolInteractEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<BlockToolInteractEvent,PlayerAPI<?,?>> wrapPlayerField() {
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