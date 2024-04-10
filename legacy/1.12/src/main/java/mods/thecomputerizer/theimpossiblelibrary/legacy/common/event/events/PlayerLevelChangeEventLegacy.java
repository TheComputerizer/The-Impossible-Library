package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLevelChangeEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;

public class PlayerLevelChangeEventLegacy extends PlayerLevelChangeEventWrapper<Object> { //TODO This is wrong

    @Override
    protected EventFieldWrapper<Object,Integer> wrapLevelsField() {
        return wrapGenericBoth(event -> 0,(event,levels) -> {},0);
    }

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
}