package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector4;

@SuppressWarnings("UnusedReturnValue")
public class Vertex {

    @Getter private Number[][] buffer;
    private int bIndex;

    public Vertex(int ... sizes) {
        setBufferSize(sizes);
    }

    public void clearBuffer() {
        for(int i=0; i<this.buffer.length; i++)
            this.buffer[i] = new Number[this.buffer[i].length];
        this.bIndex = 0;
    }

    public void setBufferSize(int ... sizes) {
        this.buffer = new Number[sizes.length][];
        for(int index=0; index<sizes.length; index++) this.buffer[index] = new Number[sizes[index]];
    }

    public Vertex setColor(Vector4 color) {
        return setColor(color.fX(),color.fY(),color.fZ(),color.fW());
    }

    public Vertex setColor(float r, float g, float b, float a) {
        if(this.bIndex>=this.buffer.length)
            TILRef.logError("Tried to buffer the color of a filled vertex!");
        else if(this.buffer[this.bIndex].length!=4)
            TILRef.logError("Tried to buffer the color of a vertex in the wrong spot! Index: {} | Size: {}",
                    this.bIndex,this.buffer[this.bIndex].length);
        else {
            this.buffer[this.bIndex][0] = r;
            this.buffer[this.bIndex][1] = g;
            this.buffer[this.bIndex][2] = b;
            this.buffer[this.bIndex][3] = a;
            this.bIndex++;
        }
        return this;
    }

    public Vertex setPosition(Vector3 pos) {
        return setPosition(pos.dX(),pos.dY(),pos.dZ());
    }

    public Vertex setPosition(double x, double y, double z) {
        if(this.bIndex>=this.buffer.length)
            TILRef.logError("Tried to buffer the position of a filled vertex!");
        else if(this.buffer[this.bIndex].length!=3)
            TILRef.logError("Tried to buffer the position of a vertex in the wrong spot! Index: {} | Size: {}",
                    this.bIndex,this.buffer[this.bIndex].length);
        else {
            this.buffer[this.bIndex][0] = x;
            this.buffer[this.bIndex][1] = y;
            this.buffer[this.bIndex][2] = z;
            this.bIndex++;
        }
        return this;
    }

    public Vertex setTexture(Vector2 uv) {
        return setTexture(uv.dX(),uv.dY());
    }

    public Vertex setTexture(double u, double v) {
        if(this.bIndex >=this.buffer.length)
            TILRef.logError("Tried to buffer the texture of a filled vertex!");
        else if(this.buffer[this.bIndex].length!=2)
            TILRef.logError("Tried to buffer the texture of a vertex in the wrong spot! Index: {} | Size: {}",
                    this.bIndex,this.buffer[this.bIndex].length);
        else {
            this.buffer[this.bIndex][0] = u;
            this.buffer[this.bIndex][1] = v;
            this.bIndex++;
        }
        return this;
    }
}
