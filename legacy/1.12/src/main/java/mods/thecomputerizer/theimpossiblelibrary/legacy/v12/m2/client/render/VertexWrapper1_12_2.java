package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUsage;

public class VertexWrapper1_12_2 extends VertexWrapper {

    private final VertexFormat format;
    private final BufferBuilder buffer;
    
    public VertexWrapper1_12_2(int mode, VertexFormat format, int numVertices, int... vertexSizes) {
        super(mode,numVertices,vertexSizes);
        this.format = format;
        this.buffer = Tessellator.getInstance().getBuffer();
    }

    @Override protected void begin() {
        this.buffer.begin(this.mode,this.format);
    }

    @Override protected void draw() {
        Tessellator.getInstance().draw();
    }

    @Override protected void onVertexEnded(Number[][] numbers) {
        for(int i=0; i<numbers.length; i++)
            pushBuffer(this.format.getElement(i),numbers[i]);
        this.buffer.endVertex();
    }

    private void pushBuffer(VertexFormatElement element, Number[] numbers) {
        int count = element.getElementCount();
        EnumType type = element.getType();
        EnumUsage usage = element.getUsage();
        if(numbers.length!=count) {
            TILRef.logError("Incorrect buffer size {} for VertextFormatElement! (Count={}|Type={}|Usage={}!",
                           numbers.length,count,type,usage);
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
                this.buffer.pos(numbers[0].doubleValue(),numbers[1].doubleValue(),numbers[2].doubleValue());
                return;
            }
            case UV: {
                this.buffer.tex(numbers[0].doubleValue(),numbers[1].doubleValue());
                return;
            }
            default: TILRef.logError("Unsupported VertextFormatElement (Count={}|Type={}|Usage={}!",count,type,usage);
        }
    }
}
