package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.font.FontLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.render.RenderLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.render.VertexWrapperLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.text.TextStringLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.text.TextTranslationLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.util.Objects;

public class MinecraftLegacy implements MinecraftAPI {

    private static MinecraftLegacy INSTANCE;

    public static MinecraftLegacy getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new MinecraftLegacy(Minecraft.getMinecraft());
        return INSTANCE;
    }

    private final Minecraft mc;
    private final FontAPI font;
    private final RenderAPI render;

    private MinecraftLegacy(Minecraft mc) {
        this.mc = mc;
        this.font = new FontLegacy();
        this.render = new RenderLegacy();
    }

    @Override
    public VertexWrapper getBufferBuilderPC(int vertices) {
        return new VertexWrapperLegacy(DefaultVertexFormats.POSITION_COLOR,vertices,3,4);
    }

    @Override
    public VertexWrapper getBufferBuilderPTC(int vertices) {
        return new VertexWrapperLegacy(DefaultVertexFormats.POSITION_TEX_COLOR,vertices,3,2,4);
    }

    @Override
    public FontAPI getFont() {
        return this.font;
    }

    @Override
    public TextAPI<?> getLiteralText(String text) {
        return new TextStringLegacy(text);
    }

    @Override
    public RenderAPI getRenderer() {
        return this.render;
    }

    @Override
    public TextAPI<?> getTranslatedText(String key, Object... args) {
        return new TextTranslationLegacy(key,args);
    }

    /**
     * TODO Cache this?
     */
    @Override
    public MinecraftWindow getWindow() {
        ScaledResolution res = new ScaledResolution(this.mc);
        return new MinecraftWindow(res.getScaledWidth(),res.getScaledHeight(),res.getScaleFactor());
    }

    @Override
    public boolean isCurrentScreen(ScreenAPI<?> screen) {
        return screen.getScreen()==this.mc.currentScreen;
    }
}