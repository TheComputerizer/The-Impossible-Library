package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.joml.Vector4i;

@SuppressWarnings("unused") @Getter
public class ColorCache {
    
    public static ColorCache grayscale(float gray) {
        return of(new Vector4f(gray,gray,gray,1f));
    }
    
    public static ColorCache grayscale(float gray, float alpha) {
        return of(new Vector4f(gray,gray,gray,alpha));
    }
    
    @SuppressWarnings("SuspiciousNameCombination")
    public static ColorCache grayscale(Vector2f gray) {
        return of(new Vector4f(gray.x,gray.x,gray.x,gray.y));
    }
    
    public static ColorCache grayscale(int gray) {
        return of(new Vector4i(gray,gray,gray,255));
    }
    
    public static ColorCache grayscale(int gray, int a) {
        return of(new Vector4i(gray,gray,gray,a));
    }
    
    @SuppressWarnings("SuspiciousNameCombination")
    public static ColorCache grayscale(Vector2i gray) {
        return of(new Vector4i(gray.x,gray.x,gray.x,gray.y));
    }
    
    public static ColorCache of(float r, float g, float b) {
        return of(new Vector4f(r,g,b,1f));
    }
    
    public static ColorCache of(float r, float g, float b, float a) {
        return of(new Vector4f(r,g,b,a));
    }
    
    public static ColorCache of(Vector3f colorVF) {
        return of(new Vector4f(colorVF.x,colorVF.y,colorVF.z,1f));
    }
    
    public static ColorCache of(Vector3f colorVF, float alpha) {
        return of(new Vector4f(colorVF.x,colorVF.y,colorVF.z,alpha));
    }
    
    public static ColorCache of(Vector4f colorVF, float alpha) {
        return of(new Vector4f(colorVF.x,colorVF.y,colorVF.z,alpha));
    }
    
    public static ColorCache of(Vector4f colorVF) {
        return new ColorCache(colorVF);
    }
    
    public static ColorCache of(int r, int g, int b) {
        return of(new Vector4i(r,g,b,255));
    }
    
    public static ColorCache of(int r, int g, int b, float a) {
        return of(new Vector4i(r,g,b,(int)(255f*a)));
    }
    
    public static ColorCache of(int r, int g, int b, int a) {
        return of(new Vector4i(r,g,b,a));
    }
    
    public static ColorCache of(Vector3i colorVI) {
        return of(new Vector4i(colorVI.x,colorVI.y,colorVI.z,255));
    }
    
    public static ColorCache of(Vector3i colorVI, int alpha) {
        return of(new Vector4i(colorVI.x,colorVI.y,colorVI.z,alpha));
    }
    
    public static ColorCache of(Vector4i colorVI, int alpha) {
        return of(new Vector4i(colorVI.x,colorVI.y,colorVI.z,alpha));
    }
    
    public static ColorCache of(Vector4i colorVI) {
        return new ColorCache(colorVI);
    }

    private final int colorI;
    private final Vector4f colorVF;
    private final Vector4i colorVI;
    
    public ColorCache(Vector4f colorVF) {
        this(colorVF,ColorHelper.convert(colorVF));
    }
    
    public ColorCache(Vector4i colorVI) {
        this(ColorHelper.convert(colorVI),colorVI);
    }
    
    protected ColorCache(Vector4f colorVF, Vector4i colorVI) {
        this.colorVF = colorVF;
        this.colorVI = colorVI;
        this.colorI = ColorHelper.makeRGBAInt(this.colorVI);
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof ColorCache && this.colorI==((ColorCache)other).colorI;
    }

    public int getIntWithAlpha(float alpha) {
        return ColorHelper.makeRGBAInt(this.colorVF.x,this.colorVF.y,this.colorVF.z,alpha);
    }

    public int getIntWithAlpha(int alpha) {
        return ColorHelper.makeRGBAInt(this.colorVI.x,this.colorVI.y,this.colorVI.z,alpha);
    }

    public Vector4f getVFWithAlpha(float alpha) {
        return new Vector4f(this.colorVF.x,this.colorVF.y,this.colorVF.z,alpha);
    }

    public Vector4f getVFWithAlpha(int alpha) {
        return new Vector4f(this.colorVF.x,this.colorVF.y,this.colorVF.z,((float)alpha)/255f);
    }

    public Vector4i getVIWithAlpha(float alpha) {
        return new Vector4i(this.colorVI.x,this.colorVI.y,this.colorVI.z,(int)(alpha*255f));
    }

    public Vector4i getVIWithAlpha(int alpha) {
        return new Vector4i(this.colorVI.x,this.colorVI.y,this.colorVI.z,alpha);
    }
    
    public float r() {
        return this.colorVF.x;
    }
    
    public float g() {
        return this.colorVF.y;
    }
    
    public float b() {
        return this.colorVF.z;
    }
    
    public float a() {
        return this.colorVF.w;
    }
    
    public ColorCache withAlpha(int alpha) {
        return ColorCache.of(this.colorVI.x,this.colorVI.y,this.colorVI.z,alpha);
    }
    
    public ColorCache withAlpha(float alpha) {
        return ColorCache.of(this.colorVF.x,this.colorVF.y,this.colorVF.z,alpha);
    }
}
