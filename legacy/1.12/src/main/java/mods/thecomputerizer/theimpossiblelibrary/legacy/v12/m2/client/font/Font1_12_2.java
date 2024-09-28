package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderTooltipEvent.Color;
import net.minecraftforge.client.event.RenderTooltipEvent.PostBackground;
import net.minecraftforge.client.event.RenderTooltipEvent.PostText;
import net.minecraftforge.client.event.RenderTooltipEvent.Pre;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static net.minecraft.item.ItemStack.EMPTY;
import static net.minecraft.util.text.TextFormatting.RESET;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class Font1_12_2 implements FontAPI {

    private FontRenderer font;

    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().drawString(text,x,y,color,false);
    }

    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().drawStringWithShadow(text,x,y,color);
    }

    private FontRenderer getFont() {
        if(Objects.isNull(this.font)) this.font = Minecraft.getMinecraft().fontRenderer;
        return this.font;
    }

    @Override public int getCharWidth(char c) {
        return getFont().getCharWidth(c);
    }

    @Override public int getFontHeight() {
        return getFont().FONT_HEIGHT;
    }

    @Override public int getStringWidth(String str) {
        return getFont().getStringWidth(str);
    }
    
    /**
     * GuiUtils$drawHoveringText implementation from Forge but without disabling lighting
     */
    @Override public void renderToolTip(RenderAPI renderer, Collection<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        List<String> textLines = new ArrayList<>();
        for(TextAPI<?> text : lines) {
            String asLine = text.getApplied();
            if(StringUtils.isNotBlank(asLine)) textLines.add(asLine);
        }
        if(!textLines.isEmpty()) {
            FontRenderer font = getFont();
            Pre event = new Pre(EMPTY,textLines,x,y,width,height,maxWidth,font);
            if(EVENT_BUS.post(event)) return;
            x = event.getX();
            y = event.getY();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableDepth();
            int tooltipTextWidth = 0;
            for(String textLine : textLines) {
                int textLineWidth = font.getStringWidth(textLine);
                if(textLineWidth>tooltipTextWidth) tooltipTextWidth = textLineWidth;
            }
            boolean needsWrap = false;
            int titleLinesCount = 1;
            int tooltipX = x+12;
            if(tooltipX+tooltipTextWidth+4>width) {
                tooltipX = x-16-tooltipTextWidth;
                if(tooltipX<4) {// if the tooltip doesn't fit on the screen
                    if(x>width/2) tooltipTextWidth = x-12-8;
                    else tooltipTextWidth = width-16-x;
                    needsWrap = true;
                }
            }
            if(needsWrap) {
                int wrappedTooltipWidth = 0;
                List<String> wrappedTextLines = new ArrayList<>();
                for(int i = 0;i<textLines.size();i++) {
                    String textLine = textLines.get(i);
                    List<String> wrappedLine = font.listFormattedStringToWidth(textLine,tooltipTextWidth);
                    if(i==0) titleLinesCount = wrappedLine.size();
                    for(String line : wrappedLine) {
                        int lineWidth = font.getStringWidth(line);
                        if(lineWidth>wrappedTooltipWidth) wrappedTooltipWidth = lineWidth;
                        wrappedTextLines.add(line);
                    }
                }
                tooltipTextWidth = wrappedTooltipWidth;
                textLines = wrappedTextLines;
                if(x>width/2) tooltipX = x-16-tooltipTextWidth;
                else tooltipX = x+12;
            }
            int tooltipY = y - 12;
            int tooltipHeight = 8;
            if(textLines.size()>1) {
                tooltipHeight+=(textLines.size()-1)*10;
                if(textLines.size()>titleLinesCount) tooltipHeight+=2; // gap between title lines and next lines
            }
            if(tooltipY<4) tooltipY = 4;
            else if(tooltipY+tooltipHeight+4>height) tooltipY = height-tooltipHeight-4;
            final int zLevel = 300;
            int backgroundColor = 0xF0100010;
            int borderColorStart = 0x505000FF;
            int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
            Color colorEvent = new Color(EMPTY,textLines,tooltipX,tooltipY,font,backgroundColor,borderColorStart,
                                         borderColorEnd);
            EVENT_BUS.post(colorEvent);
            backgroundColor = colorEvent.getBackground();
            borderColorStart = colorEvent.getBorderStart();
            borderColorEnd = colorEvent.getBorderEnd();
            renderTooltipGradient(zLevel,tooltipX-3,tooltipY-4,tooltipX+tooltipTextWidth+3,
                                  tooltipY-3,backgroundColor,backgroundColor);
            renderTooltipGradient(zLevel,tooltipX-3,tooltipY+tooltipHeight+3,tooltipX+tooltipTextWidth+3,
                                  tooltipY+tooltipHeight+4,backgroundColor,backgroundColor);
            renderTooltipGradient(zLevel,tooltipX-3,tooltipY-3,tooltipX+tooltipTextWidth+3,
                                  tooltipY+tooltipHeight+3,backgroundColor,backgroundColor);
            renderTooltipGradient(zLevel,tooltipX-4,tooltipY-3,tooltipX-3,tooltipY+tooltipHeight+3,
                                  backgroundColor,backgroundColor);
            renderTooltipGradient(zLevel,tooltipX+tooltipTextWidth+3,tooltipY-3,tooltipX+tooltipTextWidth+4,
                                  tooltipY+tooltipHeight+3,backgroundColor,backgroundColor);
            renderTooltipGradient(zLevel,tooltipX-3,tooltipY-3+1,tooltipX-3+1,
                                  tooltipY+tooltipHeight+3-1,borderColorStart,borderColorEnd);
            renderTooltipGradient(zLevel,tooltipX+tooltipTextWidth+2,tooltipY-3+1,tooltipX+tooltipTextWidth+3,
                                  tooltipY+tooltipHeight+3-1,borderColorStart,borderColorEnd);
            renderTooltipGradient(zLevel,tooltipX-3,tooltipY-3,tooltipX+tooltipTextWidth+3,
                                  tooltipY-3+1,borderColorStart,borderColorStart);
            renderTooltipGradient(zLevel,tooltipX-3,tooltipY+tooltipHeight+2,tooltipX+tooltipTextWidth+3,
                                  tooltipY+tooltipHeight+3,borderColorEnd,borderColorEnd);
            EVENT_BUS.post(new PostBackground(EMPTY,textLines,tooltipX,tooltipY,font,tooltipTextWidth,tooltipHeight));
            int tooltipTop = tooltipY;
            for(int lineNumber = 0;lineNumber<textLines.size(); lineNumber++) {
                String line = textLines.get(lineNumber);
                font.drawStringWithShadow(line,(float)tooltipX,(float)tooltipY,-1);
                if(lineNumber+1==titleLinesCount) tooltipY+=2;
                tooltipY+=10;
            }
            EVENT_BUS.post(new PostText(EMPTY,textLines,tooltipX,tooltipTop,font,tooltipTextWidth,tooltipHeight));
            GlStateManager.enableDepth();
            GlStateManager.enableRescaleNormal();
        }
    }
    
    @SuppressWarnings("SameParameterValue")
    private void renderTooltipGradient(int z, int left, int top, int right, int bottom, int colorStart, int colorEnd) {
        GuiUtils.drawGradientRect(z,left,top,right,bottom,colorStart,colorEnd);
    }
    
    @Override public String trimStringTo(String str, int width, boolean withReset) {
        String trimmed = getFont().trimStringToWidth(str,width);
        String reset = RESET.toString();
        return !withReset && trimmed.endsWith(reset) ? trimmed.substring(0,trimmed.length()-reset.length()) : trimmed;
    }
}
