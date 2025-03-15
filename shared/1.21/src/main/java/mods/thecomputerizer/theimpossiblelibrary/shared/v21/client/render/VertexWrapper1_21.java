package mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.LINES;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.LINE_STRIP;

public class VertexWrapper1_21 extends VertexWrapper {
    
    protected final VertexFormat format;
    protected final Mode formatMode;
    protected final Tesselator tesselator;
    protected BufferBuilder buffer;
    
    public VertexWrapper1_21(Mode mode, VertexFormat format, int numVertices, int ... vertexSizes) {
        super(mode.asGLMode,numVertices,vertexSizes);
        this.format = format;
        this.formatMode = mode;
        this.tesselator = mode==LINES || mode==LINE_STRIP ?
                RenderSystem.renderThreadTesselator() : Tesselator.getInstance();
    }
    
    @Override protected void begin() {
        this.buffer = this.tesselator.begin(this.formatMode,this.format);
    }
    
    @Override protected void draw() {
        BufferUploader.drawWithShader(this.buffer.buildOrThrow());
    }

    @Override protected void onVertexEnded(Number[][] numbers) {
        for(int i=0; i<numbers.length; i++)
            pushBuffer(this.format.getElements().get(i),numbers[i]);
    }
    
    protected void pushBuffer(VertexFormatElement element, Number[] numbers) {
        if(element==VertexFormatElement.POSITION)
            this.buffer.addVertex(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue());
        else if(element==VertexFormatElement.COLOR)
            this.buffer.setColor(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue(),numbers[3].floatValue());
        else if(element==VertexFormatElement.UV || element==VertexFormatElement.UV0)
            this.buffer.setUv(numbers[0].floatValue(),numbers[1].floatValue());
        else if(element==VertexFormatElement.UV1)
            this.buffer.setUv1(numbers[0].intValue(),numbers[1].intValue());
        else if(element==VertexFormatElement.UV2)
            this.buffer.setUv2(numbers[0].intValue(),numbers[1].intValue());
    }
}