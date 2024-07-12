package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import java.util.Collection;

public class WidgetHelper {
    
    private static WidgetList calculateDropDown(Widget template, double bottom) {
        return calculateDropDown(template,template.x,template.getBottom(),bottom,template.getWidth());
    }
    
    private static WidgetList calculateDropDown(Widget template, double x, double top, double bottom, double width) {
        double height = Math.abs(top-bottom);
        double y = Math.max(top,bottom)-(height/2d);
        return WidgetList.from(template,x,y,width,height);
    }
    
    public static WidgetList dropDownAt(Widget template, double x, double top, double bottom, double width,
            Widget ... options) {
        WidgetList list = calculateDropDown(template,x,top,bottom,width);
        list.addWidgets(options);
        return list;
    }
    
    public static WidgetList dropDownAt(Widget template, double x, double top, double bottom, double width,
            Collection<Widget> options) {
        WidgetList list = calculateDropDown(template,x,top,bottom,width);
        list.addWidgets(options);
        return list;
    }
    
    public static WidgetList dropDownFrom(Widget from, double bottom, Widget ... options) {
        WidgetList list = calculateDropDown(from,bottom);
        list.addWidgets(options);
        return list;
    }
    
    public static WidgetList dropDownFrom(Widget from, double bottom, Collection<Widget> options) {
        WidgetList list = calculateDropDown(from,bottom);
        list.addWidgets(options);
        return list;
    }
}