package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings({"unused", "UnusedReturnValue"}) @Getter
public class TextureWrapper {

    private ResourceLocationAPI<?> texture;
    private float alpha;
    private ColorCache colorMask;
    private double minU;
    private double minV;
    private double maxU;
    private double maxV;
    private double width;
    private double height;

    public TextureWrapper() {
        this.alpha = 1f;
        this.colorMask = WHITE;
    }

    public TextureWrapper(TextureWrapper other) {
        this.texture = other.texture;
        this.alpha = other.alpha;
        this.colorMask = other.colorMask;
        this.minU = other.minU;
        this.minV = other.minV;
        this.maxU = other.maxU;
        this.maxV = other.maxV;
        this.width = other.width;
        this.height = other.height;
    }
    
    public void bind(RenderAPI renderer) {
        renderer.bindTexture(this.texture);
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        ctx.drawTexturedPlane(center,Plane.getBoundedAxis(Y,this.width,this.height),this);
    }

    public TextureWrapper setTexture(ResourceLocationAPI<?> texture) {
        this.texture = texture;
        return this;
    }

    public TextureWrapper setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public TextureWrapper setMask(ColorCache mask) {
        this.colorMask = mask;
        return this;
    }

    public TextureWrapper setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public TextureWrapper setU(float min, float max) {
        this.minU = min;
        this.maxU = max;
        return this;
    }

    public TextureWrapper setV(float min, float max) {
        this.minV = min;
        this.maxV = max;
        return this;
    }
}