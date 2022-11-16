package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.vecmath.*;

public class GuiUtil {
    public static final int WHITE = makeRGBAInt(255,255,255,255);

    /*
        Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
        zLevel can be accessed from any class that extends GuiScreen
     */
    public static void setBuffer(BufferBuilder builder, Point2i pos1In, Point2i pos2In, Point2i pos1Out, Point2i pos2Out,
                                 float zLevel, Point4i colors) {
        tupleColor(builder.pos(pos1Out.x, pos1Out.y, zLevel),colors).endVertex();
        tupleColor(builder.pos(pos1In.x, pos1In.y, zLevel),colors).endVertex();
        tupleColor(builder.pos(pos2In.x, pos2In.y, zLevel),colors).endVertex();
        tupleColor(builder.pos(pos2Out.x, pos2Out.y, zLevel),colors).endVertex();
    }

    /*
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

    /*
        Draws a colored box with an outline
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxWithOutline(Tuple2i topLeft, int width, int height, Tuple4i color,
                                          Tuple4i outlineColor, float zLevel) {
        drawBox(topLeft, width, height, color, zLevel);
        drawBoxOutline(topLeft, width, height, color, zLevel);
    }

    /*
       Draws a box using tuple inputs
       The zLevel can be obtained from any GuiScreen or set to 0
    */
    public static void drawBox(Tuple2i topLeft, int width, int height, Tuple4i color, float zLevel) {
        Point2i bottomRight = new Point2i(topLeft.x+width,topLeft.y+height);
        GLColorStart(color);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        builder.pos(topLeft.x, topLeft.y, zLevel).endVertex();
        builder.pos(topLeft.x, bottomRight.y, zLevel).endVertex();
        builder.pos(bottomRight.x, bottomRight.y, zLevel).endVertex();
        builder.pos(bottomRight.x, topLeft.y, zLevel).endVertex();
        tessellator.draw();
        GLColorFinish();
    }

    /*
        Draws the outline of a box
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxOutline(Tuple2i topLeft, int width, int height, Tuple4i color, float zLevel) {
        Point2i topRight = new Point2i(topLeft.x+width,topLeft.y);
        Point2i bottomRight = new Point2i(topLeft.x+width,topLeft.y+height);
        Point2i bottomLeft = new Point2i(topLeft.x,topLeft.y+height);
        drawLine(topLeft,topRight,color,zLevel);
        drawLine(topRight,bottomRight,color,zLevel);
        drawLine(bottomRight,bottomLeft,color,zLevel);
        drawLine(bottomLeft,topLeft,color,zLevel);
    }

    /*
        Draws a colored line between 2 points
        The zLevel can be obtained from any GuiScreen or set to 0
     */
    public static void drawLine(Tuple2i start, Tuple2i end, Tuple4i color, float zLevel) {
        GLColorStart(color);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        builder.pos(start.x, start.y, zLevel).endVertex();
        builder.pos(end.x, end.y, zLevel).endVertex();
        tessellator.draw();
        GLColorFinish();
    }

    /*
        Primes the GLStateManager to draw a solid color
     */
    public static void GLColorStart(Tuple4i color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        tupleColor(color);
    }

    /*
        Resets some necessary GLStateManager stuff so other rendering works as intended
     */
    public static void GLColorFinish() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /*
        Utilizes a tuple to set the color for a BufferBuilder
     */
    public static BufferBuilder tupleColor(BufferBuilder builder, Tuple4i colors) {
        return builder.color(colors.x, colors.y, colors.z, colors.w);
    }

    /*
        Utilizes a tuple to set the color the GLStateManager
     */
    public static void tupleColor(Tuple4i colors) {
        GlStateManager.color(colors.x, colors.y, colors.z, colors.w);
    }

    /*
        Reverses a color tuple which
        This is generally used to set an opposite hover color
     */
    public static Point4i reverseColors(Tuple4i colors) {
        return new Point4i(Math.abs(colors.x-255), Math.abs(colors.y-255), Math.abs(colors.z-255), colors.w);
    }

    /*
        Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Tuple4i colors) {
        return makeRGBAInt(colors.x,colors.y,colors.z,colors.w);
    }

    /*
        Converts rgba integers into a single color integer
     */
    public static int makeRGBAInt(int r, int g, int b, int a) {
        return ((r & 0xFF) << 24) | ((g & 0xFF) << 16) | ((b & 0xFF) << 8) | (a & 0xFF);
    }
}
