package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangeGamemodeEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;

public class PlayerChangeGamemodeEventLegacy extends PlayerChangeGamemodeEventWrapper<Object> { //TODO Implement replacement


    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
}