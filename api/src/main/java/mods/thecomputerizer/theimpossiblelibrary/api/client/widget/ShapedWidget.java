package mods.thecomputerizer.theimpossiblelibrary.api.client.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import java.util.Objects;

@Setter
public abstract class ShapedWidget<W> implements WidgetAPI<W> {

    @Getter protected WidgetShape shape;
    protected ScreenAPI<?> screen;

    protected ShapedWidget(WidgetShape shape) {
        this.shape = shape;
    }

    @Override
    public void draw(RenderAPI renderer, float offset) {
        this.shape.draw(renderer,offset);
    }

    @Override
    public @Nonnull Vector2f getCenter() {
        return getShape().getCenter();
    }

    @Override
    public ScreenAPI<?> getScreen() {
        return this.screen;
    }

    @Override
    public boolean isHovering(int x, int y) {
        return getShape().overlaps(x,y);
    }

    @Override
    public boolean overlaps(WidgetAPI<?> widget) {
        if(widget instanceof ShapedWidget) {
            ShapedWidget<?> shaped = (ShapedWidget<?>)widget;
            return Objects.nonNull(this.shape) && this.shape.overlaps(shaped.getShape());
        }
        return false;
    }
}