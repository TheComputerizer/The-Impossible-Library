package mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import org.joml.Vector2f;

import javax.annotation.Nullable;
import java.util.Objects;

@Setter
public class WidgetRadial extends WidgetShape {

    private Vector2f center;
    @Getter private double innerRadius;
    @Getter private double outerRadius;
    @Getter private double startAngle; //radians
    @Getter private double endAngle; //radians
    @Getter private int resolution;

    public WidgetRadial(float centerX, float centerY, double innerRadius, double outerRadius) {
        super();
        this.center = new Vector2f(centerX,centerY);
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.resolution = 100;
    }

    public WidgetRadial(float centerX, float centerY, double innerRadius, double outerRadius, ColorCache outlineColor,
                        float outlineThickness) {
        super(outlineColor,outlineThickness);
        this.center = new Vector2f(centerX,centerY);
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.resolution = 100;
    }

    public WidgetRadial(WidgetRadial other) {
        super(other);
        this.center = other.center;
        this.innerRadius = other.innerRadius;
        this.outerRadius = other.outerRadius;
        this.startAngle = other.startAngle;
        this.endAngle = other.endAngle;
        this.resolution = other.resolution;
    }

    @Override
    public void draw(RenderAPI renderer, float offset) {
        RenderHelper.drawCircleSlice(renderer,this.center,this.innerRadius,this.outerRadius,this.startAngle,
                this.endAngle,getTexture().getColorMask().getColorVF(),100,offset);
        if(getOutlineThickness()>0f)
            RenderHelper.drawCircleSlice(renderer,this.center,this.outerRadius-(double)getOutlineThickness(),
                    this.outerRadius,this.startAngle,this.endAngle,getOutlineColor().getColorVF(),100,offset);
    }

    @Override
    public Vector2f getCenter() {
        return this.center;
    }

    /**
     * TODO Figure out a better way to check for overlap
     */
    @Override
    public boolean overlaps(@Nullable WidgetShape shape) {
        return Objects.nonNull(shape) &&
                (shape.overlaps((float)(this.center.x+this.innerRadius),this.center.y) ||
                        shape.overlaps((float)(this.center.x+this.outerRadius),this.center.y) ||
                        shape.overlaps(this.center.x,(float)(this.center.y+this.innerRadius)) ||
                        shape.overlaps(this.center.x,(float)(this.center.y+this.outerRadius)) ||
                        shape.overlaps((float)(this.center.x+this.innerRadius),(float)(this.center.y+this.innerRadius)) ||
                        shape.overlaps((float)(this.center.x+this.innerRadius),(float)(this.center.y+this.outerRadius)) ||
                        shape.overlaps((float)(this.center.x+this.outerRadius),(float)(this.center.y+this.innerRadius)) ||
                        shape.overlaps((float)(this.center.x+this.outerRadius),(float)(this.center.y+this.outerRadius)));
    }

    @Override
    public boolean overlaps(float x, float y) {
        float distance = this.center.distance(x,y);
        return distance>=this.innerRadius && distance<=this.outerRadius;
    }

    public void setAngles(double start, double end) {
        this.startAngle = start;
        this.endAngle = end;
    }

    public void setAnglesDegrees(double startDegrees, double endDegrees) {
        this.startAngle = Math.toRadians(startDegrees);
        this.endAngle = Math.toRadians(endDegrees);
    }
}
