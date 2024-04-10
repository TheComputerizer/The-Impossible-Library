package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;

public abstract class CommonBlockStatePlayerEventType<E> extends CommonBlockStateEventType<E> {

    protected EventFieldWrapper<E,PlayerAPI<?>> player;

    protected CommonBlockStatePlayerEventType(CommonType<?> type) {
        super(type);
    }

    public PlayerAPI<?> getPlayer() {
        return this.player.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.player = wrapPlayerField();
    }

    protected abstract EventFieldWrapper<E,PlayerAPI<?>> wrapPlayerField();
}