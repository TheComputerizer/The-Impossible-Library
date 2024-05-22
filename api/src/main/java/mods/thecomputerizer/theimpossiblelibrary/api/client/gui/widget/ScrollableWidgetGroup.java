package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import java.util.Collection;

@Getter
public abstract class ScrollableWidgetGroup extends BoundedWidgetGroup implements Scrollable {
    
    @Setter protected double scrollSpeed;
    protected double scrollOffset;
    
    protected ScrollableWidgetGroup() {
        this.scrollSpeed = 0.001d;
    }
    
    @Override public void addWidget(Widget widget) {
        super.addWidget(widget);
        int index = 0;
        for(Widget w : this.widgets) {
            Vector3d center = calculatePosition(w,index);
            w.setX(center.x);
            w.setY(center.y);
            index++;
        }
    }
    
    protected abstract Vector3d calculatePosition(Widget widget, int index);
    
    @Override public abstract ScrollableWidgetGroup copy();
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        super.draw(ctx,new Vector3d(-getOffsetX(center.x),-getOffsetY(center.y),center.z),getOffsetX(mouseX),getOffsetY(mouseY));
    }
    
    @Override public void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        super.drawHovered(ctx,center,mouseX,mouseY);
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double mouseX, double mouseY) {
        return super.getHoverLines(getOffsetX(mouseX),getOffsetY(mouseY));
    }
    
    public double getOffsetX(double x) {
        return x;
    }
    
    public double getOffsetY(double y) {
        return y-this.scrollOffset;
    }
    
    @Override protected Box getRenderBounds(Widget widget, int index, Vector3d center) {
        return super.getRenderBounds(widget,index,center).offset(0d,-this.scrollOffset,0d);
    }
    
    @Override public boolean onClicked(double mouseX, double mouseY, boolean leftClick) {
        return super.onClicked(getOffsetX(mouseX),getOffsetY(mouseY),leftClick);
    }
    
    protected double scroll(int scrollAmount) {
        return this.scrollSpeed*((double)Math.abs(scrollAmount));
    }
    
    @Override
    public boolean scrollDown(int scroll) {
        double height = getHeight();
        double elementsHeight = getElementsHeight();
        if(this.scrollOffset+height<elementsHeight) {
            this.scrollOffset = Math.min(this.scrollOffset+(scroll(scroll)),elementsHeight-height);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean scrollUp(int scroll) {
        if(this.scrollOffset>0d) {
            this.scrollOffset = Math.max(this.scrollOffset-(scroll(scroll)),0d);
            return true;
        }
        return false;
    }
    
    @Override public void setWidgets(Collection<Widget> widgets) {
        super.setWidgets(widgets);
        int index = 0;
        for(Widget widget : widgets) {
            Vector3d center = calculatePosition(widget,index);
            widget.setX(center.x);
            widget.setY(center.y);
            index++;
        }
    }
}