package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;

public abstract class CommonPlayerEventType<E> extends CommonEventWrapper<E> {

    protected EventFieldWrapper<E,PlayerAPI<?,?>> player;

    protected CommonPlayerEventType(CommonType<?> type) {
        super(type);
    }

    public PlayerAPI<?,?> getPlayer() {
        return this.player.get(this.event);
    }

    @Override public void populate() {
        this.player = wrapPlayerField();
    }

    protected abstract EventFieldWrapper<E,PlayerAPI<?,?>> wrapPlayerField();
}
