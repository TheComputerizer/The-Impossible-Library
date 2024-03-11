package mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ButtonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.MinecraftLegacy;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ButtonLegacy extends ButtonAPI<GuiButtonExt> {

    public ButtonLegacy(GuiButtonExt button, WidgetShape shape) {
        super(button,shape);
    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        ScreenAPI<?> screen = getScreen();
        return Objects.nonNull(screen) ? screen.getMinecraft() : MinecraftLegacy.getInstance();
    }

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
    }

    @Override
    public @Nonnull List<TextAPI<?>> getHoverLines() {
        return Collections.emptyList();
    }

    @Override
    public GuiButtonExt make(int id, int x, int y, int width, int height, String locale, Object ... args) {
        return null;
    }

    @Override
    public void playClickSound() {

    }

    @Override
    public void setLocale(String locale) {

    }
}
