package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedInEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

public class PlayerLoggedInEventForge extends PlayerLoggedInEventWrapper<PlayerLoggedInEvent> {

    @Override
    protected EventFieldWrapper<PlayerLoggedInEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerLoggedInEvent::getPlayer);
    }
}