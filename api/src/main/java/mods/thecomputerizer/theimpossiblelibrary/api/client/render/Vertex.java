package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4f;

public class Vertex {

    private Number[][] buffer;
    private int bIndex;

    public Vertex(int ... sizes) {
        setBufferSize(sizes);
    }

    public void clearBuffer() {
        for(int i=0; i<this.buffer.length; i++)
            this.buffer[i] = new Number[this.buffer[i].length];
    }

    public void setBufferSize(int ... sizes) {
        this.buffer = new Number[sizes.length][];
        for(int index=0; index<sizes.length; index++) this.buffer[index] = new Number[sizes[index]];
    }

    public Vertex setColor(Vector4f color) {
        return setColor(color.x,color.y,color.z,color.w);
    }

    public Vertex setColor(float r, float g, float b, float a) {
        if(this.bIndex >=this.buffer.length)
            TILRef.logError("Tried to buffer the color of a filled vertex!");
        else if(this.buffer[this.bIndex].length!=4)
            TILRef.logError("Tried to buffer the color of a vertex in the wrong spot!");
        else {
            this.buffer[this.bIndex][0] = r;
            this.buffer[this.bIndex][1] = g;
            this.buffer[this.bIndex][2] = b;
            this.buffer[this.bIndex][3] = a;
            this.bIndex++;
        }
        return this;
    }

    public Vertex setPosition(Vector3d pos) {
        return setPosition(pos.x,pos.y,pos.z);
    }

    public Vertex setPosition(double x, double y, double z) {
        if(this.bIndex >=this.buffer.length)
            TILRef.logError("Tried to buffer the position of a filled vertex!");
        else if(this.buffer[this.bIndex].length!=3)
            TILRef.logError("Tried to buffer the position of a vertex in the wrong spot!");
        else {
            this.buffer[this.bIndex][0] = x;
            this.buffer[this.bIndex][1] = x;
            this.buffer[this.bIndex][2] = x;
            this.bIndex++;
        }
        return this;
    }

    public Vertex setTexture(Vector2d uv) {
        return setTexture(uv.x,uv.y);
    }

    public Vertex setTexture(double u, double v) {
        if(this.bIndex >=this.buffer.length)
            TILRef.logError("Tried to buffer the texture of a filled vertex!");
        else if(this.buffer[this.bIndex].length!=4)
            TILRef.logError("Tried to buffer the texture of a vertex in the wrong spot!");
        else {
            this.buffer[this.bIndex][0] = u;
            this.buffer[this.bIndex][1] = v;
            this.bIndex++;
        }
        return this;
    }
}
