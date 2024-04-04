package mods.thecomputerizer.theimpossiblelibrary.api.client.event.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class RenderEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderEventWrapper(ClientType<?> type) {
        super(type);
    }
    protected abstract void initRenderer(RenderAPI renderer);

    @Override
    public void prepareInvoke() {
        RenderAPI renderer = RenderHelper.getRenderer();
        if(Objects.nonNull(renderer)) initRenderer(renderer);
    }
}
