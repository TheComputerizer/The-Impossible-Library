package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class BlockBreakEventForge<E extends Event> extends BlockBreakEventWrapper<E>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<E,Integer> wrapXPField() {
        return wrapGenericBoth(getter("getExpToDrop"),setter("setExpToDrop"),0);
    }

    @Override protected EventFieldWrapper<E,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(getter("getPlayer"));
    }

    @Override protected EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(getter("getPos"));
    }

    @Override protected EventFieldWrapper<E,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(getter("getState"));
    }

    @Override protected EventFieldWrapper<E,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(worldGetter());
    }
}