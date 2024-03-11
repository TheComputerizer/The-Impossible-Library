package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.ShapedWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;

import javax.annotation.Nonnull;

public abstract class TextWidgetAPI<T> extends ShapedWidget<T> {

    protected int priority;
    protected T text;

    protected TextWidgetAPI(T text, WidgetShape shape) {
        super(shape);
        this.text = text;
    }

    @Override
    public boolean allowKeys() {
        return true;
    }

    @Override
    public T get() {
        return this.text;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean selectable() {
        return true;
    }

    @Override
    public T set(@Nonnull T widget) {
        return this.text = widget;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
