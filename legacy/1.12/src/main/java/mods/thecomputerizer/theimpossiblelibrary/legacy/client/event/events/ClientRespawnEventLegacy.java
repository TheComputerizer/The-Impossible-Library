package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientRespawnEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;

public class ClientRespawnEventLegacy extends ClientRespawnEventWrapper<Object> { //TODO Find or make a suitable replacement

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapOldPlayerField() {
        return wrapPlayerGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
}