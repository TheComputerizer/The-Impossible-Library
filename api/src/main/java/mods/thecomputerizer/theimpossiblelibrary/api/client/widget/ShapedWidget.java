package mods.thecomputerizer.theimpossiblelibrary.api.client.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;

import java.util.Objects;

@Setter @Getter
public abstract class ShapedWidget<W> implements WidgetAPI<W> {

    protected WidgetShape shape;

    protected ShapedWidget(WidgetShape shape) {
        this.shape = shape;
    }

    @Override
    public void draw(RenderAPI renderer, float offset) {
        this.shape.draw(renderer,offset);
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