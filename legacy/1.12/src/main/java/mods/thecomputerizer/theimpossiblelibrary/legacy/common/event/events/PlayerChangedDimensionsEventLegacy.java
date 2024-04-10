package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangedDimensionsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHANGED_DIMENSIONS;

public class PlayerChangedDimensionsEventLegacy extends PlayerChangedDimensionsEventWrapper<PlayerChangedDimensionEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerChangedDimensionEvent event) {
        PLAYER_CHANGED_DIMENSIONS.invoke(event);
    }

    @Override
    protected EventFieldWrapper<PlayerChangedDimensionEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }
}