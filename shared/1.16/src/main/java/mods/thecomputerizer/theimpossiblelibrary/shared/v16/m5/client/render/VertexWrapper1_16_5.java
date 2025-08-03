package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.VertexFormatElement.Type;
import com.mojang.blaze3d.vertex.VertexFormatElement.Usage;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import static com.mojang.blaze3d.vertex.VertexFormatElement.Type.FLOAT;

public class VertexWrapper1_16_5 extends VertexWrapper {
    
    protected final VertexFormat format;
    protected final BufferBuilder buffer;
    
    public VertexWrapper1_16_5(int mode, VertexFormat format, int numVertices, int ... vertexSizes) {
        super(mode,numVertices,vertexSizes);
        this.format = format;
        this.buffer = Tesselator.getInstance().getBuilder();
    }
    
    @Override protected void begin() {
        this.buffer.begin(this.mode,this.format);
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
        Type type = element.getType();
        int count = element.getByteSize()/type.getSize();
        Usage usage = element.getUsage();
        if(numbers.length!=count) {
            TILRef.logError("Incorrect buffer size {} for VertextFormatElement! (Count={}|Type={}|Usage={}!",
                            numbers.length, count, type, usage);
            return;
        }
        switch(usage) {
            case COLOR: {
                Number r = numbers[0];
                Number g = numbers[1];
                Number b = numbers[2];
                Number a = numbers[3];
                if(r instanceof Integer || r instanceof Long || r instanceof Short)
                    this.buffer.color(r.intValue(),g.intValue(),b.intValue(),a.intValue());
                else this.buffer.color(r.floatValue(),g.floatValue(),b.floatValue(),a.floatValue());
                return;
            }
            case NORMAL: {
                this.buffer.normal(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue());
                return;
            }
            case POSITION: {
                this.buffer.vertex(numbers[0].doubleValue(),numbers[1].doubleValue(),numbers[2].doubleValue());
                return;
            }
            case UV: {
                Number u = numbers[0];
                Number v = numbers[1];
                if(type==FLOAT) this.buffer.uv(u.floatValue(),v.floatValue());
                else this.buffer.uv2(u.intValue(),v.intValue());
                return;
            }
            default: TILRef.logError("Unsupported VertextFormatElement (Count={}|Type={}|Usage={}!",count,type,usage);
        }
    }
}