package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Setter;

import java.util.Collection;

@Setter
public class BasicWidgetGroup extends WidgetGroup {
    
    public static BasicWidgetGroup from(Widget ... widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(0d,0d,2d,2d);
        basic.addWidgets(widgets);
        return basic;
    }
    
    public static BasicWidgetGroup from(Collection<Widget> widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(0d,0d,2d,2d);
        basic.addWidgets(widgets);
        return basic;
    }
    
    public static BasicWidgetGroup from(double width, double height, Widget ... widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(0d,0d,width,height);
        basic.addWidgets(widgets);
        return basic;
    }
    
    public static BasicWidgetGroup from(double width, double height, Collection<Widget> widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(0d,0d,width,height);
        basic.addWidgets(widgets);
        return basic;
    }
    
    public static BasicWidgetGroup from(double x, double y, double width, double height, Widget ... widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(x,y,width,height);
        basic.addWidgets(widgets);
        return basic;
    }
    
    public static BasicWidgetGroup from(double x, double y, double width, double height, Collection<Widget> widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(x,y,width,height);
        basic.addWidgets(widgets);
        return basic;
    }
    
    public static BasicWidgetGroup from(WidgetGroup group, Widget ... widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(group.x,group.y,group.getWidth(),group.getHeight());
        basic.addWidgets(widgets);
        return basic;
    }
    
    public static BasicWidgetGroup from(WidgetGroup group, Collection<Widget> widgets) {
        BasicWidgetGroup basic = new BasicWidgetGroup(group.x,group.y,group.getWidth(),group.getHeight());
        basic.addWidgets(widgets);
        return basic;
    }
    
    protected double width;
    protected double height;
    
    public BasicWidgetGroup(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    @Override public double getHeight() {
        return this.height;
    }
    
    @Override public double getWidth() {
        return this.width;
    }
}
