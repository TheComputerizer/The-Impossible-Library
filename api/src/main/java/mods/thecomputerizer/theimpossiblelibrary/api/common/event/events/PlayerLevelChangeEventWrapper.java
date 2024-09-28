package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_LEVEL_CHANGE;

public abstract class PlayerLevelChangeEventWrapper<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,Integer> levels;

    protected PlayerLevelChangeEventWrapper() {
        super(PLAYER_XP_LEVEL_CHANGE);
    }

    public int getLevels() {
        return this.levels.get(this.event);
    }

    @Override public void populate() {
        super.populate();
    }

    public void setLevels(int levels) {
        this.levels.set(this.event,levels);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapLevelsField();
}