package mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.render.Render1_20;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.ChatFormatting.RESET;
import static net.minecraft.client.gui.Font.DisplayMode.NORMAL;

public class Font1_20 extends FontAPI<Font> {
    
    public Font1_20() {
        super(mc -> ((Minecraft)mc.unwrap()).font);
    }
    
    protected void draw(@Nullable GuiGraphics graphics, String text, int x, int y, int color, boolean shadow) {
        if(Objects.nonNull(graphics) && Objects.nonNull(text)) graphics.drawString(getWrapped(),text,x,y,color,shadow);
    }
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        draw(getGraphics(renderer),text,(int)x,(int)y,color,false);
    }
    
    @Override public void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light) {
        if(text instanceof FormattedCharSequence chars && matrix instanceof Matrix4f mat4f &&
           source instanceof MultiBufferSource buffer)
            getWrapped().drawInBatch(chars,x,y,color,shadow,mat4f,buffer,NORMAL,bgColor,light);
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
        return renderer instanceof Render1_20 render20 ? render20.getGraphics() : null;
    }
    
    @Override public int getStringWidth(String str) {
        return getStringWidth(str,Font::width);
    }
    
    @Override public String trimStringTo(String str, int width, boolean withReset) {
        if(Objects.isNull(str)) return "";
        String trimmed = getWrapped().plainSubstrByWidth(str,width);
        String reset = RESET.toString();
        return !withReset && trimmed.endsWith(reset) ? trimmed.substring(0,trimmed.length()-reset.length()) : trimmed;
    }
}