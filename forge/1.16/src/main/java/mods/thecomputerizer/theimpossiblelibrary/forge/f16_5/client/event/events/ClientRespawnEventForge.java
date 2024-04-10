package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientRespawnEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.RespawnEvent;

public class ClientRespawnEventForge extends ClientRespawnEventWrapper<RespawnEvent> {

    @Override
    protected EventFieldWrapper<RespawnEvent,PlayerAPI<?>> wrapOldPlayerField() {
        return wrapPlayerGetter(RespawnEvent::getOldPlayer);
    }

    @Override
    protected EventFieldWrapper<RespawnEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(RespawnEvent::getNewPlayer);
    }
}