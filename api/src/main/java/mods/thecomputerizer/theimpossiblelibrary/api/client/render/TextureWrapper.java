package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import java.util.Objects;

@Getter
public class TextureWrapper {

    private ResourceLocationAPI<?> texture;
    private float alpha;
    private ColorCache colorMask;
    private float minU;
    private float minV;
    private float maxU;
    private float maxV;
    private int width;
    private int height;

    public TextureWrapper() {
        this.alpha = 1f;
        this.colorMask = new ColorCache(0f,0f,0f,0f);
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

    public void render(RenderAPI render, float x, float y, float offset) {
        if(Objects.nonNull(this.texture))
            RenderHelper.drawTexturedRect(render,x,y,this.width,this.height,this.alpha,this.texture,this.minU,
                    this.maxU,this.minV,this.maxV);
        if(this.colorMask.getColorVF().w>0f)
            RenderHelper.drawBox(render,x,y,this.width,this.height,this.colorMask.getColorVF(),offset);
    }
}