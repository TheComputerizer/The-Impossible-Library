package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCloneEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CLONE;

public class PlayerCloneEventNeoForge extends PlayerCloneEventWrapper<Clone> {
    
    @SubscribeEvent
    public static void onEvent(Clone event) {
        PLAYER_CLONE.invoke(event);
    }
    
    @Override public void setEvent(Clone event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<Clone,Boolean> wrapDeathField() {
        return wrapGenericGetter(Clone::isWasDeath,true);
    }

    @Override protected EventFieldWrapper<Clone,PlayerAPI<?,?>> wrapOriginalField() {
        return wrapPlayerGetter(Clone::getOriginal);
    }

    @Override protected EventFieldWrapper<Clone,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(Clone::getEntity);
    }
}