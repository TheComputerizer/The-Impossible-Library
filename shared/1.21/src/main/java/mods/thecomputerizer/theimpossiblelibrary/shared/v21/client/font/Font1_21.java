package mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.render.Render1_21;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.ChatFormatting.RESET;
import static net.minecraft.client.gui.Font.DisplayMode.NORMAL;

public class Font1_21 extends FontAPI<Font> {
    
    public Font1_21() {
        super(mc -> ((Minecraft)mc.unwrap()).font);
    }
    
    protected void draw(@Nullable GuiGraphics graphics, String text, int x, int y, int color, boolean shadow) {
        if(Objects.nonNull(graphics)) graphics.drawString(getWrapped(),text,x,y,color,shadow);
    }
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        draw(getGraphics(renderer),text,(int)x,(int)y,color,false);
    }
    
    @Override public void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light) {
        getWrapped().drawInBatch((FormattedCharSequence)text,x,y,color,shadow,(Matrix4f)matrix,
                                 (MultiBufferSource)source,NORMAL,bgColor,light);
    }
    
    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        draw(getGraphics(renderer),text,(int)x,(int)y,color,true);
    }
    
    @Override public int getCharWidth(char c) {
        return getStringWidth(""+c);
    }
    
    @Override public int getFontHeight() {
        return getWrapped().lineHeight;
    }
    
    protected @Nullable GuiGraphics getGraphics(RenderAPI renderer) {
        return ((Render1_21)renderer).getGraphics();
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