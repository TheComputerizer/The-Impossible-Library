package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import org.joml.Vector3d;

import java.util.Random;

public class TickableColumn extends Column implements ITickableGeometry<TickableColumn> {

    private boolean isInitialized = false;
    private int maxTime;
    private int time;

    public TickableColumn(Random random, Vector3d relativeBottom, double height, double radius, double spacing) {
        super(random,relativeBottom,height,radius,spacing);
    }

    @Override
    public void render(RenderAPI renderer, Vector3d relativeCenter) {
        if(this.isInitialized) super.render(renderer,relativeCenter);
    }

    @Override
    public TickableColumn setTime(int time) {
        this.maxTime = time;
        this.time = time;
        return this;
    }

    @Override
    public TickableColumn init() {
        this.isInitialized = true;
        GeometryHelper.TICKABLE_RENDERS.add(this);
        return this;
    }

    @Override
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    public void onTick() {
        if(this.time--<=0) reset();
    }

    @Override
    public TickableColumn reset() {
        this.time = this.maxTime;
        this.isInitialized = false;
        return this;
    }
}