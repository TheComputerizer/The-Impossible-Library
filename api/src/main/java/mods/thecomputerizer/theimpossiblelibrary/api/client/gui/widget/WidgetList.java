package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.function.BiConsumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.GRAY;

@SuppressWarnings("unused") @Getter @Setter
public class WidgetList extends ScrollableWidgetGroup {
    
    public static WidgetList from(Widget elementTemplate) {
        return new WidgetList(elementTemplate,0d,0d,2d,2d);
    }
    
    public static WidgetList from(Widget elementTemplate, double spacing) {
        WidgetList list = new WidgetList(elementTemplate,0d,0d,2d,2d);
        list.spacing = spacing;
        return list;
    }
    
    public static WidgetList from(Widget elementTemplate, double width, double height) {
        return new WidgetList(elementTemplate,0d,0d,width,height);
    }
    
    public static WidgetList from(Widget elementTemplate, double width, double height, double spacing) {
        WidgetList list = new WidgetList(elementTemplate,0d,0d,width,height);
        list.spacing = spacing;
        return list;
    }
    
    public static WidgetList from(Widget elementTemplate, double x, double y, double width, double height) {
        return new WidgetList(elementTemplate,x,y,width,height);
    }
    
    public static WidgetList from(Widget elementTemplate, double x, double y, double width, double height, double spacing) {
        WidgetList list = new WidgetList(elementTemplate,x,y,width,height);
        list.spacing = spacing;
        return list;
    }
    
    protected final ShapeWidget scrollBar;
    protected Widget elementTemplate;
    protected double spacing;
    
    public WidgetList(Widget elementTemplate, double x, double y, double width, double height) {
        this.scrollBar = ShapeWidget.from(ShapeHelper.plane(Axis.Y,new Vector2d(-0.01d,-height/2d),
                                                            new Vector2d(0.01d,height/2d)),GRAY);
        this.elementTemplate = elementTemplate;
        this.height = height;
        this.width = width;
        setX(MathHelper.clamp(x,-1d,1d));
        setY(MathHelper.clamp(y,-1d,1d));
        this.scrollBar.setParent(this);
        this.scrollBar.setX(this.x+(this.width/2d)-(this.scrollBar.getWidth()/2d));
        this.scrollBar.setY(this.y);
    }
    
    public void addWidgetFromTemplate(BiConsumer<Widget,Integer> settings) {
        Widget widget = this.elementTemplate.copy();
        settings.accept(widget,this.widgets.size());
        addWidget(widget);
    }
    
    @Override protected Vector3d calculatePosition(Widget widget, int index) {
        calculateScrollBar();
        return new Vector3d(0d,getElementsTop(0d)-getIndexHeight(index)-this.spacing-
                                (this.elementTemplate.getHeight()/2d),0d);
    }
    
    protected void calculateScrollBar() {
        double height = getHeight();
        this.scrollBar.setHeight(Math.max(height,height*Math.min(1d,height/getElementsHeight())));
        this.scrollBar.setY(getTop()-(this.scrollBar.getHeight()/2d));
    }
    
    @Override public WidgetList copy() {
        WidgetList copy = new WidgetList(this.elementTemplate,this.x,this.y,this.width,this.height);
        for(Widget widget : this.widgets) copy.addWidget(widget.copy());
        copy.scaleX = this.scaleX;
        copy.scaleY = this.scaleY;
        copy.scrollOffset = this.scrollOffset;
        copy.scrollSpeed = this.scrollSpeed;
        return copy;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        super.draw(ctx,center,mouseX,mouseY);
        if(getElementsHeight()>getHeight()) this.scrollBar.draw(ctx,center,mouseX,mouseY);
    }
    
    @Override protected double getElementsHeight() {
        return this.spacing+getIndexHeight(this.widgets.size());
    }
    
    public double getIndexHeight(int index) {
        return index*getTemplateHeight();
    }
    
    public double getTemplateHeight() {
        return this.elementTemplate.getHeight()+this.spacing;
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
        double top = getTop()-(barHeight/2d);
        double bottom = getBottom()+(barHeight/2d);
        double totalScrollOffset = getElementsHeight()-getHeight();
        double offset = ((totalScrollOffset-this.scrollOffset)/totalScrollOffset)*(top-bottom);
        this.scrollBar.setY(bottom+offset);
    }
}