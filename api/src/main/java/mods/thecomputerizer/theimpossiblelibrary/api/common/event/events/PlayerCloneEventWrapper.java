package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CLONE;

public abstract class PlayerCloneEventWrapper<E> extends CommonPlayerEventType<E> {
    
    protected EventFieldWrapper<E,Boolean> fromDeath;
    protected EventFieldWrapper<E,PlayerAPI<?,?>> original;

    protected PlayerCloneEventWrapper() {
        super(PLAYER_CLONE);
    }

    public PlayerAPI<?,?> getOriginal() {
        return this.original.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.fromDeath = wrapDeathField();
        this.original = wrapOriginalField();
    }

    public boolean wasFromDeath() {
        return this.fromDeath.get(this.event);
    }

    protected abstract EventFieldWrapper<E,Boolean> wrapDeathField();
    protected abstract EventFieldWrapper<E,PlayerAPI<?,?>> wrapOriginalField();
}