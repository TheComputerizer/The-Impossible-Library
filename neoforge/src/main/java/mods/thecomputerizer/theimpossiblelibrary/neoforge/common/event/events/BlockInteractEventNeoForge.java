package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockInteractEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent.BlockToolModificationEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_INTERACT;

public class BlockInteractEventNeoForge extends BlockInteractEventWrapper<BlockToolModificationEvent> {
    
    @SubscribeEvent
    public static void onEvent(BlockToolModificationEvent event) {
        BLOCK_INTERACT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(BlockToolModificationEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<BlockToolModificationEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(BlockToolModificationEvent::getPlayer);
    }
    
    @Override protected EventFieldWrapper<BlockToolModificationEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BlockToolModificationEvent::getPos);
    }
    
    @Override protected EventFieldWrapper<BlockToolModificationEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(BlockToolModificationEvent::getState);
    }
    
    @Override protected EventFieldWrapper<BlockToolModificationEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(BlockToolModificationEvent::getLevel);
    }
}