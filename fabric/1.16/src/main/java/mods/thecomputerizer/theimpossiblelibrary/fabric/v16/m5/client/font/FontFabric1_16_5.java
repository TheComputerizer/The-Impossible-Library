package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.font;

import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.render.RenderFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font.Font1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static net.minecraft.ChatFormatting.RESET;

public class FontFabric1_16_5 extends Font1_16_5 {
    
    private Font font;
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().draw(getMatrix(renderer),text,x,y,color);
    }
    
    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().drawShadow(getMatrix(renderer),text,x,y,color);
    }
    
    private Font getFont() {
        if(Objects.isNull(this.font)) this.font = Minecraft.getInstance().font;
        return this.font;
    }
    
    @Override public int getFontHeight() {
        return getFont().lineHeight;
    }
    
    private PoseStack getMatrix(RenderAPI renderer) {
        return ((RenderFabric1_16_5)renderer).getMatrix();
    }
    
    @Override public int getStringWidth(String str) {
        return getFont().width(str);
    }
    @Override public void renderToolTip(RenderAPI renderer, Collection<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        List<MutableComponent> components = new ArrayList<>();
        for(TextAPI<?> text : lines) components.add(text.getAsComponent());
        FabricHelper.renderTooltip(getMatrix(renderer),components,x,y,width,height,maxWidth,getFont());
    }
    
    @Override public String trimStringTo(String str, int width, boolean withReset) {
        String trimmed = getFont().plainSubstrByWidth(str,width);
        String reset = RESET.toString();
        return !withReset && trimmed.endsWith(reset) ? trimmed.substring(0,trimmed.length()-reset.length()) : trimmed;
    }
}
