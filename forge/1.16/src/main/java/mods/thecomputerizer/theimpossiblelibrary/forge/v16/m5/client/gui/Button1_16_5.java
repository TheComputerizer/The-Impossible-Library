package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ButtonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.gui.widget.button.Button;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class Button1_16_5 extends ButtonAPI<Button> {

    public Button1_16_5(Button button, WidgetShape shape) {
        super(button,shape);
    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public @Nonnull List<TextAPI<?>> getHoverLines() {
        return Collections.emptyList();
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return null;
    }

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
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
