package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector4;

@SuppressWarnings("unused") @Getter
public class ColorCache {
    
    public static ColorCache grayscale(float gray) {
        return of(new Vector4(gray, gray, gray, 1f));
    }
    
    public static ColorCache grayscale(float gray, float alpha) {
        return of(new Vector4(gray,gray,gray,alpha));
    }
    
    public static ColorCache grayscale(Vector2 gray) {
        return of(new Vector4(gray.dX(),gray.dX(),gray.dX(),gray.dY()));
    }
    
    public static ColorCache grayscale(int gray) {
        return of(new Vector4(gray,gray,gray,255));
    }
    
    public static ColorCache grayscale(int gray, int a) {
        return of(new Vector4(gray,gray,gray,a));
    }
    
    public static ColorCache of(float r, float g, float b) {
        return of(new Vector4(r,g,b,1f));
    }
    
    public static ColorCache of(float r, float g, float b, float a) {
        return of(new Vector4(r,g,b,a));
    }
    
    public static ColorCache of(Vector3 colorVF) {
        return of(new Vector4(colorVF.fX(),colorVF.fY(),colorVF.fZ(),1f));
    }
    
    public static ColorCache of(Vector3 colorVF, float alpha) {
        return of(new Vector4(colorVF.fX(),colorVF.fY(),colorVF.fZ(),alpha));
    }
    
    public static ColorCache of(Vector4 colorVF, float alpha) {
        return of(new Vector4(colorVF.fX(),colorVF.fY(),colorVF.fZ(),alpha));
    }
    
    public static ColorCache of(Vector4 colorVF) {
        return new ColorCache(colorVF);
    }
    
    public static ColorCache of(int r, int g, int b) {
        return of(new Vector4(r,g,b,255));
    }
    
    public static ColorCache of(int r, int g, int b, float a) {
        return of(new Vector4(r,g,b,(int)(255f*a)));
    }
    
    public static ColorCache of(int r, int g, int b, int a) {
        return of(new Vector4(r,g,b,a));
    }

    private final int colorI;
    private final Vector4 colorVF;
    private final Vector4 colorVI;
    
    public ColorCache(Vector4 colorVF) {
        this(colorVF,ColorHelper.convertF(colorVF));
    }
    
    protected ColorCache(Vector4 colorVF, Vector4 colorVI) {
        this.colorVF = colorVF;
        this.colorVI = colorVI;
        this.colorI = ColorHelper.makeARGBIntI(this.colorVI);
    }
    
    @Override public boolean equals(Object other) {
        return other instanceof ColorCache && this.colorI==((ColorCache)other).colorI;
    }

    public int getIntWithAlpha(float alpha) {
        return ColorHelper.makeARGBInt(this.colorVF.fX(),this.colorVF.fY(),this.colorVF.fZ(),alpha);
    }

    public int getIntWithAlpha(int alpha) {
        return ColorHelper.makeARGBInt(this.colorVI.iX(),this.colorVI.iY(),this.colorVI.iZ(),alpha);
    }

    public Vector4 getVFWithAlpha(float alpha) {
        return new Vector4(this.colorVF.fX(),this.colorVF.fY(),this.colorVF.fZ(),alpha);
    }

    public Vector4 getVFWithAlpha(int alpha) {
        return new Vector4(this.colorVF.fX(),this.colorVF.fY(),this.colorVF.fZ(),((float)alpha)/255f);
    }

    public Vector4 getVIWithAlpha(float alpha) {
        return new Vector4(this.colorVI.iX(),this.colorVI.iY(),this.colorVI.iZ(),(int)(alpha*255f));
    }

    public Vector4 getVIWithAlpha(int alpha) {
        return new Vector4(this.colorVI.iX(),this.colorVI.iY(),this.colorVI.iZ(),alpha);
    }
    
    public float r() {
        return this.colorVF.fX();
    }
    
    public float g() {
        return this.colorVF.fY();
    }
    
    public float b() {
        return this.colorVF.fZ();
    }
    
    public float a() {
        return this.colorVF.fW();
    }
    
    public ColorCache withAlpha(int alpha) {
        return ColorCache.of(this.colorVI.iX(),this.colorVI.iY(),this.colorVI.iZ(),alpha);
    }
    
    public ColorCache withAlpha(float alpha) {
        return ColorCache.of(this.colorVF.fX(),this.colorVF.fY(),this.colorVF.fZ(),alpha);
    }
}
