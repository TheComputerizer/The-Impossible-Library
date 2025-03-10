package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockInteractEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_INTERACT;

public class BlockInteractEventForge1_19 extends BlockInteractEventWrapper<BlockToolModificationEvent> { //TODO Finish implementing this
    
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