package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangedDimensionsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;

public class PlayerChangedDimensionsEventForge extends PlayerChangedDimensionsEventWrapper<PlayerChangedDimensionEvent> {

    @Override
    protected EventFieldWrapper<PlayerChangedDimensionEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerChangedDimensionEvent::getPlayer);
    }
}