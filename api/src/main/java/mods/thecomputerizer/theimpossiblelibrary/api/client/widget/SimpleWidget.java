package mods.thecomputerizer.theimpossiblelibrary.api.client.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class SimpleWidget extends ShapedWidget<Object> {

    private final MinecraftAPI mc;

    public SimpleWidget(WidgetShape shape) {
        super(shape);
        this.mc = TILRef.getClientSubAPI( ClientAPI::getMinecraft);
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
    public boolean click() {
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
    public Object get() {
        return null;
    }

    @Override
    public @Nonnull List<TextAPI<?>> getHoverLines() {
        return Collections.emptyList();
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return this.mc;
    }

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
    }

    @Override
    public Object make(int id, int x, int y, int width, int height, String locale, Object... args) {
        return null;
    }

    @Override
    public boolean paste(String value) {
        return false;
    }

    @Override
    public void playClickSound() {

    }

    @Override
    public boolean selectable() {
        return false;
    }

    @Override
    public Object set(Object widget) {
        return null;
    }

    @Override
    public void setLocale(String locale) {

    }

    @Override
    public void setPriority(int priority) {

    }

    @Override
    public boolean scrollable() {
        return false;
    }

    @Override
    public void scrollUp(int mouseX, int mouseY) {

    }

    @Override
    public void scrollDown(int mouseX, int mouseY) {

    }

    @Override
    public boolean pressedKey(int key) {
        return false;
    }

    @Override
    public boolean typeChar(char c) {
        return false;
    }
}
