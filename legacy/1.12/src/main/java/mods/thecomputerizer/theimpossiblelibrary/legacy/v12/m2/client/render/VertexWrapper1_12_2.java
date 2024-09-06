package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.*;

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
        if(element==POSITION_3F)
            this.buffer.pos(numbers[0].doubleValue(),numbers[1].doubleValue(),numbers[2].doubleValue());
        else if(element==COLOR_4UB)
            this.buffer.color(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue(),numbers[3].floatValue());
        else if(element==TEX_2F || element==TEX_2S)
            this.buffer.tex(numbers[0].doubleValue(),numbers[1].doubleValue());
    }
}
