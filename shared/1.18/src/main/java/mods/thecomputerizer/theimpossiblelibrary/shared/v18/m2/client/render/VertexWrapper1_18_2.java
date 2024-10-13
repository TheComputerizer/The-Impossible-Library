package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class VertexWrapper1_18_2 extends VertexWrapper {
    
    protected final VertexFormat format;
    protected final Mode formatMode;
    protected final BufferBuilder buffer;
    
    public VertexWrapper1_18_2(Mode mode, VertexFormat format, int numVertices, int ... vertexSizes) {
        super(mode.asGLMode,numVertices,vertexSizes);
        this.format = format;
        this.formatMode = mode;
        this.buffer = Tesselator.getInstance().getBuilder();
    }
    
    @Override protected void begin() {
        this.buffer.begin(this.formatMode,this.format);
    }
    
    @Override protected void draw() {
        Tesselator.getInstance().end();
    }

    @Override protected void onVertexEnded(Number[][] numbers) {
        for(int i=0; i<numbers.length; i++)
            pushBuffer(this.format.getElements().get(i),numbers[i]);
        this.buffer.endVertex();
    }
    
    protected void pushBuffer(VertexFormatElement element, Number[] numbers) {
        if(element==ELEMENT_POSITION)
            this.buffer.vertex(numbers[0].doubleValue(),numbers[1].doubleValue(),numbers[2].doubleValue());
        else if(element==ELEMENT_COLOR)
            this.buffer.color(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue(),numbers[3].floatValue());
        else if(element==ELEMENT_UV0 || element==ELEMENT_UV1 || element==ELEMENT_UV2)
            this.buffer.uv(numbers[0].floatValue(),numbers[1].floatValue());
    }
}