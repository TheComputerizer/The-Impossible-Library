package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

@SuppressWarnings("unused")
public class Tesseract extends ShapeHolder {

    private final Convex3D innerShape;
    private Vector3d maxScale = new Vector3d(1d,1d,1d);
    private int scaleCounter;
    private boolean counterReversal = false;
    public Tesseract(Convex3D shape) {
        super(shape);
        this.innerShape = new Convex3D(shape);
    }

    @Override
    public ShapeHolder setRotations(double x, double y, double z) {
        this.shape.setRotationSpeed(x,y,z);
        this.innerShape.setRotationSpeed(x,y,z);
        return this;
    }

    @Override
    public ShapeHolder setScale(float x, float y, float z) {
        this.maxScale = new Vector3d(x,y,z);
        this.shape.setScale(x,y,z);
        this.innerShape.setScale(x/2f,y/2f,z/2f);
        return this;
    }

    @Override
    public ShapeHolder setColor(float ... colors) {
        this.shape.setColor(colors);
        this.innerShape.setColor(colors);
        return this;
    }

    @Override
    public void render(RenderContext ctx, Vector3d relativeCenter) {
        if(this.scaleCounter>=200) this.counterReversal = true;
        else if(this.scaleCounter<0) this.counterReversal = false;
        if(this.counterReversal) this.scaleCounter--;
        else this.scaleCounter++;
        super.render(ctx,relativeCenter);
        this.innerShape.render(ctx,relativeCenter.add(this.relativePosVec));
    }

    @Override
    public void renderScaledRelative(RenderContext ctx, Vector3d relativeCenter, float s) {
        if(this.scaleCounter>=200) this.counterReversal = true;
        else if(this.scaleCounter<0) this.counterReversal = false;
        if(this.counterReversal) this.scaleCounter--;
        else this.scaleCounter++;
        super.renderScaledRelative(ctx,relativeCenter,s);
        this.innerShape.render(ctx,relativeCenter.add(this.relativePosVec.mul(s)));
    }

    private float getScale(float scale, float factor) {
        return scale-((scale/2f)*factor);
    }
}