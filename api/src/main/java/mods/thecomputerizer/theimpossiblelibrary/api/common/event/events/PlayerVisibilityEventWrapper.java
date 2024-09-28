package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_VISIBILITY;

public abstract class PlayerVisibilityEventWrapper<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,Double> visibilityModifier;

    protected PlayerVisibilityEventWrapper() {
        super(PLAYER_VISIBILITY);
    }

    public double getVisibilityModifier() {
        return this.visibilityModifier.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.visibilityModifier = wrapVisibilityModifierField();
    }

    public void setVisibilityModifier(double mod) {
        this.visibilityModifier.set(this.event,mod);
    }

    protected abstract EventFieldWrapper<E,Double> wrapVisibilityModifierField();
}