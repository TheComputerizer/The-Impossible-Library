package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION;
import static org.lwjgl.opengl.GL11.*;

public class GL1_16_5 implements GLAPI {
    
    protected VertexFormat workingFormat;
    private BufferBuilder workingBuffer;
    protected Pose workingPose;

    @Override public void directBegin(int mode) {
        this.workingBuffer = Tesselator.getInstance().getBuilder();
        this.workingFormat = POSITION;
        this.workingBuffer.begin(mode,this.workingFormat);
    }

    @Override public void directEnd() {
        Tesselator.getInstance().end();
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
    
    private VertexConsumer normal(VertexConsumer consumer, float x, float y, float z) {
        return Objects.nonNull(this.workingPose) ?
                consumer.normal(this.workingPose.normal(),x,y,z) : consumer.normal(x,y,z);
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
        normal(vertex(x,y,z).color(r,g,b,a),(float)nX,(float)nY,(float)nZ).endVertex();
        normal(vertex(nextX,nextY,nextZ).color(r,g,b,a),(float)nX,(float)nY,(float)nZ).endVertex();
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
    
    @Override public void setWorkingMatrix(Object matrix) {
        if(matrix instanceof PoseStack) this.workingPose = ((PoseStack)matrix).last();
        else if(matrix instanceof Pose) this.workingPose = (Pose)matrix;
        else {
            if(Objects.nonNull(matrix))
                TILRef.logError("Tried to set working Pose for GL1_16 to a non GuiGraphics, PoseStack, or Pose {}",matrix);
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
