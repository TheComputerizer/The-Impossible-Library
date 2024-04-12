package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerRespawnEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

public class PlayerRespawnEvent1_16_5 extends PlayerRespawnEventWrapper<PlayerRespawnEvent> {

    @Override
    protected EventFieldWrapper<PlayerRespawnEvent,Boolean> wrapEndConqueredField() {
        return wrapGenericGetter(PlayerRespawnEvent::isEndConquered,false);
    }

    @Override
    protected EventFieldWrapper<PlayerRespawnEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerRespawnEvent::getPlayer);
    }
}