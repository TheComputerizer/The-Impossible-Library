package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import org.joml.Vector2d;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@SuppressWarnings("unused")
public interface GLAPI {

    void directBegin(int mode);
    void directEnd();
    void directVertexD(double x, double y, double z);
    void directVertexD(double x, double y);
    void directVertexF(float x, float y, float z);
    void directVertexF(float x, float y);
    void disable(int cap);
    void enable(int cap);
    int lineStrip();
    int lines();
    
    default void normalizedVertex2D(Vector2d vec, Vector2d next) {
        normalizedVertex(vec.x,vec.y,0d,WHITE,next.x,next.y,0d);
    }
    
    default void normalizedVertex2D(Vector2d vec, ColorCache color, Vector2d next) {
        normalizedVertex(vec.x,vec.y,0d,color.r(),color.g(),color.b(),color.a(),next.x,next.y,0d);
    }
    
    default void normalizedVertex2D(double x, double y, double nextX, double nextY) {
        normalizedVertex(x,y,0d,WHITE,nextX,nextY,0d);
    }
    
    default void normalizedVertex2D(double x, double y, ColorCache color, double nextX, double nextY) {
        normalizedVertex(x,y,0d,color.r(),color.g(),color.b(),color.a(),nextX,nextY,0d);
    }
    
    default void normalizedVertex2D(double x, double y, float r, float g, float b, float a, double nextX, double nextY) {
        normalizedVertex(x,y,0d,r,g,b,a,nextX,nextY,0d);
    }
    
    default void normalizedVertex(Vector3d vec, Vector3d next) {
        normalizedVertex(vec.x,vec.y,vec.z,WHITE,next.x,next.y,next.z);
    }
    
    default void normalizedVertex(Vector3d vec, ColorCache color, Vector3d next) {
        normalizedVertex(vec.x,vec.y,vec.z,color.r(),color.g(),color.b(),color.a(),next.x,next.y,next.z);
    }
    
    default void normalizedVertex(double x, double y, double z, double nextX, double nextY, double nextZ) {
        normalizedVertex(x,y,z,WHITE,nextX,nextY,nextZ);
    }
    
    default void normalizedVertex(double x, double y, double z, ColorCache color, double nextX, double nextY, double nextZ) {
        normalizedVertex(x,y,z,color.r(),color.g(),color.b(),color.a(),nextX,nextY,nextZ);
    }
    
    void normalizedVertex(double x, double y, double z, float r, float g, float b, float a, double nextX, double nextY,
            double nextZ);
    int quads();
    void scissor(int left, int bottom, int width, int height);
    int scissorTest();
    void setLineWidth(float width);
    void setWorkingMatrix(Object matrix);
    int triangles();
    int triangleFan();
}