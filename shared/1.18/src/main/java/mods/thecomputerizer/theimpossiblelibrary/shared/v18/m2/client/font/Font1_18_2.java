package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.font;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;

import static net.minecraft.ChatFormatting.RESET;

public class Font1_18_2 extends FontAPI<Font> {
    
    public Font1_18_2() {
        super(mc -> ((Minecraft)mc.unwrap()).font);
    }
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        renderer.setFont(this.wrapped);
        getWrapped().draw(getMatrix(renderer),text,x,y,color);
    }
    
    @Override public void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light) {
        getWrapped().drawInBatch((FormattedCharSequence)text,x,y,color,shadow,(Matrix4f)matrix,
                                 (MultiBufferSource)source,transparent,bgColor,light);
    }
    
    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        renderer.setFont(this.wrapped);
        getWrapped().drawShadow(getMatrix(renderer),text,x,y,color);
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