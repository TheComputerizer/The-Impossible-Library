package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;

import javax.annotation.Nullable;
import java.util.Objects;

@Setter
public abstract class ScreenAPI<SCREEN> extends WidgetCollectionAPI<WidgetAPI<SCREEN>> {

    private ScreenAPI<SCREEN> parent;

    @Override
    public void draw(RenderAPI renderer, float offset) {
        super.draw(renderer,offset);
    }
    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return this.parent;
    }

    public @Nullable ScreenAPI<?> getParentScreen() {
        WidgetAPI<?> parent = getParent();
        return Objects.nonNull(parent)  ? (ScreenAPI<?>)parent : null;
    }

    @Override
    public @Nullable ScreenAPI<?> getScreen() {
        return this;
    }

    public abstract void init();

    public abstract SCREEN make(String locale, Object ... args);

    @Override
    public WidgetAPI<SCREEN> make(int id, int x, int y, int width, int height, String locale, Object ... args) {
        return null;
    }

    public void onClose() {}

    public boolean onEscape() {
        return true;
    }

    public boolean pausesGame() {
        return true;
    }
}
