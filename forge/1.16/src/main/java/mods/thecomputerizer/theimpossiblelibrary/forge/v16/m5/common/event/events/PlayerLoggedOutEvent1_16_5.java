package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedOutEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

public class PlayerLoggedOutEvent1_16_5 extends PlayerLoggedOutEventWrapper<PlayerLoggedOutEvent> {

    @Override
    protected EventFieldWrapper<PlayerLoggedOutEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerLoggedOutEvent::getPlayer);
    }
}