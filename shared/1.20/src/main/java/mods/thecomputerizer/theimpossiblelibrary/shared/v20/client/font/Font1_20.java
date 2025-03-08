package mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.font;

import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

import static net.minecraft.ChatFormatting.RESET;
import static net.minecraft.client.gui.Font.DisplayMode.NORMAL;

public class Font1_20 extends FontAPI<Font> {
    
    public Font1_20() {
        super(mc -> ((Minecraft)mc.unwrap()).font);
    }
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        getWrapped().draw(getMatrix(renderer),text,x,y,14737632);
    }
    
    @Override public void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light) {
        getWrapped().drawInBatch((FormattedCharSequence)text,x,y,color,shadow,(Matrix4f)matrix,
                                 (MultiBufferSource)source,NORMAL,bgColor,light);
    }
    
    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        getWrapped().drawShadow(getMatrix(renderer),text,x,y,14737632);
    }
    
    @Override public int getCharWidth(char c) {
        return getStringWidth(""+c);
    }
    
    @Override public int getFontHeight() {
        return getWrapped().lineHeight;
    }
    
    protected PoseStack getMatrix(RenderAPI renderer) {
        return (PoseStack)renderer.getMatrix();
    }
    
    @Override public int getStringWidth(String str) {
        return getWrapped().width(str);
    }
    
    @Override public String trimStringTo(String str, int width, boolean withReset) {
        String trimmed = getWrapped().plainSubstrByWidth(str, width);
        String reset = RESET.toString();
        return !withReset && trimmed.endsWith(reset) ? trimmed.substring(0,trimmed.length()-reset.length()) : trimmed;
    }
}