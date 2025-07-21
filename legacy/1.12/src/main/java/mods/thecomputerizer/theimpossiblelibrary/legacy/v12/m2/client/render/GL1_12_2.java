package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.*;
import static org.lwjgl.opengl.GL11.*;

public class GL1_12_2 implements GLAPI {
    
    public static final VertexFormat POSITION_COLOR_NORMAL = new VertexFormat();
    
    static {
        POSITION_COLOR_NORMAL.addElement(POSITION_3F);
        POSITION_COLOR_NORMAL.addElement(COLOR_4UB);
        POSITION_COLOR_NORMAL.addElement(NORMAL_3B);
        POSITION_COLOR_NORMAL.addElement(PADDING_1B);
    }
    
    private BufferBuilder workingBuffer;
    protected VertexFormat workingFormat;
    
    @Override public void directBegin(int mode) {
        this.workingBuffer = Tessellator.getInstance().getBuffer();
        this.workingFormat = POSITION_COLOR_NORMAL;
        this.workingBuffer.begin(mode,this.workingFormat);
    }

    @Override public void directEnd() {
        Tessellator.getInstance().draw();
        this.workingBuffer = null;
        this.workingFormat = null;
    }

    @Override public void directVertexD(double x, double y, double z) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (3D) to buffer before calling directBegin!");
        else normalizedVertex(x,y,z,1f,1f,1f,1f,x,y,z);
    }
    
    @Override public void directVertexD(double x, double y) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (2D) to buffer before calling directBegin!");
        else normalizedVertex(x,y,0d,1f,1f,1f,1f,x,y,0d);
    }
    
    @Override public void directVertexF(float x, float y, float z) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (3F) to buffer before calling directBegin!");
        else normalizedVertex(x,y,z,1f,1f,1f,1f,x,y,z);
    }
    
    @Override public void directVertexF(float x, float y) {
        if(Objects.isNull(this.workingBuffer))
            TILRef.logError("Cannot directly add vertex (2F) to buffer before calling directBegin!");
        else normalizedVertex(x,y,0d,1f,1f,1f,1f,x,y,0d);
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
    
    @Override public void normalizedVertex(double x, double y, double z, float r, float g, float b, float a,
            double nextX, double nextY, double nextZ) {
        if(Objects.isNull(this.workingBuffer)) {
            TILRef.logError("Cannot add normalized vertex to buffer before calling directBegin!");
            return;
        }
        boolean same = x==nextX && y==nextY && z==nextZ;
        double nX = same ? 1f : (nextX-x);
        double nY = same ? 1f : (nextY-y);
        double nZ = same ? 1f : (nextZ-z);
        double dist = MathHelper.distance(nX, nY, nZ);
        nX/=dist;
        nY/=dist;
        nZ/=dist;
        this.workingBuffer.pos(x,y,z).color(r,g,b,a).normal((float)nX,(float)nY,(float)nZ).endVertex();
        this.workingBuffer.pos(nextX,nextY,nextZ).color(r,g,b,a).normal((float)nX,(float)nY,(float)nZ).endVertex();
    }
    
    @Override public int quads() {
        return GL_QUADS;
    }
    
    @Override public void scissor(int left, int bottom, int width, int height) {
        GL11.glScissor(left,bottom,width,height);
    }
    
    @Override public int scissorTest() {
        return GL_SCISSOR_TEST;
    }
    
    @Override public void setLineWidth(float width) {
        GL11.glLineWidth(width);
    }
    
    @Override public void setWorkingMatrix(Object matrix) {} //Not needed in 1.12.2
    
    @Override public int triangles() {
        return GL_TRIANGLES;
    }
    
    @Override public int triangleFan() {
        return GL_TRIANGLE_FAN;
    }
}
