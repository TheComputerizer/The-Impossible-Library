package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.client.renderer.GameRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.Objects;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_NORMAL;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.LINES;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.LINE_STRIP;
import static org.lwjgl.opengl.GL11.*;

public class GL1_18_2 implements GLAPI {
    
    private float[] workingColor;
    private int workingVertexCount;
    private BufferBuilder workingBuilder;
    
    /**
     * GL11#glBegin was removed after OpenGL 3.1, but backwards compatibility means we can't just remove this method
     */
    @Override public void directBegin(int modeVal) {
        this.workingColor = RenderSystem.getShaderColor();
        this.workingBuilder = Tesselator.getInstance().getBuilder();
        Mode mode = modeVal==lines() ? LINES : LINE_STRIP;
        this.workingBuilder.begin(mode,POSITION_COLOR_NORMAL);
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
    }

    @Override public void directEnd() {
        if(Objects.isNull(this.workingBuilder))
            TILRef.logError("Cannot directly end buffer before calling directBegin!");
        else {
            Tesselator.getInstance().end();
            this.workingBuilder = null;
            this.workingVertexCount = 0;
        }
    }
    
    @Override public void directVertexD(double x, double y, double z) {
        if(Objects.isNull(this.workingBuilder))
            TILRef.logError("Cannot directly add vertex (3D) to buffer before calling directBegin!");
        else {
            this.workingBuilder.vertex(x,y,z).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],
                                                    this.workingColor[3]).normal(0f,1f,0f).endVertex();
            this.workingVertexCount++;
        }
    }
    
    @Override public void directVertexD(double x, double y) {
        if(Objects.isNull(this.workingBuilder))
            TILRef.logError("Cannot directly add vertex (2D) to buffer before calling directBegin!");
        else {
            this.workingBuilder.vertex(x,y,0d).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],
                                                     this.workingColor[3]).normal(0f,1f,0f).endVertex();
            this.workingVertexCount++;
        }
    }
    
    @Override public void directVertexF(float x, float y, float z) {
        if(Objects.isNull(this.workingBuilder))
            TILRef.logError("Cannot directly add vertex (3F) to buffer before calling directBegin!");
        else {
            this.workingBuilder.vertex(x,y,z).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],
                                                    this.workingColor[3]).normal(0f,1f,0f).endVertex();
            this.workingVertexCount++;
        }
    }
    
    @Override public void directVertexF(float x, float y) {
        if(Objects.isNull(this.workingBuilder))
            TILRef.logError("Cannot directly add vertex (2F) to buffer before calling directBegin!");
        else {
            this.workingBuilder.vertex(x,y,0f).color(this.workingColor[0],this.workingColor[1],this.workingColor[2],
                                                     this.workingColor[3]).normal(0f,1f,0f).endVertex();
            this.workingVertexCount++;
        }
    }
    
    @Override public void disable(int cap) {
        GL11.glDisable(cap);
    }
    
    @Override public void enable(int cap) {
        GL11.glEnable(cap);
    }
    
    @Override public int lineLoop() {
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
        RenderSystem.lineWidth(width);
    }
    
    @Override public int triangles() {
        return GL_TRIANGLES;
    }
    
    @Override public int triangleFan() {
        return GL_TRIANGLE_FAN;
    }
}
