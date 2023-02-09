package mods.thecomputerizer.theimpossiblelibrary.util.client;

import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.vecmath.*;
import java.util.ArrayList;
import java.util.List;

public class GuiUtil {
    public static final int WHITE = makeRGBAInt(255,255,255,255);

    /**
        Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
        zLevel can be accessed from any class that extends GuiScreen
     */
    public static void setBuffer(Point2i pos1In, Point2i pos2In, Point2i pos1Out, Point2i pos2Out,
                                 float zLevel, Point4i color) {
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
    public static void bufferSquareTexture(Tuple2i center, float radius, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GuiScreen.drawModalRectWithCustomSizedTexture((int) (center.x - radius / 2f), (int) (center.y - radius / 2f),
                radius, radius, (int) radius, (int) radius, radius, radius);
        GlStateManager.color(1f, 1f, 1f, 1f);
    }

    public static void drawColoredRing(Point2i center, Tuple2i radii, Point4i color, int resolution,
                                       float zLevel) {
        Point2f angles = MathUtil.makeAngleTuple(0,1);
        float startAngle = (float) Math.toRadians(angles.x);
        float angleDif = (float) Math.toRadians(angles.y-angles.x);
        for(int i=0;i<resolution;i++) {
            float angle1 = startAngle+(i/(float)resolution)*angleDif;
            float angle2 = startAngle+((i+1)/(float)resolution)*angleDif;
            Point2i pos1In = MathUtil.getVertex(center,(float)radii.x,angle1);
            Point2i pos2In = MathUtil.getVertex(center,(float)radii.x,angle2);
            Point2i pos1Out = MathUtil.getVertex(center,(float)radii.y,angle1);
            Point2i pos2Out = MathUtil.getVertex(center,(float)radii.y,angle2);
            setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,color);
        }
    }

    /**
        Draws a colored box with an outline
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxWithOutline(Tuple2i topLeft, int width, int height, Tuple4i color,
                                          Tuple4i outlineColor, float outlineWidth, float zLevel) {
        drawBox(topLeft, width, height, color, zLevel);
        drawBoxOutline(topLeft, width, height, outlineColor, outlineWidth, zLevel);
    }

    /**
       Draws a box using tuple inputs
       The zLevel can be obtained from any GuiScreen or set to 0
    */
    public static void drawBox(Tuple2i topLeft, int width, int height, Tuple4i color, float zLevel) {
        Point2i bottomRight = new Point2i(topLeft.x+width,topLeft.y+height);
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
    public static void drawBoxOutline(Tuple2i topLeft, int width, int height, Tuple4i color, float outlineWidth, float zLevel) {
        Point2i topRight = new Point2i(topLeft.x+width,topLeft.y);
        Point2i bottomRight = new Point2i(topLeft.x+width,topLeft.y+height);
        Point2i bottomLeft = new Point2i(topLeft.x,topLeft.y+height);
        drawLine(topLeft,topRight,color,outlineWidth,zLevel);
        drawLine(topRight,bottomRight,color,outlineWidth,zLevel);
        drawLine(bottomRight,bottomLeft,color,outlineWidth,zLevel);
        drawLine(bottomLeft,topLeft,color,outlineWidth,zLevel);
    }

    /**
        Draws a colored line between 2 points
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawLine(Tuple2i start, Tuple2i end, Tuple4i color, float width, float zLevel) {
        double angle = MathUtil.getAngle(start, end);
        Point2d start1 = MathUtil.getVertex(MathUtil.enhance(start),width/2d,Math.toRadians(angle+90d));
        Point2d start2 = MathUtil.getVertex(MathUtil.enhance(start),width/2d,Math.toRadians(angle-90d));
        Point2d end1 = MathUtil.getVertex(MathUtil.enhance(end),width/2d,Math.toRadians(angle-90d));
        Point2d end2 = MathUtil.getVertex(MathUtil.enhance(end),width/2d,Math.toRadians(angle+90d));
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
    public static int drawMultiLineString(GuiScreen screen, String original, int left, int right, int top, int spacing,
                                          int lineNums, int pos, int color) {
        if(lineNums<=0) lineNums = Integer.MAX_VALUE;
        if(pos<0) pos = 0;
        List<String> lines = new ArrayList<>();
        String[] words = original.split(" ");
        StringBuilder builder = new StringBuilder();
        int lineWidth = 0;
        int linePos = 0;
        int lineCounter = 0;
        for(String word : words) {
            if (lineWidth == 0) {
                builder.append(word);
                lineWidth += screen.mc.fontRenderer.getStringWidth(word);
            } else {
                String withSpace = " " + word;
                int textWidth = screen.mc.fontRenderer.getStringWidth(withSpace);
                if ((left + lineWidth + textWidth) < right) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    if (linePos < pos) linePos++;
                    else {
                        lines.add(builder.toString());
                        lineCounter++;
                        if (lineCounter >= lineNums) {
                            builder = new StringBuilder();
                            break;
                        }
                    }
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = screen.mc.fontRenderer.getStringWidth(word);
                }
            }
        }
        if(builder.length()>0) lines.add(builder.toString());
        for(String line : lines) {
            screen.drawString(screen.mc.fontRenderer,line,left,top, color);
            top+=spacing;
        }
        return top;
    }

    /**
        Returns the total number of lines a string would be if it was split
     */
    public static int howManyLinesWillThisBe(GuiScreen screen, String original, int left, int right, int top, int spacing) {
        List<String> lines = new ArrayList<>();
        String[] words = original.split(" ");
        StringBuilder builder = new StringBuilder();
        int lineWidth = 0;
        int linePos = 0;
        int lineCounter = 0;
        for(String word : words) {
            if (lineWidth == 0) {
                builder.append(word);
                lineWidth += screen.mc.fontRenderer.getStringWidth(word);
            } else {
                String withSpace = " " + word;
                int textWidth = screen.mc.fontRenderer.getStringWidth(withSpace);
                if ((left + lineWidth + textWidth) < right) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    lines.add(builder.toString());
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = screen.mc.fontRenderer.getStringWidth(word);
                }
            }
            lineCounter++;
        }
        lines.add(builder.toString());
        return lines.size();
    }

    /**
        Primes the GLStateManager to draw a solid color
     */
    public static void GLColorStart(Tuple4i color) {
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
    public static BufferBuilder tupleColor(BufferBuilder builder, Tuple4i colors) {
        return builder.color(colors.x, colors.y, colors.z, colors.w);
    }

    /**
        Utilizes a tuple to set the color the GLStateManager
     */
    public static void tupleColor(Tuple4i colors) {
        float r = ((float)colors.x)/255f;
        float g = ((float)colors.y)/255f;
        float b = ((float)colors.z)/255f;
        float a = ((float)colors.w)/255f;
        GlStateManager.color(r, g, b, a);
    }

    /**
        Reverses a color tuple which
        This is generally used to set an opposite hover color
     */
    public static Point4i reverseColors(Tuple4i colors) {
        return new Point4i(Math.abs(colors.x-255), Math.abs(colors.y-255), Math.abs(colors.z-255), colors.w);
    }

    /**
        Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Tuple4i colors) {
        return makeRGBAInt(colors.x,colors.y,colors.z,colors.w);
    }

    /**
        Converts rgba integers into a single color integer
     */
    public static int makeRGBAInt(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((b & 0xFF) << 8) | (g & 0xFF);
    }
}
