package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

import javax.annotation.Nullable;

public abstract class ClientEntityViewEventType<E> extends ClientRenderEventType<E> {

    protected EventFieldWrapper<E,EntityAPI<?>> entity;
    protected EventFieldWrapper<E,BlockStateAPI<?>> state;

    protected ClientEntityViewEventType(ClientType<?> type) {
        super(type);
    }

    public @Nullable EntityAPI<?> getEntity() {
        return this.entity.get(this.event);
    }

    public @Nullable BlockStateAPI<?> getState() {
        return this.state.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.entity = wrapEntityField();
        this.state = wrapStateField();
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?>> wrapEntityField();
    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapStateField();
}
