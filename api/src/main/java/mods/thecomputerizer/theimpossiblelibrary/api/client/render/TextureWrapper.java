package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import org.joml.Vector3d;
import org.joml.Vector4d;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class TextureWrapper {

    @Getter private ResourceLocationAPI<?> texture;
    @Getter private float alpha;
    private ColorCache colorMask;
    @Getter private double minU;
    @Getter private double minV;
    @Getter private double maxU;
    @Getter private double maxV;
    @Getter private double width;
    @Getter private double height;

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
    
    public ColorCache getColorMask(boolean withAlpha) {
        return withAlpha ? this.colorMask.withAlpha(this.alpha) : this.colorMask;
    }
    
    /**
     x = minU ; y = minV ; z = maxU ; w = maxV
     */
    public Vector4d getVectorUV() {
        return new Vector4d(this.minU,this.minV,this.maxU,this.maxV);
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