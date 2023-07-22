package mods.thecomputerizer.theimpossiblelibrary.util.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiUtil {
    public static final int WHITE = makeRGBAInt(255,255,255,255);

    /**
        Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
        offset can be accessed from any class that extends GuiScreen
     */
    public static void setBuffer(Vector2f pos1In, Vector2f pos2In, Vector2f pos1Out, Vector2f pos2Out,
                                 float offset, Vector4f colors) {
        GLColorStart(colors);
        BufferBuilder builder = Tessellator.getInstance().getBuilder();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        vectorColor(builder.vertex(pos1Out.x, pos1Out.y, offset),colors).endVertex();
        vectorColor(builder.vertex(pos1In.x, pos1In.y, offset),colors).endVertex();
        vectorColor(builder.vertex(pos2In.x, pos2In.y, offset),colors).endVertex();
        vectorColor(builder.vertex(pos2Out.x, pos2Out.y, offset),colors).endVertex();
        Tessellator.getInstance().end();
        GLColorFinish();
    }

    /**
        Pushes a generic texture for rendering via a ResourceLocation
        Make sure to pass in a valid ResourceLocation since that does not get checked here
     */
    public static void bufferSquareTexture(MatrixStack matrix, Vector2f center, float radius, ResourceLocation texture) {
        Minecraft.getInstance().getTextureManager().bind(texture);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        AbstractGui.blit(matrix,(int) (center.x - radius / 2f), (int) (center.y - radius / 2f),
                0, 0, (int) radius, (int) radius, (int) radius, (int) radius);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
    }

    public static void drawColoredRing(Vector2f center, Vector2f radii, Vector4f color, int resolution,
                                       float offset) {
        Vector2f angles = MathUtil.makeAngleVector(0,1);
        float startAngle = (float) Math.toRadians(angles.x);
        float angleDif = (float) Math.toRadians(angles.y-angles.x);
        for(int i=0;i<resolution;i++) {
            float angle1 = startAngle+(i/(float)resolution)*angleDif;
            float angle2 = startAngle+((i+1)/(float)resolution)*angleDif;
            Vector2f pos1In = MathUtil.getVertex(center, radii.x,angle1);
            Vector2f pos2In = MathUtil.getVertex(center, radii.x,angle2);
            Vector2f pos1Out = MathUtil.getVertex(center, radii.y,angle1);
            Vector2f pos2Out = MathUtil.getVertex(center, radii.y,angle2);
            setBuffer(pos1In,pos2In,pos1Out,pos2Out,offset,color);
        }
    }

    /**
        Draws a colored box with an outline
        The offset can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxWithOutline(Vector2f topLeft, int width, int height, Vector4f color,
                                          Vector4f outlineColor, float outlineWidth, float offset) {
        drawBox(topLeft, width, height, color, offset);
        drawBoxOutline(topLeft, width, height, outlineColor, outlineWidth, offset);
    }

    /**
       Draws a box using tuple inputs
       The offset can be obtained from any GuiScreen or set to 0
    */
    public static void drawBox(Vector2f topLeft, int width, int height, Vector4f colors, float offset) {
        Vector2f bottomRight = new Vector2f(topLeft.x+width,topLeft.y+height);
        GLColorStart(colors);
        BufferBuilder builder = Tessellator.getInstance().getBuilder();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        vectorColor(builder.vertex(topLeft.x, topLeft.y, offset),colors).endVertex();
        vectorColor(builder.vertex(topLeft.x, bottomRight.y, offset),colors).endVertex();
        vectorColor(builder.vertex(bottomRight.x, bottomRight.y, offset),colors).endVertex();
        vectorColor(builder.vertex(bottomRight.x, topLeft.y, offset),colors).endVertex();
        Tessellator.getInstance().end();
        GLColorFinish();
    }

    /**
        Draws the outline of a box
        The offset can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxOutline(Vector2f topLeft, int width, int height, Vector4f color, float outlineWidth, float offset) {
        Vector2f topRight = new Vector2f(topLeft.x+width,topLeft.y);
        Vector2f bottomRight = new Vector2f(topLeft.x+width,topLeft.y+height);
        Vector2f bottomLeft = new Vector2f(topLeft.x,topLeft.y+height);
        drawLine(topLeft,topRight,color,outlineWidth,offset);
        drawLine(topRight,bottomRight,color,outlineWidth,offset);
        drawLine(bottomRight,bottomLeft,color,outlineWidth,offset);
        drawLine(bottomLeft,topLeft,color,outlineWidth,offset);
    }

    /**
        Draws a colored line between 2 points
        The offset can be obtained from any GuiScreen or set to 0
     */
    public static void drawLine(Vector2f start, Vector2f end, Vector4f colors, float width, float offset) {
        double angle = MathUtil.getAngle(start, end);
        Vector2f start1 = MathUtil.getVertex(start,width/2d,Math.toRadians(angle+90d));
        Vector2f start2 = MathUtil.getVertex(start,width/2d,Math.toRadians(angle-90d));
        Vector2f end1 = MathUtil.getVertex(end,width/2d,Math.toRadians(angle-90d));
        Vector2f end2 = MathUtil.getVertex(end,width/2d,Math.toRadians(angle+90d));
        GLColorStart(colors);
        BufferBuilder builder = Tessellator.getInstance().getBuilder();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        vectorColor(builder.vertex(start1.x, start1.y, offset),colors).endVertex();
        vectorColor(builder.vertex(start2.x, start2.y, offset),colors).endVertex();
        vectorColor(builder.vertex(end1.x, end1.y, offset),colors).endVertex();
        vectorColor(builder.vertex(end2.x, end2.y, offset),colors).endVertex();
        Tessellator.getInstance().end();
        GLColorFinish();
    }

    /**
     Splits a string into multiple lines and renders them
     Use lineNums and pos to cap the number of lines that can be rendered and which line to start rendering on
     Returns the y position after the rendered lines
     */
    @SuppressWarnings("UnusedReturnValue")
    public static int drawMultiLineString(MatrixStack matrix, FontRenderer font, String original, int left, int right, int top, int spacing,
                                          int lineNums, int pos, int color) {
        if(lineNums<=0) lineNums = Integer.MAX_VALUE;
        if(pos<0) pos = 0;
        int index = 0;
        for(String line : splitLines(font, original, left, right)) {
            if(index>=pos) {
                font.drawShadow(matrix,line,left,top,color);
                top += spacing;
                lineNums--;
                if (lineNums <= 0) break;
            }
            index++;
        }
        return top;
    }

    /**
     * Splits a string into a list of lines
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
                lineWidth += font.width(word);
            } else {
                String withSpace = " " + word;
                int textWidth = font.width(withSpace);
                if ((left + lineWidth + textWidth) < right) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    lines.add(builder.toString());
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = font.width(word);
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
    public static void drawMultiLineTitle(MatrixStack matrix, MainWindow res, String text, String subText, boolean centeredText, int x,
                                          int y, float scaleX, float scaleY, float subScale, String color,
                                          String subColor, float opacity, float subOpacity, int lineSpacing) {
        FontRenderer font = Minecraft.getInstance().font;
        List<String> textLines = new ArrayList<>();
        List<String> subLines = new ArrayList<>();
        TextFormatting textFormat = TextFormatting.getByName(color);
        if(Objects.isNull(textFormat)) textFormat = TextFormatting.RED;
        TextFormatting subFormat = TextFormatting.getByName(subColor);
        if(Objects.isNull(subFormat)) subFormat = TextFormatting.WHITE;
        String[] words = text.split(" ");
        String[] subWords = subText.split(" ");
        StringBuilder builder = new StringBuilder();
        x = x>=0 ? x : res.getGuiScaledWidth()/2;
        y = y>=0 ? y : res.getGuiScaledHeight()/2;
        int left = centeredText ? 0 : x;
        int lineWidth = 0;
        int lineCounter = 0;
        for(String word : words) {
            if (lineWidth == 0) {
                builder.append(word);
                lineWidth += font.width(word);
            } else {
                String withSpace = " " + word;
                int textWidth = font.width(withSpace);
                if ((left + lineWidth + textWidth) < res.getGuiScaledWidth()) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    textLines.add(builder.toString());
                    lineCounter++;
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = font.width(word);
                }
            }
        }
        if(builder.length()>0) textLines.add(builder.toString());
        matrix.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableAlphaTest();
        matrix.scale(scaleX, scaleY, 1f);
        for(String line : textLines) {
            if(centeredText)
                font.drawShadow(matrix,line,(x/scaleX)-((float)font.width(line))/2,
                        (float)y/scaleY,FontUtil.convertTextFormatting(textFormat,(int)(255f*opacity)));
            else font.drawShadow(matrix,line,x/scaleX,y/scaleY,FontUtil.convertTextFormatting(textFormat,(int)(255f*opacity)));
            y+=lineSpacing*5;
        }
        matrix.popPose();
        builder = new StringBuilder();
        lineWidth = 0;
        lineCounter = 0;
        for(String word : subWords) {
            if (lineWidth == 0) {
                builder.append(word);
                lineWidth += font.width(word);
            } else {
                String withSpace = " " + word;
                int textWidth = font.width(withSpace);
                if ((left + lineWidth + textWidth) < res.getGuiScaledWidth()) {
                    builder.append(withSpace);
                    lineWidth += textWidth;
                } else {
                    subLines.add(builder.toString());
                    lineCounter++;
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = font.width(word);
                }
            }
        }
        if(builder.length()>0) subLines.add(builder.toString());
        float subScaleX = scaleX*subScale;
        float subScaleY = scaleY*subScale;
        matrix.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableAlphaTest();
        matrix.scale(subScaleX, subScaleY, 1f);
        for(String line : subLines) {
            if(centeredText)
                font.drawShadow(matrix,line,(x/subScaleX)-((float)font.width(line))/2,
                        (float)y/subScaleY,FontUtil.convertTextFormatting(subFormat,(int)(255f*subOpacity)));
            else font.drawShadow(matrix,line,x/subScaleX,y/subScaleY,FontUtil.convertTextFormatting(subFormat,(int)(255f*subOpacity)));
            y+=lineSpacing;
        }
        matrix.popPose();
    }

    /**
        Primes the GLStateManager to draw a solid color
     */
    public static void GLColorStart(Vector4f color) {
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.defaultBlendFunc();
        vectorColor(color);
    }

    /**
        Resets some necessary GLStateManager stuff so other rendering works as intended
     */
    public static void GLColorFinish() {
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /**
        Utilizes a tuple to set the color for a BufferBuilder
     */
    public static IVertexBuilder vectorColor(IVertexBuilder builder, Vector4f colors) {
        return builder.color(colors.x(), colors.y(), colors.z(), colors.w());
    }

    /**
        Utilizes a vector to set the color the GLStateManager
     */
    public static void vectorColor(Vector4f colors) {
        float r = colors.x()/255f;
        float g = colors.y()/255f;
        float b = colors.z()/255f;
        float a = colors.w()/255f;
        RenderSystem.color4f(r, g, b, a);
    }

    /**
        Reverses a color vector
        This is generally used to set an opposite hover color
     */
    public static Vector4f reverseColors(Vector4f colors) {
        return new Vector4f(Math.abs(colors.x()-255), Math.abs(colors.y()-255), Math.abs(colors.z()-255), colors.w());
    }

    /**
        Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Vector4f colors) {
        return makeRGBAInt((int)colors.x(),(int)colors.y(),(int)colors.z(),(int)colors.w());
    }

    /**
        Converts rgba integers into a single color integer
     */
    public static int makeRGBAInt(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((b & 0xFF) << 8) | (g & 0xFF);
    }
}
