package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedInEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

public class PlayerLoggedInEvent1_16_5 extends PlayerLoggedInEventWrapper<PlayerLoggedInEvent> {

    @Override
    protected EventFieldWrapper<PlayerLoggedInEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerLoggedInEvent::getPlayer);
    }
}