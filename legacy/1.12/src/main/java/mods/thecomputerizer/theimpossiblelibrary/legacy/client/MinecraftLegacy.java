package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class MinecraftLegacy implements MinecraftAPI {

    private static MinecraftLegacy INSTANCE;

    public static MinecraftLegacy getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new MinecraftLegacy(Minecraft.getMinecraft());
        return INSTANCE;
    }

    private final Minecraft mc;
    private FontAPI font;

    private MinecraftLegacy(Minecraft mc) {
        this.mc = mc;
    }

    @Override
    public VertexWrapper getBufferBuilder() {
        return null;
    }

    @Override
    public FontAPI getFont() {
        return null;
    }

    @Override
    public TextAPI<?> getLiteralText(String text) {
        return null;
    }

    @Override
    public RenderAPI getRenderer() {
        return null;
    }

    @Override
    public ResourceAPI getResource() {
        return null;
    }

    @Override
    public TextAPI<?> getTranslatedText(String key, Object... args) {
        return null;
    }

    @Override
    public MinecraftWindow getWindow() {
        return null;
    }

    @Override
    public boolean isCurrentScreen(ScreenAPI<?> screen) {
        return false;
    }
}
