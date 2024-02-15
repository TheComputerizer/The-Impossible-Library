package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;
import java.util.*;

public class RenderHelper {

    private static final List<Renderable> RENDERABLES = Collections.synchronizedList(new ArrayList<>());
    private static Random RANDOM;

    public static void addRenderable(Renderable renderable) {
        synchronized(RENDERABLES) {
            RENDERABLES.add(renderable);
            renderable.initializeTimers(getRandom());
        }
    }

    public static void bufferSquareTex(MinecraftAPI mc, Vector2f center, float radius, float alpha,
                                       ResourceLocationAPI<?> texture) {
        float length = radius*2f;
        drawTexturedRect(mc,(int)(center.x()-radius/2f),(int)(center.y()-radius/2f),
                (int)length,(int)length,alpha,texture,0f,length,0f,length);
    }

    /**
     * Draws a box using tuple inputs
     * The offset should be 0 for GUI screens
     */
    public static void drawBox(MinecraftAPI mc, Vector2f topLeft, int width, int height, Vector4f color, float offset) {
        Vector3f bottomRight = new Vector3f(topLeft.x()+width,topLeft.y()+height,0);
        RenderAPI renderer = mc.getRenderer();
        initSolidColor(renderer,color);
        VertexWrapper buffer = mc.getBufferBuilderPC(4);
        buffer.start();
        buffer.pos(topLeft.x,topLeft.y,offset).color(color).endVertex();
        buffer.pos(topLeft.x,bottomRight.y,offset).color(color).endVertex();
        buffer.pos(bottomRight.x,bottomRight.y,offset).color(color).endVertex();
        buffer.pos(bottomRight.x,topLeft.y,offset).color(color).endVertex();
        buffer.finish();
        finishSolidColor(renderer);
    }

    /**
     * Draws the outline of a box
     * The offset should be 0 for GUI screens
     */
    public static void drawBoxOutline(MinecraftAPI mc, Vector2f topLeft, int width, int height, Vector4f color,
                                      float outlineWidth, float offset) {
        Vector2f topRight = new Vector2f(topLeft.x()+width,topLeft.y());
        Vector2f bottomRight = new Vector2f(topLeft.x()+width,topLeft.y()+height);
        Vector2f bottomLeft = new Vector2f(topLeft.x(),topLeft.y()+height);
        drawLine(mc,topLeft,topRight,color,outlineWidth,offset);
        drawLine(mc,topRight,bottomRight,color,outlineWidth,offset);
        drawLine(mc,bottomRight,bottomLeft,color,outlineWidth,offset);
        drawLine(mc,bottomLeft,topLeft,color,outlineWidth,offset);
    }

    /**
     * Draws a colored box with an outline
     * The offset should be 0 for GUI screens
     */
    public static void drawBoxWithOutline(MinecraftAPI mc, Vector2f topLeft, int width, int height, Vector4f color,
                                          Vector4f outlineColor, float outlineWidth, float offset) {
        drawBox(mc,topLeft,width,height,color,offset);
        drawBoxOutline(mc,topLeft,width,height,outlineColor,outlineWidth,offset);
    }

    public static void drawColoredRing(MinecraftAPI mc, Vector2f center, Vector2f radii, Vector4f color, int resolution,
                                       float offset) {
        Vector2f angles = MathHelper.makeAngleVector(0,1);
        float startAngle = (float) Math.toRadians(angles.x());
        float angleDif = (float) Math.toRadians(angles.y()-angles.x());
        for(int i=0;i<resolution;i++) {
            float angle1 = startAngle+(i/(float)resolution)*angleDif;
            float angle2 = startAngle+((i+1)/(float)resolution)*angleDif;
            Vector2f pos1In = MathHelper.getVertex(center, radii.x(),angle1);
            Vector2f pos2In = MathHelper.getVertex(center, radii.x(),angle2);
            Vector2f pos1Out = MathHelper.getVertex(center, radii.y(),angle1);
            Vector2f pos2Out = MathHelper.getVertex(center, radii.y(),angle2);
            setBuffer(mc,pos1In,pos2In,pos1Out,pos2Out,offset,color);
        }
    }

    /**
     * Draws a colored line between 2
     * The offset should be 0 for GUI screens
     */
    public static void drawLine(MinecraftAPI mc, Vector2f start, Vector2f end, Vector4f color, float width, float offset) {
        double angle = MathHelper.getAngle(start, end);
        Vector2f start1 = MathHelper.getVertex(start,width/2d,Math.toRadians(angle+90d));
        Vector2f start2 = MathHelper.getVertex(start,width/2d,Math.toRadians(angle-90d));
        Vector2f end1 = MathHelper.getVertex(end,width/2d,Math.toRadians(angle-90d));
        Vector2f end2 = MathHelper.getVertex(end,width/2d,Math.toRadians(angle+90d));
        setBuffer(mc,start1,start2,end1,end2,offset,color);
    }

    /**
     * Splits a string into multiple lines and renders them
     * Returns the new y position under the rendered lines
     */
    public static int drawMultiLineString(MinecraftAPI mc, String original, int left, int right,
                                          int top, int spacing, int lineNums, int pos, int color) {
        if(lineNums<=0) lineNums = Integer.MAX_VALUE;
        if(pos<0) pos = 0;
        int index = 0;
        RenderAPI renderer = mc.getRenderer();
        FontAPI font = mc.getFont();
        for(String line : FontHelper.splitLines(font,original,left,right)) {
            if(index>=pos) {
                renderer.drawString(font,line,left,top,color);
                top += spacing;
                lineNums--;
                if (lineNums <= 0) break;
            }
            index++;
        }
        return top;
    }

    /**
     * Similar to drawMultiLineString except it is assumed this is not being called from within a GUI but rather
     * making use of the Text class.
     */
    public static void drawMultiLineTitle(
            MinecraftAPI mc, TextBuffer title, TextBuffer subtitle, boolean centered, int x, int y) {
        RenderAPI renderer = mc.getRenderer();
        renderer.pushMatrix();
        renderer.enableBlend();
        renderer.defaultBlendFunc();
        int width = (int)mc.getWindow().getWidth();
        int height = (int)mc.getWindow().getHeight();
        x = x>=0 ? x : width/2;
        y = y>=0 ? y : height/2;
        int left = centered ? 0 : x;
        FontAPI font = mc.getFont();
        List<String> textLines = FontHelper.splitLines(font,title.getText(),left,width);
        renderer.pushMatrix();
        float scaleX = title.getScaleX()*subtitle.getScaleX();
        float scaleY = title.getScaleX()*subtitle.getScaleY();
        renderer.scale(title.getScaleX(),title.getScaleY(),1f);
        for(String line : textLines) {
            if(centered)
                renderer.drawString(font,line,(int)((x/scaleX)-((float)font.getStringWidth(line))/2),
                        (int)((float)y/scaleY),title.getColor());
            else renderer.drawString(font,line,(int)(x/scaleX),(int)(y/scaleY),title.getColor());
            y+=title.getLineSpacing();
        }
        renderer.popMatrix();
        List<String> subLines = FontHelper.splitLines(font,subtitle.getText(),left,width);
        scaleX = scaleX*subtitle.getScaleX();
        scaleY = scaleY*subtitle.getScaleY();
        renderer.pushMatrix();
        renderer.scale(scaleX,scaleY,1f);
        for(String line : subLines) {
            if(centered)
                renderer.drawString(font,line,(int)((x/scaleX)-((float)font.getStringWidth(line))/2),
                        (int)((float)y/scaleY),subtitle.getColor());
            else renderer.drawString(font,line,(int)(x/scaleX),(int)(y/scaleY),subtitle.getColor());
            y+= subtitle.getLineSpacing();
        }
        renderer.popMatrix();
        renderer.disableBlend();
        renderer.popMatrix();
    }

    public static void drawTexturedRect(MinecraftAPI mc, int x, int y, int width, int height, float alpha,
                                        ResourceLocationAPI<?> texture, float uMin, float uMax, float vMin, float vMax) {
        texture.bind(mc);
        mc.getRenderer().setColor(1f,1f,1f,alpha);
        VertexWrapper buffer = mc.getBufferBuilderPTC(4);
        buffer.start();
        buffer.pos(x,y+height,1d).tex(uMin,vMax).color(1f,1f,1f,alpha).endVertex();
        buffer.pos(x+width,y+height,1d).tex(uMax,vMax).color(1f,1f,1f,alpha).endVertex();
        buffer.pos(x+width,y,1d).tex(uMax,vMin).color(1f,1f,1f,alpha).endVertex();
        buffer.pos(x,y,1d).tex(uMin,vMin).color(1f,1f,1f,alpha).endVertex();
        buffer.finish();
    }

    public static void enforceAlphaTexture(MinecraftAPI mc, int x, int y, int width, int height, float alpha,
                                           ResourceLocationAPI<?> texture) {
        drawTexturedRect(mc,x,y,width,height,alpha,texture,0f,1f,0f,1f);
    }

    /**
     * Resets some necessary render stuff so other rendering works as intended
     */
    public static void finishSolidColor(RenderAPI renderer) {
        renderer.resetTextureMatrix();
        renderer.disableBlend();
    }

    public static Random getRandom() {
        if(Objects.isNull(RANDOM)) RANDOM = new Random();
        return RANDOM;
    }
    /**
     * Primes the renderer to draw a solid color
     */
    public static void initSolidColor(RenderAPI renderer, Vector4f color) {
        renderer.enableBlend();
        renderer.resetTextureMatrix();
        renderer.defaultBlendFunc();
        renderer.setPosColorShader();
    }

    public static RenderablePNG initPNG(ResourceLocationAPI<?> source, Map<String,Object> parameters) {
        try {
            boolean isAnimated = parameters.containsKey("animated") && Boolean.parseBoolean(parameters.get("animated").toString());
            return isAnimated ? new RenderableAnimated(source,parameters) : new RenderablePNG(source,parameters);
        } catch(IOException ex) {
            TILRef.logError("Failed to initialize png at resource location {}",source,ex);
        }
        return null;
    }

    public static void removeRenderable(Renderable renderable) {
        synchronized (RENDERABLES) {
            RENDERABLES.remove(renderable);
        }
    }


    public static void renderAllBackgroundStuff(MinecraftAPI mc) {
        synchronized (RENDERABLES) {
            for(Renderable type : RENDERABLES) type.render(mc);
        }
    }

    /**
     * Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
     * offset if how far away from the screen the section is rendered
     */
    public static void setBuffer(MinecraftAPI mc, Vector2f pos1In, Vector2f pos2In, Vector2f pos1Out, Vector2f pos2Out,
                                 float offset, Vector4f color) {
        RenderAPI renderer = mc.getRenderer();
        initSolidColor(renderer,color);
        VertexWrapper buffer = mc.getBufferBuilderPC(4);
        buffer.start();
        buffer.pos(pos1Out.x,pos1Out.y,offset).color(color).endVertex();
        buffer.pos(pos1In.x,pos1In.y,offset).color(color).endVertex();
        buffer.pos(pos2In.x,pos2In.y,offset).color(color).endVertex();
        buffer.pos(pos2Out.x,pos2Out.y,offset).color(color).endVertex();
        buffer.finish();
        finishSolidColor(renderer);
    }

    public static void tickRenderables() {
        synchronized (RENDERABLES) {
            RENDERABLES.removeIf(renderable -> !renderable.tick());
        }
    }
}
