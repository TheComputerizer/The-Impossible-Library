package mods.thecomputerizer.theimpossiblelibrary.util.client;

import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import javax.vecmath.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiUtil {
    public static final int WHITE = makeRGBAInt(255,255,255,255);

    /**
        Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
        zLevel can be accessed from any class that extends GuiScreen
     */
    public static void setBuffer(Vec2f pos1In, Vec2f pos2In, Vec2f pos1Out, Vec2f pos2Out,
                                 float zLevel, Tuple4f color) {
        GLColorStart(color);
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        tupleColor(builder.pos(pos1Out.x, pos1Out.y, zLevel),color).endVertex();
        tupleColor(builder.pos(pos1In.x, pos1In.y, zLevel),color).endVertex();
        tupleColor(builder.pos(pos2In.x, pos2In.y, zLevel),color).endVertex();
        tupleColor(builder.pos(pos2Out.x, pos2Out.y, zLevel),color).endVertex();
        Tessellator.getInstance().draw();
        GLColorFinish();
    }

    /**
        Pushes a generic texture for rendering via a ResourceLocation
        Make sure to pass in a valid ResourceLocation since that does not get checked here
     */
    public static void bufferSquareTexture(Vec2f center, float radius, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GuiScreen.drawModalRectWithCustomSizedTexture((int) (center.x - radius / 2f), (int) (center.y - radius / 2f),
                radius, radius, (int) radius, (int) radius, radius, radius);
        GlStateManager.color(1f, 1f, 1f, 1f);
    }

    public static void drawColoredRing(Vec2f center, Vec2f radii, Tuple4f color, int resolution,
                                       float zLevel) {
        Vec2f angles = MathUtil.makeAngleTuple(0,1);
        float startAngle = (float) Math.toRadians(angles.x);
        float angleDif = (float) Math.toRadians(angles.y-angles.x);
        for(int i=0;i<resolution;i++) {
            float angle1 = startAngle+(i/(float)resolution)*angleDif;
            float angle2 = startAngle+((i+1)/(float)resolution)*angleDif;
            Vec2f pos1In = MathUtil.getVertex(center, radii.x,angle1);
            Vec2f pos2In = MathUtil.getVertex(center, radii.x,angle2);
            Vec2f pos1Out = MathUtil.getVertex(center, radii.y,angle1);
            Vec2f pos2Out = MathUtil.getVertex(center, radii.y,angle2);
            setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,color);
        }
    }

    /**
        Draws a colored box with an outline
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxWithOutline(Vec2f topLeft, int width, int height, Tuple4f color,
                                          Tuple4f outlineColor, float outlineWidth, float zLevel) {
        drawBox(topLeft, width, height, color, zLevel);
        drawBoxOutline(topLeft, width, height, outlineColor, outlineWidth, zLevel);
    }

    /**
       Draws a box using tuple inputs
       The zLevel can be obtained from any GuiScreen or set to 0
    */
    public static void drawBox(Vec2f topLeft, int width, int height, Tuple4f color, float zLevel) {
        Vec2f bottomRight = new Vec2f(topLeft.x+width,topLeft.y+height);
        GLColorStart(color);
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        tupleColor(builder.pos(topLeft.x, topLeft.y, zLevel),color).endVertex();
        tupleColor(builder.pos(topLeft.x, bottomRight.y, zLevel),color).endVertex();
        tupleColor(builder.pos(bottomRight.x, bottomRight.y, zLevel),color).endVertex();
        tupleColor(builder.pos(bottomRight.x, topLeft.y, zLevel),color).endVertex();
        Tessellator.getInstance().draw();
        GLColorFinish();
    }

    /**
        Draws the outline of a box
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxOutline(Vec2f topLeft, int width, int height, Tuple4f color, float outlineWidth, float zLevel) {
        Vec2f topRight = new Vec2f(topLeft.x+width,topLeft.y);
        Vec2f bottomRight = new Vec2f(topLeft.x+width,topLeft.y+height);
        Vec2f bottomLeft = new Vec2f(topLeft.x,topLeft.y+height);
        drawLine(topLeft,topRight,color,outlineWidth,zLevel);
        drawLine(topRight,bottomRight,color,outlineWidth,zLevel);
        drawLine(bottomRight,bottomLeft,color,outlineWidth,zLevel);
        drawLine(bottomLeft,topLeft,color,outlineWidth,zLevel);
    }

    /**
        Draws a colored line between 2 points
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawLine(Vec2f start, Vec2f end, Tuple4f color, float width, float zLevel) {
        double angle = MathUtil.getAngle(start, end);
        Vec2f start1 = MathUtil.getVertex(MathUtil.enhance(start),width/2d,Math.toRadians(angle+90d));
        Vec2f start2 = MathUtil.getVertex(MathUtil.enhance(start),width/2d,Math.toRadians(angle-90d));
        Vec2f end1 = MathUtil.getVertex(MathUtil.enhance(end),width/2d,Math.toRadians(angle-90d));
        Vec2f end2 = MathUtil.getVertex(MathUtil.enhance(end),width/2d,Math.toRadians(angle+90d));
        GLColorStart(color);
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        tupleColor(builder.pos(start1.x, start1.y, zLevel),color).endVertex();
        tupleColor(builder.pos(start2.x, start2.y, zLevel),color).endVertex();
        tupleColor(builder.pos(end1.x, end1.y, zLevel),color).endVertex();
        tupleColor(builder.pos(end2.x, end2.y, zLevel),color).endVertex();
        Tessellator.getInstance().draw();
        GLColorFinish();
    }

    /**
        Splits a string into multiple lines and renders them
        Use lineNums and pos to cap the number of lines that can be rendered and which line to start rendering on
        Returns the y position after the rendered lines
     */
    @SuppressWarnings("UnusedReturnValue")
    public static int drawMultiLineString(FontRenderer font, String original, int left, int right, int top, int spacing,
                                          int lineNums, int pos, int color) {
        if(lineNums<=0) lineNums = Integer.MAX_VALUE;
        if(pos<0) pos = 0;
        int index = 0;
        for(String line : splitLines(font, original, left, right)) {
            if(index>=pos) {
                font.drawStringWithShadow(line, left, top, color);
                top += spacing;
                lineNums--;
                if (lineNums <= 0) break;
            }
            index++;
        }
        return top;
    }

    /**
     * Splits a string into an array of lines
     */
    public static List<String> splitLines(FontRenderer font, String original, int left, int right) {
        List<String> lines = new ArrayList<>();
        String[] words = original.split(" ");
        StringBuilder builder = new StringBuilder();
        int lineWidth = 0;
        int linePos = 0;
        int lineCounter = 0;
        for(String word : words) {
            if (lineWidth == 0) {
                builder.append(word);
                lineWidth += font.getStringWidth(word);
            } else {
                String withSpace = " " + word;
                int textWidth = font.getStringWidth(withSpace);
                if ((left + lineWidth + textWidth) < right) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    lines.add(builder.toString());
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = font.getStringWidth(word);
                }
            }
            lineCounter++;
        }
        lines.add(builder.toString());
        return lines;
    }

    /**
        Returns the total number of lines a string would be if it was split
     */
    public static int howManyLinesWillThisBe(FontRenderer font, String original, int left, int right) {
        return splitLines(font, original, left, right).size();
    }

    /**
     * Similar to drawMultiLineString except it is assumed this is not being called from within a GUI but rather
     * making use of the Text class.
     */
    public static void drawMultiLineTitle(ScaledResolution res, String text, String subText, boolean centeredText, int x,
                                          int y, float scaleX, float scaleY, float subScale, String color,
                                          String subColor, float opacity, float subOpacity, int lineSpacing) {
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        List<String> textLines = new ArrayList<>();
        List<String> subLines = new ArrayList<>();
        TextFormatting textFormat = TextFormatting.getValueByName(color);
        if(Objects.isNull(textFormat)) textFormat = TextFormatting.RED;
        TextFormatting subFormat = TextFormatting.getValueByName(subColor);
        if(Objects.isNull(subFormat)) subFormat = TextFormatting.WHITE;
        String[] words = text.split(" ");
        String[] subWords = subText.split(" ");
        StringBuilder builder = new StringBuilder();
        x = x>=0 ? x : res.getScaledWidth()/2;
        y = y>=0 ? y : res.getScaledHeight()/2;
        int left = centeredText ? 0 : x;
        int lineWidth = 0;
        int lineCounter = 0;
        for(String word : words) {
            if (lineWidth == 0) {
                builder.append(word);
                lineWidth += font.getStringWidth(word);
            } else {
                String withSpace = " " + word;
                int textWidth = font.getStringWidth(withSpace);
                if ((left + lineWidth + textWidth) < res.getScaledWidth()) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    textLines.add(builder.toString());
                    lineCounter++;
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = font.getStringWidth(word);
                }
            }
        }
        if(builder.length()>0) textLines.add(builder.toString());
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();
        GlStateManager.scale(scaleX, scaleY, 1f);
        for(String line : textLines) {
            if(centeredText)
                font.drawStringWithShadow(line,(x/scaleX)-((float)font.getStringWidth(line))/2,
                        (float)y/scaleY, FontUtil.convertTextFormatting(subFormat,(int)(255f*opacity)));
            else font.drawStringWithShadow(line,x/scaleX,y/scaleY, FontUtil.convertTextFormatting(subFormat,(int)(255f*opacity)));
            y+=lineSpacing*5;
        }
        GlStateManager.popMatrix();
        builder = new StringBuilder();
        lineWidth = 0;
        lineCounter = 0;
        for(String word : subWords) {
            if (lineWidth == 0) {
                builder.append(word);
                lineWidth += font.getStringWidth(word);
            } else {
                String withSpace = " " + word;
                int textWidth = font.getStringWidth(withSpace);
                if ((left + lineWidth + textWidth) < res.getScaledWidth()) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    subLines.add(builder.toString());
                    lineCounter++;
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = font.getStringWidth(word);
                }
            }
        }
        if(builder.length()>0) subLines.add(builder.toString());
        float subScaleX = scaleX*subScale;
        float subScaleY = scaleY*subScale;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();
        GlStateManager.scale(subScaleX, subScaleY, 1f);
        for(String line : subLines) {
            if(centeredText)
                font.drawStringWithShadow(line,(x/subScaleX)-((float)font.getStringWidth(line))/2,
                        (float)y/subScaleY, FontUtil.convertTextFormatting(textFormat,(int)(255f*subOpacity)));
            else font.drawStringWithShadow(line,x/subScaleX,y/subScaleY, FontUtil.convertTextFormatting(textFormat,(int)(255f*subOpacity)));
            y+=lineSpacing;
        }
        GlStateManager.popMatrix();
    }

    /**
        Primes the GLStateManager to draw a solid color
     */
    public static void GLColorStart(Tuple4f color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        tupleColor(color);
    }

    /**
        Resets some necessary GLStateManager stuff so other rendering works as intended
     */
    public static void GLColorFinish() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
        Utilizes a tuple to set the color for a BufferBuilder
     */
    public static BufferBuilder tupleColor(BufferBuilder builder, Tuple4f colors) {
        return builder.color((int)colors.x, (int)colors.y, (int)colors.z, (int)colors.w);
    }

    /**
        Utilizes a tuple to set the color the GLStateManager
     */
    public static void tupleColor(Tuple4f colors) {
        float r = colors.x /255f;
        float g = colors.y /255f;
        float b = colors.z /255f;
        float a = colors.w /255f;
        GlStateManager.color(r, g, b, a);
    }

    /**
        Reverses a color tuple which
        This is generally used to set an opposite hover color
     */
    public static Tuple4f reverseColors(Tuple4f colors) {
        return new Point4f(Math.abs(colors.x-255), Math.abs(colors.y-255), Math.abs(colors.z-255), colors.w);
    }

    /**
        Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Tuple4f colors) {
        return makeRGBAInt((int)colors.x,(int)colors.y,(int)colors.z,(int)colors.w);
    }

    /**
        Converts rgba integers into a single color integer
     */
    public static int makeRGBAInt(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((b & 0xFF) << 8) | (g & 0xFF);
    }
}
