package mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.VertexFormatElement.Type;
import com.mojang.blaze3d.vertex.VertexFormatElement.Usage;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.LINES;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.LINE_STRIP;
import static com.mojang.blaze3d.vertex.VertexFormatElement.Type.FLOAT;
import static com.mojang.blaze3d.vertex.VertexFormatElement.Usage.COLOR;

public class VertexWrapper1_19 extends VertexWrapper {
    
    protected final VertexFormat format;
    protected final Mode formatMode;
    protected final Tesselator tesselator;
    protected final BufferBuilder buffer;
    
    public VertexWrapper1_19(Mode mode, VertexFormat format, int numVertices, int ... vertexSizes) {
        super(mode.asGLMode,numVertices,vertexSizes);
        this.format = format;
        this.formatMode = mode;
        this.tesselator = mode==LINES || mode==LINE_STRIP ?
                RenderSystem.renderThreadTesselator() : Tesselator.getInstance();
        this.buffer = tesselator.getBuilder();
    }
    
    @Override protected void begin() {
        this.buffer.begin(this.formatMode,this.format);
    }
    
    @Override protected void draw() {
        BufferUploader.drawWithShader(this.buffer.end());
    }

    @Override protected void onVertexEnded(Number[][] numbers) {
        for(int i=0; i<numbers.length; i++)
            pushBuffer(this.format.getElements().get(i),numbers[i]);
        this.buffer.endVertex();
    }
    
    protected void pushBuffer(VertexFormatElement element, Number[] numbers) {
        int count = element.getCount();
        Type type = element.getType();
        Usage usage = element.getUsage();
        if(numbers.length!=count && !(numbers.length==1 && usage==COLOR)) {
            TILRef.logError("Incorrect buffer size {} for VertextFormatElement! (Count={}|Type={}|Usage={}!",
                            numbers.length, count, type, usage);
            return;
        }
        switch(usage) {
            case COLOR: {
                if(numbers.length==1) this.buffer.color(numbers[0].intValue());
                else {
                    Number r = numbers[0];
                    Number g = numbers[1];
                    Number b = numbers[2];
                    Number a = numbers[3];
                    if(r instanceof Integer || r instanceof Long || r instanceof Short)
                        this.buffer.color(r.intValue(),g.intValue(),b.intValue(),a.intValue());
                    else this.buffer.color(r.floatValue(),g.floatValue(),b.floatValue(),a.floatValue());
                }
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