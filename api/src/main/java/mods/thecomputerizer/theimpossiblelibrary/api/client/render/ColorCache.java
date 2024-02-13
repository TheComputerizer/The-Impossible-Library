package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import org.joml.Vector4f;
import org.joml.Vector4i;

@Getter
public class ColorCache {

    private final int colorI;
    private final Vector4f colorVF;
    private final Vector4i colorVI;

    public ColorCache(Vector4f color) {
        this.colorVF = color;
        this.colorVI = ColorHelper.convert(color);
        this.colorI = ColorHelper.makeRGBAInt(this.colorVI);
    }

    public ColorCache(Vector4i color) {
        this.colorVI = color;
        this.colorVF = ColorHelper.convert(color);
        this.colorI = ColorHelper.makeRGBAInt(this.colorVI);
    }

    public ColorCache(float r, float g, float b, float a) {
        this.colorVF = new Vector4f(r, g, b, a);
        this.colorVI = ColorHelper.convert(this.colorVF);
        this.colorI = ColorHelper.makeRGBAInt(this.colorVI);
    }

    public ColorCache(int r, int g, int b, int a) {
        this.colorVI = new Vector4i(r, g, b, a);
        this.colorVF = ColorHelper.convert(this.colorVI);
        this.colorI = ColorHelper.makeRGBAInt(this.colorVI);
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
}
