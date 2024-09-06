package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.VertexWrapper1_16_5;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class VertexWrapperFabric1_16_5 extends VertexWrapper1_16_5<VertexFormat,BufferBuilder> {
    
    public VertexWrapperFabric1_16_5(int mode, VertexFormat format, int numVertices, int... vertexSizes) {
        super(mode,format,Tesselator.getInstance().getBuilder(),numVertices,vertexSizes);
    }
    
    @Override protected void begin() {
        this.buffer.begin(this.mode,this.format);
    }
    
    @Override protected void draw() {
        Tesselator.getInstance().end();
    }
    
    @SuppressWarnings("unchecked")
    @Override protected <E> BiFunction<VertexFormat,Integer,E> elementGetter() {
        return (format,index) -> (E)format.getElements().get(index);
    }
    
    protected <E> void pushBuffer(E element, Number[] numbers) {
        if(element==ELEMENT_POSITION)
            this.buffer.vertex(numbers[0].doubleValue(),numbers[1].doubleValue(),numbers[2].doubleValue());
        else if(element==ELEMENT_COLOR)
            this.buffer.color(numbers[0].floatValue(),numbers[1].floatValue(),numbers[2].floatValue(),numbers[3].floatValue());
        else if(element==ELEMENT_UV0 || element==ELEMENT_UV1 || element==ELEMENT_UV2)
            this.buffer.uv(numbers[0].floatValue(),numbers[1].floatValue());
    }
    
    @Override protected Consumer<BufferBuilder> vertexEnder() {
        return BufferBuilder::endVertex;
    }
}