package mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import org.joml.Vector2f;

import javax.annotation.Nullable;

@Setter @Getter
public abstract class WidgetShape {

    private final TextureWrapper texture;
    private ColorCache outlineColor;
    private float outlineThickness;

    protected WidgetShape() {
        this.texture = new TextureWrapper();
        this.outlineColor = ColorHelper.BLACK;
        this.outlineThickness = 0f;
    }

    protected WidgetShape(ColorCache outlineColor, float outlineThickness) {
        this.texture = new TextureWrapper();
        this.outlineColor = outlineColor;
        this.outlineThickness = outlineThickness;
    }

    protected WidgetShape(WidgetShape other) {
        this.texture = other.texture;
        this.outlineColor = other.outlineColor;
        this.outlineThickness = other.outlineThickness;
    }

    public abstract void draw(RenderAPI renderer, float offset);
    public abstract Vector2f getCenter();
    public abstract boolean overlaps(@Nullable WidgetShape shape);
    public abstract boolean overlaps(float x, float y);

    public void setColor(ColorCache color) {
        this.texture.setMask(color);
    }

    public void setOutline(ColorCache color, int thickness) {
        this.outlineColor = color;
        this.outlineThickness = thickness;
    }
}