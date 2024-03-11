package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ButtonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.gui.widget.button.Button;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ButtonForge extends ButtonAPI<Button> {

    protected ButtonForge(Button button, WidgetShape shape) {
        super(button,shape);
    }

    @Override
    public boolean click() {
        return false;
    }

    @Nonnull
    @Override
    public Vector2f getCenter() {
        return null;
    }

    @Nonnull
    @Override
    public List<TextAPI<?>> getHoverLines() {
        return null;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return null;
    }

    @Nullable
    @Override
    public WidgetAPI<?> getParent() {
        return null;
    }

    @Nullable
    @Override
    public ScreenAPI<?> getScreen() {
        return null;
    }

    @Override
    public boolean isHovering(int x, int y) {
        return false;
    }

    @Override
    public Button make(int id, int x, int y, int width, int height, String locale, Object ... args) {
        return null;
    }

    @Override
    public void playClickSound() {

    }

    @Override
    public void setLocale(String locale) {

    }
}
