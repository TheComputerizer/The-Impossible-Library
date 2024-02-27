package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

public interface GLAPI {

    void directBegin(int mode);
    void directEnd();
    void directVertex(float x, float y, float z);
    int lines();
    int quads();
    int triangleFan();
}