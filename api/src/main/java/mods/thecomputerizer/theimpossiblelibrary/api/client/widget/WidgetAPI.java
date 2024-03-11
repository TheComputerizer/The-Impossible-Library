package mods.thecomputerizer.theimpossiblelibrary.api.client.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface WidgetAPI<W> {

    boolean allowKeys();
    boolean backspace();
    boolean click();
    @Nullable String copy();
    @Nullable String cut();
    void draw(RenderAPI renderer, float offset);
    MinecraftAPI getMinecraft();
    @Nullable WidgetAPI<?> getParent();
    W get();
    @Nonnull Vector2f getCenter();
    @Nonnull List<TextAPI<?>> getHoverLines();

    default int getPriority() {
        return 0;
    }

    boolean isHovering(int x, int y);
    W make(int id, int x, int y, int width, int height, String locale);
    boolean overlaps(WidgetAPI<?> widget);
    boolean paste(String value);
    void playClickSound();
    boolean selectable();
    W set(W widget);
    void setLocale(String locale);
    void setPriority(int priority);
    boolean scrollable();
    void scrollUp(int mouseX, int mouseY);
    void scrollDown(int mouseX, int mouseY);
    boolean pressedKey(int key);
    boolean typeChar(char c);
}