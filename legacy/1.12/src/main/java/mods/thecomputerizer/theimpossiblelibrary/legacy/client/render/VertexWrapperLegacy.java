package mods.thecomputerizer.theimpossiblelibrary.legacy.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.*;

public class VertexWrapperLegacy extends VertexWrapper {

    private final VertexFormat format;
    private final BufferBuilder buffer;
    public VertexWrapperLegacy(VertexFormat format, int numVertices, int... vertexSizes) {
        super(numVertices,vertexSizes);
        this.format = format;
        this.buffer = Tessellator.getInstance().getBuffer();
    }

    @Override
    protected void begin() {
        this.buffer.begin(GL11.GL_QUADS,this.format);
    }

    @Override
    protected void draw() {
        Tessellator.getInstance().draw();
    }

    @Override
    protected void onVertexEnded(Number[][] numbers) {
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
