package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

import javax.annotation.Nullable;
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

    public static void bufferSquareTex(RenderAPI renderer, Vector2f center, float radius, float alpha,
                                       ResourceLocationAPI<?> texture) {
        float length = radius*2f;
        drawTexturedRect(renderer,(int)(center.x()-radius/2f),(int)(center.y()-radius/2f),
                (int)length,(int)length,alpha,texture,0f,length,0f,length);
    }

    /**
     * Draws a box using vector inputs
     * The offset should be 0 for GUI screens
     */
    public static void drawBox(RenderAPI renderer, Vector2f topLeft, int width, int height, Vector4f color, float offset) {
        drawBox(renderer,topLeft.x,topLeft.y,width,height,color,offset);
    }

    public static void drawBox(RenderAPI renderer, float x, float y, int width, int height, Vector4f color, float offset) {
        initSolidColor(renderer,color);
        VertexWrapper buffer = renderer.getBufferBuilderPC(renderer.getGLAPI().quads(),4);
        buffer.start();
        buffer.pos(x,y,offset).color(color).endVertex();
        buffer.pos(x,y+height,offset).color(color).endVertex();
        buffer.pos(x+width,y+height,offset).color(color).endVertex();
        buffer.pos(x+width,y,offset).color(color).endVertex();
        buffer.finish();
        finishSolidColor(renderer);
    }

    /**
     * Draws the outline of a box
     * The offset should be 0 for GUI screens
     */
    public static void drawBoxOutline(RenderAPI renderer, Vector2f topLeft, int width, int height, Vector4f color,
                                      float outlineWidth, float offset) {
        drawBoxOutline(renderer,topLeft.x,topLeft.y,width,height,color,outlineWidth,offset);
    }

    public static void drawBoxOutline(RenderAPI renderer, float x, float y, int width, int height, Vector4f color,
                                      float outlineWidth, float offset) {
        drawLine(renderer,x,y,x+width,y,color,outlineWidth,offset);
        drawLine(renderer,x+width,y,x+width,y+height,color,outlineWidth,offset);
        drawLine(renderer,x+width,y+height,x,y+height,color,outlineWidth,offset);
        drawLine(renderer,x,y+height,x,y,color,outlineWidth,offset);
    }

    /**
     * Draws a colored box with an outline
     * The offset should be 0 for GUI screens
     */
    public static void drawBoxWithOutline(RenderAPI renderer, Vector2f topLeft, int width, int height, Vector4f color,
                                          Vector4f outlineColor, float outlineWidth, float offset) {
        drawBox(renderer,topLeft,width,height,color,offset);
        drawBoxOutline(renderer,topLeft,width,height,outlineColor,outlineWidth,offset);
    }

    public static void drawCircle(RenderAPI renderer, Vector2f center, double radius, Vector4f color, int resolution,
                                  float offset) {
        drawCircleSlice(renderer,center,0d,radius,0d,2*Math.PI,color,resolution,offset);
    }

    public static void drawCircleSlice(RenderAPI renderer, Vector2f center, double minRadius, double maxRadius,
                                       double startAngle, double endAngle, Vector4f color, int resolution,
                                       float offset) {
        double angleDif = endAngle-startAngle;
        for(int i=0;i<resolution;i++) {
            double angle1 = startAngle+(i/(double)resolution)*angleDif;
            double angle2 = startAngle+((i+1)/(double)resolution)*angleDif;
            Vector2f pos1In = MathHelper.getVertex(center,minRadius,angle1);
            Vector2f pos2In = MathHelper.getVertex(center,minRadius,angle2);
            Vector2f pos1Out = MathHelper.getVertex(center,maxRadius,angle1);
            Vector2f pos2Out = MathHelper.getVertex(center,maxRadius,angle2);
            setBuffer(renderer,pos1In,pos2In,pos1Out,pos2Out,offset,color);
        }
    }

    /**
     * Interfaces directly with OpenGL to draw a line. Assumes the color has already been set
     * Used by the geometry system
     */
    public static void drawLineDirect(RenderAPI renderer, Vector3d start, Vector3d end) {
        GLAPI gl = renderer.getGLAPI();
        gl.directBegin(gl.lines());
        gl.directVertex((float)start.x,(float)start.y,(float)start.z);
        gl.directVertex((float)end.x,(float)end.y,(float)end.z);
        gl.directEnd();
    }

    /**
     * Interfaces directly with OpenGL to draw a line. Assumes the color has already been set
     * Used by the geometry system
     */
    public static void drawLineDirect(RenderAPI renderer, Vector3f start, Vector3f end) {
        GLAPI gl = renderer.getGLAPI();
        gl.directBegin(gl.lines());
        gl.directVertex(start.x,start.y,start.z);
        gl.directVertex(end.x,end.y,end.z);
        gl.directEnd();
    }

    /**
     * Draws a colored line between 2
     * The offset should be 0 for GUI screens
     */
    public static void drawLine(RenderAPI renderer, Vector2f start, Vector2f end, Vector4f color, float width, float offset) {
        drawLine(renderer,start.x,start.y,end.x,end.y,color,width,offset);
    }

    public static void drawLine(RenderAPI renderer, float startX, float startY, float endX, float endY,
                                Vector4f color, float width, float offset) {
        double angle1 = new Vector2f(startX,startY).angle(new Vector2f(endX,endY))-MathHelper.RADIANS_90;
        double angle2 = angle1+MathHelper.RADIANS_180;
        Vector2f start1 = MathHelper.getVertex(startX,startY,width/2d,angle2);
        Vector2f start2 = MathHelper.getVertex(startX,startY,width/2d,angle1);
        Vector2f end1 = MathHelper.getVertex(endX,endY,width/2d,angle1);
        Vector2f end2 = MathHelper.getVertex(endX,endY,width/2d,angle2);
        setBuffer(renderer,start1,start2,end1,end2,offset,color);
    }

    /**
     * Splits a string into multiple lines and renders them
     * Returns the new y position under the rendered lines
     */
    public static int drawMultiLineString(RenderAPI renderer, String original, int left, int right,
                                          int top, int spacing, int lineNums, int pos, int color) {
        if(lineNums<=0) lineNums = Integer.MAX_VALUE;
        if(pos<0) pos = 0;
        int index = 0;
        FontAPI font = renderer.getFont();
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
            RenderAPI renderer, TextBuffer title, TextBuffer subtitle, boolean centered, int x, int y) {
        renderer.pushMatrix();
        renderer.enableBlend();
        renderer.defaultBlendFunc();
        int width = (int)renderer.getWindow().getWidth();
        int height = (int)renderer.getWindow().getHeight();
        x = x>=0 ? x : width/2;
        y = y>=0 ? y : height/2;
        int left = centered ? 0 : x;
        FontAPI font = renderer.getFont();
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

    public static void drawRing(RenderAPI renderer, Vector2f center, double minRadius, double maxRadius, Vector4f color,
                                int resolution, float offset) {
        drawCircleSlice(renderer,center,minRadius,maxRadius,0d,2*Math.PI,color,resolution,offset);
    }

    public static void drawTexturedRect(RenderAPI renderer, float x, float y, int width, int height, float alpha,
                                        ResourceLocationAPI<?> texture, float uMin, float uMax, float vMin, float vMax) {
        renderer.bindTexture(texture);
        renderer.setColor(1f,1f,1f,alpha);
        VertexWrapper buffer = renderer.getBufferBuilderPTC(renderer.getGLAPI().quads(),4);
        buffer.start();
        buffer.pos(x,y+height,1d).tex(uMin,vMax).color(1f,1f,1f,alpha).endVertex();
        buffer.pos(x+width,y+height,1d).tex(uMax,vMax).color(1f,1f,1f,alpha).endVertex();
        buffer.pos(x+width,y,1d).tex(uMax,vMin).color(1f,1f,1f,alpha).endVertex();
        buffer.pos(x,y,1d).tex(uMin,vMin).color(1f,1f,1f,alpha).endVertex();
        buffer.finish();
    }

    public static void enforceAlphaTexture(RenderAPI renderer, int x, int y, int width, int height, float alpha,
                                           ResourceLocationAPI<?> texture) {
        drawTexturedRect(renderer,x,y,width,height,alpha,texture,0f,1f,0f,1f);
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

    public static @Nullable RenderAPI getRenderer() {
        MinecraftAPI mc = TILRef.getClientSubAPI("MinecraftAPI",ClientAPI::getMinecraft);
        return Objects.nonNull(mc) ? mc.getRenderer() : null;
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


    public static void renderAllBackgroundStuff(RenderAPI renderer) {
        synchronized (RENDERABLES) {
            for(Renderable type : RENDERABLES) type.render(renderer);
        }
    }

    /**
     * Pushes a colored section to a BufferBuilder for rendering given 4 corner positions stored in vectors
     * offset if how far away from the screen the section is rendered
     */
    public static void setBuffer(RenderAPI renderer, Vector2f pos1In, Vector2f pos2In, Vector2f pos1Out, Vector2f pos2Out,
                                 float offset, Vector4f color) {
        initSolidColor(renderer,color);
        VertexWrapper buffer = renderer.getBufferBuilderPC(renderer.getGLAPI().quads(),4);
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
