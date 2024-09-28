package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_NAME_FORMAT;

public abstract class PlayerNameFormatEventWrapper<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,String> displayName;
    protected EventFieldWrapper<E,String> username;

    protected PlayerNameFormatEventWrapper() {
        super(PLAYER_NAME_FORMAT);
    }

    public String getDisplayName() {
        return this.displayName.get(this.event);
    }

    public String getUsername() {
        return this.username.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.displayName = wrapDisplayNameField();
        this.username = wrapUsernameField();
    }

    public void setDisplayName(String name) {
        this.displayName.set(this.event,name);
    }

    protected abstract EventFieldWrapper<E,String> wrapDisplayNameField();
    protected abstract EventFieldWrapper<E,String> wrapUsernameField();
}