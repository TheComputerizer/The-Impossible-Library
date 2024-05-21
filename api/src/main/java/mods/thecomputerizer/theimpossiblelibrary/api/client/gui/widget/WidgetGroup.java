package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 A collection of widgets from which there can only be one hovered, clicked, selected, or typed on at a time
 */
@SuppressWarnings("unused") @Getter @Setter
public abstract class WidgetGroup extends Widget implements Clickable, Hoverable {
    
    protected Collection<Widget> widgets;
    protected double scaleX;
    private double scaleY;
    
    protected WidgetGroup() {
        this.widgets = new ArrayList<>();
        this.scaleX = 1d;
        this.scaleY = 1d;
    }
    
    public void addWidget(Widget widget) {
        this.widgets.add(widget);
    }
    
    public void addWidgets(Widget ... widgets) {
        for(Widget widget : widgets) addWidget(widget);
    }
    
    public void addWidgets(Collection<Widget> widgets) {
        for(Widget widget : widgets) addWidget(widget);
    }
    
    protected void applyScale(RenderContext ctx) {
        ctx.getScale().modScales(this.scaleX,this.scaleY,1d);
    }
    
    protected boolean checkEachClickable(Function<Clickable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Clickable && func.apply((Clickable)widget));
    }
    
    protected boolean checkEachHoverable(Function<Hoverable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Hoverable && func.apply((Hoverable)widget));
    }
    
    protected boolean checkEachSelectable(Function<Selectable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Selectable && func.apply((Selectable)widget));
    }
    
    protected boolean checkEachWidget(Function<Widget,Boolean> func) {
        for(Widget widget : this.widgets)
            if(func.apply(widget)) return true;
        return false;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        double padding = getInitialPadding(center);
        for(Widget widget : this.widgets) {
            boolean drawn = false;
            Vector3d widgetCenter = getElementCenter(center,padding,widget);
            if(widget instanceof Hoverable) {
                Hoverable hoverable = (Hoverable)widget;
                if(hoverable.isHovering(mouseX,mouseY) && hoverable.shouldDrawHovered()) {
                    hoverable.drawHovered(ctx,widgetCenter,mouseX,mouseY);
                    drawn = true;
                }
            }
            if(widget instanceof Selectable) {
                Selectable selectable = (Selectable)widget;
                if(selectable.isSelected()) {
                    selectable.drawSelected(ctx,widgetCenter,mouseX,mouseY);
                    drawn = true;
                }
            }
            if(!drawn) widget.draw(ctx,widgetCenter,mouseX,mouseY);
            //padding+=(widget.getHeight()*1.05d);
        }
    }
    
    @Override public void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        draw(ctx,center,mouseX,mouseY);
    }
    
    protected void eachClickable(Consumer<Clickable> func) {
        eachWidget(widget -> {
            if(widget instanceof Clickable) func.accept((Clickable)widget);
        });
    }
    
    protected void eachHoverable(Consumer<Hoverable> func) {
        eachWidget(widget -> {
            if(widget instanceof Hoverable) func.accept((Hoverable)widget);
        });
    }
    
    protected void eachSelectable(Consumer<Selectable> func) {
        eachWidget(widget -> {
            if(widget instanceof Selectable) func.accept((Selectable)widget);
        });
    }
    
    protected void eachWidget(Consumer<Widget> func) {
        for(Widget widget : this.widgets) func.accept(widget);
    }
    
    protected double getElementsHeight() {
        double height = 0d;
        for(Widget widget : this.widgets) height+=widget.getHeight();
        return height;
    }
    
    protected Vector3d getElementCenter(Vector3d center, double padding, Widget widget) {
        return widget.getCenter(center).add(0d,padding,0d);
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double x, double y) {
        for(Widget widget : this.widgets)
            if(widget instanceof Hoverable && ((Hoverable)widget).isHovering(x,y))
                return ((Hoverable)widget).getHoverLines(x,y);
        return Collections.emptyList();
    }
    
    protected double getInitialPadding(Vector3d center) {
        return 0d;
    }
    
    @Override public boolean isHovering(double x, double y) {
        return checkEachHoverable(hoverable -> hoverable.isHovering(x,y));
    }
    
    @Override public boolean onClicked(double x, double y, boolean leftClick) {
        for(Widget widget : this.widgets)
            if(widget instanceof Clickable && ((Clickable)widget).onClicked(x,y,leftClick)) return true;
        return false;
    }
    
    @Override public void playClickSound() {}
    
    @Override public void onResolutionUpdated(MinecraftWindow window) {
        eachWidget(widget -> widget.onResolutionUpdated(window));
    }
    
    public void setWidgets(Widget ... widgets) {
        this.widgets = new ArrayList<>(Arrays.asList(widgets));
    }
    
    @Override public boolean shouldDrawHovered() {
        return checkEachHoverable(Hoverable::shouldDrawHovered);
    }
    
    protected void unapplyScale(RenderContext ctx) {
        ctx.getScale().modScales(1d/this.scaleX,1d/this.scaleY,1d);
    }
}