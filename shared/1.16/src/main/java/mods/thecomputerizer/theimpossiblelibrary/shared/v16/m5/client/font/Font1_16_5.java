package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.Render1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;

import static net.minecraft.ChatFormatting.RESET;

public class Font1_16_5 extends FontAPI<Font> {
    
    public Font1_16_5() {
        super(mc -> ((Minecraft)mc.unwrap()).font);
    }
    
    protected void draw(@Nullable RenderAPI renderer, @Nullable String text, BiConsumer<PoseStack,String> drawFunc) {
        if(Objects.isNull(renderer)) return;
        renderer.setFont(this.wrapped);
        PoseStack matrix = getMatrix(renderer);
        if(Objects.nonNull(matrix) && Objects.nonNull(text)) drawFunc.accept(matrix, text);
    }
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        draw(renderer,text,(matrix,t) -> getWrapped().draw(matrix,t,x,y,color));
    }
    
    @Override public void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light) {
        if(text instanceof Component && matrix instanceof Matrix4f && source instanceof MultiBufferSource)
            getWrapped().drawInBatch((Component)text,x,y,color,shadow,(Matrix4f)matrix,(MultiBufferSource)source,
                                     transparent,bgColor,light);
    }
    
    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        draw(renderer,text,(matrix,t) -> getWrapped().drawShadow(matrix,t,x,y,color));
    }
    
    @Override public int getCharWidth(char c) {
        return getStringWidth(""+c);
    }
    
    @Override public int getFontHeight() {
        return getWrapped().lineHeight;
    }
    
    protected PoseStack getMatrix(RenderAPI renderer) {
        return renderer instanceof Render1_16_5 ? ((Render1_16_5)renderer).getMatrix() : null;
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