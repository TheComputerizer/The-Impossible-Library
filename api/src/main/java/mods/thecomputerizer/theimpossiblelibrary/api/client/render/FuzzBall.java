package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;

import java.util.function.Supplier;

@Getter
public class FuzzBall {
    
    private final Supplier<VectorSupplier2D> generator2D;
    private final Supplier<VectorSupplier3D> generator3D;
    private final Supplier<Float> widthGenerator;
    @Setter private Supplier<ColorCache> colorGenerator;
    
    public FuzzBall(Supplier<VectorSupplier2D> generator2D, Supplier<VectorSupplier3D> generator3D,
            Supplier<Float> widthGenerator, Supplier<ColorCache> colorGenerator) {
        this.generator2D = generator2D;
        this.generator3D = generator3D;
        this.widthGenerator = widthGenerator;
        this.colorGenerator = colorGenerator;
    }
    
    public void draw2D(RenderContext ctx) {
        ctx.drawOutline(this.generator2D.get(),this.widthGenerator.get(),this.colorGenerator.get());
    }
    
    public void draw3D(RenderContext ctx) {
        ctx.drawOutline(this.generator3D.get(),this.widthGenerator.get(),this.colorGenerator.get());
    }
}