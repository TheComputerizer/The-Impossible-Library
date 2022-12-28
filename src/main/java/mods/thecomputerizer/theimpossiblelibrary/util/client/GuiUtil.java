package mods.thecomputerizer.theimpossiblelibrary.util.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiUtil {
    public static final int WHITE = makeRGBAInt(255,255,255,255);

    /*
        Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
        offset can be accessed from any class that extends GuiScreen
     */
    public static void setBuffer(Vector3f pos1In, Vector3f pos2In, Vector3f pos1Out, Vector3f pos2Out,
                                 float offset, Vector4f color) {
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        GLColorStart(color);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        vectorColor(builder.vertex(pos1Out.x(), pos1Out.y(), offset),color).endVertex();
        vectorColor(builder.vertex(pos1In.x(), pos1In.y(), offset),color).endVertex();
        vectorColor(builder.vertex(pos2In.x(), pos2In.y(), offset),color).endVertex();
        vectorColor(builder.vertex(pos2Out.x(), pos2Out.y(), offset),color).endVertex();
        builder.end();
        BufferUploader.end(builder);
        GLColorFinish();
    }

    /*
        Pushes a generic texture for rendering via a ResourceLocation
        Make sure to pass in a valid ResourceLocation since that does not get checked here
     */
    public static void bufferSquareTexture(PoseStack matrix, Vector3f center, float radius, ResourceLocation texture) {
        Minecraft.getInstance().getTextureManager().bindForSetup(texture);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        GuiComponent.blit(matrix,(int) (center.x() - radius / 2f), (int) (center.y() - radius / 2f),
                0, 0, (int) radius, (int) radius, (int) radius, (int) radius);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    public static void drawColoredRing(Vector3f center, Vector3f radii, Vector4f color, int resolution,
                                       float offset) {
        Vector3f angles = MathUtil.makeAngleVector(0,1);
        float startAngle = (float) Math.toRadians(angles.x());
        float angleDif = (float) Math.toRadians(angles.y()-angles.x());
        for(int i=0;i<resolution;i++) {
            float angle1 = startAngle+(i/(float)resolution)*angleDif;
            float angle2 = startAngle+((i+1)/(float)resolution)*angleDif;
            Vector3f pos1In = MathUtil.getVertex(center, radii.x(),angle1);
            Vector3f pos2In = MathUtil.getVertex(center, radii.x(),angle2);
            Vector3f pos1Out = MathUtil.getVertex(center, radii.y(),angle1);
            Vector3f pos2Out = MathUtil.getVertex(center, radii.y(),angle2);
            setBuffer(pos1In,pos2In,pos1Out,pos2Out,offset,color);
        }
    }

    /*
        Draws a colored box with an outline
        The offset can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxWithOutline(Vector3f topLeft, int width, int height, Vector4f color,
                                          Vector4f outlineColor, float outlineWidth, float offset) {
        drawBox(topLeft, width, height, color, offset);
        drawBoxOutline(topLeft, width, height, outlineColor, outlineWidth, offset);
    }

    /*
       Draws a box using tuple inputs
       The offset can be obtained from any GuiScreen or set to 0
    */
    public static void drawBox(Vector3f topLeft, int width, int height, Vector4f color, float offset) {
        Vector3f bottomRight = new Vector3f(topLeft.x()+width,topLeft.y()+height,0);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        GLColorStart(color);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        vectorColor(builder.vertex(topLeft.x(), topLeft.y(), offset), color).endVertex();
        vectorColor(builder.vertex(topLeft.x(), bottomRight.y(), offset), color).endVertex();
        vectorColor(builder.vertex(bottomRight.x(), bottomRight.y(), offset), color).endVertex();
        vectorColor(builder.vertex(bottomRight.x(), topLeft.y(), offset), color).endVertex();
        builder.end();
        BufferUploader.end(builder);
        GLColorFinish();
    }

    /*
        Draws the outline of a box
        The offset can be obtained from any GuiScreen or set to 0
     */
    public static void drawBoxOutline(Vector3f topLeft, int width, int height, Vector4f color, float outlineWidth, float offset) {
        Vector3f topRight = new Vector3f(topLeft.x()+width,topLeft.y(),0);
        Vector3f bottomRight = new Vector3f(topLeft.x()+width,topLeft.y()+height,0);
        Vector3f bottomLeft = new Vector3f(topLeft.x(),topLeft.y()+height,0);
        drawLine(topLeft,topRight,color,outlineWidth,offset);
        drawLine(topRight,bottomRight,color,outlineWidth,offset);
        drawLine(bottomRight,bottomLeft,color,outlineWidth,offset);
        drawLine(bottomLeft,topLeft,color,outlineWidth,offset);
    }

    /*
        Draws a colored line between 2 points
        The offset can be obtained from any GuiScreen or set to 0
     */
    public static void drawLine(Vector3f start, Vector3f end, Vector4f color, float width, float offset) {
        double angle = MathUtil.getAngle(start, end);
        Vector3f start1 = MathUtil.getVertex(start,width/2d,Math.toRadians(angle+90d));
        Vector3f start2 = MathUtil.getVertex(start,width/2d,Math.toRadians(angle-90d));
        Vector3f end1 = MathUtil.getVertex(end,width/2d,Math.toRadians(angle-90d));
        Vector3f end2 = MathUtil.getVertex(end,width/2d,Math.toRadians(angle+90d));
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        GLColorStart(color);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        vectorColor(builder.vertex(start1.x(), start1.y(), offset), color).endVertex();
        vectorColor(builder.vertex(start2.x(), start2.y(), offset), color).endVertex();
        vectorColor(builder.vertex(end1.x(), end1.y(), offset), color).endVertex();
        vectorColor(builder.vertex(end2.x(), end2.y(), offset), color).endVertex();
        builder.end();
        BufferUploader.end(builder);
        GLColorFinish();
    }

    /*
        Splits a string into multiple lines and renders them
        Returns the new y position under the rendered lines
     */
    public static int drawMultiLineString(Screen screen, PoseStack matrix, String original, int left, int right,
                                          int top, int spacing) {
        List<String> lines = new ArrayList<>();
        String[] words = original.split(" ");
        StringBuilder builder = new StringBuilder();
        int lineWidth = 0;
        for(String word : words) {
            if(lineWidth==0) {
                builder.append(word);
                lineWidth+=screen.getMinecraft().font.width(word);
            }
            else {
                String withSpace = " "+word;
                int textWidth = screen.getMinecraft().font.width(withSpace);
                if((left+lineWidth+textWidth)<right) {
                    builder.append(withSpace);
                    lineWidth+=textWidth;
                } else {
                    lines.add(builder.toString());
                    builder = new StringBuilder();
                    builder.append(word);
                    lineWidth = screen.getMinecraft().font.width(word);
                }
            }
        }
        lines.add(builder.toString());
        for(String line : lines) {
            screen.getMinecraft().font.drawShadow(matrix,line,left,top, GuiUtil.WHITE);
            top+=spacing;
        }
        return top;
    }

    /*
        Primes the GLStateManager to draw a solid color
     */
    public static void GLColorStart(Vector4f color) {
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
    }

    /*
        Resets some necessary GLStateManager stuff so other rendering works as intended
     */
    public static void GLColorFinish() {
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /*
        Sets the color of a vertex given a color vector
     */
    public static VertexConsumer vectorColor(VertexConsumer vertex, Vector4f colors) {
        return vertex.color(colors.x()/255f, colors.y()/255f, colors.z()/255f, colors.w()/255f);
    }

    /*
        Reverses a color vector
        This is generally used to set an opposite hover color
     */
    public static Vector4f reverseColors(Vector4f colors) {
        return new Vector4f(Math.abs(colors.x()-255), Math.abs(colors.y()-255), Math.abs(colors.z()-255), colors.w());
    }

    /*
        Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Vector4f colors) {
        return makeRGBAInt((int)colors.x(),(int)colors.y(),(int)colors.z(),(int)colors.w());
    }

    /*
        Converts rgba integers into a single color integer
     */
    public static int makeRGBAInt(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((b & 0xFF) << 8) | (g & 0xFF);
    }
}
