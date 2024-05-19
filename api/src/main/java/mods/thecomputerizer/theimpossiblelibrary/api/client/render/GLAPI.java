package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

@SuppressWarnings("unused")
public interface GLAPI {

    void directBegin(int mode);
    void directEnd();
    void directVertexD(double x, double y, double z);
    void directVertexD(double x, double y);
    void directVertexF(float x, float y, float z);
    void directVertexF(float x, float y);
    int lineLoop();
    int lines();
    int quads();
    void setLineWidth(float width);
    int triangles();
    int triangleFan();
}