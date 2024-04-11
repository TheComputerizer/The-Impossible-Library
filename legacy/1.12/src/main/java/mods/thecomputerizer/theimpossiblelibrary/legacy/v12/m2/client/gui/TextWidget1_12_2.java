package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.TextWidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Minecraft1_12_2;
import net.minecraft.client.gui.GuiTextField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TextWidget1_12_2 extends TextWidgetAPI<GuiTextField> {

    public TextWidget1_12_2(GuiTextField text, WidgetShape shape) {
        super(text,shape);
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
    public @Nonnull List<TextAPI<?>> getHoverLines() {
        return Collections.emptyList();
    }

    @Override
    public MinecraftAPI getMinecraft() {
        ScreenAPI<?> screen = getScreen();
        return Objects.nonNull(screen) ? screen.getMinecraft() : Minecraft1_12_2.getInstance();
    }

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
    }

    @Override
    public GuiTextField make(int id, int x, int y, int width, int height, String locale, Object... args) {
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
    public void setLocale(String locale) {

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
