package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameTabFormatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;

public class PlayerNameTabFormatEventLegacy extends PlayerNameTabFormatEventWrapper<Object> { //TODO Figure out a replacement for this

    @Override
    protected EventFieldWrapper<Object,String> wrapDisplayNameField() {
        return wrapGenericBoth(event -> null,(event,name) -> {},null);
    }

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
}