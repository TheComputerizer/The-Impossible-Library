package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
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
        recalculatePositions();
    }
    
    @Override public void addWidgets(Widget ... widgets) {
        for(Widget widget : widgets) {
            this.widgets.add(widget);
            widget.setParent(this);
        }
        recalculatePositions();
    }
    
    @Override public void addWidgets(Collection<Widget> widgets) {
        for(Widget widget : widgets) {
            this.widgets.add(widget);
            widget.setParent(this);
        }
        recalculatePositions();
    }
    
    @Override public abstract ScrollableWidgetGroup copy();
    
    public abstract boolean doesNotRequireHoverToScroll();
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        super.draw(ctx,center.add(0d,this.scrollOffset,0d,new Vector3d()),mouseX,mouseY);
    }
    
    @Override protected boolean drawHoverable(RenderContext ctx, Hoverable hoverable, Vector3d center, double mouseX,
            double mouseY) {
        mouseX = getOffsetX(mouseX);
        mouseY = getOffsetY(mouseY);
        if(isBounded(center,mouseX,mouseY+this.scrollOffset) && hoverable.isHovering(mouseX,mouseY) && hoverable.shouldDrawHovered()) {
            hoverable.drawHovered(ctx,center,mouseX,mouseY);
            return true;
        }
        return false;
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
    
    @Override protected Box getRenderBounds(Vector3d center) {
        return super.getRenderBounds(center).offset(0d,-this.scrollOffset,0d);
    }
    
    public boolean isHoveringForScroll() {
        double mouseX = RenderHelper.getScaledMouseX();
        double mouseY = RenderHelper.getScaledMouseY();
        double width = getWidth();
        double height = getHeight();
        double left = getX()-(width/2d);
        double bottom = getY()-(height/2d);
        return mouseX>left && mouseY>bottom && mouseX<left+width && mouseY<bottom+height;
    }
    
    @Override public boolean onClicked(double mouseX, double mouseY, boolean leftClick) {
        return super.onClicked(getOffsetX(mouseX),getOffsetY(mouseY),leftClick);
    }
    
    protected abstract void recalculatePositions();
    
    protected double scroll(int scrollAmount) {
        return this.scrollSpeed*((double)Math.abs(scrollAmount));
    }
    
    @Override
    public boolean scrollDown(int scroll) {
        if(doesNotRequireHoverToScroll() || isHoveringForScroll()) {
            double height = getHeight();
            double elementsHeight = getElementsHeight();
            if(this.scrollOffset+height<elementsHeight) {
                this.scrollOffset = Math.min(this.scrollOffset+(scroll(scroll)),elementsHeight-height);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean scrollUp(int scroll) {
        if(doesNotRequireHoverToScroll() || isHoveringForScroll()) {
            if(this.scrollOffset>0d) {
                this.scrollOffset = Math.max(this.scrollOffset-(scroll(scroll)),0d);
                return true;
            }
        }
        return false;
    }
    
    @Override public void setWidgets(Collection<Widget> widgets) {
        super.setWidgets(widgets);
        recalculatePositions();
    }
}