package mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class GL1_19 implements GLAPI {
    
    private float[] workingColor;
    private BufferSource workingBufferSource;
    private RenderType workingRenderType;
    private VertexConsumer workingVertexConsumer;
    private double cutoffX;
    private double cutoffY;
    
    /**
     * GL11#glBegin was removed after OpenGL 3.1, but backwards compatibility means we can't just remove this method
     */
    @Override public void directBegin(int modeVal) {
        Minecraft mc = Minecraft.getInstance();
        this.workingBufferSource = mc.renderBuffers().bufferSource();
        this.workingRenderType = modeVal==lines() ? RenderType.lines() : RenderType.lineStrip();
        this.workingVertexConsumer = this.workingBufferSource.getBuffer(this.workingRenderType);
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        this.workingColor = RenderSystem.getShaderColor();
        Window window = mc.getWindow();
        double width = window.getGuiScaledWidth();
        double height = window.getGuiScaledHeight();
        this.cutoffX = width/2d;
        this.cutoffY = height/2d;
    }

    @Override public void directEnd() {
        if(Objects.isNull(this.workingBufferSource))
            TILRef.logError("Cannot directly end buffer before calling directBegin!");
        else {
            this.workingBufferSource.endBatch();
            this.workingBufferSource = null;
            this.workingRenderType = null;
            this.workingVertexConsumer = null;
        }
    }
    
    @Override public void directVertexD(double x, double y, double z) {
        if(Objects.isNull(this.workingBufferSource))
            TILRef.logError("Cannot directly add vertex (3D) to buffer before calling directBegin!");
        else {
            if(x>this.cutoffX) x+=(1.5d*(this.cutoffX/this.cutoffY));
            if(y>this.cutoffY) y+=(1.5d*(this.cutoffY/this.cutoffX));
            this.workingVertexConsumer.vertex(x,y,z).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).normal(1f,1f,1f).endVertex();
            //this.workingVertexConsumer.vertex(x,y,z).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).endVertex();
            //this.workingVertexConsumer.vertex(x,y,z).endVertex();
        }
    }
    
    @Override public void directVertexD(double x, double y) {
        if(Objects.isNull(this.workingBufferSource))
            TILRef.logError("Cannot directly add vertex (2D) to buffer before calling directBegin!");
        else {
            if(x>this.cutoffX) x+=(1.5d*(this.cutoffX/this.cutoffY));
            if(y>this.cutoffY) y+=(1.5d*(this.cutoffY/this.cutoffX));
            this.workingVertexConsumer.vertex(x,y,0d).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).normal(1f,1f,0f).endVertex();
            //this.workingVertexConsumer.vertex(x,y,0d).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).endVertex();
            //this.workingVertexConsumer.vertex(x,y,0d).endVertex();
        }
    }
    
    @Override public void directVertexF(float x, float y, float z) {
        if(Objects.isNull(this.workingBufferSource))
            TILRef.logError("Cannot directly add vertex (3F) to buffer before calling directBegin!");
        else {
            if(x>this.cutoffX) x+=(float)(1.5f*(this.cutoffX/this.cutoffY));
            if(y>this.cutoffY) y+=(float)(1.5f*(this.cutoffY/this.cutoffX));
            this.workingVertexConsumer.vertex(x,y,z).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).normal(1f,1f,1f).endVertex();
            //this.workingVertexConsumer.vertex(x,y,z).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).endVertex();
            //this.workingVertexConsumer.vertex(x,y,z).endVertex();
        }
    }
    
    @Override public void directVertexF(float x, float y) {
        if(Objects.isNull(this.workingBufferSource))
            TILRef.logError("Cannot directly add vertex (2F) to buffer before calling directBegin!");
        else {
            if(x>this.cutoffX) x+=(float)(1.5f*(this.cutoffX/this.cutoffY));
            if(y>this.cutoffY) y+=(float)(1.5f*(this.cutoffY/this.cutoffX));
            this.workingVertexConsumer.vertex(x,y,0f).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).normal(1f,1f,0f).endVertex();
            //this.workingVertexConsumer.vertex(x,y,0f).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],this.workingColor[3]).endVertex();
            //this.workingVertexConsumer.vertex(x,y,0f).endVertex();
        }
    }
    
    @Override public void disable(int cap) {
        GL11.glDisable(cap);
    }
    
    @Override public void enable(int cap) {
        GL11.glEnable(cap);
    }
    
    @Override public int lineStrip() {
        return GL_LINE_STRIP;
    }

    @Override public int lines() {
        return GL_LINES;
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
        RenderSystem.enableDepthTest();
        RenderSystem.lineWidth(width);
    }
    
    @Override public int triangles() {
        return GL_TRIANGLES;
    }
    
    @Override public int triangleFan() {
        return GL_TRIANGLE_FAN;
    }
}
