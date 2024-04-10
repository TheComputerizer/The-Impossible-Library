package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedOutEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

public class PlayerLoggedOutEventForge extends PlayerLoggedOutEventWrapper<PlayerLoggedOutEvent> {

    @Override
    protected EventFieldWrapper<PlayerLoggedOutEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerLoggedOutEvent::getPlayer);
    }
}