package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.minecraft.util.text.TextFormatting.RESET;

public class Font1_16_5 extends FontAPI<FontRenderer> {
    
    public Font1_16_5() {
        super(mc -> ((Minecraft)mc.unwrap()).font);
    }
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        renderer.setFont(this.wrapped);
        getWrapped().draw(getMatrix(renderer),text,x,y,color);
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
    
    protected MatrixStack getMatrix(RenderAPI renderer) {
        return (MatrixStack)renderer.getMatrix();
    }
    
    @Override public int getStringWidth(String str) {
        return getWrapped().width(str);
    }
    
    @Override public void renderToolTip(RenderAPI renderer, Collection<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        List<ITextComponent> components = new ArrayList<>();
        for(TextAPI<?> text : lines) components.add(text.getAsComponent());
        renderer.setFont(this.wrapped);
        GuiUtils.drawHoveringText(getMatrix(renderer), components, x, y, width, height, maxWidth, getWrapped());
    }
    
    @Override public String trimStringTo(String str, int width, boolean withReset) {
        String trimmed = getWrapped().plainSubstrByWidth(str, width);
        String reset = RESET.toString();
        return !withReset && trimmed.endsWith(reset) ? trimmed.substring(0,trimmed.length()-reset.length()) : trimmed;
    }
}