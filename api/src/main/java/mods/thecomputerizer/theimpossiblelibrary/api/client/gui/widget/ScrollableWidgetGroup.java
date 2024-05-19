package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

@Getter
public abstract class ScrollableWidgetGroup extends BoundedWidgetGroup implements Scrollable {
    
    @Setter protected double scrollSpeed;
    protected double scrollOffset;
    
    protected ScrollableWidgetGroup() {
        this.scrollSpeed = 0.01d;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        ctx.getScale().modTransforms(0d,this.scrollOffset,0d);
        super.draw(ctx,center,mouseX,mouseY);
        ctx.getScale().modTransforms(0d,-this.scrollOffset,0d);
    }
    
    @Override protected double getInitialPadding(Vector3d center) {
        return center.y+(getHeight()/2d);
    }
    
    @Override
    public boolean scrollDown(int scroll) {
        double height = getHeight();
        double elementsHeight = getElementsHeight();
        if(this.scrollOffset+height<elementsHeight) {
            this.scrollOffset = Math.min(this.scrollOffset+(this.scrollSpeed*Math.abs(scroll)),elementsHeight-height);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean scrollUp(int scroll) {
        if(this.scrollOffset>0d) {
            this.scrollOffset = Math.max(this.scrollOffset-(this.scrollSpeed*Math.abs(scroll)),0d);
            return true;
        }
        return false;
    }
}