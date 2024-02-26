package mods.thecomputerizer.theimpossiblelibrary.legacy.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import org.lwjgl.opengl.GL11;

public class GLLegacy implements GLAPI {
    @Override
    public void directBegin(int mode) {
        GL11.glBegin(mode);
    }

    @Override
    public void directEnd() {
        GL11.glEnd();
    }

    @Override
    public void directVertex(float x, float y, float z) {
        GL11.glVertex3f(x,y,z);
    }

    @Override
    public int lines() {
        return GL11.GL_LINES;
    }

    @Override
    public int quads() {
        return GL11.GL_QUADS;
    }

    @Override
    public int triangleFan() {
        return GL11.GL_TRIANGLE_FAN;
    }
}
