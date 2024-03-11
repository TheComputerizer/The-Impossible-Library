package mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import org.joml.Vector2f;
import org.joml.Vector2i;

import javax.annotation.Nullable;
import java.util.Objects;

@Getter
public class WidgetBox extends WidgetShape {

    private int height;
    private int width;
    @Setter private int x; //left
    @Setter private int y; //top

    public WidgetBox(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        getTexture().setSize(width,height);
    }

    public WidgetBox(int x, int y, int width, int height, ColorCache outlineColor, int outlineThickness) {
        super(outlineColor,outlineThickness);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        getTexture().setSize(width,height);
    }

    public WidgetBox(WidgetBox other) {
        super(other);
        this.height = other.height;
        this.width = other.width;
        this.x = other.x;
        this.y = other.y;
    }

    @Override
    public void draw(RenderAPI renderer, float offset) {
        getTexture().render(renderer,this.x,this.y,offset);
        if(getOutlineThickness()>0f)
            RenderHelper.drawBoxOutline(renderer,this.x,this.y,this.width,this.height,getOutlineColor().getColorVF(),
                    getOutlineThickness(),offset);
    }

    @Override
    public Vector2f getCenter() {
        return new Vector2f(this.x+((float)this.width/2),this.y+((float)this.height/2));
    }

    @Override
    public boolean overlaps(@Nullable WidgetShape shape) {
        if(Objects.isNull(shape)) return false;
        Vector2f center = shape.getCenter();
        return shape.overlaps(center.x,center.y) || shape.overlaps(this.x,this.y) ||
                shape.overlaps(this.x+this.width,this.y) ||
                shape.overlaps(this.x+this.width,this.y+this.height) ||
                shape.overlaps(this.x,this.y+this.height);
    }

    @Override
    public boolean overlaps(float x, float y) {
        return x>=this.x && x<=this.x+this.width && y>=this.y && y<=this.y+this.height;
    }

    public void setHeight(int height) {
        this.height = height;
        getTexture().setSize(this.width,height);
    }

    public void setPos(Vector2i pos) {
        setPos(pos.x,pos.y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(Vector2i size) {
        setSize(size.x,size.y);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        getTexture().setSize(width,height);
    }

    public void setWidth(int width) {
        this.width = width;
        getTexture().setSize(width,this.height);
    }
}