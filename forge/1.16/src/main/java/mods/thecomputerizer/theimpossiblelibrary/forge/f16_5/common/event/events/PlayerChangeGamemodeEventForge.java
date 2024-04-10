package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangeGamemodeEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangeGameModeEvent;

public class PlayerChangeGamemodeEventForge extends PlayerChangeGamemodeEventWrapper<PlayerChangeGameModeEvent> {

    @Override
    protected EventFieldWrapper<PlayerChangeGameModeEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerChangeGameModeEvent::getPlayer);
    }
}