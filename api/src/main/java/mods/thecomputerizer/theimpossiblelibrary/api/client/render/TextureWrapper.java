package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.joml.Vector4i;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class TextureWrapper {
    
    public static TextureWrapper of(String path) {
        return of(ResourceHelper.getResource(path),WHITE,1f,1d,1d);
    }
    
    public static TextureWrapper of(String modid, String path) {
        return of(ResourceHelper.getResource(modid,path),WHITE,1f,1d,1d);
    }
    
    public static TextureWrapper of(ResourceLocationAPI<?> texture) {
        return of(texture,WHITE,1f,1d,1d);
    }
    
    public static TextureWrapper of(String path, ColorCache colorMask) {
        return of(ResourceHelper.getResource(path),colorMask,1f,1d,1d);
    }
    
    public static TextureWrapper of(String modid, String path, ColorCache colorMask) {
        return of(ResourceHelper.getResource(modid,path),colorMask,1f,1d,1d);
    }
    
    public static TextureWrapper of(ResourceLocationAPI<?> texture, ColorCache colorMask) {
        return of(texture,colorMask,1f,1d,1d);
    }
    
    public static TextureWrapper of(String path, ColorCache colorMask, float alpha) {
        return of(ResourceHelper.getResource(path),colorMask,alpha,1d,1d);
    }
    
    public static TextureWrapper of(String modid, String path, ColorCache colorMask, float alpha) {
        return of(ResourceHelper.getResource(modid,path),colorMask,alpha,1d,1d);
    }
    
    public static TextureWrapper of(ResourceLocationAPI<?> texture, ColorCache colorMask, float alpha) {
        return of(texture,colorMask,alpha,1d,1d);
    }
    
    public static TextureWrapper of(String path, ColorCache colorMask, float alpha, Vector2d maxUV) {
        return of(ResourceHelper.getResource(path),colorMask,alpha,maxUV.x,maxUV.y);
    }
    
    public static TextureWrapper of(String path, ColorCache colorMask, float alpha, double maxU,
            double maxV) {
        return of(ResourceHelper.getResource(path),colorMask,alpha,maxU,maxV);
    }
    
    public static TextureWrapper of(String modid, String path, ColorCache colorMask, float alpha, Vector2d maxUV) {
        return of(ResourceHelper.getResource(modid,path),colorMask,alpha,maxUV.x,maxUV.y);
    }
    
    public static TextureWrapper of(String modid, String path, ColorCache colorMask, float alpha, double maxU,
            double maxV) {
        return of(ResourceHelper.getResource(modid,path),colorMask,alpha,maxU,maxV);
    }
    
    public static TextureWrapper of(ResourceLocationAPI<?> texture, ColorCache colorMask, float alpha, Vector2d maxUV) {
        return of(texture,colorMask,alpha,maxUV.x,maxUV.y);
    }
    
    public static TextureWrapper of(ResourceLocationAPI<?> texture, ColorCache colorMask, float alpha, double maxU,
            double maxV) {
        return new TextureWrapper(texture,colorMask,alpha,maxU,maxV);
    }
    
    public static TextureWrapper of(ResourceLocationAPI<?> texture, ColorCache colorMask, float alpha, double width,
            double height, double maxU, double maxV, double minU, double minV) {
        return of(texture,colorMask,alpha,maxU,maxV).setU(minU,maxU);
    }

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
        this(null,WHITE,1f,1d,1d);
    }

    public TextureWrapper(TextureWrapper other) {
        this(other.texture,other.colorMask,other.alpha,other.maxU,other.maxV);
        this.height = other.height;
        this.minU = other.minU;
        this.minV = other.minV;
        this.width = other.width;
    }
    
    public TextureWrapper(ResourceLocationAPI<?> texture, ColorCache mask, float alpha, double maxU, double maxV) {
        this.alpha = MathHelper.clamp(alpha,0f,1f);
        this.colorMask = mask;
        this.maxU = maxU;
        this.maxV = maxV;
        this.texture = texture;
    }
    
    public void bind(RenderContext ctx) {
        if(Objects.nonNull(this.texture)) ctx.getRenderer().bindTexture(this.texture);
    }
    
    public void bind(RenderAPI renderer) {
        if(Objects.nonNull(this.texture)) renderer.bindTexture(this.texture);
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        if(Objects.nonNull(this.texture))
            ctx.drawTexturedPlane(center,Plane.getBoundedAxis(Y,this.width,this.height,ctx.getHeightRatio()),this);
    }
    
    public ColorCache getColorMask(boolean withAlpha) {
        return withAlpha ? this.colorMask.withAlpha(this.alpha) : this.colorMask;
    }
    
    /**
     x = minV ; y = maxV
     */
    public Vector2d getVectorU() {
        return new Vector2d(this.minU,this.maxU);
    }
    
    /**
     x = minU ; y = maxU
     */
    public Vector2d getVectorV() {
        return new Vector2d(this.minV,this.maxV);
    }
    
    /**
     x = minU ; y = minV ; z = maxU ; w = maxV
     */
    public Vector4d getVectorUV() {
        return new Vector4d(this.minU,this.minV,this.maxU,this.maxV);
    }

    public TextureWrapper setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }
    
    public TextureWrapper setHeight(double height) {
        this.height = height;
        return this;
    }
    
    public TextureWrapper setMask(float r, float g, float b) {
        return setMask(ColorCache.of(r,g,b));
    }
    
    public TextureWrapper setMask(float r, float g, float b, float a) {
        return setMask(ColorCache.of(r,g,b,a));
    }
    
    public TextureWrapper setMask(Vector3f rgb) {
        return setMask(ColorCache.of(rgb));
    }
    
    public TextureWrapper setMask(Vector3f rgb, float alpha) {
        return setMask(ColorCache.of(rgb,alpha));
    }
    
    public TextureWrapper setMask(Vector4f rgba) {
        return setMask(new ColorCache(rgba));
    }
    
    public TextureWrapper setMask(int r, int g, int b) {
        return setMask(ColorCache.of(r,g,b));
    }
    
    public TextureWrapper setMask(int r, int g, int b, int a) {
        return setMask(ColorCache.of(r,g,b,a));
    }
    
    public TextureWrapper setMask(Vector3i rgb) {
        return setMask(ColorCache.of(rgb));
    }
    
    public TextureWrapper setMask(Vector3i rgb, int alpha) {
        return setMask(ColorCache.of(rgb,alpha));
    }
    
    public TextureWrapper setMask(Vector4i rgba) {
        return setMask(new ColorCache(rgba));
    }

    public TextureWrapper setMask(ColorCache mask) {
        this.colorMask = mask;
        return this;
    }
    
    public TextureWrapper setMaxU(double max) {
        this.maxU = max;
        return this;
    }
    
    public TextureWrapper setMaxV(double max) {
        this.maxV = max;
        return this;
    }
    
    public TextureWrapper setMinU(double max) {
        this.minU = max;
        return this;
    }
    
    public TextureWrapper setMinV(double max) {
        this.minV = max;
        return this;
    }

    public TextureWrapper setSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    
    public TextureWrapper setTexture(ResourceLocationAPI<?> texture) {
        this.texture = texture;
        return this;
    }
    
    public TextureWrapper setU(Vector2d minMax) {
        return setU(minMax.x,minMax.y);
    }

    public TextureWrapper setU(double min, double max) {
        this.minU = min;
        this.maxU = max;
        return this;
    }
    
    public TextureWrapper setUV(Vector4d uv) {
        return setUV(uv.x,uv.y,uv.z,uv.w);
    }
    
    public TextureWrapper setUV(double minU, double minV, double maxU, double maxV) {
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
        return this;
    }
    
    public TextureWrapper setV(Vector2d minMax) {
        return setV(minMax.x,minMax.y);
    }

    public TextureWrapper setV(double min, double max) {
        this.minV = min;
        this.maxV = max;
        return this;
    }
    
    public TextureWrapper setWidth(double width) {
        this.width = width;
        return this;
    }
}