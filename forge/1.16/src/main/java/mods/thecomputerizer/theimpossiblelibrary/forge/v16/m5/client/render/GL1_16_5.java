package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GL1_16_5 implements GLAPI {

    @Override public void directBegin(int mode) {
        GL11.glBegin(mode);
    }

    @Override public void directEnd() {
        GL11.glEnd();
    }
    
    @Override public void directVertexD(double x, double y, double z) {
        GL11.glVertex3d(x,y,z);
    }
    
    @Override public void directVertexD(double x, double y) {
        GL11.glVertex2d(x,y);
    }
    
    @Override public void directVertexF(float x, float y, float z) {
        GL11.glVertex3f(x,y,z);
    }
    
    @Override public void directVertexF(float x, float y) {
        GL11.glVertex2f(x,y);
    }
    
    @Override public void disable(int cap) {
        GL11.glDisable(cap);
    }
    
    @Override public void enable(int cap) {
        GL11.glEnable(cap);
    }
    
    @Override public int lineLoop() {
        return GL_LINE_LOOP;
    }

    @Override public int lines() {
        return GL_LINES;
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
    
    @Override public int triangles() {
        return GL_TRIANGLES;
    }
    
    @Override public int triangleFan() {
        return GL_TRIANGLE_FAN;
    }
}
