package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.Objects;
import java.util.function.BiConsumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.GRAY;

@SuppressWarnings("unused") @Getter @Setter
public class WidgetList extends ScrollableWidgetGroup {
    
    public static WidgetList from(Widget template) {
        return new WidgetList(template,0d,0d,2d,2d);
    }
    
    public static WidgetList from(Widget template, double spacing) {
        WidgetList list = new WidgetList(template,0d,0d,2d,2d);
        list.spacing = spacing;
        return list;
    }
    
    public static WidgetList from(Widget template, double width, double height) {
        return new WidgetList(template,0d,0d,width,height);
    }
    
    public static WidgetList from(Widget template, double width, double height, double spacing) {
        WidgetList list = new WidgetList(template,0d,0d,width,height);
        list.spacing = spacing;
        return list;
    }
    
    public static WidgetList from(Widget template, double x, double y, double width, double height) {
        return new WidgetList(template,x,y,width,height);
    }
    
    public static WidgetList from(Widget template, double x, double y, double width, double height, double spacing) {
        WidgetList list = new WidgetList(template,x,y,width,height);
        list.spacing = spacing;
        return list;
    }
    
    protected final ShapeWidget scrollBar;
    protected Widget elementTemplate;
    protected double spacing;
    
    public WidgetList(Widget elementTemplate, double x, double y, double width, double height) {
        double barWidth = 0.01d*(Math.min(2d,width)/2d);
        this.scrollBar = ShapeWidget.from(ShapeHelper.plane(Axis.Y, new Vector2d(-barWidth,-height/2d),
                                                            new Vector2d(barWidth,height/2d)),GRAY);
        this.elementTemplate = elementTemplate;
        this.height = height;
        this.width = width;
        setX(x);
        setY(y);
        this.scrollBar.setParent(this);
        this.scrollBar.setX((this.width/2d)-(this.scrollBar.getWidth()/2d));
        this.scrollBar.setY(this.y);
    }
    
    public void addWidgetFromTemplate(BiConsumer<Widget,Integer> settings) {
        Widget widget = this.elementTemplate.copy();
        settings.accept(widget,this.widgets.size());
        addWidget(widget);
    }
    
    protected void calculateScrollBar() {
        double height = getHeight();
        this.scrollBar.setHeight(height*Math.min(1d,height/getElementsHeight()));
        this.scrollBar.setY(getRelativeTop()-(this.scrollBar.getHeight()/2d));
    }
    
    @Override public WidgetList copy() {
        WidgetList copy = new WidgetList(this.elementTemplate,this.x,this.y,this.width,this.height);
        copy.copyScrollable(this);
        return copy;
    }
    
    @Override public boolean doesNotRequireHoverToScroll() {
        return false;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        super.draw(ctx,center,mouseX,mouseY);
        if(Objects.nonNull(this.scrollBar) && this.scrollBar.canDraw() && getElementsHeight()>getHeight())
            this.scrollBar.draw(ctx,center,mouseX,mouseY);
    }
    
    @Override protected double getElementsHeight() {
        double height = this.spacing;
        for(Widget widget : this.widgets)
            if(widget.canDraw()) height+=(widget.getHeight()+this.spacing);
        return height;
    }
    
    @Override protected void recalculatePositions() {
        double offset = this.spacing;
        double top = getRelativeTop();
        for(Widget widget : this.widgets) {
            double height = widget.getHeight();
            widget.setY(top-offset-(height/2d));
            offset+=(height+spacing);
        }
        calculateScrollBar();
    }
    
    @Override
    public boolean scrollDown(int scroll) {
        if(super.scrollDown(scroll)) {
            setScrollBarPos();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean scrollUp(int scroll) {
        if(super.scrollUp(scroll)) {
            setScrollBarPos();
            return true;
        }
        return false;
    }
    
    protected void setScrollBarPos() {
        double barHeight = this.scrollBar.getHeight();
        double height = getHeight();
        double top = getRelativeTop()-(barHeight/2d);
        double bottom = top-height+barHeight;
        double totalScrollOffset = getElementsHeight()-height;
        double offset = ((totalScrollOffset-this.scrollOffset)/totalScrollOffset)*(top-bottom);
        this.scrollBar.setY(bottom+offset);
    }
}