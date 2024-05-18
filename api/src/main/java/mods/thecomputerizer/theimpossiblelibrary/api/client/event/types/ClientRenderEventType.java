package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;

import javax.annotation.Nonnull;
import java.util.Objects;

@Getter
public abstract class ClientRenderEventType<E> extends ClientEventWrapper<E> {

    protected RenderContext renderer;

    protected ClientRenderEventType(ClientType<?> type) {
        super(type);
    }

    public float getPartialTicks() {
        return Objects.nonNull(this.renderer) ? this.renderer.getPartialTicks() : 0f;
    }

    protected abstract RenderContext initRenderer(@Nonnull E event);

    @Override
    protected void populate() {
        this.renderer = Objects.nonNull(this.event) ? initRenderer(this.event) : null;
    }
}
