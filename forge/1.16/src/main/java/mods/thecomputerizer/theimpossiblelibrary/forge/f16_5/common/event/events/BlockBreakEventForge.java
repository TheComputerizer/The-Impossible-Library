package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class BlockBreakEventForge extends BlockBreakEventWrapper<BreakEvent> {

    @Override
    protected EventFieldWrapper<BreakEvent,Integer> wrapXPField() {
        return wrapGenericBoth(BreakEvent::getExpToDrop,BreakEvent::setExpToDrop,0);
    }

    @Override
    protected EventFieldWrapper<BreakEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(BreakEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<BreakEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(BreakEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<BreakEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(BreakEvent::getState);
    }

    @Override
    protected EventFieldWrapper<BreakEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(BreakEvent::getWorld);
    }
}
