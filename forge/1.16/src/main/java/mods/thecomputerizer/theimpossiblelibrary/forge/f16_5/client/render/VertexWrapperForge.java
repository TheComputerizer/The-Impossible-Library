package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.*;

public class VertexWrapperForge extends VertexWrapper {

    private final VertexFormat format;
    private final BufferBuilder buffer;
    public VertexWrapperForge(int mode, VertexFormat format, int numVertices, int... vertexSizes) {
        super(mode,numVertices,vertexSizes);
        this.format = format;
        this.buffer = Tessellator.getInstance().getBuilder();
    }

    @Override
    protected void begin() {
        this.buffer.begin(this.mode,this.format);
    }

    @Override
    protected void draw() {
        Tessellator.getInstance().end();
    }

    @Override
    protected void onVertexEnded(Number[][] numbers) {
        for(int i=0; i<numbers.length; i++)
            pushBuffer(this.format.getElements().get(i),numbers[i]);
        this.buffer.endVertex();
    }

    private void pushBuffer(VertexFormatElement element, Number[] numbers) {
        if(element==ELEMENT_POSITION)
            this.buffer.vertex(numbers[0].doubleValue(),numbers[1].doubleValue(),numbers[2].doubleValue());
        else if(element==ELEMENT_COLOR)
            this.buffer.color(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue(),numbers[3].floatValue());
        else if(element==ELEMENT_UV0 || element==ELEMENT_UV1 || element==ELEMENT_UV2)
            this.buffer.uv(numbers[0].floatValue(),numbers[1].floatValue());
    }
}