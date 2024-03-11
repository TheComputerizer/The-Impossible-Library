package mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.MinecraftLegacy;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class ScreenLegacy extends ScreenAPI<GuiScreen> {

    private final GuiScreen screen;
    private MinecraftAPI mc;

    public ScreenLegacy(GuiScreen screen) {
        this.screen = screen;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        if(Objects.isNull(this.mc)) this.mc = MinecraftLegacy.getInstance();
        return this.mc;
    }

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
    }

    @Override
    public GuiScreen get() {
        return this.screen;
    }

    @Override
    public GuiScreen set(GuiScreen widget) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public GuiScreen make(String locale) {
        return null;
    }

    @Override
    public boolean pausesGame() {
        return false;
    }
}
