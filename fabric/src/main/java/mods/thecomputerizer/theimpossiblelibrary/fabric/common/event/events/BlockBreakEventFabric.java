package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class BlockBreakEventFabric extends BlockBreakEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],Integer> wrapXPField() {
        return wrapGenericBoth(BreakEvent::getExpToDrop,BreakEvent::setExpToDrop,0);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(BreakEvent::getPlayer);
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BreakEvent::getPos);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(BreakEvent::getState);
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(BreakEvent::getWorld);
    }
}
