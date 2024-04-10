package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_TAB_FORMAT;

public abstract class PlayerNameTabFormatEventWrapper<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,String> displayName;

    protected PlayerNameTabFormatEventWrapper() {
        super(PLAYER_TAB_FORMAT);
    }

    public String getDisplayName() {
        return this.displayName.get(this.event);
    }

    public void populate() {
        super.populate();
        this.displayName = wrapDisplayNameField();
    }

    public void setDisplayName(String name) {
        this.displayName.set(this.event,name);
    }

    protected abstract EventFieldWrapper<E,String> wrapDisplayNameField();
}