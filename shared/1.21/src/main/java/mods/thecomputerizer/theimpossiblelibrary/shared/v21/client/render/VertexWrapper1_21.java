package mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.render;

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
import static com.mojang.blaze3d.vertex.VertexFormatElement.UV1;
import static com.mojang.blaze3d.vertex.VertexFormatElement.Usage.COLOR;

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
        int count = element.count();
        Type type = element.type();
        Usage usage = element.usage();
        if(numbers.length!=count && !(numbers.length==1 && usage==COLOR)) {
            TILRef.logError("Incorrect buffer size {} for VertextFormatElement! (Count={}|Type={}|Usage={}!",
                            numbers.length, count, type, usage);
            return;
        }
        switch(usage) {
            case COLOR: {
                if(numbers.length==1) this.buffer.setColor(numbers[0].intValue());
                else {
                    Number r = numbers[0];
                    Number g = numbers[1];
                    Number b = numbers[2];
                    Number a = numbers[3];
                    if(r instanceof Integer || r instanceof Long || r instanceof Short)
                        this.buffer.setColor(r.intValue(),g.intValue(),b.intValue(),a.intValue());
                    else this.buffer.setColor(r.floatValue(),g.floatValue(),b.floatValue(),a.floatValue());
                }
                return;
            }
            case NORMAL: {
                this.buffer.setNormal(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue());
                return;
            }
            case POSITION: {
                this.buffer.addVertex(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue());
                return;
            }
            case UV: {
                Number u = numbers[0];
                Number v = numbers[1];
                if(type==FLOAT) this.buffer.setUv(u.floatValue(),v.floatValue());
                else if(element==UV1) this.buffer.setUv1(u.intValue(),v.intValue());
                else this.buffer.setUv2(u.intValue(),v.intValue());
                return;
            }
            default: TILRef.logError("Unsupported VertextFormatElement (Count={}|Type={}|Usage={}!",count,type,usage);
        }
    }
}