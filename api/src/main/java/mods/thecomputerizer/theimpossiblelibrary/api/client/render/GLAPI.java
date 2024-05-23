package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

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
    int lineLoop();
    int lines();
    int quads();
    void scissor(int left, int bottom, int width, int height);
    int scissorTest();
    void setLineWidth(float width);
    int triangles();
    int triangleFan();
}