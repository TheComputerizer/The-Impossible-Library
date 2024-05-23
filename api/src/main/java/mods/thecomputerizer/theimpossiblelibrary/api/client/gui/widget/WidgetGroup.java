package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 A collection of widgets from which there can only be one hovered, clicked, selected, or typed on at a time
 */
@SuppressWarnings("unused") @Getter
public abstract class WidgetGroup extends Widget implements Clickable, Hoverable, Scrollable, Typeable {
    
    protected Collection<Widget> widgets;
     @Setter protected double scaleX;
     @Setter protected double scaleY;
    
    protected WidgetGroup() {
        this(null);
    }
    
    protected WidgetGroup(@Nullable Widget parent) {
        super(parent);
        this.widgets = new ArrayList<>();
        this.scaleX = 1d;
        this.scaleY = 1d;
    }
    
    public void addWidget(Widget widget) {
        this.widgets.add(widget);
        widget.setParent(this);
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
    
    @Override public boolean canType() {
        return checkEachTypeable(Typeable::canType);
    }
    
    protected boolean checkEachClickable(Function<Clickable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Clickable && func.apply((Clickable)widget));
    }
    
    protected boolean checkEachHoverable(Function<Hoverable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Hoverable && func.apply((Hoverable)widget));
    }
    
    protected boolean checkEachScrollable(Function<Scrollable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Scrollable && func.apply((Scrollable)widget));
    }
    
    protected boolean checkEachSelectable(Function<Selectable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Selectable && func.apply((Selectable)widget));
    }
    
    protected boolean checkEachTypeable(Function<Typeable,Boolean> func) {
        return checkEachWidget(widget -> widget instanceof Typeable && func.apply((Typeable)widget));
    }
    
    protected boolean checkEachWidget(Function<Widget,Boolean> func) {
        for(Widget widget : this.widgets)
            if(func.apply(widget)) return true;
        return false;
    }
    
    @Override public abstract WidgetGroup copy();
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        for(Widget widget : this.widgets) drawWidget(ctx,widget,center,mouseX,mouseY);
    }
    
    protected boolean drawHoverable(RenderContext ctx, Hoverable hoverable, Vector3d center, double mouseX, double mouseY) {
        if(hoverable.isHovering(mouseX,mouseY) && hoverable.shouldDrawHovered()) {
            hoverable.drawHovered(ctx,center,mouseX,mouseY);
            return true;
        }
        return false;
    }
    
    protected boolean drawSelectable(RenderContext ctx, Selectable selectable, Vector3d center, double mouseX, double mouseY) {
        if(selectable.isSelected()) {
            selectable.drawSelected(ctx,center,mouseX,mouseY);
            return true;
        }
        return false;
    }
    
    @Override public void drawHovered(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        draw(ctx,center,mouseX,mouseY);
    }
    
    public void drawWidget(RenderContext ctx, Widget widget, Vector3d center, double mouseX, double mouseY) {
        boolean drawn = widget instanceof Hoverable && drawHoverable(ctx,(Hoverable)widget,center,mouseX,mouseY);
        if(widget instanceof Selectable && drawSelectable(ctx,(Selectable)widget,center,mouseX,mouseY)) drawn = true;
        if(!drawn) widget.draw(ctx,center,mouseX,mouseY);
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
    
    protected void eachScrollable(Consumer<Scrollable> func) {
        eachWidget(widget -> {
            if(widget instanceof Scrollable) func.accept((Scrollable)widget);
        });
    }
    
    protected void eachSelectable(Consumer<Selectable> func) {
        eachWidget(widget -> {
            if(widget instanceof Selectable) func.accept((Selectable)widget);
        });
    }
    
    protected void eachTypeable(Consumer<Typeable> func) {
        eachWidget(widget -> {
            if(widget instanceof Typeable) func.accept((Typeable)widget);
        });
    }
    
    protected void eachWidget(Consumer<Widget> func) {
        for(Widget widget : this.widgets) func.accept(widget);
    }
    
    protected double getElementsBottom(double z) {
        return Math.max(getBottom(),getCenter(z).y-(getElementsHeight()/2d));
    }
    
    protected double getElementsHeight() {
        double top = 0d;
        double bottom = 0d;
        boolean first = true;
        for(Widget widget : this.widgets) {
            double wTop = widget.getTop();
            double wBottom = widget.getBottom();
            if(first) {
                top = wTop;
                bottom = wBottom;
                first = false;
            } else {
                if(wBottom<bottom) bottom = wBottom;
                if(wTop>top) top = wTop;
            }
        }
        return Math.abs(top-bottom);
    }
    
    protected double getElementsLeft(double z) {
        return Math.max(getLeft(),getCenter(z).x-(getElementsWidth()/2d));
    }
    
    protected double getElementsWidth() {
        double left = 0d;
        double right = 0d;
        boolean first = true;
        for(Widget widget : this.widgets) {
            double wLeft = widget.getLeft();
            double wRight = widget.getRight();
            if(first) {
                right = wRight;
                left = wLeft;
                first = false;
            } else {
                if(wLeft<left) left = wLeft;
                if(wRight>right) right = wRight;
            }
        }
        return Math.abs(right-left);
    }
    
    protected double getElementsTop(double z) {
        return Math.min(getTop(),getCenter(z).y+(getElementsHeight()/2d));
    }
    
    protected double getElementsRight(double z) {
        return Math.min(getRight(),getCenter(z).x+(getElementsWidth()/2d));
    }
    
    public @Nullable Widget getHoveredElement(double mouseX, double mouseY) {
        for(Widget widget : this.widgets)
            if(widget instanceof Hoverable && ((Hoverable)widget).isHovering(mouseX,mouseY)) return widget;
        return null;
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double mouseX, double mouseY) {
        for(Widget widget : this.widgets)
            if(widget instanceof Hoverable && ((Hoverable)widget).isHovering(mouseX,mouseY))
                return ((Hoverable)widget).getHoverLines(mouseX,mouseY);
        return Collections.emptyList();
    }
    
    public @Nullable Widget getSelectedElement(double mouseX, double mouseY) {
        for(Widget widget : this.widgets)
            if(widget instanceof Selectable && ((Selectable)widget).isSelected()) return widget;
        return null;
    }
    
    @Override public boolean isHovering(double mouseX, double mouseY) {
        return checkEachHoverable(hoverable -> hoverable.isHovering(mouseX,mouseY));
    }
    
    @Override public boolean onBackspace() {
        return checkEachTypeable(Typeable::onBackspace);
    }
    
    @Override public boolean onCharTyped(char c) {
        return checkEachTypeable(typeable -> typeable.onCharTyped(c));
    }
    
    @Override public boolean onClicked(double x, double y, boolean leftClick) {
        for(Widget widget : this.widgets)
            if(widget instanceof Clickable && ((Clickable)widget).onClicked(x,y,leftClick)) return true;
        return false;
    }
    
    @Override public @Nullable String onCopy() {
        for(Widget widget : this.widgets) {
            if(widget instanceof Typeable) {
                String copied = ((Typeable)widget).onCopy();
                if(Objects.nonNull(copied)) return copied;
            }
        }
        return null;
    }
    
    @Override public @Nullable String onCut() {
        for(Widget widget : this.widgets) {
            if(widget instanceof Typeable) {
                String copied = ((Typeable)widget).onCut();
                if(Objects.nonNull(copied)) return copied;
            }
        }
        return null;
    }
    
    @Override public boolean onPaste(@Nullable String text) {
        return checkEachTypeable(typeable -> typeable.onPaste(text));
    }
    
    @Override public boolean onSelectAll() {
        return checkEachTypeable(Typeable::onSelectAll);
    }
    
    @Override public void playClickSound() {}
    
    @Override public void onResolutionUpdated(MinecraftWindow window) {
        eachWidget(widget -> widget.onResolutionUpdated(window));
    }
    
    @Override
    public boolean scrollDown(int scroll) {
        return checkEachScrollable(scrollable -> scrollable.scrollDown(scroll));
    }
    
    @Override
    public boolean scrollUp(int scroll) {
        return checkEachScrollable(scrollable -> scrollable.scrollUp(scroll));
    }
    
    public void setWidgets(Widget ... widgets) {
        setWidgets(new ArrayList<>(Arrays.asList(widgets)));
    }
    
    public void setWidgets(Collection<Widget> widgets) {
        this.widgets = widgets;
        for(Widget widget : this.widgets) widget.setParent(this);
    }
    
    @Override public boolean shouldDrawHovered() {
        return checkEachHoverable(Hoverable::shouldDrawHovered);
    }
    
    protected void unapplyScale(RenderContext ctx) {
        ctx.getScale().modScales(1d/this.scaleX,1d/this.scaleY,1d);
    }
}