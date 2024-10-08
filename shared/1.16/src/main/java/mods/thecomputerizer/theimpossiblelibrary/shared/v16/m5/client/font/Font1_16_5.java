package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.Objects;

import static net.minecraft.util.text.TextFormatting.RESET;

public abstract class Font1_16_5 implements FontAPI {
    
    private FontRenderer font;
    
    @Override public void draw(RenderAPI<?> renderer, String text, float x, float y, int color) {
        getFont().draw(getMatrix(renderer),text,x,y,color);
    }
    
    @Override public void drawWithShadow(RenderAPI<?> renderer, String text, float x, float y, int color) {
        getFont().drawShadow(getMatrix(renderer),text,x,y,color);
    }
    
    @Override public int getCharWidth(char c) {
        return getStringWidth(""+c);
    }
    
    protected FontRenderer getFont() {
        if(Objects.isNull(this.font)) this.font = Minecraft.getInstance().font;
        return this.font;
    }
    
    @Override public int getFontHeight() {
        return getFont().lineHeight;
    }
    
    protected MatrixStack getMatrix(RenderAPI<?> renderer) {
        return (MatrixStack)renderer.getMatrix();
    }
    
    @Override public int getStringWidth(String str) {
        return getFont().width(str);
    }
    
    @Override public String trimStringTo(String str, int width, boolean withReset) {
        String trimmed = getFont().plainSubstrByWidth(str,width);
        String reset = RESET.toString();
        return !withReset && trimmed.endsWith(reset) ? trimmed.substring(0,trimmed.length()-reset.length()) : trimmed;
    }
}