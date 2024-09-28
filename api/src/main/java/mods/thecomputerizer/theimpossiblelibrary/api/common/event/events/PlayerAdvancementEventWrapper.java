package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public abstract class PlayerAdvancementEventWrapper<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,AdvancementAPI<?>> advancement;

    protected PlayerAdvancementEventWrapper() {
        super(PLAYER_ADVANCEMENT);
    }

    public AdvancementAPI<?> getAdvancement() {
        return this.advancement.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.advancement = wrapAdvancementField();
    }

    protected abstract EventFieldWrapper<E,AdvancementAPI<?>> wrapAdvancementField();
}