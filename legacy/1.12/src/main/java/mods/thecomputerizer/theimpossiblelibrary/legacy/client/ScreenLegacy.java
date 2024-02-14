package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import net.minecraft.client.gui.GuiScreen;

import java.util.Objects;

public class ScreenLegacy implements ScreenAPI<GuiScreen> {

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
    public GuiScreen getScreen() {
        return this.screen;
    }
}
