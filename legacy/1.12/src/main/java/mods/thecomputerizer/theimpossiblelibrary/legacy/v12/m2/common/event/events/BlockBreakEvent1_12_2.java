package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

public class BlockBreakEvent1_12_2 extends BlockBreakEventWrapper<BreakEvent> {

    @SubscribeEvent
    public static void onEvent(BreakEvent event) {
        BLOCK_BREAK.invoke(event);
    }

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
