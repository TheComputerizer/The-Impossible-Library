package mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.Objects;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_NORMAL;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_TEX_COLOR;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.LINES;
import static org.lwjgl.opengl.GL11.*;

public class GL1_20 implements GLAPI {
    
    protected float r = 1f;
    protected float g = 1f;
    protected float b = 1f;
    protected float a = 1f;
    protected VertexFormat workingFormat;
    protected BufferBuilder workingBuffer;
    protected Pose workingPose;
    private double scaleX = 1d;
    private double scaleY = 1d;
    
    /**
     * GL11#glBegin was removed after OpenGL 3.1, but backwards compatibility means we can't just remove this method
     */
    @Override public void directBegin(int modeVal) {
        this.workingBuffer = RenderSystem.renderThreadTesselator().getBuilder();
        this.workingFormat = POSITION_COLOR_NORMAL;
        this.workingBuffer.begin(LINES,this.workingFormat);
        setWorkingColor(RenderSystem.getShaderColor());
        Window window = Minecraft.getInstance().getWindow();
        double width = window.getWidth();
        double height = window.getHeight();
        double widthFactor = height>width ? width/height : 1d;
        double heightFactor = width>height ? height/width : 1d;
        this.scaleX = 1d+((1d/width)*16d*widthFactor);
        this.scaleY = 1d+((1d/height)*16d*heightFactor);
        
    }
    
    @Override public void directEnd() {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly end buffer before calling directBegin!");
        else {
            RenderSystem.disableCull();
            endWithShader(this.workingFormat);
            RenderSystem.enableCull();
            this.workingBuffer = null;
            this.r = 1f;
            this.g = 1f;
            this.b = 1f;
            this.a = 1f;
        }
    }
    
    @Override public void directVertexD(double x, double y, double z) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (3D) to buffer before calling directBegin!");
        else normalizedVertex(x,y,z,this.r,this.g,this.b,this.a,x,y,z);
    }
    
    @Override public void directVertexD(double x, double y) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (2D) to buffer before calling directBegin!");
        else normalizedVertex(x,y,0d,this.r,this.g,this.b,this.a,x,y,0d);
    }
    
    @Override public void directVertexF(float x, float y, float z) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (3F) to buffer before calling directBegin!");
        else normalizedVertex(x,y,z,this.r,this.g,this.b,this.a,x,y,z);
    }
    
    @Override public void directVertexF(float x, float y) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (2F) to buffer before calling directBegin!");
        else normalizedVertex(x,y,0f,this.r,this.g,this.b,this.a,x,y,0f);
    }
    
    @Override public void disable(int cap) {
        GL11.glDisable(cap);
    }
    
    @Override public void enable(int cap) {
        GL11.glEnable(cap);
    }
    
    void endWithShader(VertexFormat format) {
        if(format==POSITION_COLOR) RenderSystem.setShader(GameRenderer::getPositionColorShader);
        else if(format==POSITION_TEX_COLOR) RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        else if(format==POSITION_COLOR_TEX) RenderSystem.setShader(GameRenderer::getRendertypeOutlineShader);
        else if(format==POSITION_COLOR_NORMAL) RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        BufferUploader.drawWithShader(this.workingBuffer.end());
        RenderSystem.setShader(() -> null);
    }
    
    @Override public int lineStrip() {
        return GL_LINE_STRIP;
    }

    @Override public int lines() {
        return GL_LINES;
    }
    
    protected VertexConsumer normal(VertexConsumer consumer, float x, float y, float z) {
        return Objects.nonNull(this.workingPose) ?
                consumer.normal(this.workingPose.normal(),x,y,z) : consumer.normal(x,y,z);
    }
    
    @Override public void normalizedVertex(double x, double y, double z, float r, float g, float b, float a,
            double nextX, double nextY, double nextZ) { //Direct GL calls don't work in 1.18.2+ so we need this instead
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot add normalized vertex to buffer before calling directBegin!");
        boolean same = x==nextX && y==nextY && z==nextZ;
        x*=this.scaleX;
        y*=this.scaleY;
        nextX*=this.scaleX;
        nextY*=this.scaleY;
        double nX = same ? 1f : (nextX-x);
        double nY = same ? 1f : (nextY-y);
        double nZ = same ? 1f : (nextZ-z);
        double dist = MathHelper.distance(nX,nY,nZ);
        nX/=dist;
        nY/=dist;
        nZ/=dist;
        normal(vertex(x,y,z).color(r,g,b,a),(float)nX,(float)nY,(float)nZ).endVertex();
        normal(vertex(nextX,nextY,nextZ).color(r,g,b,a),(float)nX,(float)nY,(float)nZ).endVertex();
    }

    @Override public int quads() {
        return GL_QUADS;
    }
    
    @Override public void scissor(int left, int bottom, int width, int height) {
        GL20.glScissor(left, bottom, width, height);
    }
    
    @Override public int scissorTest() {
        return GL_SCISSOR_TEST;
    }
    
    @Override public void setLineWidth(float width) {
        RenderSystem.lineWidth(width);
    }
    
    private void setWorkingColor(float ... workingColor) {
        this.r = workingColor[0];
        this.g = workingColor[1];
        this.b = workingColor[2];
        this.a = workingColor[3];
    }
    
    @Override public void setWorkingMatrix(Object matrix) {
        if(matrix instanceof GuiGraphics) this.workingPose = ((GuiGraphics)matrix).pose().last();
        else if(matrix instanceof PoseStack) this.workingPose = ((PoseStack)matrix).last();
        else if(matrix instanceof Pose) this.workingPose = (Pose)matrix;
        else {
            if(Objects.nonNull(matrix))
                TILRef.logError("Tried to set working Pose for GL1_20 to a non GuiGraphics, PoseStack, or Pose {}",matrix);
            this.workingPose = null;
        }
    }
    
    @Override public int triangles() {
        return GL_TRIANGLES;
    }
    
    @Override public int triangleFan() {
        return GL_TRIANGLE_FAN;
    }
    
    protected VertexConsumer vertex(double x, double y, double z) {
        return Objects.nonNull(this.workingPose) ?
                this.workingBuffer.vertex(this.workingPose.pose(),(float)x,(float)y,(float)z) :
                this.workingBuffer.vertex(x,y,z);
    }
}