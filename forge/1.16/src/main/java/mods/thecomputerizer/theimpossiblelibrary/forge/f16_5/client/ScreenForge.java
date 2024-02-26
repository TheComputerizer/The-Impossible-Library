package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import net.minecraft.client.gui.screen.Screen;

import java.util.Objects;

public class ScreenForge implements ScreenAPI<Screen> {

    private final Screen screen;
    private MinecraftAPI mc;

    public ScreenForge(Screen screen) {
        this.screen = screen;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        if(Objects.isNull(this.mc)) this.mc = MinecraftForgeTIL.getInstance();
        return this.mc;
    }

    @Override
    public Screen getScreen() {
        return this.screen;
    }
}
