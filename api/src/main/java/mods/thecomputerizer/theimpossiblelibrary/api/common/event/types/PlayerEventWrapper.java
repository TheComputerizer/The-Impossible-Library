package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

import javax.annotation.Nullable;

public abstract class PlayerEventWrapper<E> extends CommonEventWrapper<E> {

    protected EventFieldWrapper<E,PlayerAPI<?>> player;

    protected PlayerEventWrapper(CommonType<?> type) {
        super(type);
    }

    public @Nullable PlayerAPI<?> getPlayer() {
        return this.player.get(this.event);
    }

    @Override
    protected void populate() {
        this.player = wrapPlayerField();
    }

    protected abstract EventFieldWrapper<E,PlayerAPI<?>> wrapPlayerField();
}
