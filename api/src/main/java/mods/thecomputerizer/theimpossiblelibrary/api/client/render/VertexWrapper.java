package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4f;

import javax.annotation.Nullable;

public abstract class VertexWrapper {

    protected final int mode;
    private final Vertex[] vertices;
    private int vIndex;

    protected VertexWrapper(int mode, int numVertices, int ... vertexSizes) {
        this.mode = mode;
        this.vertices = new Vertex[numVertices];
        for(int i=0; i<numVertices; i++) this.vertices[i] = new Vertex(vertexSizes);
    }

    protected abstract void begin();

    protected void clearBuffers() {
        for(Vertex vertex : this.vertices) vertex.clearBuffer();
        this.vIndex = 0;
    }

    public VertexWrapper color(Vector4f color) {
        if(this.vIndex>=this.vertices.length)
            TILRef.logError("Tried to buffer the color of a filled vertex wrapper!");
        else this.vertices[this.vIndex].setColor(color);
        return this;
    }

    public VertexWrapper color(float r, float g, float b, float a) {
        if(this.vIndex>=this.vertices.length)
            TILRef.logError("Tried to buffer the color of a filled vertex wrapper!");
        else this.vertices[this.vIndex].setColor(r,g,b,a);
        return this;
    }

    protected abstract void draw();

    public void endVertex() {
        onVertexEnded(this.vertices[this.vIndex].getBuffer());
        this.vIndex++;
    }

    public void finish() {
        this.draw();
    }

    public @Nullable Vertex getCurrentVertex() {
        if(this.vIndex>=this.vertices.length) {
            TILRef.logError("Tried to get the current vertex of a filled vertex wrapper!");
            return null;
        }
        return this.vertices[this.vIndex];
    }

    public Vertex getVertex(int i) {
        return this.vertices[i];
    }

    protected abstract void onVertexEnded(Number[][] numbers);

    public VertexWrapper pos(Vector3d pos) {
        if(this.vIndex>=this.vertices.length)
            TILRef.logError("Tried to buffer the position of a filled vertex wrapper!");
        else this.vertices[this.vIndex].setPosition(pos);
        return this;
    }

    public VertexWrapper pos(double x, double y, double z) {
        if(this.vIndex>=this.vertices.length)
            TILRef.logError("Tried to buffer the position of a filled vertex wrapper!");
        else this.vertices[this.vIndex].setPosition(x,y,z);
        return this;
    }

    public void setVertexSize(int ... vertexSizes) {
        for(Vertex vertex : this.vertices) vertex.setBufferSize(vertexSizes);
    }

    public void start() {
        clearBuffers();
        this.begin();
    }

    public VertexWrapper tex(Vector2d uv) {
        if(this.vIndex>=this.vertices.length)
            TILRef.logError("Tried to buffer the texture of a filled vertex wrapper!");
        else this.vertices[this.vIndex].setTexture(uv);
        return this;
    }

    public VertexWrapper tex(double u, double v) {
        if(this.vIndex>=this.vertices.length)
            TILRef.logError("Tried to buffer the texture of a filled vertex wrapper!");
        else this.vertices[this.vIndex].setTexture(u,v);
        return this;
    }
}
