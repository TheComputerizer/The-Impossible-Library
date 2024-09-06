package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class VertexWrapper1_16_5<F,B> extends VertexWrapper {
    
    protected final F format;
    protected final B buffer;
    
    protected VertexWrapper1_16_5(int mode, F format, B buffer, int numVertices, int ... vertexSizes) {
        super(mode,numVertices,vertexSizes);
        this.format = format;
        this.buffer = buffer;
    }
    
    protected abstract <E> BiFunction<F,Integer,E> elementGetter();

    @Override protected void onVertexEnded(Number[][] numbers) {
        for(int i=0; i<numbers.length; i++)
            pushBuffer(elementGetter().apply(this.format,i),numbers[i]);
        vertexEnder().accept(this.buffer);
    }

    protected abstract <E> void pushBuffer(E element, Number[] numbers);
    protected abstract Consumer<B> vertexEnder();
}