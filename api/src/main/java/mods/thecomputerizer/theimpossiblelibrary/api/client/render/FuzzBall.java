package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import org.joml.Vector3d;

import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@SuppressWarnings("unused") @Getter @Setter
public class FuzzBall {
    
    public static FuzzBall from(Supplier<VectorSupplier2D> generator2D, Supplier<VectorSupplier3D> generator3D) {
        return new FuzzBall(generator2D,generator3D,null,null);
    }
    
    public static FuzzBall from(Supplier<VectorSupplier2D> generator2D, Supplier<VectorSupplier3D> generator3D,
            Supplier<Float> widthGenerator) {
        return new FuzzBall(generator2D,generator3D,widthGenerator,null);
    }
    
    public static FuzzBall from(Supplier<VectorSupplier2D> generator2D, Supplier<VectorSupplier3D> generator3D,
            Supplier<Float> widthGenerator, Supplier<ColorCache> colorGenerator) {
        return new FuzzBall(generator2D,generator3D,widthGenerator,colorGenerator);
    }
    
    public static FuzzBall from2D(Supplier<VectorSupplier2D> generator2D) {
        return new FuzzBall(generator2D,null,null,null);
    }
    
    public static FuzzBall from2D(Supplier<VectorSupplier2D> generator2D, Supplier<Float> widthGenerator) {
        return new FuzzBall(generator2D,null,widthGenerator,null);
    }
    
    public static FuzzBall from2D(Supplier<VectorSupplier2D> generator2D, Supplier<Float> widthGenerator,
            Supplier<ColorCache> colorGenerator) {
        return new FuzzBall(generator2D,null,widthGenerator,colorGenerator);
    }
    
    public static FuzzBall from3D(Supplier<VectorSupplier3D> generator3D) {
        return new FuzzBall(null,generator3D,null,null);
    }
    
    public static FuzzBall from3D(Supplier<VectorSupplier3D> generator3D, Supplier<Float> widthGenerator) {
        return new FuzzBall(null,generator3D,widthGenerator,null);
    }
    
    public static FuzzBall from3D(Supplier<VectorSupplier3D> generator3D, Supplier<Float> widthGenerator,
            Supplier<ColorCache> colorGenerator) {
        return new FuzzBall(null,generator3D,widthGenerator,colorGenerator);
    }
    
    protected Supplier<VectorSupplier2D> generator2D;
    protected Supplier<VectorSupplier3D> generator3D;
    protected Supplier<Float> widthGenerator;
    protected Supplier<ColorCache> colorGenerator;
    
    public FuzzBall(Supplier<VectorSupplier2D> generator2D, Supplier<VectorSupplier3D> generator3D,
            Supplier<Float> widthGenerator, Supplier<ColorCache> colorGenerator) {
        this.generator2D = generator2D;
        this.generator3D = generator3D;
        this.widthGenerator = widthGenerator;
        this.colorGenerator = colorGenerator;
    }
    
    public void draw(RenderContext ctx) {
        draw(ctx,VectorHelper.zero3D());
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        float width = Objects.nonNull(this.widthGenerator) ? this.widthGenerator.get() : 1f;
        ColorCache color = Objects.nonNull(this.colorGenerator) ? this.colorGenerator.get() : WHITE;
        draw2D(ctx,center,width,color);
        draw3D(ctx,center,width,color);
    }
    
    public void draw2D(RenderContext ctx) {
        draw2D(ctx,VectorHelper.zero3D());
    }
    
    public void draw2D(RenderContext ctx, Vector3d center) {
        float width = Objects.nonNull(this.widthGenerator) ? this.widthGenerator.get() : 1f;
        ColorCache color = Objects.nonNull(this.colorGenerator) ? this.colorGenerator.get() : WHITE;
        draw2D(ctx,center,width,color);
    }
    
    public void draw2D(RenderContext ctx, Vector3d center, float width, ColorCache color) {
        if(Objects.isNull(this.generator2D)) return;
        ctx.drawOutline(center,this.generator2D.get(),width,color);
    }
    
    public void draw3D(RenderContext ctx) {
        draw3D(ctx,VectorHelper.zero3D());
    }
    
    public void draw3D(RenderContext ctx, Vector3d center) {
        float width = Objects.nonNull(this.widthGenerator) ? this.widthGenerator.get() : 1f;
        ColorCache color = Objects.nonNull(this.colorGenerator) ? this.colorGenerator.get() : WHITE;
        draw3D(ctx,center,width,color);
    }
    
    public void draw3D(RenderContext ctx, Vector3d center, float width, ColorCache color) {
        if(Objects.isNull(this.generator3D)) return;
        ctx.drawOutline(center,this.generator3D.get(),width,color);
    }
}