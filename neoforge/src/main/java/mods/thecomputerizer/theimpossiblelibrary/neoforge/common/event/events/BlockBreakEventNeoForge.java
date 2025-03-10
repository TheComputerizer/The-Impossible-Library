package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

public class BlockBreakEventNeoForge extends BlockBreakEventWrapper<BreakEvent> {
    
    @SubscribeEvent
    public static void onEvent(BreakEvent event) {
        BLOCK_BREAK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(BreakEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<BreakEvent,Integer> wrapXPField() {
        return wrapGenericBoth(BreakEvent::getExpToDrop,BreakEvent::setExpToDrop,0);
    }

    @Override protected EventFieldWrapper<BreakEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(BreakEvent::getPlayer);
    }

    @Override protected EventFieldWrapper<BreakEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BreakEvent::getPos);
    }

    @Override protected EventFieldWrapper<BreakEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(BreakEvent::getState);
    }

    @Override protected EventFieldWrapper<BreakEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(BreakEvent::getLevel);
    }
}
