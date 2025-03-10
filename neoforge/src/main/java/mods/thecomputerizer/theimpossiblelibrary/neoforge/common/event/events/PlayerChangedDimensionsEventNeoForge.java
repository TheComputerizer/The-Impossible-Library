package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangedDimensionsEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHANGED_DIMENSIONS;

public class PlayerChangedDimensionsEventNeoForge extends PlayerChangedDimensionsEventWrapper<PlayerChangedDimensionEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerChangedDimensionEvent event) {
        PLAYER_CHANGED_DIMENSIONS.invoke(event);
    }
    
    @Override public void setEvent(PlayerChangedDimensionEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<PlayerChangedDimensionEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerChangedDimensionEvent::getEntity);
    }
}