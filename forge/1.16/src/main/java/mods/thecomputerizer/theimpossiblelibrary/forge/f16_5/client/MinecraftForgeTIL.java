package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.font.FontForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.render.RenderForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextStringForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextTranslationForge;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class MinecraftForgeTIL implements MinecraftAPI {

    private static MinecraftForgeTIL INSTANCE;

    public static MinecraftForgeTIL getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new MinecraftForgeTIL(Minecraft.getInstance());
        return INSTANCE;
    }

    private final Minecraft mc;
    private final FontAPI font;
    private final RenderAPI render;

    private MinecraftForgeTIL(Minecraft mc) {
        this.mc = mc;
        this.font = new FontForge();
        this.render = new RenderForge();
    }

    @Override
    public FontAPI getFont() {
        return this.font;
    }

    @Override
    public TextAPI<?> getLiteralText(String text) {
        return new TextStringForge(text);
    }

    @Override
    public RenderAPI getRenderer() {
        return this.render;
    }

    @Override
    public TextAPI<?> getTranslatedText(String key, Object... args) {
        return new TextTranslationForge(key,args);
    }

    /**
     * TODO Cache this?
     */
    @Override
    public MinecraftWindow getWindow() {
        MainWindow window = this.mc.getWindow();
        return new MinecraftWindow(window.getGuiScaledWidth(),window.getGuiScaledHeight(),(int)window.getGuiScale());
    }

    @Override
    public boolean isCurrentScreen(ScreenAPI<?> screen) {
        return screen.getScreen()==this.mc.screen;
    }
}
