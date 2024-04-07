package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

import javax.annotation.Nullable;

public abstract class ClientPlayerEventWrapper<E> extends ClientEventWrapper<E> {

    protected EventFieldWrapper<E,PlayerAPI<?>> player;

    protected ClientPlayerEventWrapper(ClientType<?> type) {
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
