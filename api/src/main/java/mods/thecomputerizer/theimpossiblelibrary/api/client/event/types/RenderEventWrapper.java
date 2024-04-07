package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import javax.annotation.Nonnull;
import java.util.Objects;

@Getter
public abstract class RenderEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderAPI renderer;

    protected RenderEventWrapper(ClientType<?> type) {
        super(type);
    }

    public float getPartialTicks() {
        return Objects.nonNull(this.renderer) ? this.renderer.getPartialTicks() : 0f;
    }

    protected abstract RenderAPI initRenderer(@Nonnull E event);

    @Override
    protected void populate() {
        this.renderer = Objects.nonNull(this.event) ? initRenderer(this.event) : null;
    }
}
