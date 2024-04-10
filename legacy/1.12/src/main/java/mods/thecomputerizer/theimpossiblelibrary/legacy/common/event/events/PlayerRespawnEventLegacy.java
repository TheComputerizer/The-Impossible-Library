package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerRespawnEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_RESPAWN;

public class PlayerRespawnEventLegacy extends PlayerRespawnEventWrapper<PlayerRespawnEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerRespawnEvent event) {
        PLAYER_RESPAWN.invoke(event);
    }

    @Override
    protected EventFieldWrapper<PlayerRespawnEvent,Boolean> wrapEndConqueredField() {
        return wrapGenericGetter(PlayerRespawnEvent::isEndConquered,false);
    }

    @Override
    protected EventFieldWrapper<PlayerRespawnEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }
}