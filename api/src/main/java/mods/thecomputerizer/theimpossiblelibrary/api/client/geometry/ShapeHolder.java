package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ShapeHolder {

    protected final Convex3D shape;
    private final List<ShapeHolder> childHolders;
    private boolean isMoving;
    protected Vector3d relativePosVec;
    private Vector3d dirVec;

    public ShapeHolder(Convex3D shape) {
        this.shape = shape;
        this.childHolders = new ArrayList<>();
        this.relativePosVec = VectorHelper.ZERO_3D;
    }

    public void addChild(Convex3D shape, Consumer<ShapeHolder> holderSettings) {
        ShapeHolder childHolder = new ShapeHolder(shape);
        holderSettings.accept(childHolder);
        addChild(childHolder);
    }

    public void addChild(ShapeHolder childHolder) {
        this.childHolders.add(childHolder);
    }

    public void startMoving() {
        this.isMoving = true;
    }

    public ShapeHolder setRelativePosition(Vector3d relativePos) {
        this.relativePosVec = relativePos;
        return this;
    }

    public void setRelativeBottom() {
        setRelativePosition(new Vector3d(0d,this.shape.getScaledHeight(),0d));
    }

    public ShapeHolder setDirection(Vector3d dirVec) {
        this.dirVec = dirVec;
        return this;
    }

    public ShapeHolder setRotations(double x, double y, double z) {
        this.shape.setRotationSpeed(x,y,z);
        return this;
    }

    public ShapeHolder setScale(float x, float y, float z) {
        this.shape.setScale(x,y,z);
        return this;
    }

    public ShapeHolder setColor(float ... colors) {
        this.shape.setColor(colors);
        return this;
    }

    public void stopMoving() {
        this.isMoving = false;
    }

    public Vector3d getRelativePosition() {
        return this.relativePosVec;
    }

    public void render(RenderAPI renderer, Vector3d relativeCenter) {
        this.shape.render(renderer,relativeCenter.add(this.relativePosVec));
        if(this.isMoving) setRelativePosition(this.relativePosVec.add(this.dirVec));
        for(ShapeHolder child : this.childHolders) child.render(renderer,relativeCenter);
    }

    public void renderScaledRelative(RenderAPI renderer, Vector3d relativeCenter, float scale) {
        this.shape.render(renderer,relativeCenter.add(this.relativePosVec.mul(scale)));
        if(this.isMoving) setRelativePosition(this.relativePosVec.add(this.dirVec));
        for(ShapeHolder child : this.childHolders) child.render(renderer,relativeCenter);
    }
}