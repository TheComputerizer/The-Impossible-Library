package mods.thecomputerizer.theimpossiblelibrary.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.util.MathUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec2f;

import javax.vecmath.Point4f;

public abstract class AbstractRadialButton extends Gui {

    protected final Point4f colors;
    private Vec2f centerPos;
    protected boolean hover;

    protected AbstractRadialButton(Point4f colors) {
        this.colors = colors;
        this.hover = false;
        this.centerPos = new Vec2f(0,0);
    }

    public void setColor(int r, int b, int g, int a) {
        this.colors.set(r, b, g, a);
    }

    protected void setCenterPos(float x, float y) {
        this.centerPos = new Vec2f(x,y);
    }

    protected void setCenterPos(Vec2f newCenter) {
        this.centerPos = newCenter;
    }

    public Vec2f getCenterPos() {
        return this.centerPos;
    }

    protected void drawRadialSection(Vec2f center, float zLevel, Vec2f radius, float startAngle,
                                   float angleDif, int index, int resolution) {
        float angle1 = startAngle+(index/(float)resolution)*angleDif;
        float angle2 = startAngle+((index+1)/(float)resolution)*angleDif;
        Vec2f pos1In = MathUtil.getVertex(center,radius.x,angle1);
        Vec2f pos2In = MathUtil.getVertex(center,radius.x,angle2);
        Vec2f pos1Out = MathUtil.getVertex(center,radius.y,angle1);
        Vec2f pos2Out = MathUtil.getVertex(center,radius.y,angle2);
        if(this.hover) GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,GuiUtil.reverseColors(this.colors));
        else GuiUtil.setBuffer(pos1In,pos2In,pos1Out,pos2Out,zLevel,this.colors);
    }

    protected void playPressSound(SoundHandler handler) {
        handler.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
