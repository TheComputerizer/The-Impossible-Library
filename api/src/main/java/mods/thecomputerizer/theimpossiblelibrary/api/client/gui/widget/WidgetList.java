package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector3d;

import java.util.function.BiConsumer;

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
    
    protected Widget elementTemplate;
    protected double spacing;
    
    public WidgetList(Widget elementTemplate, double x, double y, double width, double height) {
        this.elementTemplate = elementTemplate;
        this.height = height;
        this.width = width;
        setX(MathHelper.clamp(x,-1d,1d));
        setY(MathHelper.clamp(y,-1d,1d));
    }
    
    public void addWidgetFromTemplate(BiConsumer<Widget,Integer> settings) {
        Widget widget = this.elementTemplate.copy();
        settings.accept(widget,this.widgets.size());
        addWidget(widget);
    }
    
    @Override protected Vector3d calculatePosition(Widget widget, int index) {
        return new Vector3d(0d,getElementsTop(VectorHelper.zero3D())-getIndexHeight(index)-
                                (this.elementTemplate.getHeight()/2d),0d);
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
    
    @Override protected double getElementsHeight() {
        return getIndexHeight(this.widgets.size());
    }
    
    public double getIndexHeight(int index) {
        return index*getTemplateHeight();
    }
    
    public double getTemplateHeight() {
        return this.elementTemplate.getHeight()+this.spacing;
    }
}