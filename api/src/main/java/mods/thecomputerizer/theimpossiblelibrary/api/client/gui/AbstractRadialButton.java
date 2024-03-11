package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Objects;

public abstract class AbstractRadialButton {

    protected final Vector4f colors;
    @Setter protected SoundAPI clickSound;
    @Getter private Vector2f centerPos;
    protected boolean hover;

    protected AbstractRadialButton(Vector4f colors) {
        this.colors = colors;
        this.hover = false;
        this.centerPos = new Vector2f(0,0);
    }

    public void setColor(int r, int b, int g, int a) {
        this.colors.set(r, b, g, a);
    }

    protected void setCenterPos(float x, float y) {
        this.centerPos = new Vector2f(x,y);
    }

    protected void setCenterPos(Vector2f newCenter) {
        this.centerPos = newCenter;
    }

    protected void drawRadialSection(RenderAPI renderer, Vector2f center, float offset, Vector2f radius, float startAngle,
                                     float angleDif, int index, int resolution) {
        float angle1 = startAngle+(index/(float)resolution)*angleDif;
        float angle2 = startAngle+((index+1)/(float)resolution)*angleDif;
        Vector2f pos1In = MathHelper.getVertex(center,radius.x(),angle1);
        Vector2f pos2In = MathHelper.getVertex(center,radius.x(),angle2);
        Vector2f pos1Out = MathHelper.getVertex(center,radius.y(),angle1);
        Vector2f pos2Out = MathHelper.getVertex(center,radius.y(),angle2);
        Vector4f hoverColor = this.hover ? ColorHelper.reverseColors(this.colors) : this.colors;
        RenderHelper.setBuffer(renderer,pos1In,pos2In,pos1Out,pos2Out,offset,hoverColor);
    }

    protected void playPressSound() {
        if(Objects.nonNull(this.clickSound)) this.clickSound.play(1f);
    }
}