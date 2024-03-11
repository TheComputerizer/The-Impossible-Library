package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.geometry.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class WidgetCollectionAPI<W extends WidgetAPI<?>> implements WidgetAPI<W> {

    @Getter protected final List<WidgetAPI<?>> widgets;
    @Setter @Getter protected WidgetAPI<?> selected;
    private int priority;

    protected WidgetCollectionAPI() {
        this.widgets = new ArrayList<>();
    }

    public void addWidget(WidgetAPI<?> widget) {
        this.widgets.add(widget);
    }

    @Override
    public boolean allowKeys() {
        return Objects.nonNull(this.selected) && this.selected.allowKeys();
    }

    @Override
    public boolean backspace() {
        return Objects.nonNull(this.selected) && this.selected.allowKeys() && this.selected.backspace();
    }

    @Override
    public boolean click() {
        return Objects.nonNull(this.selected) && this.selected.click();
    }

    @Override
    public @Nullable String copy() {
        return Objects.nonNull(this.selected) && this.selected.allowKeys() ? this.selected.copy() : null;
    }

    @Override
    public @Nullable String cut() {
        return Objects.nonNull(this.selected) && this.selected.allowKeys() ? this.selected.cut() : null;
    }

    @Override
    public void draw(RenderAPI renderer, float offset) {
        for(WidgetAPI<?> widget : this.widgets)
            widget.draw(renderer,offset);
    }

    @Override
    public W get() {
        return null;
    }

    @Override
    public @Nonnull Vector2f getCenter() {
        return VectorHelper.ZERO_2F;
    }

    @Override
    public @Nonnull List<TextAPI<?>> getHoverLines() {
        return Collections.emptyList();
    }

    public @Nonnull List<TextAPI<?>> getHoverLines(int x, int y) {
        WidgetAPI<?> widget = getWidget(x,y);
        return Objects.nonNull(widget) ? widget.getHoverLines() : Collections.emptyList();
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return null;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    public @Nullable WidgetAPI<?> getWidget(int x, int y) {
        WidgetAPI<?> hovered = null;
        for(WidgetAPI<?> widget : this.widgets)
            if(widget.isHovering(x,y) && (Objects.isNull(hovered) || widget.getPriority()>hovered.getPriority()))
                hovered = widget;
        return hovered;
    }

    /**
     * Only applies for the selected widget
     */
    @Override
    public boolean isHovering(int x, int y) {
        return Objects.nonNull(this.selected) && this.selected.isHovering(x,y);
    }

    @Override
    public W make(int id, int x, int y, int width, int height, String locale, Object... args) {
        return null;
    }

    @Override
    public boolean overlaps(WidgetAPI<?> widget) {
        for(WidgetAPI<?> child : this.widgets)
            if(widget.overlaps(child)) return true;
        return false;
    }

    @Override
    public boolean paste(String value) {
        return Objects.nonNull(this.selected) && this.selected.allowKeys() && this.selected.paste(value);
    }

    @Override
    public void playClickSound() {}

    @Override
    public boolean pressedKey(int key) {
        return Objects.nonNull(this.selected) && this.selected.allowKeys() && this.selected.pressedKey(key);
    }

    /**
     * Removes the top level widget at the input (x,y)
     */
    public void removeWidget(int x, int y) {
        WidgetAPI<?> widget = getWidget(x,y);
        if(Objects.nonNull(widget)) this.widgets.remove(widget);
    }

    /**
     * Removes all widgets at the input (x,y)
     */
    public void removeWidgets(int x, int y) {
        WidgetAPI<?> widget = getWidget(x,y);
        while(Objects.nonNull(widget)) {
            this.widgets.remove(widget);
            widget = getWidget(x,y);
        }
    }

    @Override
    public boolean scrollable() {
        for(WidgetAPI<?> widget : this.widgets)
            if(widget.scrollable()) return true;
        return false;
    }

    @Override
    public void scrollUp(int mouseX, int mouseY) {
        WidgetAPI<?> widget = getWidget(mouseX,mouseY);
        if(Objects.nonNull(widget) && widget.scrollable()) widget.scrollUp(mouseX,mouseY);
    }

    @Override
    public void scrollDown(int mouseX, int mouseY) {
        WidgetAPI<?> widget = getWidget(mouseX,mouseY);
        if(Objects.nonNull(widget) && widget.scrollable()) widget.scrollDown(mouseX,mouseY);
    }

    public @Nullable WidgetAPI<?> select(int x, int y) {
        return select(getWidget(x,y));
    }

    /**
     * Returns the result of the select which is NOT the same as the currently selected if the selection fails
     */
    public @Nullable WidgetAPI<?> select(@Nullable WidgetAPI<?> widget) {
        if(Objects.nonNull(widget) && widget.selectable()) {
            this.selected = widget;
            return this.selected;
        }
        return null;
    }

    @Override
    public boolean selectable() {
        return false;
    }

    @Override
    public void setLocale(String locale) {
        if(Objects.nonNull(this.selected)) this.selected.setLocale(locale);
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean typeChar(char c) {
        return Objects.nonNull(this.selected) && this.selected.allowKeys() && this.selected.typeChar(c);
    }

    public void unselect() {
        this.selected = null;
    }
}
