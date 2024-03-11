package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.ShapedWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ButtonAPI<B> extends ShapedWidget<B> {

    protected int priority;
    protected B button;

    protected ButtonAPI(B button, WidgetShape shape) {
        super(shape);
        this.button = button;
    }

    @Override
    public boolean allowKeys() {
        return false;
    }

    @Override
    public boolean backspace() {
        return false;
    }

    @Override
    public @Nullable String copy() {
        return null;
    }

    @Override
    public @Nullable String cut() {
        return null;
    }

    @Override
    public B get() {
        return this.button;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean paste(String value) {
        return false;
    }

    @Override
    public boolean selectable() {
        return false;
    }

    @Override
    public B set(@Nonnull B widget) {
        return this.button = widget;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean scrollable() {
        return false;
    }

    @Override
    public void scrollUp(int mouseX, int mouseY) {}

    @Override
    public void scrollDown(int mouseX, int mouseY) {}

    @Override
    public boolean pressedKey(int key) {
        return false;
    }

    @Override
    public boolean typeChar(char c) {
        return false;
    }
}