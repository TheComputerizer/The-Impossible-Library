package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Button extends BoundedWidgetGroup {
    
    @Getter protected final ShapeWidget shape;
    protected Collection<TextAPI<?>> hoverLines;
    @Setter protected Widget hoverWidget;
    
    public Button(ShapeWidget shape) {
        this(shape,null);
    }
    
    public Button(ShapeWidget shape, Widget hoverWidget) {
        this.shape = shape;
        this.x = shape.x;
        this.y = shape.y;
        this.hoverLines = new ArrayList<>();
        this.widgets.add(shape);
    }
    
    
    @Override public Collection<TextAPI<?>> getHoverLines(double x, double y) {
        return Objects.nonNull(this.hoverLines) ? this.hoverLines : Collections.emptyList();
    }
    
    @Override public boolean isHovering(double x, double y) {
        return this.shape.isInside(x,y,0d);
    }
    
    @Override public void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        draw(ctx,center,mouseX,mouseY);
        if(Objects.nonNull(this.hoverWidget)) this.hoverWidget.draw(ctx,center,mouseX,mouseY);
    }
    
    @Override public boolean shouldDrawHovered() {
        return Objects.nonNull(this.hoverWidget) || !this.hoverLines.isEmpty();
    }
    
    @Override public double getHeight() {
        return this.shape.getHeight();
    }
    
    @Override public double getWidth() {
        return this.shape.getWidth();
    }
    
    /**
     No click actions by default
     */
    @Override public boolean onClicked(double x, double y, int button) {
        return false;
    }
}
